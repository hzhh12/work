<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/30
  Time: 4:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <h4>index page</h4>
  <%--如果有ROLE_ADMIN权限再显示--%>
  <security:authorize ifAllGranted="ROLE_ADMIN">
      <a href="admin.jsp">admin</a>
    </security:authorize>
  <br>
  <br>
  <a href="Logout">Logout</a>
  </body>
</html>
