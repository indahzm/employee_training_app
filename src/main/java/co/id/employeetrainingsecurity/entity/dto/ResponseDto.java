package co.id.employeetrainingsecurity.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {
	private Integer code;
	private Object data;
	private String status;
	
	public ResponseDto (String status, Object data, Integer code){
		this.code = code;
		this.data = data;
		this.status = status;
	}
}
