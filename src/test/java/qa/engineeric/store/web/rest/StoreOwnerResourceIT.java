package qa.engineeric.store.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static qa.engineeric.store.web.rest.TestUtil.sameInstant;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import qa.engineeric.store.domain.StoreOwner;
import qa.engineeric.store.repository.StoreOwnerRepository;

/**
 * Integration tests for the {@link StoreOwnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class StoreOwnerResourceIT {

    private static final String DEFAULT_USER_STORE_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_STORE_OWNER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STORE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_NAME_IN_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_STORE_NAME_IN_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_IN_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_IN_ARABIC = "BBBBBBBBBB";

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Boolean DEFAULT_IS_BUSY = false;
    private static final Boolean UPDATED_IS_BUSY = true;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_IN_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_CITY_IN_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_IN_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_IN_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STORE_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STORE_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_STORE_LOGO_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Boolean DEFAULT_HAS_DELIVERY = false;
    private static final Boolean UPDATED_HAS_DELIVERY = true;

    private static final Boolean DEFAULT_HAS_FREE_DELIVERY = false;
    private static final Boolean UPDATED_HAS_FREE_DELIVERY = true;

    private static final ZonedDateTime DEFAULT_AVAILABLE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AVAILABLE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Instant DEFAULT_SHOP_OPEINING_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHOP_OPEINING_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SHOP_CLOSING_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHOP_CLOSING_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final Double DEFAULT_DELIVERY_COST = 1D;
    private static final Double UPDATED_DELIVERY_COST = 2D;

    private static final String DEFAULT_WEB_KEY = "AAAAAAAAAA";
    private static final String UPDATED_WEB_KEY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/store-owners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StoreOwnerRepository storeOwnerRepository;

    @Autowired
    private WebTestClient webTestClient;

    private StoreOwner storeOwner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreOwner createEntity() {
        StoreOwner storeOwner = new StoreOwner()
            .userStoreOwnerId(DEFAULT_USER_STORE_OWNER_ID)
            .storeName(DEFAULT_STORE_NAME)
            .storeNameInArabic(DEFAULT_STORE_NAME_IN_ARABIC)
            .address(DEFAULT_ADDRESS)
            .addressInArabic(DEFAULT_ADDRESS_IN_ARABIC)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .isBusy(DEFAULT_IS_BUSY)
            .city(DEFAULT_CITY)
            .cityInArabic(DEFAULT_CITY_IN_ARABIC)
            .description(DEFAULT_DESCRIPTION)
            .descriptionInArabic(DEFAULT_DESCRIPTION_IN_ARABIC)
            .storeContactNumber(DEFAULT_STORE_CONTACT_NUMBER)
            .createdDate(DEFAULT_CREATED_DATE)
            .storeLogoUrl(DEFAULT_STORE_LOGO_URL)
            .isActive(DEFAULT_IS_ACTIVE)
            .hasDelivery(DEFAULT_HAS_DELIVERY)
            .hasFreeDelivery(DEFAULT_HAS_FREE_DELIVERY)
            .availableDateTime(DEFAULT_AVAILABLE_DATE_TIME)
            .shopOpeiningTime(DEFAULT_SHOP_OPEINING_TIME)
            .shopClosingTime(DEFAULT_SHOP_CLOSING_TIME)
            .currency(DEFAULT_CURRENCY)
            .deliveryCost(DEFAULT_DELIVERY_COST)
            .webKey(DEFAULT_WEB_KEY);
        return storeOwner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreOwner createUpdatedEntity() {
        StoreOwner storeOwner = new StoreOwner()
            .userStoreOwnerId(UPDATED_USER_STORE_OWNER_ID)
            .storeName(UPDATED_STORE_NAME)
            .storeNameInArabic(UPDATED_STORE_NAME_IN_ARABIC)
            .address(UPDATED_ADDRESS)
            .addressInArabic(UPDATED_ADDRESS_IN_ARABIC)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .isBusy(UPDATED_IS_BUSY)
            .city(UPDATED_CITY)
            .cityInArabic(UPDATED_CITY_IN_ARABIC)
            .description(UPDATED_DESCRIPTION)
            .descriptionInArabic(UPDATED_DESCRIPTION_IN_ARABIC)
            .storeContactNumber(UPDATED_STORE_CONTACT_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .storeLogoUrl(UPDATED_STORE_LOGO_URL)
            .isActive(UPDATED_IS_ACTIVE)
            .hasDelivery(UPDATED_HAS_DELIVERY)
            .hasFreeDelivery(UPDATED_HAS_FREE_DELIVERY)
            .availableDateTime(UPDATED_AVAILABLE_DATE_TIME)
            .shopOpeiningTime(UPDATED_SHOP_OPEINING_TIME)
            .shopClosingTime(UPDATED_SHOP_CLOSING_TIME)
            .currency(UPDATED_CURRENCY)
            .deliveryCost(UPDATED_DELIVERY_COST)
            .webKey(UPDATED_WEB_KEY);
        return storeOwner;
    }

    @BeforeEach
    public void initTest() {
        storeOwnerRepository.deleteAll().block();
        storeOwner = createEntity();
    }

    @Test
    void createStoreOwner() throws Exception {
        int databaseSizeBeforeCreate = storeOwnerRepository.findAll().collectList().block().size();
        // Create the StoreOwner
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeCreate + 1);
        StoreOwner testStoreOwner = storeOwnerList.get(storeOwnerList.size() - 1);
        assertThat(testStoreOwner.getUserStoreOwnerId()).isEqualTo(DEFAULT_USER_STORE_OWNER_ID);
        assertThat(testStoreOwner.getStoreName()).isEqualTo(DEFAULT_STORE_NAME);
        assertThat(testStoreOwner.getStoreNameInArabic()).isEqualTo(DEFAULT_STORE_NAME_IN_ARABIC);
        assertThat(testStoreOwner.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testStoreOwner.getAddressInArabic()).isEqualTo(DEFAULT_ADDRESS_IN_ARABIC);
        assertThat(testStoreOwner.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testStoreOwner.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testStoreOwner.getIsBusy()).isEqualTo(DEFAULT_IS_BUSY);
        assertThat(testStoreOwner.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStoreOwner.getCityInArabic()).isEqualTo(DEFAULT_CITY_IN_ARABIC);
        assertThat(testStoreOwner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStoreOwner.getDescriptionInArabic()).isEqualTo(DEFAULT_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreOwner.getStoreContactNumber()).isEqualTo(DEFAULT_STORE_CONTACT_NUMBER);
        assertThat(testStoreOwner.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStoreOwner.getStoreLogoUrl()).isEqualTo(DEFAULT_STORE_LOGO_URL);
        assertThat(testStoreOwner.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testStoreOwner.getHasDelivery()).isEqualTo(DEFAULT_HAS_DELIVERY);
        assertThat(testStoreOwner.getHasFreeDelivery()).isEqualTo(DEFAULT_HAS_FREE_DELIVERY);
        assertThat(testStoreOwner.getAvailableDateTime()).isEqualTo(DEFAULT_AVAILABLE_DATE_TIME);
        assertThat(testStoreOwner.getShopOpeiningTime()).isEqualTo(DEFAULT_SHOP_OPEINING_TIME);
        assertThat(testStoreOwner.getShopClosingTime()).isEqualTo(DEFAULT_SHOP_CLOSING_TIME);
        assertThat(testStoreOwner.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testStoreOwner.getDeliveryCost()).isEqualTo(DEFAULT_DELIVERY_COST);
        assertThat(testStoreOwner.getWebKey()).isEqualTo(DEFAULT_WEB_KEY);
    }

    @Test
    void createStoreOwnerWithExistingId() throws Exception {
        // Create the StoreOwner with an existing ID
        storeOwner.setId("existing_id");

        int databaseSizeBeforeCreate = storeOwnerRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkUserStoreOwnerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setUserStoreOwnerId(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStoreNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setStoreName(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStoreNameInArabicIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setStoreNameInArabic(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setAddress(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressInArabicIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setAddressInArabic(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setLongitude(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setLatitude(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIsBusyIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setIsBusy(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setCity(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCityInArabicIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setCityInArabic(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkHasDeliveryIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setHasDelivery(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAvailableDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeOwnerRepository.findAll().collectList().block().size();
        // set the field null
        storeOwner.setAvailableDateTime(null);

        // Create the StoreOwner, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllStoreOwnersAsStream() {
        // Initialize the database
        storeOwnerRepository.save(storeOwner).block();

        List<StoreOwner> storeOwnerList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(StoreOwner.class)
            .getResponseBody()
            .filter(storeOwner::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(storeOwnerList).isNotNull();
        assertThat(storeOwnerList).hasSize(1);
        StoreOwner testStoreOwner = storeOwnerList.get(0);
        assertThat(testStoreOwner.getUserStoreOwnerId()).isEqualTo(DEFAULT_USER_STORE_OWNER_ID);
        assertThat(testStoreOwner.getStoreName()).isEqualTo(DEFAULT_STORE_NAME);
        assertThat(testStoreOwner.getStoreNameInArabic()).isEqualTo(DEFAULT_STORE_NAME_IN_ARABIC);
        assertThat(testStoreOwner.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testStoreOwner.getAddressInArabic()).isEqualTo(DEFAULT_ADDRESS_IN_ARABIC);
        assertThat(testStoreOwner.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testStoreOwner.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testStoreOwner.getIsBusy()).isEqualTo(DEFAULT_IS_BUSY);
        assertThat(testStoreOwner.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStoreOwner.getCityInArabic()).isEqualTo(DEFAULT_CITY_IN_ARABIC);
        assertThat(testStoreOwner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStoreOwner.getDescriptionInArabic()).isEqualTo(DEFAULT_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreOwner.getStoreContactNumber()).isEqualTo(DEFAULT_STORE_CONTACT_NUMBER);
        assertThat(testStoreOwner.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStoreOwner.getStoreLogoUrl()).isEqualTo(DEFAULT_STORE_LOGO_URL);
        assertThat(testStoreOwner.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testStoreOwner.getHasDelivery()).isEqualTo(DEFAULT_HAS_DELIVERY);
        assertThat(testStoreOwner.getHasFreeDelivery()).isEqualTo(DEFAULT_HAS_FREE_DELIVERY);
        assertThat(testStoreOwner.getAvailableDateTime()).isEqualTo(DEFAULT_AVAILABLE_DATE_TIME);
        assertThat(testStoreOwner.getShopOpeiningTime()).isEqualTo(DEFAULT_SHOP_OPEINING_TIME);
        assertThat(testStoreOwner.getShopClosingTime()).isEqualTo(DEFAULT_SHOP_CLOSING_TIME);
        assertThat(testStoreOwner.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testStoreOwner.getDeliveryCost()).isEqualTo(DEFAULT_DELIVERY_COST);
        assertThat(testStoreOwner.getWebKey()).isEqualTo(DEFAULT_WEB_KEY);
    }

    @Test
    void getAllStoreOwners() {
        // Initialize the database
        storeOwnerRepository.save(storeOwner).block();

        // Get all the storeOwnerList
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
            .value(hasItem(storeOwner.getId()))
            .jsonPath("$.[*].userStoreOwnerId")
            .value(hasItem(DEFAULT_USER_STORE_OWNER_ID))
            .jsonPath("$.[*].storeName")
            .value(hasItem(DEFAULT_STORE_NAME))
            .jsonPath("$.[*].storeNameInArabic")
            .value(hasItem(DEFAULT_STORE_NAME_IN_ARABIC))
            .jsonPath("$.[*].address")
            .value(hasItem(DEFAULT_ADDRESS))
            .jsonPath("$.[*].addressInArabic")
            .value(hasItem(DEFAULT_ADDRESS_IN_ARABIC))
            .jsonPath("$.[*].longitude")
            .value(hasItem(DEFAULT_LONGITUDE.doubleValue()))
            .jsonPath("$.[*].latitude")
            .value(hasItem(DEFAULT_LATITUDE.doubleValue()))
            .jsonPath("$.[*].isBusy")
            .value(hasItem(DEFAULT_IS_BUSY.booleanValue()))
            .jsonPath("$.[*].city")
            .value(hasItem(DEFAULT_CITY))
            .jsonPath("$.[*].cityInArabic")
            .value(hasItem(DEFAULT_CITY_IN_ARABIC))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].descriptionInArabic")
            .value(hasItem(DEFAULT_DESCRIPTION_IN_ARABIC))
            .jsonPath("$.[*].storeContactNumber")
            .value(hasItem(DEFAULT_STORE_CONTACT_NUMBER))
            .jsonPath("$.[*].createdDate")
            .value(hasItem(sameInstant(DEFAULT_CREATED_DATE)))
            .jsonPath("$.[*].storeLogoUrl")
            .value(hasItem(DEFAULT_STORE_LOGO_URL))
            .jsonPath("$.[*].isActive")
            .value(hasItem(DEFAULT_IS_ACTIVE.booleanValue()))
            .jsonPath("$.[*].hasDelivery")
            .value(hasItem(DEFAULT_HAS_DELIVERY.booleanValue()))
            .jsonPath("$.[*].hasFreeDelivery")
            .value(hasItem(DEFAULT_HAS_FREE_DELIVERY.booleanValue()))
            .jsonPath("$.[*].availableDateTime")
            .value(hasItem(sameInstant(DEFAULT_AVAILABLE_DATE_TIME)))
            .jsonPath("$.[*].shopOpeiningTime")
            .value(hasItem(DEFAULT_SHOP_OPEINING_TIME.toString()))
            .jsonPath("$.[*].shopClosingTime")
            .value(hasItem(DEFAULT_SHOP_CLOSING_TIME.toString()))
            .jsonPath("$.[*].currency")
            .value(hasItem(DEFAULT_CURRENCY))
            .jsonPath("$.[*].deliveryCost")
            .value(hasItem(DEFAULT_DELIVERY_COST.doubleValue()))
            .jsonPath("$.[*].webKey")
            .value(hasItem(DEFAULT_WEB_KEY));
    }

    @Test
    void getStoreOwner() {
        // Initialize the database
        storeOwnerRepository.save(storeOwner).block();

        // Get the storeOwner
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, storeOwner.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(storeOwner.getId()))
            .jsonPath("$.userStoreOwnerId")
            .value(is(DEFAULT_USER_STORE_OWNER_ID))
            .jsonPath("$.storeName")
            .value(is(DEFAULT_STORE_NAME))
            .jsonPath("$.storeNameInArabic")
            .value(is(DEFAULT_STORE_NAME_IN_ARABIC))
            .jsonPath("$.address")
            .value(is(DEFAULT_ADDRESS))
            .jsonPath("$.addressInArabic")
            .value(is(DEFAULT_ADDRESS_IN_ARABIC))
            .jsonPath("$.longitude")
            .value(is(DEFAULT_LONGITUDE.doubleValue()))
            .jsonPath("$.latitude")
            .value(is(DEFAULT_LATITUDE.doubleValue()))
            .jsonPath("$.isBusy")
            .value(is(DEFAULT_IS_BUSY.booleanValue()))
            .jsonPath("$.city")
            .value(is(DEFAULT_CITY))
            .jsonPath("$.cityInArabic")
            .value(is(DEFAULT_CITY_IN_ARABIC))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.descriptionInArabic")
            .value(is(DEFAULT_DESCRIPTION_IN_ARABIC))
            .jsonPath("$.storeContactNumber")
            .value(is(DEFAULT_STORE_CONTACT_NUMBER))
            .jsonPath("$.createdDate")
            .value(is(sameInstant(DEFAULT_CREATED_DATE)))
            .jsonPath("$.storeLogoUrl")
            .value(is(DEFAULT_STORE_LOGO_URL))
            .jsonPath("$.isActive")
            .value(is(DEFAULT_IS_ACTIVE.booleanValue()))
            .jsonPath("$.hasDelivery")
            .value(is(DEFAULT_HAS_DELIVERY.booleanValue()))
            .jsonPath("$.hasFreeDelivery")
            .value(is(DEFAULT_HAS_FREE_DELIVERY.booleanValue()))
            .jsonPath("$.availableDateTime")
            .value(is(sameInstant(DEFAULT_AVAILABLE_DATE_TIME)))
            .jsonPath("$.shopOpeiningTime")
            .value(is(DEFAULT_SHOP_OPEINING_TIME.toString()))
            .jsonPath("$.shopClosingTime")
            .value(is(DEFAULT_SHOP_CLOSING_TIME.toString()))
            .jsonPath("$.currency")
            .value(is(DEFAULT_CURRENCY))
            .jsonPath("$.deliveryCost")
            .value(is(DEFAULT_DELIVERY_COST.doubleValue()))
            .jsonPath("$.webKey")
            .value(is(DEFAULT_WEB_KEY));
    }

    @Test
    void getNonExistingStoreOwner() {
        // Get the storeOwner
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewStoreOwner() throws Exception {
        // Initialize the database
        storeOwnerRepository.save(storeOwner).block();

        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();

        // Update the storeOwner
        StoreOwner updatedStoreOwner = storeOwnerRepository.findById(storeOwner.getId()).block();
        updatedStoreOwner
            .userStoreOwnerId(UPDATED_USER_STORE_OWNER_ID)
            .storeName(UPDATED_STORE_NAME)
            .storeNameInArabic(UPDATED_STORE_NAME_IN_ARABIC)
            .address(UPDATED_ADDRESS)
            .addressInArabic(UPDATED_ADDRESS_IN_ARABIC)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .isBusy(UPDATED_IS_BUSY)
            .city(UPDATED_CITY)
            .cityInArabic(UPDATED_CITY_IN_ARABIC)
            .description(UPDATED_DESCRIPTION)
            .descriptionInArabic(UPDATED_DESCRIPTION_IN_ARABIC)
            .storeContactNumber(UPDATED_STORE_CONTACT_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .storeLogoUrl(UPDATED_STORE_LOGO_URL)
            .isActive(UPDATED_IS_ACTIVE)
            .hasDelivery(UPDATED_HAS_DELIVERY)
            .hasFreeDelivery(UPDATED_HAS_FREE_DELIVERY)
            .availableDateTime(UPDATED_AVAILABLE_DATE_TIME)
            .shopOpeiningTime(UPDATED_SHOP_OPEINING_TIME)
            .shopClosingTime(UPDATED_SHOP_CLOSING_TIME)
            .currency(UPDATED_CURRENCY)
            .deliveryCost(UPDATED_DELIVERY_COST)
            .webKey(UPDATED_WEB_KEY);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedStoreOwner.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedStoreOwner))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
        StoreOwner testStoreOwner = storeOwnerList.get(storeOwnerList.size() - 1);
        assertThat(testStoreOwner.getUserStoreOwnerId()).isEqualTo(UPDATED_USER_STORE_OWNER_ID);
        assertThat(testStoreOwner.getStoreName()).isEqualTo(UPDATED_STORE_NAME);
        assertThat(testStoreOwner.getStoreNameInArabic()).isEqualTo(UPDATED_STORE_NAME_IN_ARABIC);
        assertThat(testStoreOwner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStoreOwner.getAddressInArabic()).isEqualTo(UPDATED_ADDRESS_IN_ARABIC);
        assertThat(testStoreOwner.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStoreOwner.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStoreOwner.getIsBusy()).isEqualTo(UPDATED_IS_BUSY);
        assertThat(testStoreOwner.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testStoreOwner.getCityInArabic()).isEqualTo(UPDATED_CITY_IN_ARABIC);
        assertThat(testStoreOwner.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStoreOwner.getDescriptionInArabic()).isEqualTo(UPDATED_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreOwner.getStoreContactNumber()).isEqualTo(UPDATED_STORE_CONTACT_NUMBER);
        assertThat(testStoreOwner.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStoreOwner.getStoreLogoUrl()).isEqualTo(UPDATED_STORE_LOGO_URL);
        assertThat(testStoreOwner.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testStoreOwner.getHasDelivery()).isEqualTo(UPDATED_HAS_DELIVERY);
        assertThat(testStoreOwner.getHasFreeDelivery()).isEqualTo(UPDATED_HAS_FREE_DELIVERY);
        assertThat(testStoreOwner.getAvailableDateTime()).isEqualTo(UPDATED_AVAILABLE_DATE_TIME);
        assertThat(testStoreOwner.getShopOpeiningTime()).isEqualTo(UPDATED_SHOP_OPEINING_TIME);
        assertThat(testStoreOwner.getShopClosingTime()).isEqualTo(UPDATED_SHOP_CLOSING_TIME);
        assertThat(testStoreOwner.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testStoreOwner.getDeliveryCost()).isEqualTo(UPDATED_DELIVERY_COST);
        assertThat(testStoreOwner.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
    }

    @Test
    void putNonExistingStoreOwner() throws Exception {
        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();
        storeOwner.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, storeOwner.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStoreOwner() throws Exception {
        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();
        storeOwner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStoreOwner() throws Exception {
        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();
        storeOwner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStoreOwnerWithPatch() throws Exception {
        // Initialize the database
        storeOwnerRepository.save(storeOwner).block();

        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();

        // Update the storeOwner using partial update
        StoreOwner partialUpdatedStoreOwner = new StoreOwner();
        partialUpdatedStoreOwner.setId(storeOwner.getId());

        partialUpdatedStoreOwner
            .storeNameInArabic(UPDATED_STORE_NAME_IN_ARABIC)
            .address(UPDATED_ADDRESS)
            .addressInArabic(UPDATED_ADDRESS_IN_ARABIC)
            .isBusy(UPDATED_IS_BUSY)
            .descriptionInArabic(UPDATED_DESCRIPTION_IN_ARABIC)
            .storeContactNumber(UPDATED_STORE_CONTACT_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .storeLogoUrl(UPDATED_STORE_LOGO_URL)
            .isActive(UPDATED_IS_ACTIVE)
            .hasDelivery(UPDATED_HAS_DELIVERY)
            .availableDateTime(UPDATED_AVAILABLE_DATE_TIME)
            .shopOpeiningTime(UPDATED_SHOP_OPEINING_TIME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStoreOwner.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreOwner))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
        StoreOwner testStoreOwner = storeOwnerList.get(storeOwnerList.size() - 1);
        assertThat(testStoreOwner.getUserStoreOwnerId()).isEqualTo(DEFAULT_USER_STORE_OWNER_ID);
        assertThat(testStoreOwner.getStoreName()).isEqualTo(DEFAULT_STORE_NAME);
        assertThat(testStoreOwner.getStoreNameInArabic()).isEqualTo(UPDATED_STORE_NAME_IN_ARABIC);
        assertThat(testStoreOwner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStoreOwner.getAddressInArabic()).isEqualTo(UPDATED_ADDRESS_IN_ARABIC);
        assertThat(testStoreOwner.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testStoreOwner.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testStoreOwner.getIsBusy()).isEqualTo(UPDATED_IS_BUSY);
        assertThat(testStoreOwner.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStoreOwner.getCityInArabic()).isEqualTo(DEFAULT_CITY_IN_ARABIC);
        assertThat(testStoreOwner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStoreOwner.getDescriptionInArabic()).isEqualTo(UPDATED_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreOwner.getStoreContactNumber()).isEqualTo(UPDATED_STORE_CONTACT_NUMBER);
        assertThat(testStoreOwner.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStoreOwner.getStoreLogoUrl()).isEqualTo(UPDATED_STORE_LOGO_URL);
        assertThat(testStoreOwner.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testStoreOwner.getHasDelivery()).isEqualTo(UPDATED_HAS_DELIVERY);
        assertThat(testStoreOwner.getHasFreeDelivery()).isEqualTo(DEFAULT_HAS_FREE_DELIVERY);
        assertThat(testStoreOwner.getAvailableDateTime()).isEqualTo(UPDATED_AVAILABLE_DATE_TIME);
        assertThat(testStoreOwner.getShopOpeiningTime()).isEqualTo(UPDATED_SHOP_OPEINING_TIME);
        assertThat(testStoreOwner.getShopClosingTime()).isEqualTo(DEFAULT_SHOP_CLOSING_TIME);
        assertThat(testStoreOwner.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testStoreOwner.getDeliveryCost()).isEqualTo(DEFAULT_DELIVERY_COST);
        assertThat(testStoreOwner.getWebKey()).isEqualTo(DEFAULT_WEB_KEY);
    }

    @Test
    void fullUpdateStoreOwnerWithPatch() throws Exception {
        // Initialize the database
        storeOwnerRepository.save(storeOwner).block();

        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();

        // Update the storeOwner using partial update
        StoreOwner partialUpdatedStoreOwner = new StoreOwner();
        partialUpdatedStoreOwner.setId(storeOwner.getId());

        partialUpdatedStoreOwner
            .userStoreOwnerId(UPDATED_USER_STORE_OWNER_ID)
            .storeName(UPDATED_STORE_NAME)
            .storeNameInArabic(UPDATED_STORE_NAME_IN_ARABIC)
            .address(UPDATED_ADDRESS)
            .addressInArabic(UPDATED_ADDRESS_IN_ARABIC)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .isBusy(UPDATED_IS_BUSY)
            .city(UPDATED_CITY)
            .cityInArabic(UPDATED_CITY_IN_ARABIC)
            .description(UPDATED_DESCRIPTION)
            .descriptionInArabic(UPDATED_DESCRIPTION_IN_ARABIC)
            .storeContactNumber(UPDATED_STORE_CONTACT_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .storeLogoUrl(UPDATED_STORE_LOGO_URL)
            .isActive(UPDATED_IS_ACTIVE)
            .hasDelivery(UPDATED_HAS_DELIVERY)
            .hasFreeDelivery(UPDATED_HAS_FREE_DELIVERY)
            .availableDateTime(UPDATED_AVAILABLE_DATE_TIME)
            .shopOpeiningTime(UPDATED_SHOP_OPEINING_TIME)
            .shopClosingTime(UPDATED_SHOP_CLOSING_TIME)
            .currency(UPDATED_CURRENCY)
            .deliveryCost(UPDATED_DELIVERY_COST)
            .webKey(UPDATED_WEB_KEY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStoreOwner.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreOwner))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
        StoreOwner testStoreOwner = storeOwnerList.get(storeOwnerList.size() - 1);
        assertThat(testStoreOwner.getUserStoreOwnerId()).isEqualTo(UPDATED_USER_STORE_OWNER_ID);
        assertThat(testStoreOwner.getStoreName()).isEqualTo(UPDATED_STORE_NAME);
        assertThat(testStoreOwner.getStoreNameInArabic()).isEqualTo(UPDATED_STORE_NAME_IN_ARABIC);
        assertThat(testStoreOwner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStoreOwner.getAddressInArabic()).isEqualTo(UPDATED_ADDRESS_IN_ARABIC);
        assertThat(testStoreOwner.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStoreOwner.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStoreOwner.getIsBusy()).isEqualTo(UPDATED_IS_BUSY);
        assertThat(testStoreOwner.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testStoreOwner.getCityInArabic()).isEqualTo(UPDATED_CITY_IN_ARABIC);
        assertThat(testStoreOwner.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStoreOwner.getDescriptionInArabic()).isEqualTo(UPDATED_DESCRIPTION_IN_ARABIC);
        assertThat(testStoreOwner.getStoreContactNumber()).isEqualTo(UPDATED_STORE_CONTACT_NUMBER);
        assertThat(testStoreOwner.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStoreOwner.getStoreLogoUrl()).isEqualTo(UPDATED_STORE_LOGO_URL);
        assertThat(testStoreOwner.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testStoreOwner.getHasDelivery()).isEqualTo(UPDATED_HAS_DELIVERY);
        assertThat(testStoreOwner.getHasFreeDelivery()).isEqualTo(UPDATED_HAS_FREE_DELIVERY);
        assertThat(testStoreOwner.getAvailableDateTime()).isEqualTo(UPDATED_AVAILABLE_DATE_TIME);
        assertThat(testStoreOwner.getShopOpeiningTime()).isEqualTo(UPDATED_SHOP_OPEINING_TIME);
        assertThat(testStoreOwner.getShopClosingTime()).isEqualTo(UPDATED_SHOP_CLOSING_TIME);
        assertThat(testStoreOwner.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testStoreOwner.getDeliveryCost()).isEqualTo(UPDATED_DELIVERY_COST);
        assertThat(testStoreOwner.getWebKey()).isEqualTo(UPDATED_WEB_KEY);
    }

    @Test
    void patchNonExistingStoreOwner() throws Exception {
        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();
        storeOwner.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, storeOwner.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStoreOwner() throws Exception {
        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();
        storeOwner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStoreOwner() throws Exception {
        int databaseSizeBeforeUpdate = storeOwnerRepository.findAll().collectList().block().size();
        storeOwner.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(storeOwner))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StoreOwner in the database
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStoreOwner() {
        // Initialize the database
        storeOwnerRepository.save(storeOwner).block();

        int databaseSizeBeforeDelete = storeOwnerRepository.findAll().collectList().block().size();

        // Delete the storeOwner
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, storeOwner.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<StoreOwner> storeOwnerList = storeOwnerRepository.findAll().collectList().block();
        assertThat(storeOwnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
