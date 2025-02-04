package de.schulte.smartbar.orderclient.login.dynamo;

import de.schulte.smartbar.orderclient.login.LoginService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class DynamoLoginsService implements LoginService {

    private DynamoDbAsyncClient dynamoDbClient;

    @Inject
    public DynamoLoginsService(DynamoDbAsyncClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public Uni<String> createNewLogin(String tableId) {
        final var token = UUID.randomUUID().toString();
        final var expiresAt = Instant.now().plusSeconds(60).toEpochMilli();

        final Map<String, AttributeValue> login = new HashMap<>();
        login.put("tableId", AttributeValue.builder().s(tableId).build());
        login.put("token", AttributeValue.builder().s(token).build());
        login.put("expiresAt", AttributeValue.builder().n(Long.toString(expiresAt)).build());
        return Uni.createFrom()
                  .completionStage(dynamoDbClient.putItem(PutItemRequest.builder()
                                                                        .tableName("Logins")
                                                                        .item(login)
                                                                        .build()))
                  .map(ignored -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(String tableId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("tableId", AttributeValue.builder().s(tableId).build());
        final var existingLogin = dynamoDbClient.getItem(GetItemRequest.builder().tableName("Logins").key(key).build());
        return Uni.createFrom().completionStage(existingLogin).map(GetItemResponse::hasItem);
    }

}
