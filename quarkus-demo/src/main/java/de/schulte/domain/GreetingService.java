package de.schulte.domain;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.logging.LogFactory;

@ApplicationScoped
public class GreetingService {

    public String greet(final String name) {
        LogFactory.getLog(this.getClass()).info("Greetings to " + name);
        return "Hello " + name;
    }

}
