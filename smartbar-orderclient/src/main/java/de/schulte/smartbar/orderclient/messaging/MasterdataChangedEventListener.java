package de.schulte.smartbar.orderclient.messaging;

import io.quarkus.arc.Unremovable;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKeyGenerator;
import io.quarkus.cache.DefaultCacheKey;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.lang.reflect.Method;

@ApplicationScoped
@Unremovable
public class MasterdataChangedEventListener implements CacheKeyGenerator {

    @Incoming("update-menu-events")
    @CacheInvalidate(cacheName = "menu-cache", keyGenerator = MasterdataChangedEventListener.class)
    public Uni<Void> onMenuChanged(final Message<MasterdataChangedEvent> message) {
        System.out.println("MasterdataChangedEventListener onMenuChanged");
        return Uni.createFrom().completionStage(message.ack());
    }

    @Override
    public Object generate(Method method, Object... methodParams) {
        return new DefaultCacheKey("menu-cache");
    }
}
