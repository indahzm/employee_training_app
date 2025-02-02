package co.id.employeetrainingsecurity.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPasswordRequest {

	private String username;
	private String otp;
	private String email;
	private String newPassword;
	private String confirmNewPassword;
	
}
