package de.schulte.smartbar.backoffice.categories;

import de.schulte.smartbar.backoffice.CrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CategoriesService extends CrudService<Category> {

    public CategoriesService() {
        // For CDI needs
        super(null);
    }

    @Inject
    public CategoriesService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
}
