package com.hrpirme.groupchatapp.controller;

import com.hrpirme.groupchatapp.model.ChatMessage;
import com.hrpirme.groupchatapp.service.ChatMessageService;
import com.hrpirme.groupchatapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller class for managing chat-related requests.
 */
@Controller
@RequestMapping("/")
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final UserService userService;

    /**
     * Constructs a new ChatController with the provided services.
     *
     * @param chatMessageService The service for managing chat messages.
     * @param userService        The service for managing users.
     */
    public ChatController(ChatMessageService chatMessageService, UserService userService) {
        this.chatMessageService = chatMessageService;
        this.userService = userService;
    }

    /**
     * Handles GET requests to the root URL ("/").
     * Retrieves all chat messages and renders the login page.
     *
     * @param model The model to be populated with data for the view.
     * @return The view name for the login page.
     */
    @GetMapping("/")
    public String login(Model model) {
        List<ChatMessage> messages = chatMessageService.getAllChatMessages();
        model.addAttribute("messages", messages);
        return "login";
    }

    /**
     * Handles POST requests to "/login".
     * Saves the user, retrieves chat messages and users, and renders the chat page.
     *
     * @param username The username submitted through the login form.
     * @param model    The model to be populated with data for the view.
     * @return The view name for the chat page.
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, Model model) {
        userService.saveUser(username);
        model.addAttribute("username", username);
        model.addAttribute("messages", chatMessageService.getAllChatMessages());
        model.addAttribute("users", userService.getAllUsers());
        return "chat";
    }
}
