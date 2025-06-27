package de.schulte.smartbar.backoffice.articles;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

public interface ArticlesResource extends PanacheEntityResource<Article, Long> {

    @GET
    @Path("/name")
    @Produces({MediaType.APPLICATION_JSON})
    default List<PanacheEntityBase> getByNameContaining(@QueryParam("s") String fragment) {
        return Article.list("#Article.nameContaining", fragment);
    }

}
