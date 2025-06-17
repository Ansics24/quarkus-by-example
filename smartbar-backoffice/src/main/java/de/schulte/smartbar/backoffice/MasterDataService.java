package de.schulte.smartbar.backoffice;

import io.quarkus.arc.Unremovable;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
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

        String simpleName = baseEntity.getClass().getSimpleName();
        final var payload = new EntityChangedEvent(baseEntity.getId(),
                simpleName);
        final var metadata = OutgoingRabbitMQMetadata.builder().withRoutingKey(simpleName).build();
        final var message = Message.of(payload, Metadata.of(metadata));
        emitter.sendMessageAndAwait(message);
    }

}
