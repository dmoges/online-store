package de.moges.onlineshop.service.mapper;

import de.moges.onlineshop.domain.Invoice;
import de.moges.onlineshop.domain.ProductOrder;
import de.moges.onlineshop.service.dto.InvoiceDTO;
import de.moges.onlineshop.service.dto.ProductOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoice} and its DTO {@link InvoiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {
    @Mapping(target = "order", source = "order", qualifiedByName = "productOrderId")
    InvoiceDTO toDto(Invoice s);

    @Named("productOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductOrderDTO toDtoProductOrderId(ProductOrder productOrder);
}
