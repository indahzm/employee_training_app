package co.id.employeetrainingsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration 
@EnableAuthorizationServer 
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter { 
	
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
	
	@Autowired
	@Lazy
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	@Lazy
	private AuthenticationManager authenticationManagerBean;
	
	@Autowired
	@Lazy
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManagerBean)
			.userDetailsService(userDetailsService);
	}

	@Override
	public void configure (AuthorizationServerSecurityConfigurer security) throws Exception
	{
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure (ClientDetailsServiceConfigurer clients) throws Exception
	{
		clients
			.inMemory().withClient(clientId)
			.authorizedGrantTypes("password", "implicit", "refresh_token", "authorization_code").autoApprove(true)
			.secret(bCryptPasswordEncoder.encode("password")).scopes("read", "write")
			.accessTokenValiditySeconds(72000)
			.refreshTokenValiditySeconds(80000);
	}

	@Bean
	TokenStore tokenStore () 	{
		return new InMemoryTokenStore();
	}
}