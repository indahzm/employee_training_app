package co.id.employeetrainingsecurity.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import co.id.employeetrainingsecurity.entity.User;
import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.entity.dto.RegisterRequest;
import co.id.employeetrainingsecurity.entity.dto.ResponseConstant;
import co.id.employeetrainingsecurity.service.AuthenticationService;
import co.id.employeetrainingsecurity.service.UserService;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class.getName());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	
    @Value("${BASEURL}") 
    private String baseUrl; 
    
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;  
	
	@Override
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public AuthenticationResponse signInGoogle(String accessToken) {
		
		logger.info(">> Start SignIn Google <<");
		
        Map<String, Object> mapResponse = new HashMap<>(); 
        
		GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken); 
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName( 
                "Oauth2").build(); 
        Userinfoplus profile= null; 
        try {
        	profile = oauth2.userinfo().get().execute(); 
            profile.toPrettyString(); 
        } catch (Exception e) {
        	logger.error(">> Error SignIn Google : " + e.getMessage() + " <<");
            return new AuthenticationResponse("Bad Gateway", "Bad Gateway", String.valueOf(HttpStatus.BAD_GATEWAY.value()));
		
        } 
        User user = userService.findByUsername(profile.getEmail()); 
        if (null != user) { 
        	logger.info(">> SignIn Google User Exist <<");
            if(!user.isEnabled()) {
                return new AuthenticationResponse("Unauthorized", "Your Account is disable. Please chek your email for activation", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
            }
 
            RegisterRequest register = new RegisterRequest(); 
            register.setUsername(profile.getEmail());
            register.setEmail(profile.getEmail()); 
            register.setPassword(profile.getId()); 
            register.setName(profile.getName()); 
 
 
            String oldPassword = user.getPassword(); 
            if (!passwordEncoder.matches(register.getPassword(), oldPassword)) {
            	user.setPassword(passwordEncoder.encode(register.getPassword()));
            	userService.save(user);
            } 
            
	        HttpHeaders headers = new HttpHeaders();
	        String auth = clientId + ":" + "password";
	        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
	        String authHeader = "Basic " + encodedAuth;
	        headers.set("Authorization", authHeader);
	        
	        String url = baseUrl + "oauth/token?username=" + register.getUsername() + 
	                "&password=" + register.getPassword() + 
	                "&grant_type=password";
	        
			ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, new HttpEntity<>(headers), new ParameterizedTypeReference<Map>() {}); 
 
            if (response.getStatusCode() == HttpStatus.OK) { 
                userService.save(user); 
 
                mapResponse.put("access_token", response.getBody().get("access_token")); 
                mapResponse.put("token_type", response.getBody().get("token_type")); 
                mapResponse.put("refresh_token", response.getBody().get("refresh_token")); 
                mapResponse.put("expires_in", response.getBody().get("expires_in")); 
                mapResponse.put("scope", response.getBody().get("scope")); 
                mapResponse.put("jti", response.getBody().get("jti")); 
		        
                mapResponse.put("code", 200); 
                mapResponse.put("message", "SUKSESS"); 
                mapResponse.put("type", "login");
                user.setPassword(oldPassword); 
                userService.save(user);
                
                return new AuthenticationResponse(new AuthenticationResponse(mapResponse, ResponseConstant.DATA_SUKSES, "login", String.valueOf(HttpStatus.OK.value())), ResponseConstant.DATA_SUKSES, String.valueOf(HttpStatus.OK.value()));
 
            } 
        } else { 
        	logger.info(">> SignIn Google New User <<");
            RegisterRequest registerModel = new RegisterRequest(); 
            registerModel.setEmail(profile.getEmail()); 
            registerModel.setUsername(profile.getEmail());
            registerModel.setName(profile.getName()); 
            registerModel.setPassword(profile.getId()); 
 
    		AuthenticationResponse authenticationResponse = userService.registerManual(registerModel);
    		authenticationResponse.setType("register");
            return new AuthenticationResponse(authenticationResponse, ResponseConstant.DATA_SUKSES, String.valueOf(HttpStatus.OK.value()));
        }
        logger.info(">> End Success SignIn Google <<");
        return new AuthenticationResponse(mapResponse, ResponseConstant.DATA_SUKSES, String.valueOf(HttpStatus.OK.value()));
	}
	
	@Override
    public AuthenticationResponse exchangeCodeForAccessToken(String code) {
		
        logger.info(">> Change code with access token <<");
        
        RestTemplate restTemplate = new RestTemplate();

        String tokenEndpoint = "https://oauth2.googleapis.com/token";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenEndpoint, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                String accessToken = jsonNode.get("access_token").asText();
                logger.info("Access Token: " + accessToken);
                return signInGoogle(accessToken);
            } catch (Exception e) {
            	logger.info(">> Error Change code : " + e.getMessage() + " <<");
                e.printStackTrace();
            }
        }

        return null;
    }
	
}
