package de.schulte.smartbar.orderclient.orders;

import de.schulte.smartbar.orderclient.api.OrdersApi;
import de.schulte.smartbar.orderclient.api.model.PlaceOrderRequest;
import de.schulte.smartbar.orderclient.login.LoginService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class OrdersResource implements OrdersApi {

    private final LoginService loginService;

    @Inject
    public OrdersResource(LoginService loginService) {
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
        return order.persist().map(o -> Response.ok().build());
    }

    private Uni<Response> notAllowed() {
        return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
