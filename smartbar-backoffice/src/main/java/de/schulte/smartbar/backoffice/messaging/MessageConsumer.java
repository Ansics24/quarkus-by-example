package de.schulte.smartbar.backoffice.messaging;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class MessageConsumer {

    @Incoming("my-channel")
    @Outgoing("uppcase-channel")
    public Uni<Message<String>> consume(final Message<String> message) {
        final var payload = message.getPayload();
        final var currentMillis = message.getMetadata(Long.class);
        System.out.printf("Message '%s' consumed in %s %n", payload, this.getClass().getSimpleName());
        currentMillis.ifPresent(System.out::println);
        return Uni.createFrom().item(Message.of(payload.toUpperCase(), () -> message.ack()));
    }

}
