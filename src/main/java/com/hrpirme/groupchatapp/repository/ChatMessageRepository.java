package com.hrpirme.groupchatapp.repository;

import com.hrpirme.groupchatapp.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing chat message data in the database.
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
