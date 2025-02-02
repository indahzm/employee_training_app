package co.id.employeetrainingsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.service.AuthenticationService;

@RestController
public class CommonController {
	
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
        // Authorization code diterima
		
    	ModelAndView model = new ModelAndView();
    	model.setViewName("success_login");
    	
        System.out.println("Authorization Code: " + code);
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
//		try {
//            httpTransport =  GoogleNetHttpTransport.newTrustedTransport(); 
//            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR); 
//            Credential credential = authorize(); 
//            oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName( 
//                    APPLICATION_NAME).build(); 
//            System.out.println("token saya = "+credential.getAccessToken()); 
//            tokenInfo(credential.getAccessToken()); 
//            userInfo();
//            return "SUCCESS LOGIN"; 
//        } catch (IOException e) { 
//            System.err.println(e.getMessage()); 
//        } catch (Throwable t) { 
//            t.printStackTrace(); 
//        }
//		return "FAILED";
//    } 
	

	
}
//@RestController
//public class CommonController {
//
//    @Autowired
//    private OAuth2AuthorizedClientService authorizedClientService;
//
//    private static final List<String> SCOPES = Arrays.asList(
//        "https://www.googleapis.com/auth/userinfo.profile",
//        "https://www.googleapis.com/auth/userinfo.email"
//    );
//
//    @GetMapping("/login")
//    public ModelAndView login() {
//        ModelAndView model = new ModelAndView();
//        model.setViewName("login");
//        return model;
//    }
//
//    @GetMapping("/home")
//    public String home(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
//                      @AuthenticationPrincipal OAuth2User oauth2User) {
//        try {
//            String accessToken = authorizedClient.getAccessToken().getTokenValue();
//            
//            // Use the access token to get user info if needed
//            System.out.println("Access Token: " + accessToken);
//            System.out.println("User Email: " + oauth2User.getAttribute("email"));
//            System.out.println("User Name: " + oauth2User.getAttribute("name"));
//
//            return "SUCCESS LOGIN";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "FAILED";
//        }
//    }
//}


