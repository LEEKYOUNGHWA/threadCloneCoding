package com.example.config;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.example.service.UserService;
import com.example.util.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
    	.antMatchers("/","v2/**", "/css/**", "/images/**", "/login/**", "/js/**", "/h2-console/**"
                    		 ,"/api-docs",
                    	      "/swagger-ui-custom.html",
                    	      "/swagger-custom-ui.html",
                    	      "/v3/api-docs/**",
                    	      "/swagger-ui/**",
                    	      "/api-docs/**",
                    	      "/user/**",
                    	      "/swagger-ui.html", 
                    	      "/swagger-ui/**", 
                    	      "/swagger-resources/**", 
                    	      "/swagger-ui/index.html", 
                    	      "/swagger-config/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/board").permitAll()
                    .anyRequest().authenticated();
        
        http.formLogin(login -> login.loginPage("/login"));
        
        http.logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 요청을 처리할 URL 설정
                .logoutSuccessHandler((request, response, authentication) -> // 로그아웃 성공 핸들러 추가 (리다이렉션 처리)
                        response.sendRedirect("/"))
                .deleteCookies("token","JSESSIONID")); // 로그아웃 시 쿠키 삭제 설정 (예: "remember-me" 쿠키 삭제)
                
        http.addFilterAfter(jwtAuthenticationFilter,BasicAuthenticationFilter.class);
    }
    
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}