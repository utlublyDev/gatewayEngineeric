package qa.engineeric.store.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
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
import qa.engineeric.store.domain.StoreAdvBanner;
import qa.engineeric.store.repository.StoreAdvBannerRepository;

/**
 * Integration tests for the {@link StoreAdvBannerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class StoreAdvBannerResourceIT {

    private static final String DEFAULT_STORE_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_STORE_OWNER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_KEY = "AAAAAAAAAA";
    private static final String UPDATED_WEB_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/store-adv-banners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StoreAdvBannerRepository storeAdvBannerRepository;

    @Autowired
    private WebTestClient webTestClient;

    private StoreAdvBanner storeAdvBanner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreAdvBanner createEntity() {
        StoreAdvBanner storeAdvBanner = new StoreAdvBanner()
            .storeOwnerId(DEFAULT_STORE_OWNER_ID)
            .webKey(DEFAULT_WEB_KEY)
            .imageUrl(DEFAULT_IMAGE_URL);
        return storeAdvBanner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreAdvBanner createUpdatedEntity() {
        StoreAdvBanner storeAdvBanner = new StoreAdvBanner()
            .storeOwnerId(UPDATED_STORE_OWNER_ID)
            .webKey(UPDATED_WEB_KEY)
            .imageUrl(UPDATED_IMAGE_URL);
        return storeAdvBanner;
    }

    @BeforeEach
    public void initTest() {
        storeAdvBannerRepository.deleteAll().block();
        storeAdvBanner = createEntity();
    }

    @Test
    void createStoreAdvBanner() throws Exception {
        int databaseSizeBeforeCreate = storeAdvBannerRepository.findAll().collectList().block().size();
        // Create the StoreAdvBanner
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeAdvBanner))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeCreate + 1);
        StoreAdvBanner testStoreAdvBanner = storeAdvBannerList.get(storeAdvBannerList.size() - 1);
        assertThat(testStoreAdvBanner.getStoreOwnerId()).isEqualTo(DEFAULT_STORE_OWNER_ID);
        assertThat(testStoreAdvBanner.getWebKey()).isEqualTo(DEFAULT_WEB_KEY);
        assertThat(testStoreAdvBanner.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    void createStoreAdvBannerWithExistingId() throws Exception {
        // Create the StoreAdvBanner with an existing ID
        storeAdvBanner.setId("existing_id");

        int databaseSizeBeforeCreate = storeAdvBannerRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeAdvBanner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllStoreAdvBannersAsStream() {
        // Initialize the database
        storeAdvBannerRepository.save(storeAdvBanner).block();

        List<StoreAdvBanner> storeAdvBannerList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(StoreAdvBanner.class)
            .getResponseBody()
            .filter(storeAdvBanner::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(storeAdvBannerList).isNotNull();
        assertThat(storeAdvBannerList).hasSize(1);
        StoreAdvBanner testStoreAdvBanner = storeAdvBannerList.get(0);
        assertThat(testStoreAdvBanner.getStoreOwnerId()).isEqualTo(DEFAULT_STORE_OWNER_ID);
        assertThat(testStoreAdvBanner.getWebKey()).isEqualTo(DEFAULT_WEB_KEY);
        assertThat(testStoreAdvBanner.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    void getAllStoreAdvBanners() {
        // Initialize the database
        storeAdvBannerRepository.save(storeAdvBanner).block();

        // Get all the storeAdvBannerList
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
            .value(hasItem(storeAdvBanner.getId()))
            .jsonPath("$.[*].storeOwnerId")
            .value(hasItem(DEFAULT_STORE_OWNER_ID))
            .jsonPath("$.[*].webKey")
            .value(hasItem(DEFAULT_WEB_KEY))
            .jsonPath("$.[*].imageUrl")
            .value(hasItem(DEFAULT_IMAGE_URL));
    }

    @Test
    void getStoreAdvBanner() {
        // Initialize the database
        storeAdvBannerRepository.save(storeAdvBanner).block();

        // Get the storeAdvBanner
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, storeAdvBanner.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(storeAdvBanner.getId()))
            .jsonPath("$.storeOwnerId")
            .value(is(DEFAULT_STORE_OWNER_ID))
            .jsonPath("$.webKey")
            .value(is(DEFAULT_WEB_KEY))
            .jsonPath("$.imageUrl")
            .value(is(DEFAULT_IMAGE_URL));
    }

    @Test
    void getNonExistingStoreAdvBanner() {
        // Get the storeAdvBanner
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewStoreAdvBanner() throws Exception {
        // Initialize the database
        storeAdvBannerRepository.save(storeAdvBanner).block();

        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();

        // Update the storeAdvBanner
        StoreAdvBanner updatedStoreAdvBanner = storeAdvBannerRepository.findById(storeAdvBanner.getId()).block();
        updatedStoreAdvBanner.storeOwnerId(UPDATED_STORE_OWNER_ID).webKey(UPDATED_WEB_KEY).imageUrl(UPDATED_IMAGE_URL);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedStoreAdvBanner.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedStoreAdvBanner))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
        StoreAdvBanner testStoreAdvBanner = storeAdvBannerList.get(storeAdvBannerList.size() - 1);
        assertThat(testStoreAdvBanner.getStoreOwnerId()).isEqualTo(UPDATED_STORE_OWNER_ID);
        assertThat(testStoreAdvBanner.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
        assertThat(testStoreAdvBanner.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    void putNonExistingStoreAdvBanner() throws Exception {
        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();
        storeAdvBanner.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, storeAdvBanner.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeAdvBanner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStoreAdvBanner() throws Exception {
        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();
        storeAdvBanner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeAdvBanner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStoreAdvBanner() throws Exception {
        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();
        storeAdvBanner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeAdvBanner))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStoreAdvBannerWithPatch() throws Exception {
        // Initialize the database
        storeAdvBannerRepository.save(storeAdvBanner).block();

        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();

        // Update the storeAdvBanner using partial update
        StoreAdvBanner partialUpdatedStoreAdvBanner = new StoreAdvBanner();
        partialUpdatedStoreAdvBanner.setId(storeAdvBanner.getId());

        partialUpdatedStoreAdvBanner.imageUrl(UPDATED_IMAGE_URL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStoreAdvBanner.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreAdvBanner))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
        StoreAdvBanner testStoreAdvBanner = storeAdvBannerList.get(storeAdvBannerList.size() - 1);
        assertThat(testStoreAdvBanner.getStoreOwnerId()).isEqualTo(DEFAULT_STORE_OWNER_ID);
        assertThat(testStoreAdvBanner.getWebKey()).isEqualTo(DEFAULT_WEB_KEY);
        assertThat(testStoreAdvBanner.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    void fullUpdateStoreAdvBannerWithPatch() throws Exception {
        // Initialize the database
        storeAdvBannerRepository.save(storeAdvBanner).block();

        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();

        // Update the storeAdvBanner using partial update
        StoreAdvBanner partialUpdatedStoreAdvBanner = new StoreAdvBanner();
        partialUpdatedStoreAdvBanner.setId(storeAdvBanner.getId());

        partialUpdatedStoreAdvBanner.storeOwnerId(UPDATED_STORE_OWNER_ID).webKey(UPDATED_WEB_KEY).imageUrl(UPDATED_IMAGE_URL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStoreAdvBanner.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreAdvBanner))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
        StoreAdvBanner testStoreAdvBanner = storeAdvBannerList.get(storeAdvBannerList.size() - 1);
        assertThat(testStoreAdvBanner.getStoreOwnerId()).isEqualTo(UPDATED_STORE_OWNER_ID);
        assertThat(testStoreAdvBanner.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
        assertThat(testStoreAdvBanner.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    void patchNonExistingStoreAdvBanner() throws Exception {
        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();
        storeAdvBanner.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, storeAdvBanner.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeAdvBanner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStoreAdvBanner() throws Exception {
        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();
        storeAdvBanner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeAdvBanner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStoreAdvBanner() throws Exception {
        int databaseSizeBeforeUpdate = storeAdvBannerRepository.findAll().collectList().block().size();
        storeAdvBanner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeAdvBanner))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StoreAdvBanner in the database
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStoreAdvBanner() {
        // Initialize the database
        storeAdvBannerRepository.save(storeAdvBanner).block();

        int databaseSizeBeforeDelete = storeAdvBannerRepository.findAll().collectList().block().size();

        // Delete the storeAdvBanner
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, storeAdvBanner.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<StoreAdvBanner> storeAdvBannerList = storeAdvBannerRepository.findAll().collectList().block();
        assertThat(storeAdvBannerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
