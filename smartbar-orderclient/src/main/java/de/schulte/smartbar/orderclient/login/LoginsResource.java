package de.schulte.smartbar.orderclient.login;

import de.schulte.smartbar.orderclient.api.LoginsApi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.concurrent.CompletionStage;

public class LoginsResource implements LoginsApi {

    private final MenuApiClient menuApiClient;

    private final MenuMapper menuMapper;

    private final LoginService loginService;

    @Inject
    public LoginsResource(@RestClient MenuApiClient menuApiClient, MenuMapper menuMapper, LoginService loginService) {
        this.menuApiClient = menuApiClient;
        this.menuMapper = menuMapper;
        this.loginService = loginService;
    }

    @Override
    public CompletionStage<Response> login(String tableId) {
        final Uni<Response> chain = Uni.createFrom()
                                       .completionStage(menuApiClient.getMenu()
                                                                     .thenApply(menuMapper::mapToLoginResonse))
                                       .flatMap(reponse -> this.loginService.createNewLogin(tableId)
                                                                            .onItem()
                                                                            .invoke(reponse::setLoginToken)
                                                                            .onItem()
                                                                            .transform(token -> Response.ok(reponse)
                                                                                                        .build()));

        return loginService.hasLoginByTableId(tableId)
                           .chain(hasLogin -> hasLogin ? getLoginAlreadyExists() : chain)
                           .subscribeAsCompletionStage();
    }

    public Uni<Response> getLoginAlreadyExists() {
        return Uni.createFrom().item(Response.status(Response.Status.CONFLICT).build());
    }

}
