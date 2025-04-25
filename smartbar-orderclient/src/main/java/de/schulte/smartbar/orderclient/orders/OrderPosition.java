package de.schulte.smartbar.orderclient.orders;

import java.math.BigDecimal;

public record OrderPosition(Long articleId, Integer quantity, BigDecimal price) {}
