package de.moges.onlineshop.service.mapper;

import static de.moges.onlineshop.domain.ProductCategoryAsserts.*;
import static de.moges.onlineshop.domain.ProductCategoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductCategoryMapperTest {

    private ProductCategoryMapper productCategoryMapper;

    @BeforeEach
    void setUp() {
        productCategoryMapper = new ProductCategoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProductCategorySample1();
        var actual = productCategoryMapper.toEntity(productCategoryMapper.toDto(expected));
        assertProductCategoryAllPropertiesEquals(expected, actual);
    }
}