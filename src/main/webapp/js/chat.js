var websocket;

// Функция для открытия WebSocket соединения
function openWebSocket() {
    websocket = new WebSocket("ws://localhost:8085/chat");

    // Установка обработчика для события открытия соединения
    websocket.onopen = function(event) {
        console.log("WebSocket connection opened.");
    };

    // Установка обработчика для события получения сообщения
    websocket.onmessage = function(event) {
        var data = JSON.parse(event.data);
        if (data.onlineUsers) { // Исправлено на правильный ключ
            data.onlineUsers.forEach(function(user) {
                appendUser(user); // Добавление пользователя в список
            });
        } else {
            var message = JSON.parse(event.data);
            appendMessage(message.username, message.content);
        }
    };

    // Установка обработчика для события закрытия соединения
    websocket.onclose = function(event) {
        console.log("WebSocket connection closed.");
    };
}

// Функция для отправки сообщения через WebSocket
function sendMessage() {
    var messageInput = document.getElementById("message");
    var message = messageInput.value;
    var usernameInput = document.getElementById("username");
    var username = usernameInput.value;

    // Проверка наличия сообщения
    if (message.trim() !== "") {
        // Формирование объекта сообщения
        var chatMessage = {
            username: username,
            content: message
        };

        // Отправка сообщения в формате JSON
        websocket.send(JSON.stringify(chatMessage));

        // Очистка поля ввода сообщения
        messageInput.value = "";
    }
}

// Функция для добавления нового пользователя в список онлайн пользователей
function appendUser(username) {
    var userList = document.getElementById("userList");
    var listItem = document.createElement("li");
    listItem.textContent = username;
    userList.appendChild(listItem);
}

// Функция для добавления нового сообщения на страницу
function appendMessage(username, content) {
    var messageList = document.getElementById("messageList");
    var listItem = document.createElement("li");
    listItem.innerHTML = "<strong>" + username + ":</strong> " + content;
    messageList.appendChild(listItem);
}

// Функция для инициализации чата
function initChat() {
    openWebSocket();

    var messageForm = document.getElementById("messageForm");
    messageForm.addEventListener("submit", function(event) {
        event.preventDefault();
        sendMessage();
    });
}

// Инициализация чата после загрузки страницы
document.addEventListener("DOMContentLoaded", function(event) {
    initChat();
});
