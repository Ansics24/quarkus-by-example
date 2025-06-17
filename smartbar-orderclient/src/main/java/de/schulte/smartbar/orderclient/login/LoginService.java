package de.schulte.smartbar.orderclient.login;

import io.smallrye.mutiny.Uni;

public interface LoginService {

    Uni<String> createNewLogin(final String tableId);

    Uni<Boolean> hasLoginByTableId(final String tableId);

    Uni<String> getTableIdByToken(final String loginToken);
}
