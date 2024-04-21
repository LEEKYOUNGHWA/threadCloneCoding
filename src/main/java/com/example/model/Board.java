package com.example.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "BOARD", description = "게시글")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity
@Table(name="BOARD")
public class Board {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "게시글 시퀀스 번호", defaultValue = "1")
	private final Integer no;
	
	@NonNull
	@Schema(description = "게시글 작성 유저 아이디", defaultValue = "test")
	private String userid;
	
	@NonNull
	@Schema(description = "게시글 내용", defaultValue = "게시글 내용 블라블라블라")
	private String board;
	
	@CreationTimestamp
	@Schema(description = "타임스탬프", defaultValue = "jpa에서 자동으로 생성됨 넣지 말것")
	private Timestamp date;

}