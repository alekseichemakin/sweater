package org.example.sweater.repository;

import org.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {
	List<Message> findByTag(String tag);
}
