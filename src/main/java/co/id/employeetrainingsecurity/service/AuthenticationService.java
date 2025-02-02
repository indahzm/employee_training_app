package co.id.employeetrainingsecurity.service;

import co.id.employeetrainingsecurity.entity.dto.AuthenticationResponse;

public interface AuthenticationService {
	
	AuthenticationResponse signInGoogle(String accessToken);
	
	AuthenticationResponse exchangeCodeForAccessToken(String code);
}
