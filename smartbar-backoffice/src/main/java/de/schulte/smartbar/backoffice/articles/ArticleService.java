package de.schulte.smartbar.backoffice.articles;

import de.schulte.smartbar.backoffice.Order;
import de.schulte.smartbar.backoffice.OrderPosition;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDate;
import java.util.Optional;

@ApplicationScoped
@Unremovable
public class ArticleService {

    @Incoming("article-ordered-channel")
    @Transactional
    public void onArticleOrdered(final Order order) {
        System.out.println("onArticleOrdered (Update Article)");
        order.positions().forEach(this::processOrderPosition);
    }

    private void processOrderPosition(final OrderPosition orderPosition) {
        Article.<Article>findByIdOptional(orderPosition.articleId()).ifPresent(
                a -> this.updateArticleData(a, orderPosition.quantity()));
    }

    private void updateArticleData(final Article article, final Integer quantityOrdered) {
        article.lastOrdered = LocalDate.now();
        article.timesOrdered = Optional.ofNullable(article.timesOrdered).orElse(0) + quantityOrdered;
    }

}
