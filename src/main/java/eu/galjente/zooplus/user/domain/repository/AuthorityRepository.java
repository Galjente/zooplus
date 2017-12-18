package eu.galjente.zooplus.user.domain.repository;

import eu.galjente.zooplus.user.domain.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	Authority findOneByName(String name);
}
