package co.id.employeetrainingsecurity.controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.entity.dto.LoginRequest;
import co.id.employeetrainingsecurity.service.UserService;


@RestController
@RequestMapping("/user-login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
    @Value("${BASEURL}") 
    private String baseUrl; 
	
    @PostMapping("/login") 
    @ExceptionHandler(ConstraintViolationException.class) 
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) { 
        AuthenticationResponse authenticationResponse = userService.login(loginRequest); 
        return new ResponseEntity<>(authenticationResponse.getStatus().equals(String.valueOf(200)) ? authenticationResponse.getData() : authenticationResponse, 
        		HttpStatus.valueOf(Integer.valueOf(authenticationResponse.getStatus()))); 
    }
    
}
