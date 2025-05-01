package de.schulte.smartbar.backoffice.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class MessageConsumer {

    @Incoming("my-channel")
    @Outgoing("uppercase-channel")
    public String consumeMessage(String content) {
        System.out.printf("%s: %s%n", this.getClass().getSimpleName(), content);
        return content.toUpperCase();
    }

}
