package co.id.employeetrainingsecurity.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {
	
	private Object data;
	private String message;
	private String status;
	
	public AuthenticationResponse (Object data, String message, String status){
		this.data = data;
		this.message = message;
		this.status = status;
	}

}
