package de.schulte.smartbar.backoffice.messaging;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

@Path("/message")
public class MessagingResource {

    private final MutinyEmitter<String> emitter;

    public MessagingResource(@Channel("my-channel") MutinyEmitter<String> emitter) {
        this.emitter = emitter;
    }

    @POST
    @Consumes("text/plain")
    public Uni<String> postMessage(final String text) {
        final var message = Message.of(text, Metadata.of(System.currentTimeMillis()));
        return this.emitter.sendMessage(message).map(r -> "Message sent");
    }

}
