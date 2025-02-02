package co.id.employeetrainingsecurity.entity.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
	@NotEmpty(message = "username is required")
	private String username;
	
	@NotEmpty(message = "password is required")
	private String password;

}
