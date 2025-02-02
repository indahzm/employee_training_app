package co.id.employeetrainingsecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import co.id.employeetrainingsecurity.repository.ClientRepository;

@Service
public class Oauth2ClientDetailsService implements ClientDetailsService { 
 
    @Autowired
    private ClientRepository clientRepository; 
 
    @Override 
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException { 
        ClientDetails client = clientRepository.findOneByClientId(s); 
        if (null == client) { 
            throw new ClientRegistrationException("Client not found"); 
        } 
        return client; 
    } 

}
