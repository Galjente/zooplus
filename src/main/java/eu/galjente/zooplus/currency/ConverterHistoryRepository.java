package eu.galjente.zooplus.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConverterHistoryRepository extends JpaRepository<ConverterHistory, Integer> {
}
