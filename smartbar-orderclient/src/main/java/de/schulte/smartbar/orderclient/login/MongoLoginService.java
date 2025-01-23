package de.schulte.smartbar.orderclient.login;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class MongoLoginService implements LoginService {

    private final ReactiveMongoClient mongoClient;

    @Inject
    public MongoLoginService(ReactiveMongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public Uni<String> createNewLogin(String tableId) {
        final var token = UUID.randomUUID().toString();
        final var expiresAt = Instant.now().plusSeconds(60);
        final var document = new Document().append("tableId", tableId)
                                           .append("token", token)
                                           .append("expiresAt", expiresAt);
        return loginCollection().insertOne(document).map(d -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(String tableId) {
        return loginCollection().find(new Document().append("tableId", tableId))
                                .collect()
                                .asList()
                                .map(documents -> !documents.isEmpty());
    }

    private ReactiveMongoCollection<Document> loginCollection() {
        return mongoClient.getDatabase("logins").getCollection("logins-timed");
    }
}
