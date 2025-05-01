package de.schulte.smartbar.backoffice.messaging;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/message")
public class MessagingResource {

    private final Emitter<String> emitter;

    public MessagingResource(@Channel("my-channel") Emitter<String> emitter) {
        this.emitter = emitter;
    }

    @POST
    @Consumes("text/plain")
    public Object postMessage(final String text) {
        return emitter.send(text);
    }

}
