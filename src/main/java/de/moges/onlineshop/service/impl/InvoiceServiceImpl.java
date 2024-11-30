package de.moges.onlineshop.service.impl;

import de.moges.onlineshop.domain.Invoice;
import de.moges.onlineshop.repository.InvoiceRepository;
import de.moges.onlineshop.service.InvoiceService;
import de.moges.onlineshop.service.dto.InvoiceDTO;
import de.moges.onlineshop.service.mapper.InvoiceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link de.moges.onlineshop.domain.Invoice}.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        LOG.debug("Request to save Invoice : {}", invoiceDTO);
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoiceDTO) {
        LOG.debug("Request to update Invoice : {}", invoiceDTO);
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    @Override
    public Optional<InvoiceDTO> partialUpdate(InvoiceDTO invoiceDTO) {
        LOG.debug("Request to partially update Invoice : {}", invoiceDTO);

        return invoiceRepository
            .findById(invoiceDTO.getId())
            .map(existingInvoice -> {
                invoiceMapper.partialUpdate(existingInvoice, invoiceDTO);

                return existingInvoice;
            })
            .map(invoiceRepository::save)
            .map(invoiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvoiceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Invoices");
        return invoiceRepository.findAll(pageable).map(invoiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceDTO> findOne(Long id) {
        LOG.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id).map(invoiceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
