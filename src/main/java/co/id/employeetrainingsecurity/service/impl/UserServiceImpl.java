package co.id.employeetrainingsecurity.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import co.id.employeetrainingsecurity.entity.Role;
import co.id.employeetrainingsecurity.entity.User;
import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.entity.dto.LoginRequest;
import co.id.employeetrainingsecurity.entity.dto.RegisterRequest;
import co.id.employeetrainingsecurity.entity.dto.ResponseConstant;
import co.id.employeetrainingsecurity.repository.RoleRepository;
import co.id.employeetrainingsecurity.repository.UserRepository;
import co.id.employeetrainingsecurity.service.UserService;
import co.id.employeetrainingsecurity.util.EmailSender;
import co.id.employeetrainingsecurity.util.EmailTemplate;
import co.id.employeetrainingsecurity.util.GenerateString;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Value("${BASEURL}") 
    private String baseUrl; 
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder; 
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private EmailTemplate emailTemplate;
	
	@Autowired
	private EmailSender emailSender;
	
	@Value("${expired.token.password.minute}")
	private String expiredToken;
	
	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}

	@Override
	public User findByOtp(String otp) {
		User user = userRepository.findByOtp(otp);
		return user;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public AuthenticationResponse registerManual(RegisterRequest registerRequest) {
		try {
			
			User exist = userRepository.findByUsername(registerRequest.getUsername().toLowerCase());
			
			if (exist != null) {
				return new AuthenticationResponse("User has been registered", ResponseConstant.STATUS_BAD_REQUEST, "400");
			}
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, 30);
			
			String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"};
			List<Role> roleList = roleRepository.findByNameIn(roleNames);
			 
			User user = new User();
			user.setUsername(registerRequest.getUsername());
			user.setFullname(registerRequest.getFullname());
			user.setPassword(passwordEncoder.encode(registerRequest.getPassword().replaceAll("\\s+", "")));
			user.setOtp(GenerateString.generateOtp());
			user.setOtpExpiredDate(calendar.getTime());
			user.setRoles(roleList);
			String urlOtp = baseUrl + "user-register/web/index/";
			sendEmailOtp(user, urlOtp);
			save(user);
			return new AuthenticationResponse(user, ResponseConstant.STATUS_SUKSES, "200");
		} catch(Exception e) {
			return new AuthenticationResponse(e.getMessage(), ResponseConstant.STATUS_ERROR, "500");
		}
	}
	
	@Override
	public void sendEmailOtp(User user) {
		sendEmailOtp(user, "");
	}
	
	public void sendEmailOtp(User user, String urlOtp) {
		String template = emailTemplate.getRegistrationTemplate();
		
        Date dateNow = new Date(); 
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(dateNow); 
        calendar.add(Calendar.MINUTE, Integer.valueOf(expiredToken)); 
        Date expirationDate = calendar.getTime(); 

        user.setOtpExpiredDate(expirationDate); 
        String otp = GenerateString.generateOtp();
    	urlOtp += otp;
    	user.setOtp(otp);
    	String url = baseUrl + "user-register/web/index/" + otp;
		
    	template = template.replaceAll("\\{\\{USERNAME}}", (user.getFullname() == null ? user.getUsername() : user.getFullname())); 
        template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", otp);
        template = template.replaceAll("\\{\\{VERIFY_URL}}", url);
        template = template.replaceAll("\\{\\{URL}}", url);
        
        emailSender.sendAsync(user.getUsername(), "Register", template);
	}

    @SuppressWarnings("rawtypes")
	@Override
	public AuthenticationResponse login(LoginRequest loginRequest) {
		try {
			Map<String, Object> map = new HashMap<>();
			User user = userRepository.findByUsername(loginRequest.getUsername());
			
			if (user == null) {
				return new AuthenticationResponse(ResponseConstant.DATA_NOT_FOUND.replace("${object}", "User"), ResponseConstant.STATUS_NOT_FOUD, "404");
			}
			
			boolean isPassMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
			if(isPassMatch && !user.isEnabled()) {
				return new AuthenticationResponse("User is disabled", ResponseConstant.STATUS_BAD_REQUEST, "400");
			}
			
			if (!isPassMatch) {
				return new AuthenticationResponse("Incorect Password", ResponseConstant.STATUS_BAD_REQUEST, "400");
			}
			
	        HttpHeaders headers = new HttpHeaders();
	        String auth = "577901321751-mrinahrqlbqbfg8prdat8jkbnv5mdckl.apps.googleusercontent.com" + ":" + "password";
	        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
	        String authHeader = "Basic " + encodedAuth;
	        headers.set("Authorization", authHeader);
	        
	        String url = baseUrl + "oauth/token?username=" + loginRequest.getUsername() + 
	                "&password=" + loginRequest.getPassword() + 
	                "&grant_type=password";
//	        String url = baseUrl + "/oauth/token?grant_type=password&username=zakiyyahindahm@gmail.com&password=indah1234";
//	                "&client_id=" + "577901321751-mrinahrqlbqbfg8prdat8jkbnv5mdckl.apps.googleusercontent.com" +
//	                "&client_secret=" + "password"; 
			ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, new HttpEntity<>(headers), new ParameterizedTypeReference<Map>() {}); 

	        if (response.getStatusCode() == HttpStatus.OK) { 
	            List<String> roles = new ArrayList<>(); 
	            
	            for (Role role : user.getRoles()) {
	            	roles.add(role.getName());
	            } 
		        //save token 
		        user.setVerifyToken(response.getBody().get("access_token").toString()); 
		        userRepository.save(user); 
	
		        map.put("access_token", response.getBody().get("access_token")); 
		        map.put("token_type", response.getBody().get("token_type")); 
		        map.put("refresh_token", response.getBody().get("refresh_token")); 
		        map.put("expires_in", response.getBody().get("expires_in")); 
		        map.put("scope", response.getBody().get("scope")); 
		        map.put("jti", response.getBody().get("jti")); 
		
		        return new AuthenticationResponse(map, ResponseConstant.STATUS_SUKSES, "200");
		        
		    } else { 
		    	return new AuthenticationResponse(ResponseConstant.DATA_NOT_FOUND.replace("${object}", "User"), ResponseConstant.STATUS_NOT_FOUD, "404");
		    }
		} catch (HttpStatusCodeException e) { 
		    e.printStackTrace(); 
		    if (e.getStatusCode() == HttpStatus.BAD_REQUEST) { 
		    	return new AuthenticationResponse("Invalid login", ResponseConstant.STATUS_BAD_REQUEST, "400");
		    } 
		    return new AuthenticationResponse("error: " + e, ResponseConstant.STATUS_ERROR, String.valueOf(e.getStatusCode().value()));
		} catch (Exception e) { 
		    e.printStackTrace(); 
		
		    return new AuthenticationResponse("error: " + e, ResponseConstant.STATUS_ERROR, String.valueOf(500));
		} 
	} 

}
