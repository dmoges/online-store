package de.moges.onlineshop.service.mapper;

import static de.moges.onlineshop.domain.ProductOrderAsserts.*;
import static de.moges.onlineshop.domain.ProductOrderTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductOrderMapperTest {

    private ProductOrderMapper productOrderMapper;

    @BeforeEach
    void setUp() {
        productOrderMapper = new ProductOrderMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProductOrderSample1();
        var actual = productOrderMapper.toEntity(productOrderMapper.toDto(expected));
        assertProductOrderAllPropertiesEquals(expected, actual);
    }
}
