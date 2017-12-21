<%-- 
    Document   : RedirectJsp
    Created on : Dec 19, 2017, 10:43:10 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
                Object id = request.getAttribute("convId");
        %>
            
        <p> <%= id %> </p>
    </body>
</html>
