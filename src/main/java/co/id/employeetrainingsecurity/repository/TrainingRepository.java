package co.id.employeetrainingsecurity.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.id.employeetrainingsecurity.entity.TrainingEntity;

@SuppressWarnings("unchecked")
@Repository
public interface TrainingRepository extends PagingAndSortingRepository<TrainingEntity, Long>{

	Optional<TrainingEntity> findById(Long id);
	TrainingEntity save(TrainingEntity training);
	Page<TrainingEntity> findAll(Pageable pageable);
	
}
