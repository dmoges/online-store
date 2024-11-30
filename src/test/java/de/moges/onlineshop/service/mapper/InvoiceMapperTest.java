package de.moges.onlineshop.service.mapper;

import static de.moges.onlineshop.domain.InvoiceAsserts.*;
import static de.moges.onlineshop.domain.InvoiceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvoiceMapperTest {

    private InvoiceMapper invoiceMapper;

    @BeforeEach
    void setUp() {
        invoiceMapper = new InvoiceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInvoiceSample1();
        var actual = invoiceMapper.toEntity(invoiceMapper.toDto(expected));
        assertInvoiceAllPropertiesEquals(expected, actual);
    }
}
