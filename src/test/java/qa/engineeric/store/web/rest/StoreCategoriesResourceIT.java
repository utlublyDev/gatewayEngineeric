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
import qa.engineeric.store.domain.StoreCategories;
import qa.engineeric.store.repository.StoreCategoriesRepository;

/**
 * Integration tests for the {@link StoreCategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class StoreCategoriesResourceIT {

    private static final String DEFAULT_USER_STORE_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_STORE_OWNER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_NAME_IN_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME_IN_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_IN_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_IN_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_USER_STORE_OWNER_ID_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_USER_STORE_OWNER_ID_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/store-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StoreCategoriesRepository storeCategoriesRepository;

    @Autowired
    private WebTestClient webTestClient;

    private StoreCategories storeCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreCategories createEntity() {
        StoreCategories storeCategories = new StoreCategories()
            .userStoreOwnerId(DEFAULT_USER_STORE_OWNER_ID)
            .categoryName(DEFAULT_CATEGORY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .categoryNameInArabic(DEFAULT_CATEGORY_NAME_IN_ARABIC)
            .descriptionInArabic(DEFAULT_DESCRIPTION_IN_ARABIC)
            .userStoreOwnerIdImageUrl(DEFAULT_USER_STORE_OWNER_ID_IMAGE_URL);
        return storeCategories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreCategories createUpdatedEntity() {
        StoreCategories storeCategories = new StoreCategories()
            .userStoreOwnerId(UPDATED_USER_STORE_OWNER_ID)
            .categoryName(UPDATED_CATEGORY_NAME)
            .description(UPDATED_DESCRIPTION)
            .categoryNameInArabic(UPDATED_CATEGORY_NAME_IN_ARABIC)
            .descriptionInArabic(UPDATED_DESCRIPTION_IN_ARABIC)
            .userStoreOwnerIdImageUrl(UPDATED_USER_STORE_OWNER_ID_IMAGE_URL);
        return storeCategories;
    }

    @BeforeEach
    public void initTest() {
        storeCategoriesRepository.deleteAll().block();
        storeCategories = createEntity();
    }

    @Test
    void createStoreCategories() throws Exception {
        int databaseSizeBeforeCreate = storeCategoriesRepository.findAll().collectList().block().size();
        // Create the StoreCategories
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        StoreCategories testStoreCategories = storeCategoriesList.get(storeCategoriesList.size() - 1);
        assertThat(testStoreCategories.getUserStoreOwnerId()).isEqualTo(DEFAULT_USER_STORE_OWNER_ID);
        assertThat(testStoreCategories.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testStoreCategories.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStoreCategories.getCategoryNameInArabic()).isEqualTo(DEFAULT_CATEGORY_NAME_IN_ARABIC);
        assertThat(testStoreCategories.getDescriptionInArabic()).isEqualTo(DEFAULT_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreCategories.getUserStoreOwnerIdImageUrl()).isEqualTo(DEFAULT_USER_STORE_OWNER_ID_IMAGE_URL);
    }

    @Test
    void createStoreCategoriesWithExistingId() throws Exception {
        // Create the StoreCategories with an existing ID
        storeCategories.setId("existing_id");

        int databaseSizeBeforeCreate = storeCategoriesRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkUserStoreOwnerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeCategoriesRepository.findAll().collectList().block().size();
        // set the field null
        storeCategories.setUserStoreOwnerId(null);

        // Create the StoreCategories, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeCategoriesRepository.findAll().collectList().block().size();
        // set the field null
        storeCategories.setCategoryName(null);

        // Create the StoreCategories, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeCategoriesRepository.findAll().collectList().block().size();
        // set the field null
        storeCategories.setDescription(null);

        // Create the StoreCategories, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCategoryNameInArabicIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeCategoriesRepository.findAll().collectList().block().size();
        // set the field null
        storeCategories.setCategoryNameInArabic(null);

        // Create the StoreCategories, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllStoreCategoriesAsStream() {
        // Initialize the database
        storeCategoriesRepository.save(storeCategories).block();

        List<StoreCategories> storeCategoriesList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(StoreCategories.class)
            .getResponseBody()
            .filter(storeCategories::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(storeCategoriesList).isNotNull();
        assertThat(storeCategoriesList).hasSize(1);
        StoreCategories testStoreCategories = storeCategoriesList.get(0);
        assertThat(testStoreCategories.getUserStoreOwnerId()).isEqualTo(DEFAULT_USER_STORE_OWNER_ID);
        assertThat(testStoreCategories.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testStoreCategories.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStoreCategories.getCategoryNameInArabic()).isEqualTo(DEFAULT_CATEGORY_NAME_IN_ARABIC);
        assertThat(testStoreCategories.getDescriptionInArabic()).isEqualTo(DEFAULT_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreCategories.getUserStoreOwnerIdImageUrl()).isEqualTo(DEFAULT_USER_STORE_OWNER_ID_IMAGE_URL);
    }

    @Test
    void getAllStoreCategories() {
        // Initialize the database
        storeCategoriesRepository.save(storeCategories).block();

        // Get all the storeCategoriesList
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
            .value(hasItem(storeCategories.getId()))
            .jsonPath("$.[*].userStoreOwnerId")
            .value(hasItem(DEFAULT_USER_STORE_OWNER_ID))
            .jsonPath("$.[*].categoryName")
            .value(hasItem(DEFAULT_CATEGORY_NAME))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].categoryNameInArabic")
            .value(hasItem(DEFAULT_CATEGORY_NAME_IN_ARABIC))
            .jsonPath("$.[*].descriptionInArabic")
            .value(hasItem(DEFAULT_DESCRIPTION_IN_ARABIC))
            .jsonPath("$.[*].userStoreOwnerIdImageUrl")
            .value(hasItem(DEFAULT_USER_STORE_OWNER_ID_IMAGE_URL));
    }

    @Test
    void getStoreCategories() {
        // Initialize the database
        storeCategoriesRepository.save(storeCategories).block();

        // Get the storeCategories
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, storeCategories.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(storeCategories.getId()))
            .jsonPath("$.userStoreOwnerId")
            .value(is(DEFAULT_USER_STORE_OWNER_ID))
            .jsonPath("$.categoryName")
            .value(is(DEFAULT_CATEGORY_NAME))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.categoryNameInArabic")
            .value(is(DEFAULT_CATEGORY_NAME_IN_ARABIC))
            .jsonPath("$.descriptionInArabic")
            .value(is(DEFAULT_DESCRIPTION_IN_ARABIC))
            .jsonPath("$.userStoreOwnerIdImageUrl")
            .value(is(DEFAULT_USER_STORE_OWNER_ID_IMAGE_URL));
    }

    @Test
    void getNonExistingStoreCategories() {
        // Get the storeCategories
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewStoreCategories() throws Exception {
        // Initialize the database
        storeCategoriesRepository.save(storeCategories).block();

        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();

        // Update the storeCategories
        StoreCategories updatedStoreCategories = storeCategoriesRepository.findById(storeCategories.getId()).block();
        updatedStoreCategories
            .userStoreOwnerId(UPDATED_USER_STORE_OWNER_ID)
            .categoryName(UPDATED_CATEGORY_NAME)
            .description(UPDATED_DESCRIPTION)
            .categoryNameInArabic(UPDATED_CATEGORY_NAME_IN_ARABIC)
            .descriptionInArabic(UPDATED_DESCRIPTION_IN_ARABIC)
            .userStoreOwnerIdImageUrl(UPDATED_USER_STORE_OWNER_ID_IMAGE_URL);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedStoreCategories.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedStoreCategories))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
        StoreCategories testStoreCategories = storeCategoriesList.get(storeCategoriesList.size() - 1);
        assertThat(testStoreCategories.getUserStoreOwnerId()).isEqualTo(UPDATED_USER_STORE_OWNER_ID);
        assertThat(testStoreCategories.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testStoreCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStoreCategories.getCategoryNameInArabic()).isEqualTo(UPDATED_CATEGORY_NAME_IN_ARABIC);
        assertThat(testStoreCategories.getDescriptionInArabic()).isEqualTo(UPDATED_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreCategories.getUserStoreOwnerIdImageUrl()).isEqualTo(UPDATED_USER_STORE_OWNER_ID_IMAGE_URL);
    }

    @Test
    void putNonExistingStoreCategories() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();
        storeCategories.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, storeCategories.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStoreCategories() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();
        storeCategories.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStoreCategories() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();
        storeCategories.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStoreCategoriesWithPatch() throws Exception {
        // Initialize the database
        storeCategoriesRepository.save(storeCategories).block();

        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();

        // Update the storeCategories using partial update
        StoreCategories partialUpdatedStoreCategories = new StoreCategories();
        partialUpdatedStoreCategories.setId(storeCategories.getId());

        partialUpdatedStoreCategories
            .categoryName(UPDATED_CATEGORY_NAME)
            .descriptionInArabic(UPDATED_DESCRIPTION_IN_ARABIC)
            .userStoreOwnerIdImageUrl(UPDATED_USER_STORE_OWNER_ID_IMAGE_URL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStoreCategories.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreCategories))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
        StoreCategories testStoreCategories = storeCategoriesList.get(storeCategoriesList.size() - 1);
        assertThat(testStoreCategories.getUserStoreOwnerId()).isEqualTo(DEFAULT_USER_STORE_OWNER_ID);
        assertThat(testStoreCategories.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testStoreCategories.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStoreCategories.getCategoryNameInArabic()).isEqualTo(DEFAULT_CATEGORY_NAME_IN_ARABIC);
        assertThat(testStoreCategories.getDescriptionInArabic()).isEqualTo(UPDATED_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreCategories.getUserStoreOwnerIdImageUrl()).isEqualTo(UPDATED_USER_STORE_OWNER_ID_IMAGE_URL);
    }

    @Test
    void fullUpdateStoreCategoriesWithPatch() throws Exception {
        // Initialize the database
        storeCategoriesRepository.save(storeCategories).block();

        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();

        // Update the storeCategories using partial update
        StoreCategories partialUpdatedStoreCategories = new StoreCategories();
        partialUpdatedStoreCategories.setId(storeCategories.getId());

        partialUpdatedStoreCategories
            .userStoreOwnerId(UPDATED_USER_STORE_OWNER_ID)
            .categoryName(UPDATED_CATEGORY_NAME)
            .description(UPDATED_DESCRIPTION)
            .categoryNameInArabic(UPDATED_CATEGORY_NAME_IN_ARABIC)
            .descriptionInArabic(UPDATED_DESCRIPTION_IN_ARABIC)
            .userStoreOwnerIdImageUrl(UPDATED_USER_STORE_OWNER_ID_IMAGE_URL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStoreCategories.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreCategories))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
        StoreCategories testStoreCategories = storeCategoriesList.get(storeCategoriesList.size() - 1);
        assertThat(testStoreCategories.getUserStoreOwnerId()).isEqualTo(UPDATED_USER_STORE_OWNER_ID);
        assertThat(testStoreCategories.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testStoreCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStoreCategories.getCategoryNameInArabic()).isEqualTo(UPDATED_CATEGORY_NAME_IN_ARABIC);
        assertThat(testStoreCategories.getDescriptionInArabic()).isEqualTo(UPDATED_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreCategories.getUserStoreOwnerIdImageUrl()).isEqualTo(UPDATED_USER_STORE_OWNER_ID_IMAGE_URL);
    }

    @Test
    void patchNonExistingStoreCategories() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();
        storeCategories.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, storeCategories.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStoreCategories() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();
        storeCategories.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStoreCategories() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoriesRepository.findAll().collectList().block().size();
        storeCategories.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeCategories))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StoreCategories in the database
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStoreCategories() {
        // Initialize the database
        storeCategoriesRepository.save(storeCategories).block();

        int databaseSizeBeforeDelete = storeCategoriesRepository.findAll().collectList().block().size();

        // Delete the storeCategories
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, storeCategories.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<StoreCategories> storeCategoriesList = storeCategoriesRepository.findAll().collectList().block();
        assertThat(storeCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
