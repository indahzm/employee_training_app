package co.id.employeetrainingsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.service.AuthenticationService;

@RestController
public class CommonController {
	
	private final static Logger logger = LoggerFactory.getLogger(CommonController.class); 
	@Autowired
	private AuthenticationService authenticationService;
	
	@GetMapping("/")
    public ModelAndView registerWebIndex() { 
    	ModelAndView model = new ModelAndView();
    	model.setViewName("login");
    	return model; 
    } 
	
	@GetMapping("/dashboard")
    public String dashboard() {
		return "Dashboard";
    } 
	
	@GetMapping("/Callback")
    public ModelAndView home(@RequestParam String code, @RequestParam(required = false) String scope) {
		
		logger.info(">> Authorization code accepted : " +  code + "<<" );
    	ModelAndView model = new ModelAndView();
    	model.setViewName("success_login");
    	
        AuthenticationResponse authenticationResponse = authenticationService.exchangeCodeForAccessToken(code);
        Boolean isSuccess = authenticationResponse == null ? false : (authenticationResponse.getStatus().equals((String.valueOf(200))) ? true : false);
        
        if (!isSuccess) {
			model.addObject("status", "failure");
			model.addObject("title", "Login Failed!");
			model.addObject("message", "Please try again later.");
			return model;
        }
		
		model.addObject("status", "success");
		model.addObject("title", "Login Success!");
		model.addObject("message", "Congratulation, you have been login successfully.");
		return model; 
    }
	
}