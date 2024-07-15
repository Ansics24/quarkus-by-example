package de.schulte.smartbar.orderclient.login;

import de.schulte.smartbar.backoffice.api.model.ApiMenu;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MenuApiClientImpl implements MenuApiClient {

    private final String apiUrl;

    public MenuApiClientImpl(@ConfigProperty(name = "backoffice.menuapi.url") String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public ApiMenu getMenu() {
        return null;
    }
}
