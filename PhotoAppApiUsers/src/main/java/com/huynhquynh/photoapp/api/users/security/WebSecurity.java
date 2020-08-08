package com.huynhquynh.photoapp.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.huynhquynh.photoapp.api.users.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	private UsersService usersService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment environment, UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.environment = environment;
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
//		Chỉ cho phép ác request đến từ gateway truy cập
		http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
		
//		http.authorizeRequests().antMatchers("/users-ws/**").permitAll()
		
		.and()
//		B2: Thêm lớp filter xác thực và gọi lớp xử lý xác thực để làm nhiệm vụ này
		.addFilter(getAuthenticationFilter());
		
//		Vô hiệu hóa security cho H2
		http.headers().frameOptions().disable();
	}

//	B2
	private AuthenticationFilter getAuthenticationFilter() throws Exception{
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
		
//		B8: Customize đường dẫn login nếu k muốn dùng mặc định của pring security
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}

//	B3: Khai báo với Spring là service nào sẽ kiểm soát người dùng và loại mã hóa password nào sẽ được sử dụng
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	

}
