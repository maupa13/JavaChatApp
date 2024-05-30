<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- JSP taglib directive to include JSTL Core library -->

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Group Chat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">   <!-- Links an external stylesheet -->
    <script src="${pageContext.request.contextPath}/js/chat.js"></script> <!-- Links an external JavaScript file -->
</head>

<body>
<div class="container">

    <header>
        <h1>Group Chat</h1>
    </header>

    <div class="chat-container"> <!-- Container for chat-related elements -->
        <div class="online-users">
            <h2>Online Users:</h2>
            <ul id="userList"> <!-- List for displaying online users -->
                <c:forEach var="user" items="${users}"> <!-- JSTL forEach loop to iterate over online users -->
                    <li>${user.username}</li>
                </c:forEach>
            </ul>
        </div>

        <div class="messages"> <!-- Container for chat messages -->
            <h2>Messages:</h2>
            <ul id="messageList"> <!-- Unordered list for displaying chat messages -->
                <c:forEach var="message" items="${messages}"> <!-- JSTL forEach loop to iterate over chat messages -->
                    <li><strong>${message.user.username}:</strong> ${message.content}</li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <form id="messageForm"> <!-- Form for sending messages -->
        <input type="hidden" id="username" value="${username}"> <!-- Hidden input field to store current user's username -->
        <input type="text" id="message" placeholder="Type a message" required>
        <button type="submit">Send</button>
    </form>

    <a href="login" class="logout-btn">Logout</a> <!-- Link for logging out -->
</div>
</body>

</html>
