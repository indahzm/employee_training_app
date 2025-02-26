package co.id.employeetrainingsecurity.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.entity.dto.LoginRequest;
import co.id.employeetrainingsecurity.service.AuthenticationService;
import co.id.employeetrainingsecurity.service.UserService;


@RestController
@RequestMapping("/user-login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
    @Value("${BASEURL}") 
    private String baseUrl; 
	
    @PostMapping("/login") 
    @ExceptionHandler(ConstraintViolationException.class) 
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) { 
        AuthenticationResponse authenticationResponse = userService.login(loginRequest); 
        return new ResponseEntity<>(authenticationResponse.getStatus().equals(String.valueOf(200)) ? authenticationResponse.getData() : authenticationResponse, 
        		HttpStatus.valueOf(Integer.valueOf(authenticationResponse.getStatus()))); 
    }
    
    @PostMapping("/signin_google") 
    @ResponseBody
    public ResponseEntity<?> repairGoogleSigninAction(@RequestParam MultiValueMap<String, String> parameters) throws IOException { 
 
        Map<String, String> map = parameters.toSingleValueMap(); 
        String accessToken = map.get("accessToken"); 
 
        AuthenticationResponse authenticationResponse = authenticationService.signInGoogle(accessToken);
        return new ResponseEntity<>(authenticationResponse.getStatus().equals(String.valueOf(200)) ? authenticationResponse.getData() : authenticationResponse, 
        		HttpStatus.valueOf(Integer.valueOf(authenticationResponse.getStatus()))); 
    }
    
}
