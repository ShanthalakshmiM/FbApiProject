<%-- 
    Document   : RedirectJsp
    Created on : Dec 19, 2017, 10:43:10 AM
    Author     : HP
--%>

<%@page import="com.fb.api.Constants"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
        #textarea{
            border:none;
            width: 750px;
            height: 500px;
            display: block;
            margin-left: auto;
            margin-right: auto;
            margin-top: 5%;
            font-family: Georgia;
            font-size: 24px;
        }
        .header {
            position: absolute;
            left: 0;
            right: 0;
            top: 0;
            background-color: #4775d1;
            height: 50px;
        }
        h3{
            color: white;
        }
    </style>
    <body>
        <div class="header"> 
            <h3 align="right" style="padding-right: 15px">Logged in as </h3>  
        </div>
       
    <center>
      
        <script>
            var messages = ${result};
            document.writeln("<textarea id = 'textarea'>");
            for (var i = 0; i < messages.length; i++) {
                if(messages[i].postContent != null){
                    document.writeln("Post Message : "+messages[i].postContent);
                }
                if(messages[i].sender!= null || messages[i].content != null)                    
                    document.writeln(messages[i].sender + " : " + messages[i].content+"\n");

            }
            document.writeln(messages[0].convId);
            document.write("</textarea>");
        </script>
    </center>
           
</body>
</html>
