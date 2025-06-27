package de.schulte.smartbar.orderclient.orders;

import de.schulte.smartbar.orderclient.api.OrdersApi;
import de.schulte.smartbar.orderclient.api.model.PlaceOrderRequest;
import de.schulte.smartbar.orderclient.login.LoginService;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;

import java.util.concurrent.CompletionStage;

public class OrdersResource implements OrdersApi {

    private final MutinyEmitter<Order> ordersEventEmitter;

    private final LoginService loginService;

    @Inject
    public OrdersResource(@Channel("order-placed-channel") MutinyEmitter<Order> ordersEventEmitter, LoginService loginService) {
        this.ordersEventEmitter = ordersEventEmitter;
        this.loginService = loginService;
    }

    @Override
    public CompletionStage<Response> getOrderDetails(String loginToken, String orderId) {
        return null;
    }

    @Override
    public CompletionStage<Response> listOrders(String loginToken) {
        return null;
    }

    @Override
    public CompletionStage<Response> placeOrder(String loginToken, PlaceOrderRequest placeOrderRequest) {
        return this.loginService.getTableIdByToken(loginToken).chain(tableId -> {
            if (tableId != null) {
                return this.mapAndStore(placeOrderRequest, tableId);
            } else {
                return this.notAllowed();
            }
        }).subscribeAsCompletionStage();
    }

    private Uni<Response> mapAndStore(PlaceOrderRequest placeOrderRequest, String tableId) {
        final var order = new Order();
        order.orderPositions = placeOrderRequest.getItems().stream().map(item ->
                new OrderPosition(item.getArticleId(), item.getQuantity(), item.getPrice())).toList();
        order.tableId = tableId;
        return order.<Order>persist().flatMap(this::sendOrderPlacedEvent).map(v -> Response.ok().build());
    }

    private Uni<Void> sendOrderPlacedEvent(Order order) {
        return this.ordersEventEmitter.send(order);
    }

    private Uni<Response> notAllowed() {
        return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
