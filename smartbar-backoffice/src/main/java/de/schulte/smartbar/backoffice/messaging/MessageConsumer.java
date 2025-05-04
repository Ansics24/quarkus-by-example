package de.schulte.smartbar.backoffice.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class MessageConsumer {

    @Incoming("my-channel")
    @Outgoing("uppcase-channel")
    public String consume(String message) {
        System.out.printf("Message '%s' consumed in %s %n", message, this.getClass().getSimpleName());
        return message.toUpperCase();
    }

}
