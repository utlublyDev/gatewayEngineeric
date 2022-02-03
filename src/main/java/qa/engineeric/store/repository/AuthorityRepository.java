package qa.engineeric.store.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import qa.engineeric.store.domain.Authority;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends ReactiveMongoRepository<Authority, String> {}
