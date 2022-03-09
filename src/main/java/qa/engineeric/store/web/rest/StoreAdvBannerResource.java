package qa.engineeric.store.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import qa.engineeric.store.domain.StoreAdvBanner;
import qa.engineeric.store.repository.StoreAdvBannerRepository;
import qa.engineeric.store.web.rest.errors.BadRequestAlertException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link qa.engineeric.store.domain.StoreAdvBanner}.
 */
@RestController
@RequestMapping("/api")
public class StoreAdvBannerResource {

    private final Logger log = LoggerFactory.getLogger(StoreAdvBannerResource.class);

    private static final String ENTITY_NAME = "storeAdvBanner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreAdvBannerRepository storeAdvBannerRepository;

    public StoreAdvBannerResource(StoreAdvBannerRepository storeAdvBannerRepository) {
        this.storeAdvBannerRepository = storeAdvBannerRepository;
    }

    /**
     * {@code POST  /store-adv-banners} : Create a new storeAdvBanner.
     *
     * @param storeAdvBanner the storeAdvBanner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeAdvBanner, or with status {@code 400 (Bad Request)} if the storeAdvBanner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/store-adv-banners")
    public Mono<ResponseEntity<StoreAdvBanner>> createStoreAdvBanner(@RequestBody StoreAdvBanner storeAdvBanner) throws URISyntaxException {
        log.debug("REST request to save StoreAdvBanner : {}", storeAdvBanner);
        if (storeAdvBanner.getId() != null) {
            throw new BadRequestAlertException("A new storeAdvBanner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return storeAdvBannerRepository
            .save(storeAdvBanner)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/store-adv-banners/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /store-adv-banners/:id} : Updates an existing storeAdvBanner.
     *
     * @param id the id of the storeAdvBanner to save.
     * @param storeAdvBanner the storeAdvBanner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeAdvBanner,
     * or with status {@code 400 (Bad Request)} if the storeAdvBanner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeAdvBanner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/store-adv-banners/{id}")
    public Mono<ResponseEntity<StoreAdvBanner>> updateStoreAdvBanner(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody StoreAdvBanner storeAdvBanner
    ) throws URISyntaxException {
        log.debug("REST request to update StoreAdvBanner : {}, {}", id, storeAdvBanner);
        if (storeAdvBanner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeAdvBanner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return storeAdvBannerRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return storeAdvBannerRepository
                    .save(storeAdvBanner)
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
     * {@code PATCH  /store-adv-banners/:id} : Partial updates given fields of an existing storeAdvBanner, field will ignore if it is null
     *
     * @param id the id of the storeAdvBanner to save.
     * @param storeAdvBanner the storeAdvBanner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeAdvBanner,
     * or with status {@code 400 (Bad Request)} if the storeAdvBanner is not valid,
     * or with status {@code 404 (Not Found)} if the storeAdvBanner is not found,
     * or with status {@code 500 (Internal Server Error)} if the storeAdvBanner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/store-adv-banners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<StoreAdvBanner>> partialUpdateStoreAdvBanner(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody StoreAdvBanner storeAdvBanner
    ) throws URISyntaxException {
        log.debug("REST request to partial update StoreAdvBanner partially : {}, {}", id, storeAdvBanner);
        if (storeAdvBanner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeAdvBanner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return storeAdvBannerRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<StoreAdvBanner> result = storeAdvBannerRepository
                    .findById(storeAdvBanner.getId())
                    .map(existingStoreAdvBanner -> {
                        if (storeAdvBanner.getStoreOwnerId() != null) {
                            existingStoreAdvBanner.setStoreOwnerId(storeAdvBanner.getStoreOwnerId());
                        }
                        if (storeAdvBanner.getWebKey() != null) {
                            existingStoreAdvBanner.setWebKey(storeAdvBanner.getWebKey());
                        }
                        if (storeAdvBanner.getImageUrl() != null) {
                            existingStoreAdvBanner.setImageUrl(storeAdvBanner.getImageUrl());
                        }

                        return existingStoreAdvBanner;
                    })
                    .flatMap(storeAdvBannerRepository::save);

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
     * {@code GET  /store-adv-banners} : get all the storeAdvBanners.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storeAdvBanners in body.
     */
    @GetMapping("/store-adv-banners")
    public Mono<List<StoreAdvBanner>> getAllStoreAdvBanners() {
        log.debug("REST request to get all StoreAdvBanners");
        return storeAdvBannerRepository.findAll().collectList();
    }

    /**
     * {@code GET  /store-adv-banners} : get all the storeAdvBanners as a stream.
     * @return the {@link Flux} of storeAdvBanners.
     */
    @GetMapping(value = "/store-adv-banners", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<StoreAdvBanner> getAllStoreAdvBannersAsStream() {
        log.debug("REST request to get all StoreAdvBanners as a stream");
        return storeAdvBannerRepository.findAll();
    }

    /**
     * {@code GET  /store-adv-banners/:id} : get the "id" storeAdvBanner.
     *
     * @param id the id of the storeAdvBanner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeAdvBanner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/store-adv-banners/{id}")
    public Mono<ResponseEntity<StoreAdvBanner>> getStoreAdvBanner(@PathVariable String id) {
        log.debug("REST request to get StoreAdvBanner : {}", id);
        Mono<StoreAdvBanner> storeAdvBanner = storeAdvBannerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(storeAdvBanner);
    }

    @GetMapping("/store-adv-banners/store/web/{storeOwnerId}/{webKey}")
    public Mono<ResponseEntity<StoreAdvBanner>> getStoreAdvBannerByStoreOwnerAndWebKey(
        @PathVariable String storeOwnerId,
        @PathVariable String webKey
    ) {
        log.debug("REST request to get StoreAdvBanner : {}", storeOwnerId);
        Mono<StoreAdvBanner> storeAdvBanner = storeAdvBannerRepository.findByStoreOwnerIdAndWebKey(storeOwnerId, webKey);
        return ResponseUtil.wrapOrNotFound(storeAdvBanner);
    }

    /**
     * {@code DELETE  /store-adv-banners/:id} : delete the "id" storeAdvBanner.
     *
     * @param id the id of the storeAdvBanner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/store-adv-banners/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteStoreAdvBanner(@PathVariable String id) {
        log.debug("REST request to delete StoreAdvBanner : {}", id);
        return storeAdvBannerRepository
            .deleteById(id)
            .map(result ->
                ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
            );
    }
}
