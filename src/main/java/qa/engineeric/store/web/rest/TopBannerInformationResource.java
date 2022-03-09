package qa.engineeric.store.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import qa.engineeric.store.domain.TopBannerInformation;
import qa.engineeric.store.repository.TopBannerInformationRepository;
import qa.engineeric.store.web.rest.errors.BadRequestAlertException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link qa.engineeric.store.domain.TopBannerInformation}.
 */
@RestController
@RequestMapping("/api")
public class TopBannerInformationResource {

    private final Logger log = LoggerFactory.getLogger(TopBannerInformationResource.class);

    private static final String ENTITY_NAME = "topBannerInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopBannerInformationRepository topBannerInformationRepository;

    public TopBannerInformationResource(TopBannerInformationRepository topBannerInformationRepository) {
        this.topBannerInformationRepository = topBannerInformationRepository;
    }

    /**
     * {@code POST  /top-banner-informations} : Create a new topBannerInformation.
     *
     * @param topBannerInformation the topBannerInformation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topBannerInformation, or with status {@code 400 (Bad Request)} if the topBannerInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/top-banner-informations")
    public Mono<ResponseEntity<TopBannerInformation>> createTopBannerInformation(@RequestBody TopBannerInformation topBannerInformation)
        throws URISyntaxException {
        log.debug("REST request to save TopBannerInformation : {}", topBannerInformation);
        if (topBannerInformation.getId() != null) {
            throw new BadRequestAlertException("A new topBannerInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return topBannerInformationRepository
            .save(topBannerInformation)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/top-banner-informations/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /top-banner-informations/:id} : Updates an existing topBannerInformation.
     *
     * @param id the id of the topBannerInformation to save.
     * @param topBannerInformation the topBannerInformation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topBannerInformation,
     * or with status {@code 400 (Bad Request)} if the topBannerInformation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topBannerInformation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/top-banner-informations/{id}")
    public Mono<ResponseEntity<TopBannerInformation>> updateTopBannerInformation(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody TopBannerInformation topBannerInformation
    ) throws URISyntaxException {
        log.debug("REST request to update TopBannerInformation : {}, {}", id, topBannerInformation);
        if (topBannerInformation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, topBannerInformation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return topBannerInformationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return topBannerInformationRepository
                    .save(topBannerInformation)
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
     * {@code PATCH  /top-banner-informations/:id} : Partial updates given fields of an existing topBannerInformation, field will ignore if it is null
     *
     * @param id the id of the topBannerInformation to save.
     * @param topBannerInformation the topBannerInformation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topBannerInformation,
     * or with status {@code 400 (Bad Request)} if the topBannerInformation is not valid,
     * or with status {@code 404 (Not Found)} if the topBannerInformation is not found,
     * or with status {@code 500 (Internal Server Error)} if the topBannerInformation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/top-banner-informations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TopBannerInformation>> partialUpdateTopBannerInformation(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody TopBannerInformation topBannerInformation
    ) throws URISyntaxException {
        log.debug("REST request to partial update TopBannerInformation partially : {}, {}", id, topBannerInformation);
        if (topBannerInformation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, topBannerInformation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return topBannerInformationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TopBannerInformation> result = topBannerInformationRepository
                    .findById(topBannerInformation.getId())
                    .map(existingTopBannerInformation -> {
                        if (topBannerInformation.getEnableBanner() != null) {
                            existingTopBannerInformation.setEnableBanner(topBannerInformation.getEnableBanner());
                        }
                        if (topBannerInformation.getBannerText() != null) {
                            existingTopBannerInformation.setBannerText(topBannerInformation.getBannerText());
                        }
                        if (topBannerInformation.getStartBanner() != null) {
                            existingTopBannerInformation.setStartBanner(topBannerInformation.getStartBanner());
                        }
                        if (topBannerInformation.getEndBanner() != null) {
                            existingTopBannerInformation.setEndBanner(topBannerInformation.getEndBanner());
                        }
                        if (topBannerInformation.getWebKey() != null) {
                            existingTopBannerInformation.setWebKey(topBannerInformation.getWebKey());
                        }
                        if (topBannerInformation.getStoreOwnerId() != null) {
                            existingTopBannerInformation.setStoreOwnerId(topBannerInformation.getStoreOwnerId());
                        }

                        return existingTopBannerInformation;
                    })
                    .flatMap(topBannerInformationRepository::save);

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
     * {@code GET  /top-banner-informations} : get all the topBannerInformations.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topBannerInformations in body.
     */
    @GetMapping("/top-banner-informations")
    public Mono<ResponseEntity<List<TopBannerInformation>>> getAllTopBannerInformations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of TopBannerInformations");
        return topBannerInformationRepository
            .count()
            .zipWith(topBannerInformationRepository.findAllBy(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /top-banner-informations/:id} : get the "id" topBannerInformation.
     *
     * @param id the id of the topBannerInformation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topBannerInformation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/top-banner-informations/{id}")
    public Mono<ResponseEntity<TopBannerInformation>> getTopBannerInformation(@PathVariable String id) {
        log.debug("REST request to get TopBannerInformation : {}", id);
        Mono<TopBannerInformation> topBannerInformation = topBannerInformationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(topBannerInformation);
    }

    @GetMapping("/top-banner-information/store/web/{storeOwnerId}/{webKey}")
    public Mono<ResponseEntity<TopBannerInformation>> getTopBannerInformationByStoreOwnerAndWebKey(
        @PathVariable String storeOwnerId,
        @PathVariable String webKey
    ) {
        log.debug("REST request to get TopBannerInformation : {}", storeOwnerId);
        Mono<TopBannerInformation> topBannerInformation = topBannerInformationRepository.findByStoreOwnerIdAndWebKey(storeOwnerId, webKey);
        return ResponseUtil.wrapOrNotFound(topBannerInformation);
    }

    /**
     * {@code DELETE  /top-banner-informations/:id} : delete the "id" topBannerInformation.
     *
     * @param id the id of the topBannerInformation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/top-banner-informations/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteTopBannerInformation(@PathVariable String id) {
        log.debug("REST request to delete TopBannerInformation : {}", id);
        return topBannerInformationRepository
            .deleteById(id)
            .map(result ->
                ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
            );
    }
}
