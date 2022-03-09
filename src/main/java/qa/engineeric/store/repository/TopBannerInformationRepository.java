package qa.engineeric.store.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import qa.engineeric.store.domain.TopBannerInformation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the TopBannerInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopBannerInformationRepository extends ReactiveMongoRepository<TopBannerInformation, String> {
    Flux<TopBannerInformation> findAllBy(Pageable pageable);

    Mono<TopBannerInformation> findByStoreOwnerIdAndWebKey(String storeOwnerId, String webKey);
}
