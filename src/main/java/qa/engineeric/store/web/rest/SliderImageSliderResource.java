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
import qa.engineeric.store.domain.SliderImageSlider;
import qa.engineeric.store.repository.SliderImageSliderRepository;
import qa.engineeric.store.web.rest.errors.BadRequestAlertException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link qa.engineeric.store.domain.SliderImageSlider}.
 */
@RestController
@RequestMapping("/api")
public class SliderImageSliderResource {

    private final Logger log = LoggerFactory.getLogger(SliderImageSliderResource.class);

    private static final String ENTITY_NAME = "sliderImageSlider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SliderImageSliderRepository sliderImageSliderRepository;

    public SliderImageSliderResource(SliderImageSliderRepository sliderImageSliderRepository) {
        this.sliderImageSliderRepository = sliderImageSliderRepository;
    }

    /**
     * {@code POST  /slider-image-sliders} : Create a new sliderImageSlider.
     *
     * @param sliderImageSlider the sliderImageSlider to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sliderImageSlider, or with status {@code 400 (Bad Request)} if the sliderImageSlider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/slider-image-sliders")
    public Mono<ResponseEntity<SliderImageSlider>> createSliderImageSlider(@RequestBody SliderImageSlider sliderImageSlider)
        throws URISyntaxException {
        log.debug("REST request to save SliderImageSlider : {}", sliderImageSlider);
        if (sliderImageSlider.getId() != null) {
            throw new BadRequestAlertException("A new sliderImageSlider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return sliderImageSliderRepository
            .save(sliderImageSlider)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/slider-image-sliders/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /slider-image-sliders/:id} : Updates an existing sliderImageSlider.
     *
     * @param id the id of the sliderImageSlider to save.
     * @param sliderImageSlider the sliderImageSlider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sliderImageSlider,
     * or with status {@code 400 (Bad Request)} if the sliderImageSlider is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sliderImageSlider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/slider-image-sliders/{id}")
    public Mono<ResponseEntity<SliderImageSlider>> updateSliderImageSlider(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SliderImageSlider sliderImageSlider
    ) throws URISyntaxException {
        log.debug("REST request to update SliderImageSlider : {}, {}", id, sliderImageSlider);
        if (sliderImageSlider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sliderImageSlider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sliderImageSliderRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return sliderImageSliderRepository
                    .save(sliderImageSlider)
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
     * {@code PATCH  /slider-image-sliders/:id} : Partial updates given fields of an existing sliderImageSlider, field will ignore if it is null
     *
     * @param id the id of the sliderImageSlider to save.
     * @param sliderImageSlider the sliderImageSlider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sliderImageSlider,
     * or with status {@code 400 (Bad Request)} if the sliderImageSlider is not valid,
     * or with status {@code 404 (Not Found)} if the sliderImageSlider is not found,
     * or with status {@code 500 (Internal Server Error)} if the sliderImageSlider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/slider-image-sliders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SliderImageSlider>> partialUpdateSliderImageSlider(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SliderImageSlider sliderImageSlider
    ) throws URISyntaxException {
        log.debug("REST request to partial update SliderImageSlider partially : {}, {}", id, sliderImageSlider);
        if (sliderImageSlider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sliderImageSlider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return sliderImageSliderRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SliderImageSlider> result = sliderImageSliderRepository
                    .findById(sliderImageSlider.getId())
                    .map(existingSliderImageSlider -> {
                        if (sliderImageSlider.getStoreOwnerId() != null) {
                            existingSliderImageSlider.setStoreOwnerId(sliderImageSlider.getStoreOwnerId());
                        }
                        if (sliderImageSlider.getWebKey() != null) {
                            existingSliderImageSlider.setWebKey(sliderImageSlider.getWebKey());
                        }
                        if (sliderImageSlider.getImageUrl() != null) {
                            existingSliderImageSlider.setImageUrl(sliderImageSlider.getImageUrl());
                        }
                        if (sliderImageSlider.getAlt() != null) {
                            existingSliderImageSlider.setAlt(sliderImageSlider.getAlt());
                        }

                        return existingSliderImageSlider;
                    })
                    .flatMap(sliderImageSliderRepository::save);

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
     * {@code GET  /slider-image-sliders} : get all the sliderImageSliders.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sliderImageSliders in body.
     */
    @GetMapping("/slider-image-sliders")
    public Mono<ResponseEntity<List<SliderImageSlider>>> getAllSliderImageSliders(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of SliderImageSliders");
        return sliderImageSliderRepository
            .count()
            .zipWith(sliderImageSliderRepository.findAllBy(pageable).collectList())
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

    @GetMapping("/slider-image-sliders/store/web/{storeOwnerId}/{webKey}")
    public Mono<ResponseEntity<List<SliderImageSlider>>> getAllSliderImageSlidersByStoreOwnerAndWebKey(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @PathVariable String storeOwnerId,
        @PathVariable String webKey
    ) {
        log.debug("REST request to get a page of SliderImageSliders");
        return sliderImageSliderRepository
            .count()
            .zipWith(sliderImageSliderRepository.findAllByStoreOwnerIdAndWebKey(storeOwnerId, webKey).collectList())
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
     * {@code GET  /slider-image-sliders/:id} : get the "id" sliderImageSlider.
     *
     * @param id the id of the sliderImageSlider to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sliderImageSlider, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/slider-image-sliders/{id}")
    public Mono<ResponseEntity<SliderImageSlider>> getSliderImageSlider(@PathVariable String id) {
        log.debug("REST request to get SliderImageSlider : {}", id);
        Mono<SliderImageSlider> sliderImageSlider = sliderImageSliderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sliderImageSlider);
    }

    /**
     * {@code DELETE  /slider-image-sliders/:id} : delete the "id" sliderImageSlider.
     *
     * @param id the id of the sliderImageSlider to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/slider-image-sliders/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteSliderImageSlider(@PathVariable String id) {
        log.debug("REST request to delete SliderImageSlider : {}", id);
        return sliderImageSliderRepository
            .deleteById(id)
            .map(result ->
                ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
            );
    }
}
