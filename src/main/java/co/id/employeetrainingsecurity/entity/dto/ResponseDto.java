package co.id.employeetrainingsecurity.entity.dto;

public class ResponseDto {
	private Integer code;
	private Object data;
	private String status;
	
	public ResponseDto (String status, Object data, Integer code){
		this.code = code;
		this.data = data;
		this.status = status;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
