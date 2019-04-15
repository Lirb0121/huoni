<%--
  Created by IntelliJ IDEA.
  User: lirb
  Date: 2019/4/15
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<form action="/usr/login" enctype="multipart/form-data" method="post">
    <table>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password"> </td>
        </tr>

        <tr>
            <td>登录</td>
            <td><input type="submit" value="登录"></td>
        </tr>
        <tr>
            <td><input type="reset" value="重置" /></td>
        </tr>
    </table>
</form>
</body>
</html>
