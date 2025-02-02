package co.id.employeetrainingsecurity.entity.dto;

public class AuthenticationResponse {
	
	private Object data;
	private String message;
	private String status;
	
	public AuthenticationResponse (Object data, String message, String status){
		this.data = data;
		this.message = message;
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
