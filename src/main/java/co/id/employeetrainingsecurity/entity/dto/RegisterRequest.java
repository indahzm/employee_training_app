package co.id.employeetrainingsecurity.entity.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
	
	@NotEmpty(message = "username is required.")
	private String username;
	private String email;
	private String name;
	private String phoneNumber;
	private String domicile;
	private String gender;
	@NotEmpty(message = "password is required.")
	private String password;
	@NotEmpty(message = "fullname is required.")
	private String fullname;

}
