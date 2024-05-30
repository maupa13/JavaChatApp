<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Group Chat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> <!-- Links an external stylesheet -->
</head>

<body>
<div class="container">

    <header>
        <h1>Welcome to Group Chat</h1> <!-- Heading indicating the title of the chat application -->
    </header>

    <form action="/login" method="post" class="login-form"> <!-- Form for user login -->
        <label for="username">Enter your username:</label> <!-- Label for username input -->
        <input type="text" id="username" name="username" required>
        <button type="submit">Join Chat</button>
    </form>

</div>
</body>

</html>
