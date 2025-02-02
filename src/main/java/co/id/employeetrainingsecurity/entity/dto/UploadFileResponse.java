package co.id.employeetrainingsecurity.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse {
	
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;
	private String error;
	public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size, String error) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
		this.error = error;
	}
	
}
