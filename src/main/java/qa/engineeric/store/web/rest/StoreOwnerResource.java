package qa.engineeric.store.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import qa.engineeric.store.domain.StoreOwner;
import qa.engineeric.store.repository.StoreOwnerRepository;
import qa.engineeric.store.web.rest.errors.BadRequestAlertException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link qa.engineeric.store.domain.StoreOwner}.
 */
@RestController
@RequestMapping("/api")
public class StoreOwnerResource {

    private final Logger log = LoggerFactory.getLogger(StoreOwnerResource.class);

    private static final String ENTITY_NAME = "storeOwner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreOwnerRepository storeOwnerRepository;

    public StoreOwnerResource(StoreOwnerRepository storeOwnerRepository) {
        this.storeOwnerRepository = storeOwnerRepository;
    }

    /**
     * {@code POST  /store-owners} : Create a new storeOwner.
     *
     * @param storeOwner the storeOwner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeOwner, or with status {@code 400 (Bad Request)} if the storeOwner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/store-owners")
    public Mono<ResponseEntity<StoreOwner>> createStoreOwner(@Valid @RequestBody StoreOwner storeOwner) throws URISyntaxException {
        log.debug("REST request to save StoreOwner : {}", storeOwner);
        if (storeOwner.getId() != null) {
            throw new BadRequestAlertException("A new storeOwner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return storeOwnerRepository
            .save(storeOwner)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/store-owners/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /store-owners/:id} : Updates an existing storeOwner.
     *
     * @param id the id of the storeOwner to save.
     * @param storeOwner the storeOwner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeOwner,
     * or with status {@code 400 (Bad Request)} if the storeOwner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeOwner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/store-owners/{id}")
    public Mono<ResponseEntity<StoreOwner>> updateStoreOwner(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody StoreOwner storeOwner
    ) throws URISyntaxException {
        log.debug("REST request to update StoreOwner : {}, {}", id, storeOwner);
        if (storeOwner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeOwner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return storeOwnerRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return storeOwnerRepository
                    .save(storeOwner)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /store-owners/:id} : Partial updates given fields of an existing storeOwner, field will ignore if it is null
     *
     * @param id the id of the storeOwner to save.
     * @param storeOwner the storeOwner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeOwner,
     * or with status {@code 400 (Bad Request)} if the storeOwner is not valid,
     * or with status {@code 404 (Not Found)} if the storeOwner is not found,
     * or with status {@code 500 (Internal Server Error)} if the storeOwner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/store-owners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<StoreOwner>> partialUpdateStoreOwner(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody StoreOwner storeOwner
    ) throws URISyntaxException {
        log.debug("REST request to partial update StoreOwner partially : {}, {}", id, storeOwner);
        if (storeOwner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeOwner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return storeOwnerRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<StoreOwner> result = storeOwnerRepository
                    .findById(storeOwner.getId())
                    .map(existingStoreOwner -> {
                        if (storeOwner.getUserStoreOwnerId() != null) {
                            existingStoreOwner.setUserStoreOwnerId(storeOwner.getUserStoreOwnerId());
                        }
                        if (storeOwner.getStoreName() != null) {
                            existingStoreOwner.setStoreName(storeOwner.getStoreName());
                        }
                        if (storeOwner.getStoreNameInArabic() != null) {
                            existingStoreOwner.setStoreNameInArabic(storeOwner.getStoreNameInArabic());
                        }
                        if (storeOwner.getAddress() != null) {
                            existingStoreOwner.setAddress(storeOwner.getAddress());
                        }
                        if (storeOwner.getAddressInArabic() != null) {
                            existingStoreOwner.setAddressInArabic(storeOwner.getAddressInArabic());
                        }
                        if (storeOwner.getLongitude() != null) {
                            existingStoreOwner.setLongitude(storeOwner.getLongitude());
                        }
                        if (storeOwner.getLatitude() != null) {
                            existingStoreOwner.setLatitude(storeOwner.getLatitude());
                        }
                        if (storeOwner.getIsBusy() != null) {
                            existingStoreOwner.setIsBusy(storeOwner.getIsBusy());
                        }
                        if (storeOwner.getCity() != null) {
                            existingStoreOwner.setCity(storeOwner.getCity());
                        }
                        if (storeOwner.getCityInArabic() != null) {
                            existingStoreOwner.setCityInArabic(storeOwner.getCityInArabic());
                        }
                        if (storeOwner.getDescription() != null) {
                            existingStoreOwner.setDescription(storeOwner.getDescription());
                        }
                        if (storeOwner.getDescriptionInArabic() != null) {
                            existingStoreOwner.setDescriptionInArabic(storeOwner.getDescriptionInArabic());
                        }
                        if (storeOwner.getStoreContactNumber() != null) {
                            existingStoreOwner.setStoreContactNumber(storeOwner.getStoreContactNumber());
                        }
                        if (storeOwner.getCreatedDate() != null) {
                            existingStoreOwner.setCreatedDate(storeOwner.getCreatedDate());
                        }
                        if (storeOwner.getStoreLogoUrl() != null) {
                            existingStoreOwner.setStoreLogoUrl(storeOwner.getStoreLogoUrl());
                        }
                        if (storeOwner.getIsActive() != null) {
                            existingStoreOwner.setIsActive(storeOwner.getIsActive());
                        }
                        if (storeOwner.getHasDelivery() != null) {
                            existingStoreOwner.setHasDelivery(storeOwner.getHasDelivery());
                        }
                        if (storeOwner.getHasFreeDelivery() != null) {
                            existingStoreOwner.setHasFreeDelivery(storeOwner.getHasFreeDelivery());
                        }
                        if (storeOwner.getAvailableDateTime() != null) {
                            existingStoreOwner.setAvailableDateTime(storeOwner.getAvailableDateTime());
                        }
                        if (storeOwner.getShopOpeiningTime() != null) {
                            existingStoreOwner.setShopOpeiningTime(storeOwner.getShopOpeiningTime());
                        }
                        if (storeOwner.getShopClosingTime() != null) {
                            existingStoreOwner.setShopClosingTime(storeOwner.getShopClosingTime());
                        }
                        if (storeOwner.getCurrency() != null) {
                            existingStoreOwner.setCurrency(storeOwner.getCurrency());
                        }
                        if (storeOwner.getDeliveryCost() != null) {
                            existingStoreOwner.setDeliveryCost(storeOwner.getDeliveryCost());
                        }
                        if (storeOwner.getWebKey() != null) {
                            existingStoreOwner.setWebKey(storeOwner.getWebKey());
                        }

                        return existingStoreOwner;
                    })
                    .flatMap(storeOwnerRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /store-owners} : get all the storeOwners.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storeOwners in body.
     */
    @GetMapping("/store-owners")
    public Mono<List<StoreOwner>> getAllStoreOwners() {
        log.debug("REST request to get all StoreOwners");
        return storeOwnerRepository.findAll().collectList();
    }

    /**
     * {@code GET  /store-owners} : get all the storeOwners as a stream.
     * @return the {@link Flux} of storeOwners.
     */
    @GetMapping(value = "/store-owners", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<StoreOwner> getAllStoreOwnersAsStream() {
        log.debug("REST request to get all StoreOwners as a stream");
        return storeOwnerRepository.findAll();
    }

    /**
     * {@code GET  /store-owners/:id} : get the "id" storeOwner.
     *
     * @param id the id of the storeOwner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeOwner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/store-owners/{id}")
    public Mono<ResponseEntity<StoreOwner>> getStoreOwner(@PathVariable String id) {
        log.debug("REST request to get StoreOwner : {}", id);
        Mono<StoreOwner> storeOwner = storeOwnerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(storeOwner);
    }

    /**
     * {@code DELETE  /store-owners/:id} : delete the "id" storeOwner.
     *
     * @param id the id of the storeOwner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/store-owners/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteStoreOwner(@PathVariable String id) {
        log.debug("REST request to delete StoreOwner : {}", id);
        return storeOwnerRepository
            .deleteById(id)
            .map(result ->
                ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
            );
    }
}
