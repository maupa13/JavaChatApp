package com.hrpirme.groupchatapp.config;

import com.hrpirme.groupchatapp.websocket.ChatMessageWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for WebSocket support in the application.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatMessageWebSocketHandler chatMessageWebSocketHandler;

    /**
     * Constructs a new WebSocketConfig with the provided ChatMessageWebSocketHandler.
     *
     * @param chatMessageWebSocketHandler The handler for WebSocket connections.
     */
    @Autowired
    public WebSocketConfig(ChatMessageWebSocketHandler chatMessageWebSocketHandler) {
        this.chatMessageWebSocketHandler = chatMessageWebSocketHandler;
    }

    /**
     * Registers the ChatMessageWebSocketHandler to handle WebSocket connections.
     * WebSocket connections are allowed at the "/chat" endpoint with any origin.
     *
     * @param registry The WebSocketHandlerRegistry used to register WebSocket handlers.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatMessageWebSocketHandler, "/chat").setAllowedOrigins("*");
    }
}
