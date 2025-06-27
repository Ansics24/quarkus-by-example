package de.schulte.smartbar.backoffice;

import java.math.BigDecimal;

/**
 * Identically structured as in the Orderclient
 */
public record OrderPosition(Long articleId, Integer quantity, BigDecimal price) {
}
