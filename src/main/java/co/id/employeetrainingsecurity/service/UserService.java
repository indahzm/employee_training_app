package co.id.employeetrainingsecurity.service;

import co.id.employeetrainingsecurity.entity.User;
import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;
import co.id.employeetrainingsecurity.entity.dto.LoginRequest;
import co.id.employeetrainingsecurity.entity.dto.RegisterRequest;

public interface UserService {
	
	User findByUsername(String username);
	User findByOtp(String otp);
	User save(User user);
	
	AuthenticationResponse login(LoginRequest loginRequest);
	AuthenticationResponse registerManual(RegisterRequest registerRequest);
	void sendEmailOtp(User user);
	
}
