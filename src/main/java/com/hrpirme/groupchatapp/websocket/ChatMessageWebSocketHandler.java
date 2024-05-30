package com.hrpirme.groupchatapp.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hrpirme.groupchatapp.model.ChatMessage;
import com.hrpirme.groupchatapp.model.User;
import com.hrpirme.groupchatapp.service.ChatMessageService;
import com.hrpirme.groupchatapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles WebSocket connections for the Group Chat application.
 */
@Slf4j
@Component
public class ChatMessageWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatMessageService chatMessageService;
    private final UserService userService;
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    /**
     * Constructs a new ChatMessageWebSocketHandler.
     *
     * @param chatMessageService The service for managing chat messages.
     * @param userService        The service for managing users.
     */
    @Autowired
    public ChatMessageWebSocketHandler(ChatMessageService chatMessageService, UserService userService) {
        this.chatMessageService = chatMessageService;
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Invoked after a new WebSocket connection is established.
     * Adds the session to the set of active sessions and sends online user information to all clients.
     *
     * @param session The WebSocket session that was established.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("AfterConnectionEstablished {}", session.getId());
        sessions.add(session);
        sendOnlineUsers();
    }

    /**
     * Invoked after a WebSocket connection is closed.
     * Removes the session from the set of active sessions and sends updated online user information to all clients.
     *
     * @param session The WebSocket session that was closed.
     * @param status  The status indicating the reason for the closure.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("AfterConnectionClosed {}", session.getId());
        sessions.remove(session);
        sendOnlineUsers();
    }

    /**
     * Handles incoming text messages from WebSocket clients.
     * Parses the message, saves it as a chat message, and broadcasts it to all connected clients.
     *
     * @param session The WebSocket session receiving the message.
     * @param message The text message received from the client.
     * @throws IOException If there is an error reading or writing the message.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        String username = jsonNode.get("username").asText();
        String content = jsonNode.get("content").asText();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(user);
        chatMessage.setContent(content);
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageService.saveChatMessage(chatMessage);

        ObjectNode broadcastMessage = objectMapper.createObjectNode();
        broadcastMessage.put("username", user.getUsername());
        broadcastMessage.put("content", chatMessage.getContent());
        broadcastMessage.put("timestamp", chatMessage.getTimestamp().toString());

        String broadcastMessageString = objectMapper.writeValueAsString(broadcastMessage);

        synchronized (sessions) {
            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(broadcastMessageString));
            }
        }
    }

    /**
     * Sends online user information to all connected clients.
     */
    private void sendOnlineUsers() {
        Set<String> onlineUsers = new HashSet<>();
        for (WebSocketSession session : sessions) {
            String username = (String) session.getAttributes().get("username");
            if (username != null) {
                onlineUsers.add(username);
            }
        }

        // Answer for clients
        ObjectNode onlineUsersMessage = objectMapper.createObjectNode();
        onlineUsersMessage.set("onlineUsers", objectMapper.valueToTree(onlineUsers));

        String onlineUsersMessageString = null;
        try {
            onlineUsersMessageString = objectMapper.writeValueAsString(onlineUsersMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Send message for all clients
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(onlineUsersMessageString));
                } catch (IOException e) {
                    log.error("Error sending online users message", e);
                }
            }
        }
    }
}
