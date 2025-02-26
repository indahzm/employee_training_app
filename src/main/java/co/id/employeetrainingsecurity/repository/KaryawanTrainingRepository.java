package co.id.employeetrainingsecurity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.id.employeetrainingsecurity.entity.KaryawanTrainingEntity;

@SuppressWarnings("unchecked")
@Repository
public interface KaryawanTrainingRepository extends PagingAndSortingRepository<KaryawanTrainingEntity, Long>{
	
	Optional<KaryawanTrainingEntity> findById(Long id);
	KaryawanTrainingEntity save(KaryawanTrainingEntity karyawanTraining);
	Page<KaryawanTrainingEntity> findAll(Pageable pageable);
	List<KaryawanTrainingEntity> findAllByKaryawanId(Long id);
	List<KaryawanTrainingEntity> findAllByTrainingId(Long id);
	
}
