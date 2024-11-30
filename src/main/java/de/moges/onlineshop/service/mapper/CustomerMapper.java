package de.moges.onlineshop.service.mapper;

import de.moges.onlineshop.domain.Customer;
import de.moges.onlineshop.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "fullName", expression = "java(s.getFirstName() + ' ' + s.getLastName())")
    CustomerDTO toDto(Customer s);
}
