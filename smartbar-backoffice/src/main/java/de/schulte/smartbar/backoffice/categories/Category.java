package de.schulte.smartbar.backoffice.categories;

import de.schulte.smartbar.backoffice.BaseEntity;
import de.schulte.smartbar.backoffice.MasterDataService;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Category extends BaseEntity {

    @NotNull
    public String name;

    @NotNull
    public String description;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void fireChangedEvent() {
        CDI.current().select(MasterDataService.class).get().fireChangedEvent(this);
    }

}
