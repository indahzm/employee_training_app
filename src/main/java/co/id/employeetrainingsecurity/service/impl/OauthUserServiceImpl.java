package co.id.employeetrainingsecurity.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.id.employeetrainingsecurity.entity.User;
import co.id.employeetrainingsecurity.repository.UserRepository;

@Service
public class OauthUserServiceImpl implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(OauthUserServiceImpl.class.getName());
	
    @Autowired 
    private UserRepository userRepository; 
 
    @Override 
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException { 
    	
    	logger.info(">> Start loadUserByUsername <<");
        User user = userRepository.findByUsername(s); 
        if (null == user) { 
        	logger.error(">> Error : " + String.format("Username %s is not found", s) + " <<");
            throw new UsernameNotFoundException(String.format("Username %s is not found", s)); 
        } 
 
        return user; 
    }

}
