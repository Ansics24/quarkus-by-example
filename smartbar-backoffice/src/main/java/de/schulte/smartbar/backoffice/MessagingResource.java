package de.schulte.smartbar.backoffice;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/message")
public class MessagingResource {

    @POST
    @Consumes("text/plain")
    public Object postMessage(final String text) {
        return text.toUpperCase();
    }

}
