package com.host.homehelper.config.security;

import com.host.homehelper.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final String[] defaultEndPoint = {"/", "/index", "/auth/**"};
	private final String[] adminEndPoint = {"/secure/**"};

	private final UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authorize) ->
						authorize
								.requestMatchers(defaultEndPoint).permitAll()
								.requestMatchers(adminEndPoint).hasAnyAuthority(Role.ADMIN.name())
								.anyRequest().authenticated()
				)
				.formLogin(
						login -> login
								.loginPage("/auth/login")
								.usernameParameter("email")
								.defaultSuccessUrl("/about")
								.permitAll()
				)
				.logout(
						logout -> logout
								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
								.invalidateHttpSession(true)
								.permitAll()
				);
		return http.build();
	}

}
