package co.id.employeetrainingsecurity.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.id.employeetrainingsecurity.entity.DetailKaryawanEntity;

@SuppressWarnings("unchecked")
@Repository
public interface DetailKaryawanRepository extends PagingAndSortingRepository<DetailKaryawanEntity, Long> {

	DetailKaryawanEntity save(DetailKaryawanEntity detailKaryawan);
	
}
