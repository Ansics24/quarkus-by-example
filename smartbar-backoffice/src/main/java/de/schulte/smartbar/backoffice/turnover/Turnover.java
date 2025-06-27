package de.schulte.smartbar.backoffice.turnover;

import de.schulte.smartbar.backoffice.BaseEntity;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Turnover extends BaseEntity {

    public Long articleId;

    public Integer quantity;

    public BigDecimal turnoverTotalValue;

}
