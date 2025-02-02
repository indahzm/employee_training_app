package co.id.employeetrainingsecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.id.employeetrainingsecurity.entity.User;
import co.id.employeetrainingsecurity.repository.UserRepository;

@Service
public class OauthUserServiceImpl implements UserDetailsService {
	
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Contoh hardcode user. Gunakan database di production
//        if ("admin".equals(username)) {
//            return User.builder()
//                .username("admin")
//                .password(new BCryptPasswordEncoder().encode("admin"))
//                .roles("USER")
//                .build();
//        }
//        throw new UsernameNotFoundException("User not found");
//    }
	
    @Autowired 
    private UserRepository userRepository; 
 
    @Override 
    public UserDetails loadUserByUsername(String s) throws 
UsernameNotFoundException { 
        User user = userRepository.findByUsername(s); 
        if (null == user) { 
            throw new UsernameNotFoundException(String.format("Username %s is not found", s)); 
        } 
 
        return user; 
    }

}
