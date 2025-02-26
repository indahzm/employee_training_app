package co.id.employeetrainingsecurity.controller;

import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import co.id.employeetrainingsecurity.entity.User;
import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.entity.dto.RegisterRequest;
import co.id.employeetrainingsecurity.entity.dto.ResponseConstant;
import co.id.employeetrainingsecurity.service.UserService;

@RestController
@RequestMapping("/user-register")
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {

		AuthenticationResponse authenticationResponse = userService.registerManual(registerRequest);
        return new ResponseEntity<>(authenticationResponse.getStatus().equals(String.valueOf(200)) ? authenticationResponse.getData() : authenticationResponse, 
        		HttpStatus.valueOf(Integer.valueOf(authenticationResponse.getStatus()))); 
	}
	
    @PostMapping("/send-otp") 
    public ResponseEntity<?> sendEmailegister(@RequestBody RegisterRequest registerRequest) {
    	
        String message = "Thanks, please check your email for activation."; 

        User userExist = userService.findByUsername(registerRequest.getUsername());
        
		if (userExist == null) {
			return new ResponseEntity<>(new AuthenticationResponse("User not found", ResponseConstant.STATUS_NOT_FOUD, "404"), 
					HttpStatus.NOT_FOUND);
		}
		
        userService.sendEmailOtp(userExist);
        userService.save(userExist); 
    	
        return new ResponseEntity<>(new AuthenticationResponse(message, ResponseConstant.STATUS_SUKSES, "200"), 
        		HttpStatus.OK);
    } 
    
    @GetMapping("/register-confirm-otp/{otp}")
    public ResponseEntity<?> registerConfirmOtp(@PathVariable(required = true) String otp, @RequestBody @Nullable RegisterRequest registerRequest) { 
        String message = "Your account has been active. Please login with your account"; 
        
        User userExist = userService.findByUsername(registerRequest.getUsername());
        
		if (userExist == null) {
			return new ResponseEntity<>(new AuthenticationResponse("User not found", ResponseConstant.STATUS_NOT_FOUD, "404"),
					HttpStatus.NOT_FOUND);
		}
		
		if (userExist.getOtpExpiredDate().before(new Date())) {
			return new ResponseEntity<>(new AuthenticationResponse("Your token is expired. Please get token again", ResponseConstant.STATUS_BAD_REQUEST, "400"), 
					HttpStatus.BAD_REQUEST);
		}
		userExist.setOtp(null);
		userExist.setOtpExpiredDate(null);
		userExist.setEnabled(true);
		userExist.setCredentialsNonExpired(true);
        userService.save(userExist); 
		
        return new ResponseEntity<>(new AuthenticationResponse(message, ResponseConstant.STATUS_SUKSES, "200"), 
        		HttpStatus.OK);    
    } 
    
    @GetMapping("/web/index/{otp}")
    public ModelAndView registerWebIndex(@PathVariable(required = true) String otp) {
    	ModelAndView model = new ModelAndView();
    	model.setViewName("success_register");
    	
        User userExist = userService.findByOtp(otp);
        
		if (userExist == null) {
			model.addObject("title", "Registration Failed!");
			model.addObject("status", "failure");
			model.addObject("message", "User not found.");
			return model;
		}
		
		if (userExist.getOtpExpiredDate().before(new Date())) {
			model.addObject("status", "failure");
			model.addObject("title", "Registration Failed!");
			model.addObject("message", "The link has been expired.");
			return model;
		}
		userExist.setOtp(null);
		userExist.setEnabled(true);
		userExist.setCredentialsNonExpired(true);
        userService.save(userExist); 
		
		model.addObject("status", "success");
		model.addObject("title", "Registration Successful!");
		model.addObject("message", "Thank you for confirming your email address. Your account is now active.");
		return model; 
    } 
    
} 

