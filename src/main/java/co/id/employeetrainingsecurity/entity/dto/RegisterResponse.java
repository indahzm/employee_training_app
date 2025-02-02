package co.id.employeetrainingsecurity.entity.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
	
	private Long id;
	private String username;
	private String fullname;
	private String otp;
	private Date otpExpiredDate;
	private List<String> roles;
	private List<String> authorities;

}
