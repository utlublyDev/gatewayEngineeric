package qa.engineeric.store.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import qa.engineeric.store.domain.SliderImageSlider;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the SliderImageSlider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SliderImageSliderRepository extends ReactiveMongoRepository<SliderImageSlider, String> {
    Flux<SliderImageSlider> findAllBy(Pageable pageable);

    Flux<SliderImageSlider> findAllByStoreOwnerIdAndWebKey(String storeOwnerId, String webKey);
}
