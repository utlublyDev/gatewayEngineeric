package qa.engineeric.store.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import qa.engineeric.store.domain.StoreOwner;

/**
 * Spring Data MongoDB reactive repository for the StoreOwner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreOwnerRepository extends ReactiveMongoRepository<StoreOwner, String> {}
