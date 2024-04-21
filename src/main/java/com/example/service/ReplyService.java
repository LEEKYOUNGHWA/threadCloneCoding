package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.model.Reply;
import com.example.model.User;
import com.example.repository.ReplyRepository;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService{
	
	@Autowired
	ReplyRepository repo;
	

	public void insertReply(Reply reply, Authentication auth) {
		
		Reply replyd = new Reply( null, reply.getBoardno(), auth.getName(), reply.getReply());
		repo.save(replyd);
	}
	
	@Transactional
	public void deleteReply(Reply reply, Authentication auth) {
		repo.deleteByUseridAndReplyno(auth.getName(), reply.getReplyno());
	}

	public List<Reply> findByBoardnoOrderByReplynoDesc(Integer board) {
		
		Optional<List<Reply>> optionalReply = Optional.ofNullable(repo.findByBoardnoOrderByReplynoDesc(board));

		return optionalReply.get();
	}
}
