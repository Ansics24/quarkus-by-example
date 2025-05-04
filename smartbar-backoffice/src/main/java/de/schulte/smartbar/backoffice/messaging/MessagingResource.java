package de.schulte.smartbar.backoffice.messaging;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.reactive.messaging.Channel;

@Path("/message")
public class MessagingResource {

    private final MutinyEmitter<String> emitter;

    public MessagingResource(@Channel("my-channel") MutinyEmitter<String> emitter) {
        this.emitter = emitter;
    }

    @POST
    @Consumes("text/plain")
    public Uni<String> postMessage(final String text) {
        return this.emitter.send(text).map(r -> "Message sent");
    }

}
