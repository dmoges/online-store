package de.moges.onlineshop.service.impl;

import de.moges.onlineshop.domain.ProductOrder;
import de.moges.onlineshop.repository.ProductOrderRepository;
import de.moges.onlineshop.security.AuthoritiesConstants;
import de.moges.onlineshop.security.SecurityUtils;
import de.moges.onlineshop.service.ProductOrderService;
import de.moges.onlineshop.service.dto.ProductOrderDTO;
import de.moges.onlineshop.service.mapper.ProductOrderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link de.moges.onlineshop.domain.ProductOrder}.
 */
@Service
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductOrderServiceImpl.class);

    private final ProductOrderRepository productOrderRepository;

    private final ProductOrderMapper productOrderMapper;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, ProductOrderMapper productOrderMapper) {
        this.productOrderRepository = productOrderRepository;
        this.productOrderMapper = productOrderMapper;
    }

    @Override
    public ProductOrderDTO save(ProductOrderDTO productOrderDTO) {
        LOG.debug("Request to save ProductOrder : {}", productOrderDTO);
        ProductOrder productOrder = productOrderMapper.toEntity(productOrderDTO);
        productOrder = productOrderRepository.save(productOrder);
        return productOrderMapper.toDto(productOrder);
    }

    @Override
    public ProductOrderDTO update(ProductOrderDTO productOrderDTO) {
        LOG.debug("Request to update ProductOrder : {}", productOrderDTO);
        ProductOrder productOrder = productOrderMapper.toEntity(productOrderDTO);
        productOrder = productOrderRepository.save(productOrder);
        return productOrderMapper.toDto(productOrder);
    }

    @Override
    public Optional<ProductOrderDTO> partialUpdate(ProductOrderDTO productOrderDTO) {
        LOG.debug("Request to partially update ProductOrder : {}", productOrderDTO);

        return productOrderRepository
            .findById(productOrderDTO.getId())
            .map(existingProductOrder -> {
                productOrderMapper.partialUpdate(existingProductOrder, productOrderDTO);

                return existingProductOrder;
            })
            .map(productOrderRepository::save)
            .map(productOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductOrderDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ProductOrders");
        //return productOrderRepository.findAll(pageable).map(productOrderMapper::toDto);

        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            return productOrderRepository.findAll(pageable).map(productOrderMapper::toDto);
        } else {
            /*
            return productOrderRepository.findAllByCutomerUserLogin(SecurityUtils.getCurrentUserLogin().get(),pageable)
            .map(productOrderMapper::toDto);
             */
            return productOrderRepository.findAll(pageable).map(productOrderMapper::toDto);
        }
    }

    public Page<ProductOrderDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productOrderRepository.findAllWithEagerRelationships(pageable).map(productOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOrderDTO> findOne(Long id) {
        LOG.debug("Request to get ProductOrder : {}", id);
        return productOrderRepository.findOneWithEagerRelationships(id).map(productOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ProductOrder : {}", id);
        productOrderRepository.deleteById(id);
    }
}
