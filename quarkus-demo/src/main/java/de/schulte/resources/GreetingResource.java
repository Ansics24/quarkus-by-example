package de.schulte.resources;

import de.schulte.domain.GreetingService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.apache.commons.logging.LogFactory;

@Path("/greeting")
public class GreetingResource {

    private final GreetingService greetingService;

    public GreetingResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GET
    @Path("/{name}")
    public String greet(@PathParam("name") final String name) {
        if (name.equals("Bob")) {
            LogFactory.getLog(this.getClass()).warn("Bob is here");
        }
        return this.greetingService.greet(name);
    }

}
