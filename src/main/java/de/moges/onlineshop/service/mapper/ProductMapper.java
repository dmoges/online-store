package de.moges.onlineshop.service.mapper;

import de.moges.onlineshop.domain.Product;
import de.moges.onlineshop.domain.ProductCategory;
import de.moges.onlineshop.service.dto.ProductCategoryDTO;
import de.moges.onlineshop.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "productCategory", source = "productCategory", qualifiedByName = "productCategoryName")
    ProductDTO toDto(Product s);

    @Named("productCategoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductCategoryDTO toDtoProductCategoryName(ProductCategory productCategory);
}
