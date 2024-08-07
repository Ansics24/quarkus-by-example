package de.schulte.smartbar.backoffice.categories;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(rolesAllowed = {"admin"})
public interface CategoriesResource extends PanacheEntityResource<Category, Long> {

}
