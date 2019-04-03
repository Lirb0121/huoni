<%--
  Created by IntelliJ IDEA.
  User: lirb
  Date: 2019/4/3
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/upload/upload" enctype="multipart/form-data" method="post">
        <table>
            <tr>
                <td>请选择文件：</td>
                <td><input type="file" name="file"></td>
            </tr>
            <tr>
                <td>开始上传</td>
                <td><input type="submit" value="上传"></td>
            </tr>
            <tr>
                <td><input type="reset" value="重置" /></td>
            </tr>
        </table>
    </form>
</body>
</html>
