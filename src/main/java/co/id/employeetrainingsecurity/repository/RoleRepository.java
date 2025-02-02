package co.id.employeetrainingsecurity.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.id.employeetrainingsecurity.entity.Role;

@SuppressWarnings("unchecked")
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long>{

	Role findByName(String name);
	List<Role> findByNameIn(String[] names);
	Role save(Role role);

}
