package de.schulte.smartbar.orderclient.login;

import io.quarkus.redis.client.RedisClientName;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import io.vertx.redis.client.RedisAPI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class RedisLoginService implements LoginService {

    private final ReactiveValueCommands<String, String> tokens;

    private final ReactiveKeyCommands<String> keys;

    private final RedisAPI redisApi;

    @Inject
    public RedisLoginService(@RedisClientName("logins") ReactiveRedisDataSource dataSource, @RedisClientName("logins") RedisAPI redisApi) {
        this.tokens = dataSource.value(String.class);
        this.keys = dataSource.key();
        this.redisApi = redisApi;
    }

    @Override
    public Uni<String> createNewLogin(final String tableId) {
        final var token = UUID.randomUUID().toString();
        //return tokens.set(tableId, token).chain(v-> keys.expire(tableId, 20)).map(v -> token);

        return Uni.createFrom()
                  .completionStage(redisApi.set(List.of(tableId, token, "EX", "20"))
                                           .map(r -> token)
                                           .toCompletionStage());
    }

    @Override
    public Uni<Boolean> hasLogin(final String tableId) {
        return Uni.createFrom().completionStage(redisApi.get(tableId).map(Objects::nonNull).toCompletionStage());
        //return keys.exists(tableId);
    }

}
