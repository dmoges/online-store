package de.moges.onlineshop.service;

import de.moges.onlineshop.service.dto.ProductCategoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link de.moges.onlineshop.domain.ProductCategory}.
 */
public interface ProductCategoryService {
    /**
     * Save a productCategory.
     *
     * @param productCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    ProductCategoryDTO save(ProductCategoryDTO productCategoryDTO);

    /**
     * Updates a productCategory.
     *
     * @param productCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    ProductCategoryDTO update(ProductCategoryDTO productCategoryDTO);

    /**
     * Partially updates a productCategory.
     *
     * @param productCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductCategoryDTO> partialUpdate(ProductCategoryDTO productCategoryDTO);

    /**
     * Get all the productCategories.
     *
     * @return the list of entities.
     */
    List<ProductCategoryDTO> findAll();

    /**
     * Get the "id" productCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" productCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
