package de.moges.onlineshop.service;

import de.moges.onlineshop.service.dto.ProductOrderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link de.moges.onlineshop.domain.ProductOrder}.
 */
public interface ProductOrderService {
    /**
     * Save a productOrder.
     *
     * @param productOrderDTO the entity to save.
     * @return the persisted entity.
     */
    ProductOrderDTO save(ProductOrderDTO productOrderDTO);

    /**
     * Updates a productOrder.
     *
     * @param productOrderDTO the entity to update.
     * @return the persisted entity.
     */
    ProductOrderDTO update(ProductOrderDTO productOrderDTO);

    /**
     * Partially updates a productOrder.
     *
     * @param productOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductOrderDTO> partialUpdate(ProductOrderDTO productOrderDTO);

    /**
     * Get all the productOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductOrderDTO> findAll(Pageable pageable);

    /**
     * Get all the productOrders with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductOrderDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" productOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductOrderDTO> findOne(Long id);

    /**
     * Delete the "id" productOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
