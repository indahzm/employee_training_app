package co.id.employeetrainingsecurity.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.id.employeetrainingsecurity.entity.User;
import co.id.employeetrainingsecurity.entity.dto.ForgetPasswordRequest;
import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.entity.dto.ResponseConstant;
import co.id.employeetrainingsecurity.service.UserService;
import co.id.employeetrainingsecurity.util.EmailSender;
import co.id.employeetrainingsecurity.util.EmailTemplate;
import co.id.employeetrainingsecurity.util.GenerateString;

@RestController
@RequestMapping("/forget-password")
public class ForgetPasswordController {

	@Autowired
	private UserService userService;
	
	@Value("${expired.token.password.minute}")
	private String expiredToken;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailTemplate emailTemplate;
	
	@PostMapping("/send")
	public ResponseEntity<AuthenticationResponse> sendEmailPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
		
		if (forgetPasswordRequest.getUsername() == null || forgetPasswordRequest.getUsername() == "") {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(ResponseConstant.DATA_IS_EMPTY.replace("${object}", "Username"), "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		
		User user = userService.findByUsername(forgetPasswordRequest.getUsername());
		if (user == null) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(ResponseConstant.DATA_NOT_FOUND.replace("${object}", "User"), "404", ResponseConstant.STATUS_NOT_FOUD), HttpStatus.valueOf(404));
		}
		
		String otp = GenerateString.generateOtp();
		
		Date dateNow = new Date(); 
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(dateNow); 
        calendar.add(Calendar.MINUTE, Integer.valueOf(expiredToken)); 
        Date expirationDate = calendar.getTime(); 

		user.setOtp(otp);
		user.setOtpExpiredDate(expirationDate);
		userService.save(user);
		
		String template = emailTemplate.getResetPassword();;
		template = template.replaceAll("\\{\\{OTP}}", otp);
		template = template.replaceAll("\\{\\{USERNAME}}", user.getUsername());
		
		emailSender.sendAsync(user.getUsername(), "IDStar - Forget Password", template); 
		
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
				(ResponseConstant.DATA_SUKSES, "200", ResponseConstant.STATUS_SUKSES), HttpStatus.valueOf(200));
		
	}
	
	@PostMapping("/validate")
	public ResponseEntity<AuthenticationResponse> checkValidToken(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
		
		User user = userService.findByUsername(forgetPasswordRequest.getUsername());
		if (user == null) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(ResponseConstant.DATA_NOT_FOUND.replace("${object}", "User"), "404", ResponseConstant.STATUS_NOT_FOUD), HttpStatus.valueOf(404));
		}
		
		if (forgetPasswordRequest.getOtp() == null || user.getOtp() == null 
				|| !user.getOtp().equals(forgetPasswordRequest.getOtp())) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("OTP Code mot match", "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		
		if (user.getOtpExpiredDate() != null && user.getOtpExpiredDate().before(new Date())) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("OTP Code not valid", "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		user.setOtpExpiredDate(null);
		userService.save(user);
		
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
				(ResponseConstant.DATA_SUKSES, "200", ResponseConstant.STATUS_SUKSES), HttpStatus.valueOf(200));
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<AuthenticationResponse> changePassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
		if (forgetPasswordRequest.getEmail() == null) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(ResponseConstant.DATA_IS_EMPTY.replace("${object}", "Email"), "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		if (forgetPasswordRequest.getOtp() == null) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(ResponseConstant.DATA_IS_EMPTY.replace("${object}", "OTP"), "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		if(forgetPasswordRequest.getNewPassword() == null) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(ResponseConstant.DATA_IS_EMPTY.replace("${object}", "New Password"), "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		if(forgetPasswordRequest.getConfirmNewPassword() == null) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(ResponseConstant.DATA_IS_EMPTY.replace("${object}", "Confirm New Password"), "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		if(!forgetPasswordRequest.getNewPassword().equals(forgetPasswordRequest.getConfirmNewPassword())) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("New Password and Confirm New Password mot match", "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		
		User user = userService.findByUsername(forgetPasswordRequest.getEmail());
		if (user == null) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(ResponseConstant.DATA_NOT_FOUND.replace("${object}", "User"), "404", ResponseConstant.STATUS_NOT_FOUD), HttpStatus.valueOf(404));
		}
		
		if(user.getOtpExpiredDate() != null || user.getOtp() == null) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("Please check your email. Validate your request using confirmation code!", "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		
		if (!user.getOtp().equals(forgetPasswordRequest.getOtp())) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("OTP Code mot match", "400", ResponseConstant.STATUS_BAD_REQUEST), HttpStatus.valueOf(400));
		}
		
		user.setPassword(passwordEncoder.encode(forgetPasswordRequest.getNewPassword()));
		user.setOtp(null);
		userService.save(user);
		
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
				(ResponseConstant.DATA_SUKSES, "200", ResponseConstant.STATUS_SUKSES), HttpStatus.valueOf(200));
	}
}
