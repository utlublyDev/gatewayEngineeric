package qa.engineeric.store.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import qa.engineeric.store.domain.StoreCategories;

/**
 * Spring Data MongoDB reactive repository for the StoreCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreCategoriesRepository extends ReactiveMongoRepository<StoreCategories, String> {}
