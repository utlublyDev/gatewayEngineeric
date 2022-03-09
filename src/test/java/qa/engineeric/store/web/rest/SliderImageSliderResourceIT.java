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
import qa.engineeric.store.domain.SliderImageSlider;
import qa.engineeric.store.repository.SliderImageSliderRepository;

/**
 * Integration tests for the {@link SliderImageSliderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SliderImageSliderResourceIT {

    private static final String DEFAULT_STORE_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_STORE_OWNER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_KEY = "AAAAAAAAAA";
    private static final String UPDATED_WEB_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ALT = "AAAAAAAAAA";
    private static final String UPDATED_ALT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/slider-image-sliders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SliderImageSliderRepository sliderImageSliderRepository;

    @Autowired
    private WebTestClient webTestClient;

    private SliderImageSlider sliderImageSlider;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SliderImageSlider createEntity() {
        SliderImageSlider sliderImageSlider = new SliderImageSlider()
            .storeOwnerId(DEFAULT_STORE_OWNER_ID)
            .webKey(DEFAULT_WEB_KEY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .alt(DEFAULT_ALT);
        return sliderImageSlider;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SliderImageSlider createUpdatedEntity() {
        SliderImageSlider sliderImageSlider = new SliderImageSlider()
            .storeOwnerId(UPDATED_STORE_OWNER_ID)
            .webKey(UPDATED_WEB_KEY)
            .imageUrl(UPDATED_IMAGE_URL)
            .alt(UPDATED_ALT);
        return sliderImageSlider;
    }

    @BeforeEach
    public void initTest() {
        sliderImageSliderRepository.deleteAll().block();
        sliderImageSlider = createEntity();
    }

    @Test
    void createSliderImageSlider() throws Exception {
        int databaseSizeBeforeCreate = sliderImageSliderRepository.findAll().collectList().block().size();
        // Create the SliderImageSlider
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sliderImageSlider))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeCreate + 1);
        SliderImageSlider testSliderImageSlider = sliderImageSliderList.get(sliderImageSliderList.size() - 1);
        assertThat(testSliderImageSlider.getStoreOwnerId()).isEqualTo(DEFAULT_STORE_OWNER_ID);
        assertThat(testSliderImageSlider.getWebKey()).isEqualTo(DEFAULT_WEB_KEY);
        assertThat(testSliderImageSlider.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testSliderImageSlider.getAlt()).isEqualTo(DEFAULT_ALT);
    }

    @Test
    void createSliderImageSliderWithExistingId() throws Exception {
        // Create the SliderImageSlider with an existing ID
        sliderImageSlider.setId("existing_id");

        int databaseSizeBeforeCreate = sliderImageSliderRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sliderImageSlider))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSliderImageSliders() {
        // Initialize the database
        sliderImageSliderRepository.save(sliderImageSlider).block();

        // Get all the sliderImageSliderList
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
            .value(hasItem(sliderImageSlider.getId()))
            .jsonPath("$.[*].storeOwnerId")
            .value(hasItem(DEFAULT_STORE_OWNER_ID))
            .jsonPath("$.[*].webKey")
            .value(hasItem(DEFAULT_WEB_KEY))
            .jsonPath("$.[*].imageUrl")
            .value(hasItem(DEFAULT_IMAGE_URL))
            .jsonPath("$.[*].alt")
            .value(hasItem(DEFAULT_ALT));
    }

    @Test
    void getSliderImageSlider() {
        // Initialize the database
        sliderImageSliderRepository.save(sliderImageSlider).block();

        // Get the sliderImageSlider
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, sliderImageSlider.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(sliderImageSlider.getId()))
            .jsonPath("$.storeOwnerId")
            .value(is(DEFAULT_STORE_OWNER_ID))
            .jsonPath("$.webKey")
            .value(is(DEFAULT_WEB_KEY))
            .jsonPath("$.imageUrl")
            .value(is(DEFAULT_IMAGE_URL))
            .jsonPath("$.alt")
            .value(is(DEFAULT_ALT));
    }

    @Test
    void getNonExistingSliderImageSlider() {
        // Get the sliderImageSlider
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewSliderImageSlider() throws Exception {
        // Initialize the database
        sliderImageSliderRepository.save(sliderImageSlider).block();

        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();

        // Update the sliderImageSlider
        SliderImageSlider updatedSliderImageSlider = sliderImageSliderRepository.findById(sliderImageSlider.getId()).block();
        updatedSliderImageSlider.storeOwnerId(UPDATED_STORE_OWNER_ID).webKey(UPDATED_WEB_KEY).imageUrl(UPDATED_IMAGE_URL).alt(UPDATED_ALT);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedSliderImageSlider.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedSliderImageSlider))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
        SliderImageSlider testSliderImageSlider = sliderImageSliderList.get(sliderImageSliderList.size() - 1);
        assertThat(testSliderImageSlider.getStoreOwnerId()).isEqualTo(UPDATED_STORE_OWNER_ID);
        assertThat(testSliderImageSlider.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
        assertThat(testSliderImageSlider.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSliderImageSlider.getAlt()).isEqualTo(UPDATED_ALT);
    }

    @Test
    void putNonExistingSliderImageSlider() throws Exception {
        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();
        sliderImageSlider.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, sliderImageSlider.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sliderImageSlider))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSliderImageSlider() throws Exception {
        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();
        sliderImageSlider.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sliderImageSlider))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSliderImageSlider() throws Exception {
        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();
        sliderImageSlider.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(sliderImageSlider))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSliderImageSliderWithPatch() throws Exception {
        // Initialize the database
        sliderImageSliderRepository.save(sliderImageSlider).block();

        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();

        // Update the sliderImageSlider using partial update
        SliderImageSlider partialUpdatedSliderImageSlider = new SliderImageSlider();
        partialUpdatedSliderImageSlider.setId(sliderImageSlider.getId());

        partialUpdatedSliderImageSlider.webKey(UPDATED_WEB_KEY).imageUrl(UPDATED_IMAGE_URL).alt(UPDATED_ALT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSliderImageSlider.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSliderImageSlider))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
        SliderImageSlider testSliderImageSlider = sliderImageSliderList.get(sliderImageSliderList.size() - 1);
        assertThat(testSliderImageSlider.getStoreOwnerId()).isEqualTo(DEFAULT_STORE_OWNER_ID);
        assertThat(testSliderImageSlider.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
        assertThat(testSliderImageSlider.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSliderImageSlider.getAlt()).isEqualTo(UPDATED_ALT);
    }

    @Test
    void fullUpdateSliderImageSliderWithPatch() throws Exception {
        // Initialize the database
        sliderImageSliderRepository.save(sliderImageSlider).block();

        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();

        // Update the sliderImageSlider using partial update
        SliderImageSlider partialUpdatedSliderImageSlider = new SliderImageSlider();
        partialUpdatedSliderImageSlider.setId(sliderImageSlider.getId());

        partialUpdatedSliderImageSlider
            .storeOwnerId(UPDATED_STORE_OWNER_ID)
            .webKey(UPDATED_WEB_KEY)
            .imageUrl(UPDATED_IMAGE_URL)
            .alt(UPDATED_ALT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSliderImageSlider.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSliderImageSlider))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
        SliderImageSlider testSliderImageSlider = sliderImageSliderList.get(sliderImageSliderList.size() - 1);
        assertThat(testSliderImageSlider.getStoreOwnerId()).isEqualTo(UPDATED_STORE_OWNER_ID);
        assertThat(testSliderImageSlider.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
        assertThat(testSliderImageSlider.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSliderImageSlider.getAlt()).isEqualTo(UPDATED_ALT);
    }

    @Test
    void patchNonExistingSliderImageSlider() throws Exception {
        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();
        sliderImageSlider.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, sliderImageSlider.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sliderImageSlider))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSliderImageSlider() throws Exception {
        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();
        sliderImageSlider.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sliderImageSlider))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSliderImageSlider() throws Exception {
        int databaseSizeBeforeUpdate = sliderImageSliderRepository.findAll().collectList().block().size();
        sliderImageSlider.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(sliderImageSlider))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SliderImageSlider in the database
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSliderImageSlider() {
        // Initialize the database
        sliderImageSliderRepository.save(sliderImageSlider).block();

        int databaseSizeBeforeDelete = sliderImageSliderRepository.findAll().collectList().block().size();

        // Delete the sliderImageSlider
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, sliderImageSlider.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<SliderImageSlider> sliderImageSliderList = sliderImageSliderRepository.findAll().collectList().block();
        assertThat(sliderImageSliderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
