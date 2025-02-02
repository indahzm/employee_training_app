package co.id.employeetrainingsecurity.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileStorageProperties {
	
	@Value("${app.uploadto.cdn}")
	private String uploadDir;
	
}
