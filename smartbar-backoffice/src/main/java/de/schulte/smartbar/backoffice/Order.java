package de.schulte.smartbar.backoffice;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * An order happend in the Orderclient
 */
public record Order(@JsonProperty("orderPositions") List<OrderPosition> positions) {
}
