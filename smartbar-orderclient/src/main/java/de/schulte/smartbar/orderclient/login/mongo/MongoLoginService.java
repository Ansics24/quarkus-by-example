package de.schulte.smartbar.orderclient.login.mongo;

import de.schulte.smartbar.orderclient.login.LoginService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class MongoLoginService implements LoginService {

    private final LoginRepository loginRepository;

    @Inject
    public MongoLoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public Uni<String> createNewLogin(String tableId) {
        final var token = UUID.randomUUID().toString();
        final var expiresAt = Instant.now().plusSeconds(60);
        final var login = new Login(tableId, token, expiresAt);
        return loginRepository.persist(login).map(ignored -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(String tableId) {
        return loginRepository.findByTableId(tableId).map(Objects::nonNull);
    }

}
