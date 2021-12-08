package com.ubayKyu.accountingSystem.SecurityConfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ubayKyu.accountingSystem.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	    private DataSource dataSource;
	@Bean
    public UserDetailsService userDetailsService() {
        return new AuthService();
    }
	@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(getEncoder());
         
        return authProvider;
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                            "/Images/**",
                            "/bootstrap/**");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
				.antMatchers(HttpMethod.GET, "/Default.html", "/Login.html", "/UserProfile.html")
				.permitAll() 
	            .anyRequest().authenticated()
	        .and()

			.formLogin()
				.loginPage("/Login.html")
				.usernameParameter("acc")
				.passwordParameter("pwd")
				.defaultSuccessUrl("/UserProfile.html?account=")
			.and();
		http
				.logout().logoutSuccessUrl("/Default.html")
			.and()
				.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
}
