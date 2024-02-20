package de.schulte.smartbar.backoffice.articles;

import de.schulte.smartbar.backoffice.api.ArticlesApi;
import de.schulte.smartbar.backoffice.api.model.ApiArticle;
import de.schulte.smartbar.backoffice.categories.CategoriesService;
import de.schulte.smartbar.backoffice.categories.Category;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class ArticlesResource implements ArticlesApi {

    private final ArticlesService articlesService;

    private final CategoriesService categoriesService;

    @Inject
    public ArticlesResource(ArticlesService articlesService, CategoriesService categoriesService) {
        this.articlesService = articlesService;
        this.categoriesService = categoriesService;
    }

    @Override
    public Response articlesArticleIdDelete(Long articleId) {
        final Optional<Article> article = articlesService.deleteById(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }

    @Override
    public Response articlesArticleIdGet(Long articleId) {
        final Optional<Article> article = articlesService.getById(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mapArticleToApiArticle(article.get())).build();
    }

    @Override
    public Response articlesArticleIdPut(Long articleId, ApiArticle apiArticle) {
        final Optional<Article> existingArticle = articlesService.getById(articleId);
        if (existingArticle.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Article article = existingArticle.get();
        mapApiArticleToArticle(apiArticle, article);
        articlesService.update(article);
        return Response.ok().build();
    }

    @Override
    public Response articlesGet() {
        final List<Article> articles = articlesService.listAll();
        return Response.ok(articles.stream().map(this::mapArticleToApiArticle).toList())
                       .build();
    }

    @Override
    public Response articlesPost(Long xCategoryId, ApiArticle apiArticle) {
        final Optional<Category> category = categoriesService.getById(xCategoryId);
        if(category.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Article article = new Article();
        mapApiArticleToArticle(apiArticle, article);
        article.setCategory(category.get());
        final Article persitedArticle = articlesService.persit(article);
        return Response.created(URI.create("/articles/" + persitedArticle.getId())).build();
    }

    private void mapApiArticleToArticle(ApiArticle apiArticle, Article article) {
        article.setName(apiArticle.getName());
        article.setDescription(apiArticle.getDescription());
        article.setPrice(apiArticle.getPrice());
        article.setPictureBase64(apiArticle.getPicture());
    }

    private ApiArticle mapArticleToApiArticle(Article article) {
        final ApiArticle apiArticle = new ApiArticle();
        apiArticle.setDescription(article.getDescription());
        apiArticle.setName(article.getName());
        apiArticle.setPicture(article.getPictureBase64());
        apiArticle.setPrice(article.getPrice());
        apiArticle.setId(article.getId());
        return apiArticle;
    }

}
