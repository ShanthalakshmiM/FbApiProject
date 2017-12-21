<%-- 
    Document   : index
    Created on : Dec 18, 2017, 10:41:58 AM
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
        <form action="<%= request.getContextPath()%>/Info" >
            <input type="submit" value="Go"/>
            
        </form>
        <form action="<%=request.getContextPath()%>/myServlet" method="get">
           Enter the text to post <input type="text" name ="StrPost" />  <br/>
            <input type="submit" name="btnPost" value="Post to your page"/> <br/>
            <input type="submit" name="btnGetMsg" value="Get messages"/> <br/>
            
            
            <input type="submit" name="btnGetCmt" value="Get Comments"/><br/>
            
        </form>
        <form action="<%=request.getContextPath()%>/index">
               Enter the message to be sent <input type="text" name="StrMsg"/><br/>
               <input type="submit" name="btnSendMsg" value="Send Message"/><br/>
           </form>
    </body>
</html>
