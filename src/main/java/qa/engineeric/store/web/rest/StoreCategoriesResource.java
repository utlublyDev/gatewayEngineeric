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
import qa.engineeric.store.domain.StoreCategories;
import qa.engineeric.store.repository.StoreCategoriesRepository;
import qa.engineeric.store.web.rest.errors.BadRequestAlertException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link qa.engineeric.store.domain.StoreCategories}.
 */
@RestController
@RequestMapping("/api")
public class StoreCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(StoreCategoriesResource.class);

    private static final String ENTITY_NAME = "storeCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreCategoriesRepository storeCategoriesRepository;

    public StoreCategoriesResource(StoreCategoriesRepository storeCategoriesRepository) {
        this.storeCategoriesRepository = storeCategoriesRepository;
    }

    /**
     * {@code POST  /store-categories} : Create a new storeCategories.
     *
     * @param storeCategories the storeCategories to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeCategories, or with status {@code 400 (Bad Request)} if the storeCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/store-categories")
    public Mono<ResponseEntity<StoreCategories>> createStoreCategories(@Valid @RequestBody StoreCategories storeCategories)
        throws URISyntaxException {
        log.debug("REST request to save StoreCategories : {}", storeCategories);
        if (storeCategories.getId() != null) {
            throw new BadRequestAlertException("A new storeCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return storeCategoriesRepository
            .save(storeCategories)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/store-categories/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /store-categories/:id} : Updates an existing storeCategories.
     *
     * @param id the id of the storeCategories to save.
     * @param storeCategories the storeCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeCategories,
     * or with status {@code 400 (Bad Request)} if the storeCategories is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/store-categories/{id}")
    public Mono<ResponseEntity<StoreCategories>> updateStoreCategories(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody StoreCategories storeCategories
    ) throws URISyntaxException {
        log.debug("REST request to update StoreCategories : {}, {}", id, storeCategories);
        if (storeCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return storeCategoriesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return storeCategoriesRepository
                    .save(storeCategories)
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
     * {@code PATCH  /store-categories/:id} : Partial updates given fields of an existing storeCategories, field will ignore if it is null
     *
     * @param id the id of the storeCategories to save.
     * @param storeCategories the storeCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeCategories,
     * or with status {@code 400 (Bad Request)} if the storeCategories is not valid,
     * or with status {@code 404 (Not Found)} if the storeCategories is not found,
     * or with status {@code 500 (Internal Server Error)} if the storeCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/store-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<StoreCategories>> partialUpdateStoreCategories(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody StoreCategories storeCategories
    ) throws URISyntaxException {
        log.debug("REST request to partial update StoreCategories partially : {}, {}", id, storeCategories);
        if (storeCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeCategories.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return storeCategoriesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<StoreCategories> result = storeCategoriesRepository
                    .findById(storeCategories.getId())
                    .map(existingStoreCategories -> {
                        if (storeCategories.getUserStoreOwnerId() != null) {
                            existingStoreCategories.setUserStoreOwnerId(storeCategories.getUserStoreOwnerId());
                        }
                        if (storeCategories.getCategoryName() != null) {
                            existingStoreCategories.setCategoryName(storeCategories.getCategoryName());
                        }
                        if (storeCategories.getDescription() != null) {
                            existingStoreCategories.setDescription(storeCategories.getDescription());
                        }
                        if (storeCategories.getCategoryNameInArabic() != null) {
                            existingStoreCategories.setCategoryNameInArabic(storeCategories.getCategoryNameInArabic());
                        }
                        if (storeCategories.getDescriptionInArabic() != null) {
                            existingStoreCategories.setDescriptionInArabic(storeCategories.getDescriptionInArabic());
                        }
                        if (storeCategories.getUserStoreOwnerIdImageUrl() != null) {
                            existingStoreCategories.setUserStoreOwnerIdImageUrl(storeCategories.getUserStoreOwnerIdImageUrl());
                        }

                        return existingStoreCategories;
                    })
                    .flatMap(storeCategoriesRepository::save);

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
     * {@code GET  /store-categories} : get all the storeCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storeCategories in body.
     */
    @GetMapping("/store-categories")
    public Mono<List<StoreCategories>> getAllStoreCategories() {
        log.debug("REST request to get all StoreCategories");
        return storeCategoriesRepository.findAll().collectList();
    }

    /**
     * {@code GET  /store-categories} : get all the storeCategories as a stream.
     * @return the {@link Flux} of storeCategories.
     */
    @GetMapping(value = "/store-categories", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<StoreCategories> getAllStoreCategoriesAsStream() {
        log.debug("REST request to get all StoreCategories as a stream");
        return storeCategoriesRepository.findAll();
    }

    /**
     * {@code GET  /store-categories/:id} : get the "id" storeCategories.
     *
     * @param id the id of the storeCategories to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeCategories, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/store-categories/{id}")
    public Mono<ResponseEntity<StoreCategories>> getStoreCategories(@PathVariable String id) {
        log.debug("REST request to get StoreCategories : {}", id);
        Mono<StoreCategories> storeCategories = storeCategoriesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(storeCategories);
    }

    /**
     * {@code DELETE  /store-categories/:id} : delete the "id" storeCategories.
     *
     * @param id the id of the storeCategories to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/store-categories/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteStoreCategories(@PathVariable String id) {
        log.debug("REST request to delete StoreCategories : {}", id);
        return storeCategoriesRepository
            .deleteById(id)
            .map(result ->
                ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
            );
    }
}
