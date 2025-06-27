package de.schulte.smartbar.backoffice;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;

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

}
