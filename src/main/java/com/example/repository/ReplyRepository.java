package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.model.Board;
import com.example.model.Reply;
import com.example.model.User;

public interface ReplyRepository extends CrudRepository<Reply, Integer>{
	Reply save(Reply reply);
	List<Reply> deleteByUseridAndReplyno(String userid, Integer replyno);
	List<Reply> findByBoardnoOrderByReplynoDesc(Integer replyno);
}
