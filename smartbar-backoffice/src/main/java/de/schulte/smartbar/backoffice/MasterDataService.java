package de.schulte.smartbar.backoffice;

import io.quarkus.arc.Unremovable;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

@ApplicationScoped
@Unremovable
public class MasterDataService {

    private final MutinyEmitter<EntityChangedEvent> emitter;

    public MasterDataService(@Channel("masterdata-changed-channel") MutinyEmitter<EntityChangedEvent> emitter) {
        this.emitter = emitter;
    }

    public void fireChangedEvent(BaseEntity baseEntity) {
        System.out.println("MasterDataService.fireChangedEvent");

        final var payload = new EntityChangedEvent(baseEntity.getId(),
                baseEntity.getClass().getSimpleName());
        final var kafkaMetadata = OutgoingKafkaRecordMetadata.builder()
                .withHeaders(new RecordHeaders().add("my-header", "my-value".getBytes()))
                .withKey(baseEntity.getClass().getSimpleName())
                .build();
        final var message = Message.of(payload, Metadata.of(kafkaMetadata));
        emitter.sendMessageAndAwait(message);
    }

}
