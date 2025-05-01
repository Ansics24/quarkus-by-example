package de.schulte.smartbar.backoffice.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class UppercaseConsumer {

    @Incoming("uppercase-channel")
    public void consumeMessage(String content) {
        System.out.printf("%s: %s%n", this.getClass().getSimpleName(), content);
    }

}
