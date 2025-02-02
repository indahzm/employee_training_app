package co.id.employeetrainingsecurity.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.id.employeetrainingsecurity.entity.User;

@SuppressWarnings("unchecked")
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	@Query("FROM User u WHERE LOWER(u.username) = LOWER(:username) ") 
	User findByUsername(String username);
	 
	@Query("FROM User u WHERE u.otp = ?1")
	User findByOtp(String otp);
	
	User save(User user);
	
}
