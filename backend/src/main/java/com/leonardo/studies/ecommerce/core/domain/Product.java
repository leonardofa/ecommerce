package com.leonardo.studies.ecommerce.core.domain;

import java.math.BigDecimal;

public record Product(
    String name,
    BigDecimal price,
    Long stock
) {
}
