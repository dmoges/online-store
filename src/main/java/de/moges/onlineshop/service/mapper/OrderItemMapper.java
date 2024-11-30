package de.moges.onlineshop.service.mapper;

import de.moges.onlineshop.domain.OrderItem;
import de.moges.onlineshop.domain.Product;
import de.moges.onlineshop.domain.ProductOrder;
import de.moges.onlineshop.service.dto.OrderItemDTO;
import de.moges.onlineshop.service.dto.ProductDTO;
import de.moges.onlineshop.service.dto.ProductOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productName")
    @Mapping(target = "order", source = "order", qualifiedByName = "productOrderId")
    OrderItemDTO toDto(OrderItem s);

    @Named("productName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductDTO toDtoProductName(Product product);

    @Named("productOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductOrderDTO toDtoProductOrderId(ProductOrder productOrder);
}
