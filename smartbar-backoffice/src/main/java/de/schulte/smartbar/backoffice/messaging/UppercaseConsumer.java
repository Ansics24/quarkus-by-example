package de.schulte.smartbar.backoffice.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class UppercaseConsumer {

    @Incoming("uppcase-channel")
    public void consume(String message) {
        System.out.printf("Message '%s' consumed in %s %n", message, this.getClass().getSimpleName());
    }
}
