package de.moges.onlineshop.service.mapper;

import de.moges.onlineshop.domain.Invoice;
import de.moges.onlineshop.domain.Shipment;
import de.moges.onlineshop.service.dto.InvoiceDTO;
import de.moges.onlineshop.service.dto.ShipmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shipment} and its DTO {@link ShipmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipmentMapper extends EntityMapper<ShipmentDTO, Shipment> {
    @Mapping(target = "invoice", source = "invoice", qualifiedByName = "invoiceId")
    ShipmentDTO toDto(Shipment s);

    @Named("invoiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InvoiceDTO toDtoInvoiceId(Invoice invoice);
}
