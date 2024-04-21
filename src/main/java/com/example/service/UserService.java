package com.example.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	
	@Value("${jwt.signing.key}")
    private String signingKey;
	
	@Autowired
	UserRepository repo;
	
	public List findAll() {
		return (List) repo.findAll();
	}
	
	public void saveUser(User user) {
		repo.save(user);
	}
	
	public void findByUseridAndPassword(User user, HttpServletResponse response) throws Exception {
		
		validChk(user.getUserid(), user.getPassword());
		
		Optional<User> optionalUser = repo.findByUseridAndPassword(user.getUserid(), user.getPassword());
		
		if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("회원이 존재하지 않음");
        }
		
		GrantedAuthority a = new SimpleGrantedAuthority("user");
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUserid(), null, List.of(a));
        SecurityContextHolder.getContext().setAuthentication(auth);
        
		SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder()
                .setClaims(Map.of("username", user.getUserid()))
                .signWith(key)
                .compact();
        
        Cookie cookie = new Cookie("token", jwt);
		cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
		cookie.setPath("/");
		cookie.setSecure(false);
	
		response.addCookie(cookie);
	}
	
	public void join(User user, HttpServletResponse response) throws Exception {
		
		validChk(user.getUserid(), user.getPassword());
		
		Optional<User> optionalUser = repo.findByUserid(user.getUserid()); // 아이디 중복체크
		
		if (!optionalUser.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다");
        }
		
		repo.save(user);
		
		GrantedAuthority a = new SimpleGrantedAuthority("user");
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUserid(), null, List.of(a));
        SecurityContextHolder.getContext().setAuthentication(auth);
        
		SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder()
                .setClaims(Map.of("username", user.getUserid()))
                .signWith(key)
                .compact();
        
        Cookie cookie = new Cookie("token", jwt);
		cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
		cookie.setPath("/");
		cookie.setSecure(false);
	
		response.addCookie(cookie);
	}

	private void validChk(@NonNull String userid, @NonNull String password){
		
		final String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"; // 영문, 숫자, 특수문자

		if (userid.length()<4) {
			throw new IllegalArgumentException("아이디는 영어 숫자 특수문자를 조합하여 4~15자리이어야 합니다.");
		}
		
		if (password.length()<4) {
			throw new IllegalArgumentException("비밀번호는 영어 숫자 특수문자를 조합하여 4~15자리이어야 합니다.");
		}
		
		if (!Pattern.compile(pattern).matcher(userid).find()) {
			throw new IllegalArgumentException("아이디는 영어 숫자 특수문자를 조합하여 4~15자리이어야 합니다.");
		}
		
		if (!Pattern.compile(pattern).matcher(password).find()) {
			throw new IllegalArgumentException("비밀번호는 영어 숫자 특수문자를 조합하여 4~15자리이어야 합니다.");
		}
	}
}
