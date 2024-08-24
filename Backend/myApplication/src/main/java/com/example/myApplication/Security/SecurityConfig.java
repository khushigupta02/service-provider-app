package com.example.myApplication.Security;

import com.example.myApplication.JWT.JwtAuthFilter;
import com.example.myApplication.Service.JWTService.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoService();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				   .cors().configurationSource(corsConfigurationSource()) // Add this line
				   .and()
				   .authorizeHttpRequests()
				   .requestMatchers("/", "/register", "/generateToken", "/viewAllUsers/**", "/viewAllServiceType",
						    "/viewAllRole", "/addServiceType", "/editUser/**",
						   "/findByUsername/**", "/deleteServiceType/**", "editServiceType/**",
				   "/viewAllReview"
				   ,"/viewAllCustomer" ,"/viewAllProvider", "/viewAllService" ,"/viewAllServiceByServiceProviderUsername/**",
						   "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v2/api-docs/**",
						   "/swagger-resources/**", "/webjars/**" , "/actuator/**").permitAll()
				   .and()
				   .authorizeHttpRequests().requestMatchers("/adminDashboard/**").authenticated()
				   .and()
				   .authorizeHttpRequests().requestMatchers("/serviceProviderDashboard/**","/addService/**",
						"/updateBookedServiceStatus/**","/viewBookingByServiceProviderUsername/**",
						"/viewAllCustomerHistory/**",
						"/viewAllBookingDetailsByUsername/**" ,"/viewBookingDetailByBookingId/**" ,"/deleteService/**" ,"/viewReview/**",
						"/viewAllProviderHistoryByUsername/**"  , "/viewAllBooking" ,"/viewAllSocialMedia" ,"/viewReviewOfProvider/**").authenticated()
				   .and()
				   .authorizeHttpRequests().requestMatchers("/customerDashboard/**"
						,"/findServiceProviderById/**","/findServiceByServiceProviderByID/**" ,
						"/viewAllServiceAndProvider","/searchService/**","/searchProvider/**","/searchAllService/**",
						"/sortAllService/**" ,"/viewAllServicePagination/**","/bookService/**",

						"/viewAllCustomerActivityByUsername/**","/viewServiceStatus/**" ,"/viewAllServiceStatus",
						"/cancelBookedService/**","/viewAllBooking","/reBookService/**" , "/viewBookedServiceStatus/**",
						"/getBookingId/**","/viewServiceByID/**" ,"/giveFeedback/**","/viewReviewByCustomer/**","/viewReviewByServiceId/**").authenticated()
				   .and()
				   .sessionManagement()
				   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				   .and().authenticationProvider(authenticationProvider())
				   .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				   .build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Replace with your React app's origin
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}