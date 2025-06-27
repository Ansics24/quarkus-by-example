package de.schulte.smartbar.backoffice;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.*;

@MappedSuperclass
public class BaseEntity extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "entity_seq", sequenceName = "entity_seq")
    @GeneratedValue(generator = "entity_seq")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public void fireChangedEvent() {
        CDI.current().select(MasterDataService.class).get().fireChangedEvent(this);
    }
}
