package co.id.employeetrainingsecurity.config;
//import javax.annotation.Priority;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; 
import 
org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import 
//org.springframework.security.crypto.password.PasswordEncoder;
//import 
//org.springframework.security.oauth2.provider.token.AccessTokenConverter;
//import 
//org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
//import 
//org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import 
//org.springframework.security.oauth2.provider.token.TokenStore;
//import 
//org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import 
//org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import 
//org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//@Priority(1)
@Configuration 
@EnableWebSecurity 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { 
 
//    @Value("${security.bcrypt.cost:13}") 
//    private int cost; 
//    
//    @Value("${security.jwt.enabled:false}") 
//    private boolean jwtEnabled; 
// 
//    @Value("${security.jwt.secret_key:s3cr3t}") 
//    private String jwtSecretKey; 
// 
//    @Autowired
//    private Oauth2AccessTokenConverter accessTokenConverter;
//
//    @Bean
//    PasswordEncoder passwordEncoder() { 
//        return new BCryptPasswordEncoder(cost); 
//    } 
// 
//    @Bean 
//    @Override 
//    public AuthenticationManager authenticationManagerBean() throws Exception { 
//        return super.authenticationManagerBean(); 
//    }
//
//    @Bean
//    TokenStore tokenStore() { 
//        if (jwtEnabled) { 
//            return new JwtTokenStore((JwtAccessTokenConverter) accessTokenConverter()); 
//        } 
//        return new InMemoryTokenStore(); 
//    }
//
//    @Bean
//    AccessTokenConverter accessTokenConverter() { 
//        if (jwtEnabled) { 
//            JwtAccessTokenConverter jwtConverter = new JwtAccessTokenConverter(); 
//            jwtConverter.setAccessTokenConverter(accessTokenConverter); 
//            jwtConverter.setSigningKey(jwtSecretKey); 
//            return jwtConverter; 
//        } 
//        return new DefaultAccessTokenConverter(); 
//    }
//
//    @Bean
//    @Primary
//    DefaultTokenServices tokenServices() { 
//        DefaultTokenServices services = new DefaultTokenServices(); 
//        services.setTokenStore(tokenStore()); 
//        services.setSupportRefreshToken(true); 
// 
//        return services; 
//    } 
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http
////        		.cors(Customizer.withDefaults())
////        		.csrf(csrf -> csrf.disable())
////                .authorizeHttpRequests(requests -> requests
////                        .anyRequest().permitAll())
////                .httpBasic(Customizer.withDefaults());
//        http.cors(Customizer.withDefaults())
//                .csrf(csrf -> csrf
//                        .disable())
//                .antMatcher("/**")
////                .authorizeRequests(requests -> requests
////
////                        .antMatchers("/", "/showFile/**", "/v1/showFile/**", "/v1/upload",
////                                "/user-register/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/user-login/**",
////                                "/forget-password/**",
////                                "/oauth/authorize**", "/login**", "/error**", "/oauth/token**")
////                        .permitAll()
////                        .antMatchers("/v1/role-test-global/list-barang").hasAnyAuthority("ROLE_READ")
////                        .antMatchers("/v1/role-test-global/post-barang").hasAnyAuthority("ROLE_WRITE")
////                        .antMatchers("/v1/role-test-global/post-barang-user").hasAnyAuthority("ROLE_USER")
////                        .antMatchers("/v1/role-test-global/post-barang-admin").hasAnyAuthority("ROLE_ADMIN"))
//                .authorizeRequests(requests -> requests
//                        .anyRequest()
//                        .permitAll())
//                .formLogin(login -> login
//                        .permitAll())
//              .oauth2Client(Customizer.withDefaults())
//              .oauth2Login(Customizer.withDefaults());
//
//    }

	@Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/@Bean
	@Override
	public AuthenticationManager authenticationManagerBean () throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Autowired
	@Lazy
    private UserDetailsService userDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//        .csrf(csrf -> csrf.disable())
	        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeRequests(requests -> requests.antMatchers("/oauth/token").permitAll()
	        		.anyRequest().authenticated())
	        .formLogin(Customizer.withDefaults());
    }

//    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
} 