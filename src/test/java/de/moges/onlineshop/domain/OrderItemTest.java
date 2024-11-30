package de.moges.onlineshop.domain;

import static de.moges.onlineshop.domain.OrderItemTestSamples.*;
import static de.moges.onlineshop.domain.ProductOrderTestSamples.*;
import static de.moges.onlineshop.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import de.moges.onlineshop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItem.class);
        OrderItem orderItem1 = getOrderItemSample1();
        OrderItem orderItem2 = new OrderItem();
        assertThat(orderItem1).isNotEqualTo(orderItem2);

        orderItem2.setId(orderItem1.getId());
        assertThat(orderItem1).isEqualTo(orderItem2);

        orderItem2 = getOrderItemSample2();
        assertThat(orderItem1).isNotEqualTo(orderItem2);
    }

    @Test
    void productTest() {
        OrderItem orderItem = getOrderItemRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        orderItem.setProduct(productBack);
        assertThat(orderItem.getProduct()).isEqualTo(productBack);

        orderItem.product(null);
        assertThat(orderItem.getProduct()).isNull();
    }

    @Test
    void orderTest() {
        OrderItem orderItem = getOrderItemRandomSampleGenerator();
        ProductOrder productOrderBack = getProductOrderRandomSampleGenerator();

        orderItem.setOrder(productOrderBack);
        assertThat(orderItem.getOrder()).isEqualTo(productOrderBack);

        orderItem.order(null);
        assertThat(orderItem.getOrder()).isNull();
    }
}
