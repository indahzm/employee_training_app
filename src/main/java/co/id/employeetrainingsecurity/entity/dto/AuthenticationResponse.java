package co.id.employeetrainingsecurity.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {
	
	private Object data;
	private String message;
	private String type;
	private String status;
	
	public AuthenticationResponse (Object data, String message, String status){
		this.data = data;
		this.message = message;
		this.status = status;
	}

	public AuthenticationResponse (Object data, String message, String type, String status){
		this.data = data;
		this.message = message;
		this.type = type;
		this.status = status;
	}

}
