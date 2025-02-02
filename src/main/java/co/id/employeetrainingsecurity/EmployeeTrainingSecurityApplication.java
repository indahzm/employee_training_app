package co.id.employeetrainingsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import co.id.employeetrainingsecurity.util.FileStorageProperties;


@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
@EnableWebSecurity
public class EmployeeTrainingSecurityApplication {
	     
	public static void main(String[] args) {
		SpringApplication.run(EmployeeTrainingSecurityApplication.class, args);
	}

}
