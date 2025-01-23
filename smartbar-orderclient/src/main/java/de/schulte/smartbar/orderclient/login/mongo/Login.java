package de.schulte.smartbar.orderclient.login.mongo;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Instant;

@MongoEntity(database = "logins", collection = "logins-timed")
public class Login extends ReactivePanacheMongoEntity {

    @BsonProperty("tableNumber")
    public String tableId;

    public String token;

    public Instant expiresAt;

    public Login() {
    }

    public Login(String tableId, Instant expiresAt, String token) {
        this.tableId = tableId;
        this.expiresAt = expiresAt;
        this.token = token;
    }

    public static Uni<Login> findByTableId(String tableId) {
        return find("tableNumber", tableId).firstResult();
    }
}
