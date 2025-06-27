package de.schulte.smartbar.backoffice.turnover;

import de.schulte.smartbar.backoffice.Order;
import de.schulte.smartbar.backoffice.OrderPosition;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.math.BigDecimal;

@ApplicationScoped
@Unremovable
public class TurnoverService {

    @Incoming("turnover-db-channel")
    @Transactional
    public void onArticleOrdered(final Order order) {
        System.out.println("onArticleOrdered (Write Turnover)");
        order.positions().forEach(this::writeTurnover);
    }

    private void writeTurnover(final OrderPosition position) {
        final Turnover turnover = new Turnover();
        turnover.articleId = position.articleId();
        turnover.quantity = position.quantity();
        turnover.turnoverTotalValue = position.price().multiply(new BigDecimal(position.quantity()));
        turnover.persist();
    }

}
