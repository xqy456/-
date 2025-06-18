<%-- 
    Document   : login
    Created on : 2025-6-19, 0:30:33
    Author     : Asus
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>用户登录</title></head>
<body>
<h2>用户登录</h2>
<% String msg = (String)request.getAttribute("msg"); if (msg != null) { %>
<p style="color: red;"><%=msg%></p >
<% } %>
<form method="post" action="../login">
    用户名：<input type="text" name="username" required/><br/>
    密码：<input type="password" name="password" required/><br/>
    <input type="submit" value="登录"/>
</form>
<a href=" ">没有账号？注册</a >
</body>
</html>