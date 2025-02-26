package co.id.employeetrainingsecurity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.id.employeetrainingsecurity.entity.RekeningEntity;

@SuppressWarnings("unchecked")
@Repository
public interface RekeningRepository extends PagingAndSortingRepository<RekeningEntity, Long>{

	Optional<RekeningEntity> findById(Long id);
	RekeningEntity save(RekeningEntity rekening);
	Page<RekeningEntity> findAll(Pageable pageable);
	List<RekeningEntity> findAllByKaryawanId(Long id);
	
}
