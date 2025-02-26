package co.id.employeetrainingsecurity.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import co.id.employeetrainingsecurity.repository.ClientRepository;

@Service
public class Oauth2ClientDetailsService implements ClientDetailsService { 
 
	private static final Logger logger = LoggerFactory.getLogger(Oauth2ClientDetailsService.class.getName());
	
    @Autowired
    private ClientRepository clientRepository; 
 
    @Override 
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException { 
    	logger.info(">> Start loadClientByClientId <<");
        ClientDetails client = clientRepository.findOneByClientId(s); 
        if (null == client) { 
        	logger.info(">> Error : Client not found <<");
            throw new ClientRegistrationException("Client not found"); 
        } 
        return client; 
    } 

}
