package de.schulte.smartbar.backoffice.tables;

import de.schulte.smartbar.backoffice.CrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class TablesService extends CrudService<Table> {

    public TablesService() {
        // Just for CDI requirements
        super(null);
    }

    @Inject
    public TablesService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Table> getEntityClass() {
        return Table.class;
    }

}
