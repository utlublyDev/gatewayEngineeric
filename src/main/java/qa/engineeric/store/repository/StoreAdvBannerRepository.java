package qa.engineeric.store.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import qa.engineeric.store.domain.StoreAdvBanner;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB reactive repository for the StoreAdvBanner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreAdvBannerRepository extends ReactiveMongoRepository<StoreAdvBanner, String> {
    Mono<StoreAdvBanner> findByStoreOwnerIdAndWebKey(String storeOwnerId, String webKey);
}
