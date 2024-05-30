package com.hrpirme.groupchatapp.service;

import com.hrpirme.groupchatapp.model.ChatMessage;
import com.hrpirme.groupchatapp.repository.ChatMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing chat message-related operations.
 */
@Slf4j
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    /**
     * Constructs a new ChatMessageService with the provided ChatMessageRepository.
     *
     * @param chatMessageRepository The repository for accessing chat message data.
     */
    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    /**
     * Saves a new chat message.
     *
     * @param chatMessage The chat message to save.
     */
    public void saveChatMessage(ChatMessage chatMessage) {
        log.info("Save chat message, content: {}", chatMessage.getContent());
        chatMessageRepository.save(chatMessage);
    }

    /**
     * Retrieves all chat messages.
     *
     * @return A list containing all chat messages.
     */
    public List<ChatMessage> getAllChatMessages() {
        log.info("Get all chat messages");
        return chatMessageRepository.findAll();
    }
}
