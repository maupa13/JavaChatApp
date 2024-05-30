package com.hrpirme.groupchatapp.controller;

import com.hrpirme.groupchatapp.model.ChatMessage;
import com.hrpirme.groupchatapp.model.User;
import com.hrpirme.groupchatapp.service.ChatMessageService;
import com.hrpirme.groupchatapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

    @Mock
    private ChatMessageService chatMessageService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChatController chatController;

    @Test
    public void testIndex() {
        // Given
        List<ChatMessage> mockMessages = new ArrayList<>();
        when(chatMessageService.getAllChatMessages()).thenReturn(mockMessages);
        Model model = mock(Model.class);

        // When
        String viewName = chatController.login(model);

        // Then
        assertEquals("login", viewName);
        verify(model).addAttribute("messages", mockMessages);
    }

    @Test
    public void testLogin() {
        // Given
        String username = "testUser";
        User user = new User(username);
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(user);
        doNothing().when(userService).saveUser(username); // Mocking void method behavior
        when(chatMessageService.getAllChatMessages()).thenReturn(new ArrayList<>());
        when(userService.getAllUsers()).thenReturn(mockUsers);
        Model model = Mockito.mock(Model.class);

        // When
        String viewName = chatController.login(username, model);

        // Then
        assertEquals("chat", viewName);
        verify(model).addAttribute("username", username);
        verify(model).addAttribute("messages", new ArrayList<>());
        verify(model).addAttribute("users", mockUsers);
    }
}