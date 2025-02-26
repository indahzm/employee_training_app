package co.id.employeetrainingsecurity.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration 
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter { 
	
    @Value("${BASEURL}")
    private String baseUrl;
 
	@Override
	public void configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .authorizeRequests(requests -> requests
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        .antMatchers("/v1/**").authenticated()
                        .anyRequest().permitAll())
                		
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> response.sendRedirect("/Callback"))
                        .failureHandler((request, response, exception) -> {
                          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, 
                          "Authentication failed: " + exception.getMessage());
                        })
                 )
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .invalidateHttpSession(true) // Menghapus sesi HTTP
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Arahkan pengguna ke URL logout Google
                            response.sendRedirect("https://accounts.google.com/logout?continue=" + baseUrl);
                        })
                        .permitAll())
                .httpBasic(withDefaults());

        http.headers(headers -> headers.frameOptions().disable());

	}
}
