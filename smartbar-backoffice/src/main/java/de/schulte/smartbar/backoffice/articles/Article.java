package de.schulte.smartbar.backoffice.articles;

import de.schulte.smartbar.backoffice.BaseEntity;
import de.schulte.smartbar.backoffice.MasterDataService;
import de.schulte.smartbar.backoffice.categories.Category;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "category_id"})
})
@NamedQuery(name = "Article.byCategory", query = "from Article where category.id = :id order by price desc")
@NamedQuery(name = "Article.nameContaining", query = "from Article where name like concat('%', concat(?1, '%'))")
public class Article extends BaseEntity {

    @NotNull
    public String name;

    @NotNull
    @Positive
    public BigDecimal price;

    @NotNull
    public String description;

    @NotNull
    public String pictureBase64;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    public Category category;

    public Integer timesOrdered;

    public LocalDate lastOrdered;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void fireChangedEvent() {
        CDI.current().select(MasterDataService.class).get().fireChangedEvent(this);
    }

}
