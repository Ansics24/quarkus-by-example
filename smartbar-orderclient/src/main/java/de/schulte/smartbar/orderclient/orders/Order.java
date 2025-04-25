package de.schulte.smartbar.orderclient.orders;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

@MongoEntity(database = "smartbar-oc", collection = "orders")
public class Order extends ReactivePanacheMongoEntity {

    public String tableId;

    @BsonProperty("positions")
    public List<OrderPosition> orderPositions;

}
