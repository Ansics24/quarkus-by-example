package de.schulte.smartbar.orderclient.login.mongo;

import de.schulte.smartbar.orderclient.login.LoginService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class MongoLoginService implements LoginService {

    @Override
    public Uni<String> createNewLogin(String tableId) {
        final var token = UUID.randomUUID().toString();
        final var expiresAt = Instant.now().plusSeconds(60);
        final var login = new Login(tableId, expiresAt, token);
        return login.persist().map(ignored -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(String tableId) {
        return Login.findByTableId(tableId).map(Objects::nonNull);
    }

}
