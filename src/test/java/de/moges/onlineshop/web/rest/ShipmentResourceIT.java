package de.moges.onlineshop.web.rest;

import static de.moges.onlineshop.domain.ShipmentAsserts.*;
import static de.moges.onlineshop.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.moges.onlineshop.IntegrationTest;
import de.moges.onlineshop.domain.Shipment;
import de.moges.onlineshop.repository.ShipmentRepository;
import de.moges.onlineshop.service.dto.ShipmentDTO;
import de.moges.onlineshop.service.mapper.ShipmentMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ShipmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipmentResourceIT {

    private static final String DEFAULT_TRACKING_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shipments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentMapper shipmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipmentMockMvc;

    private Shipment shipment;

    private Shipment insertedShipment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createEntity() {
        return new Shipment().trackingCode(DEFAULT_TRACKING_CODE).date(DEFAULT_DATE).details(DEFAULT_DETAILS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createUpdatedEntity() {
        return new Shipment().trackingCode(UPDATED_TRACKING_CODE).date(UPDATED_DATE).details(UPDATED_DETAILS);
    }

    @BeforeEach
    public void initTest() {
        shipment = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedShipment != null) {
            shipmentRepository.delete(insertedShipment);
            insertedShipment = null;
        }
    }

    @Test
    @Transactional
    void createShipment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);
        var returnedShipmentDTO = om.readValue(
            restShipmentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipmentDTO.class
        );

        // Validate the Shipment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShipment = shipmentMapper.toEntity(returnedShipmentDTO);
        assertShipmentUpdatableFieldsEquals(returnedShipment, getPersistedShipment(returnedShipment));

        insertedShipment = returnedShipment;
    }

    @Test
    @Transactional
    void createShipmentWithExistingId() throws Exception {
        // Create the Shipment with an existing ID
        shipment.setId(1L);
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipment.setDate(null);

        // Create the Shipment, which fails.
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        restShipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShipments() throws Exception {
        // Initialize the database
        insertedShipment = shipmentRepository.saveAndFlush(shipment);

        // Get all the shipmentList
        restShipmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingCode").value(hasItem(DEFAULT_TRACKING_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }

    @Test
    @Transactional
    void getShipment() throws Exception {
        // Initialize the database
        insertedShipment = shipmentRepository.saveAndFlush(shipment);

        // Get the shipment
        restShipmentMockMvc
            .perform(get(ENTITY_API_URL_ID, shipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipment.getId().intValue()))
            .andExpect(jsonPath("$.trackingCode").value(DEFAULT_TRACKING_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    void getNonExistingShipment() throws Exception {
        // Get the shipment
        restShipmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShipment() throws Exception {
        // Initialize the database
        insertedShipment = shipmentRepository.saveAndFlush(shipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipment
        Shipment updatedShipment = shipmentRepository.findById(shipment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedShipment are not directly saved in db
        em.detach(updatedShipment);
        updatedShipment.trackingCode(UPDATED_TRACKING_CODE).date(UPDATED_DATE).details(UPDATED_DETAILS);
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(updatedShipment);

        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipmentToMatchAllProperties(updatedShipment);
    }

    @Test
    @Transactional
    void putNonExistingShipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipment.setId(longCount.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipment.setId(longCount.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipment.setId(longCount.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShipmentWithPatch() throws Exception {
        // Initialize the database
        insertedShipment = shipmentRepository.saveAndFlush(shipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipment using partial update
        Shipment partialUpdatedShipment = new Shipment();
        partialUpdatedShipment.setId(shipment.getId());

        partialUpdatedShipment.date(UPDATED_DATE);

        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipment))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipmentUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedShipment, shipment), getPersistedShipment(shipment));
    }

    @Test
    @Transactional
    void fullUpdateShipmentWithPatch() throws Exception {
        // Initialize the database
        insertedShipment = shipmentRepository.saveAndFlush(shipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipment using partial update
        Shipment partialUpdatedShipment = new Shipment();
        partialUpdatedShipment.setId(shipment.getId());

        partialUpdatedShipment.trackingCode(UPDATED_TRACKING_CODE).date(UPDATED_DATE).details(UPDATED_DETAILS);

        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipment))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipmentUpdatableFieldsEquals(partialUpdatedShipment, getPersistedShipment(partialUpdatedShipment));
    }

    @Test
    @Transactional
    void patchNonExistingShipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipment.setId(longCount.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipment.setId(longCount.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipment.setId(longCount.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShipment() throws Exception {
        // Initialize the database
        insertedShipment = shipmentRepository.saveAndFlush(shipment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the shipment
        restShipmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipmentRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Shipment getPersistedShipment(Shipment shipment) {
        return shipmentRepository.findById(shipment.getId()).orElseThrow();
    }

    protected void assertPersistedShipmentToMatchAllProperties(Shipment expectedShipment) {
        assertShipmentAllPropertiesEquals(expectedShipment, getPersistedShipment(expectedShipment));
    }

    protected void assertPersistedShipmentToMatchUpdatableProperties(Shipment expectedShipment) {
        assertShipmentAllUpdatablePropertiesEquals(expectedShipment, getPersistedShipment(expectedShipment));
    }
}
