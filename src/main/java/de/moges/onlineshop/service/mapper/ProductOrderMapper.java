package de.moges.onlineshop.service.mapper;

import de.moges.onlineshop.domain.Customer;
import de.moges.onlineshop.domain.ProductOrder;
import de.moges.onlineshop.service.dto.CustomerDTO;
import de.moges.onlineshop.service.dto.ProductOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOrder} and its DTO {@link ProductOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductOrderMapper extends EntityMapper<ProductOrderDTO, ProductOrder> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerFullName")
    ProductOrderDTO toDto(ProductOrder s);

    @Named("customerFullName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(s.getFirstName() + ' ' + s.getLastName())")
    CustomerDTO toDtoCustomerFullName(Customer s);
}
