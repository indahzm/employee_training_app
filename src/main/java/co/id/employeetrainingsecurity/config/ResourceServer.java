package co.id.employeetrainingsecurity.config;

import static org.springframework.security.config.Customizer.withDefaults;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration 
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter { 
 
	@Override
	public void configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .authorizeRequests(requests -> requests
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
//                        .antMatchers("/").permitAll()
                        .antMatchers("/v1/**").authenticated()
                        .anyRequest().permitAll())
                		
                .oauth2Login(oauth2 -> oauth2
//                		  .loginPage(Customizer<>.withDefaults())
//                        .defaultSuccessUrl("/home", true)
                        .successHandler((request, response, authentication) -> response.sendRedirect("/Callback"))
                        .failureHandler((request, response, exception) -> {
                          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, 
                          "Authentication failed: " + exception.getMessage());
//                        	response.sendRedirect("/home");
                        })
                 )
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .invalidateHttpSession(true) // Menghapus sesi HTTP
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Arahkan pengguna ke URL logout Google
                            response.sendRedirect("https://accounts.google.com/logout?continue=http://localhost:8080");
                        })
                        .permitAll())
//                	.authorizeRequests(authorize -> authorize
//                        .antMatchers("/", "/login", "/error", "/oauth2/**").permitAll()
//                        .antMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
//                        .anyRequest().authenticated()
//                    )
//                    .oauth2Login(oauth2 -> oauth2
//                        .successHandler((request, response, authentication) -> {
//                            // Add logging or custom success handling
//                            response.sendRedirect("/home");
//                        })
//                        .failureHandler((request, response, exception) -> {
//                            // Add proper error handling
//                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, 
//                                "Authentication failed: " + exception.getMessage());
//                        })
//                    )
//                	 .oauth2Login(oauth2 -> oauth2
//                             .loginPage("/login")
//                             .defaultSuccessUrl("/home", true)
//                             .failureUrl("/login?error=true")
//                             .permitAll()
//                         )
//                    .logout(logout -> logout
//                        .invalidateHttpSession(true)
//                        .clearAuthentication(true)
//                        .deleteCookies("JSESSIONID")
//                        .logoutSuccessHandler((request, response, authentication) -> 
//                            response.sendRedirect("https://accounts.google.com/logout"))
//                    )
//                    .exceptionHandling(exceptions -> exceptions
//                            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
//                            .accessDeniedPage("/error")
//                        )
                .httpBasic(withDefaults());

        http.headers(headers -> headers.frameOptions().disable());

	}
}
