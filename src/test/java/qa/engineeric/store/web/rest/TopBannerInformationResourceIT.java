package qa.engineeric.store.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import qa.engineeric.store.IntegrationTest;
import qa.engineeric.store.domain.TopBannerInformation;
import qa.engineeric.store.repository.TopBannerInformationRepository;

/**
 * Integration tests for the {@link TopBannerInformationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TopBannerInformationResourceIT {

    private static final Boolean DEFAULT_ENABLE_BANNER = false;
    private static final Boolean UPDATED_ENABLE_BANNER = true;

    private static final String DEFAULT_BANNER_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_TEXT = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_BANNER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_BANNER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_BANNER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_BANNER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_WEB_KEY = "AAAAAAAAAA";
    private static final String UPDATED_WEB_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_STORE_OWNER_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/top-banner-informations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TopBannerInformationRepository topBannerInformationRepository;

    @Autowired
    private WebTestClient webTestClient;

    private TopBannerInformation topBannerInformation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopBannerInformation createEntity() {
        TopBannerInformation topBannerInformation = new TopBannerInformation()
            .enableBanner(DEFAULT_ENABLE_BANNER)
            .bannerText(DEFAULT_BANNER_TEXT)
            .startBanner(DEFAULT_START_BANNER)
            .endBanner(DEFAULT_END_BANNER)
            .webKey(DEFAULT_WEB_KEY)
            .storeOwnerId(DEFAULT_STORE_OWNER_ID);
        return topBannerInformation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopBannerInformation createUpdatedEntity() {
        TopBannerInformation topBannerInformation = new TopBannerInformation()
            .enableBanner(UPDATED_ENABLE_BANNER)
            .bannerText(UPDATED_BANNER_TEXT)
            .startBanner(UPDATED_START_BANNER)
            .endBanner(UPDATED_END_BANNER)
            .webKey(UPDATED_WEB_KEY)
            .storeOwnerId(UPDATED_STORE_OWNER_ID);
        return topBannerInformation;
    }

    @BeforeEach
    public void initTest() {
        topBannerInformationRepository.deleteAll().block();
        topBannerInformation = createEntity();
    }

    @Test
    void createTopBannerInformation() throws Exception {
        int databaseSizeBeforeCreate = topBannerInformationRepository.findAll().collectList().block().size();
        // Create the TopBannerInformation
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(topBannerInformation))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeCreate + 1);
        TopBannerInformation testTopBannerInformation = topBannerInformationList.get(topBannerInformationList.size() - 1);
        assertThat(testTopBannerInformation.getEnableBanner()).isEqualTo(DEFAULT_ENABLE_BANNER);
        assertThat(testTopBannerInformation.getBannerText()).isEqualTo(DEFAULT_BANNER_TEXT);
        assertThat(testTopBannerInformation.getStartBanner()).isEqualTo(DEFAULT_START_BANNER);
        assertThat(testTopBannerInformation.getEndBanner()).isEqualTo(DEFAULT_END_BANNER);
        assertThat(testTopBannerInformation.getWebKey()).isEqualTo(DEFAULT_WEB_KEY);
        assertThat(testTopBannerInformation.getStoreOwnerId()).isEqualTo(DEFAULT_STORE_OWNER_ID);
    }

    @Test
    void createTopBannerInformationWithExistingId() throws Exception {
        // Create the TopBannerInformation with an existing ID
        topBannerInformation.setId("existing_id");

        int databaseSizeBeforeCreate = topBannerInformationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(topBannerInformation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTopBannerInformations() {
        // Initialize the database
        topBannerInformationRepository.save(topBannerInformation).block();

        // Get all the topBannerInformationList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(topBannerInformation.getId()))
            .jsonPath("$.[*].enableBanner")
            .value(hasItem(DEFAULT_ENABLE_BANNER.booleanValue()))
            .jsonPath("$.[*].bannerText")
            .value(hasItem(DEFAULT_BANNER_TEXT))
            .jsonPath("$.[*].startBanner")
            .value(hasItem(DEFAULT_START_BANNER.toString()))
            .jsonPath("$.[*].endBanner")
            .value(hasItem(DEFAULT_END_BANNER.toString()))
            .jsonPath("$.[*].webKey")
            .value(hasItem(DEFAULT_WEB_KEY))
            .jsonPath("$.[*].storeOwnerId")
            .value(hasItem(DEFAULT_STORE_OWNER_ID));
    }

    @Test
    void getTopBannerInformation() {
        // Initialize the database
        topBannerInformationRepository.save(topBannerInformation).block();

        // Get the topBannerInformation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, topBannerInformation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(topBannerInformation.getId()))
            .jsonPath("$.enableBanner")
            .value(is(DEFAULT_ENABLE_BANNER.booleanValue()))
            .jsonPath("$.bannerText")
            .value(is(DEFAULT_BANNER_TEXT))
            .jsonPath("$.startBanner")
            .value(is(DEFAULT_START_BANNER.toString()))
            .jsonPath("$.endBanner")
            .value(is(DEFAULT_END_BANNER.toString()))
            .jsonPath("$.webKey")
            .value(is(DEFAULT_WEB_KEY))
            .jsonPath("$.storeOwnerId")
            .value(is(DEFAULT_STORE_OWNER_ID));
    }

    @Test
    void getNonExistingTopBannerInformation() {
        // Get the topBannerInformation
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewTopBannerInformation() throws Exception {
        // Initialize the database
        topBannerInformationRepository.save(topBannerInformation).block();

        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();

        // Update the topBannerInformation
        TopBannerInformation updatedTopBannerInformation = topBannerInformationRepository.findById(topBannerInformation.getId()).block();
        updatedTopBannerInformation
            .enableBanner(UPDATED_ENABLE_BANNER)
            .bannerText(UPDATED_BANNER_TEXT)
            .startBanner(UPDATED_START_BANNER)
            .endBanner(UPDATED_END_BANNER)
            .webKey(UPDATED_WEB_KEY)
            .storeOwnerId(UPDATED_STORE_OWNER_ID);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTopBannerInformation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTopBannerInformation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
        TopBannerInformation testTopBannerInformation = topBannerInformationList.get(topBannerInformationList.size() - 1);
        assertThat(testTopBannerInformation.getEnableBanner()).isEqualTo(UPDATED_ENABLE_BANNER);
        assertThat(testTopBannerInformation.getBannerText()).isEqualTo(UPDATED_BANNER_TEXT);
        assertThat(testTopBannerInformation.getStartBanner()).isEqualTo(UPDATED_START_BANNER);
        assertThat(testTopBannerInformation.getEndBanner()).isEqualTo(UPDATED_END_BANNER);
        assertThat(testTopBannerInformation.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
        assertThat(testTopBannerInformation.getStoreOwnerId()).isEqualTo(UPDATED_STORE_OWNER_ID);
    }

    @Test
    void putNonExistingTopBannerInformation() throws Exception {
        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();
        topBannerInformation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, topBannerInformation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(topBannerInformation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTopBannerInformation() throws Exception {
        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();
        topBannerInformation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(topBannerInformation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTopBannerInformation() throws Exception {
        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();
        topBannerInformation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(topBannerInformation))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTopBannerInformationWithPatch() throws Exception {
        // Initialize the database
        topBannerInformationRepository.save(topBannerInformation).block();

        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();

        // Update the topBannerInformation using partial update
        TopBannerInformation partialUpdatedTopBannerInformation = new TopBannerInformation();
        partialUpdatedTopBannerInformation.setId(topBannerInformation.getId());

        partialUpdatedTopBannerInformation.webKey(UPDATED_WEB_KEY).storeOwnerId(UPDATED_STORE_OWNER_ID);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTopBannerInformation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTopBannerInformation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
        TopBannerInformation testTopBannerInformation = topBannerInformationList.get(topBannerInformationList.size() - 1);
        assertThat(testTopBannerInformation.getEnableBanner()).isEqualTo(DEFAULT_ENABLE_BANNER);
        assertThat(testTopBannerInformation.getBannerText()).isEqualTo(DEFAULT_BANNER_TEXT);
        assertThat(testTopBannerInformation.getStartBanner()).isEqualTo(DEFAULT_START_BANNER);
        assertThat(testTopBannerInformation.getEndBanner()).isEqualTo(DEFAULT_END_BANNER);
        assertThat(testTopBannerInformation.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
        assertThat(testTopBannerInformation.getStoreOwnerId()).isEqualTo(UPDATED_STORE_OWNER_ID);
    }

    @Test
    void fullUpdateTopBannerInformationWithPatch() throws Exception {
        // Initialize the database
        topBannerInformationRepository.save(topBannerInformation).block();

        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();

        // Update the topBannerInformation using partial update
        TopBannerInformation partialUpdatedTopBannerInformation = new TopBannerInformation();
        partialUpdatedTopBannerInformation.setId(topBannerInformation.getId());

        partialUpdatedTopBannerInformation
            .enableBanner(UPDATED_ENABLE_BANNER)
            .bannerText(UPDATED_BANNER_TEXT)
            .startBanner(UPDATED_START_BANNER)
            .endBanner(UPDATED_END_BANNER)
            .webKey(UPDATED_WEB_KEY)
            .storeOwnerId(UPDATED_STORE_OWNER_ID);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTopBannerInformation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTopBannerInformation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
        TopBannerInformation testTopBannerInformation = topBannerInformationList.get(topBannerInformationList.size() - 1);
        assertThat(testTopBannerInformation.getEnableBanner()).isEqualTo(UPDATED_ENABLE_BANNER);
        assertThat(testTopBannerInformation.getBannerText()).isEqualTo(UPDATED_BANNER_TEXT);
        assertThat(testTopBannerInformation.getStartBanner()).isEqualTo(UPDATED_START_BANNER);
        assertThat(testTopBannerInformation.getEndBanner()).isEqualTo(UPDATED_END_BANNER);
        assertThat(testTopBannerInformation.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
        assertThat(testTopBannerInformation.getStoreOwnerId()).isEqualTo(UPDATED_STORE_OWNER_ID);
    }

    @Test
    void patchNonExistingTopBannerInformation() throws Exception {
        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();
        topBannerInformation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, topBannerInformation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(topBannerInformation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTopBannerInformation() throws Exception {
        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();
        topBannerInformation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(topBannerInformation))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTopBannerInformation() throws Exception {
        int databaseSizeBeforeUpdate = topBannerInformationRepository.findAll().collectList().block().size();
        topBannerInformation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(topBannerInformation))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TopBannerInformation in the database
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTopBannerInformation() {
        // Initialize the database
        topBannerInformationRepository.save(topBannerInformation).block();

        int databaseSizeBeforeDelete = topBannerInformationRepository.findAll().collectList().block().size();

        // Delete the topBannerInformation
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, topBannerInformation.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<TopBannerInformation> topBannerInformationList = topBannerInformationRepository.findAll().collectList().block();
        assertThat(topBannerInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
