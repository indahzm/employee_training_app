 package co.id.employeetrainingsecurity.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.id.employeetrainingsecurity.entity.Client;

@SuppressWarnings("unchecked")
@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> { 
  
    Client findOneByClientId(String clientId); 
	Client save(Client client);
  
 }