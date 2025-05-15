package de.schulte.smartbar.backoffice.messaging;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class UppercaseConsumer {

    @Incoming("uppcase-channel")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
    public Uni<Void> consume(final Message<String> message) {
        System.out.printf("Message '%s' consumed in %s %n", message, this.getClass().getSimpleName());
        return Uni.createFrom().voidItem();
    }
}
