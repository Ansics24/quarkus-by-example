package de.schulte.smartbar.orderclient.login.mongo;

import de.schulte.smartbar.orderclient.login.LoginService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
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
    public Uni<Boolean> hasLoginByTableId(String tableId) {
        return loginRepository.findByTableId(tableId).map(Objects::nonNull);
    }

    @Override
    public Uni<String> getTableIdByToken(String loginToken) {
        return loginRepository.findByLoginToken(loginToken).map(this::getTableIdIfValidOrElseNull);
    }

    private String getTableIdIfValidOrElseNull(Login login) {
        final var valid = login != null && login.getExpiresAt().isAfter(Instant.now());
        return valid ? login.getTableId() : null;
    }

}
