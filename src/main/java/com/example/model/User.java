package com.example.model;

import java.util.Collection;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Tag(name = "USER", description = "회원")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity
@Table(name="USER")
public class User {
	
	@Id
	@NonNull
	@Column(unique=true)
	@Schema(description = "유저 아이디", defaultValue = "testtest1!")
	private String userid;
	
	@NonNull
	@Schema(description = "유저 패스워드", defaultValue = "testtest1!")
	private String password;
}