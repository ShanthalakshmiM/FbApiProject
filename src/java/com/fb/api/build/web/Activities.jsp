<%-- 
    Document   : Activities
    Created on : Dec 22, 2017, 3:06:10 PM
    Author     : HP
--%>

<%@page import="com.fb.api.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*" %>



<% Constants obj = new Constants();%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>JSP Page</title>
    </head>
    <style>
        body{
            background-image: url("images/fbloginbckgrnd.jpg");
        }
        .header {
            position: absolute;
            left: 0;
            right: 0;
            top: 0;

            height: 50px;
        }
        .body{
            top:180px;
            position: absolute;
            vertical-align: middle;
            left: 40%;
        }
        h3{
            color: white;
        }
    </style>
    <body> <center>
        <div>
            <div class="header" >
                <h3 align="right" style="padding-right: 15px" class="text-white">Communicate with facebook</h3>      
            </div>
            <div class="body">
                <form action="<%=request.getContextPath()%>/myServlet" method="get">
                    <div class="btn-group-vertical">


                        <input type="submit" class="btn btn-default" name="btnGetMsg" value="Get messages"/> <br/>
                        <input type="submit" class="btn btn-default" name="btnGetCmnt" value="Get Comments"/><br/>

                        <input type="button" class="btn btn-default"  onclick="add();" value="Post to your page"/> <br/>
                        <span id="myspan" style="width: 250px; margin-left: 20px; font-weight: bold; float: none;"></span><br/>


                    </div>
                </form>
                <!-- display textbox to get post message -->
                <script type="text/javascript">
                    function add() {
                        var element = document.createElement("input");
                        element.setAttribute("type", "textarea");
                        element.setAttribute("name", "StrPost");
                        var spanvar = document.getElementById("myspan");
                        spanvar.appendChild(element);
                        var br = document.createElement('br');
                        var br2 = document.createElement('br');
                        spanvar.appendChild(br);
                        spanvar.appendChild(br2);
                        var element = document.createElement("input");
                        element.setAttribute("type", "submit");
                        element.setAttribute("name", "btnPost");
                        element.setAttribute("value", "Post");
                        element.setAttribute("class", "btn btn-default");
                        spanvar.appendChild(element);
                    }

                </script>

            </div>

<!--            <div>
                <p>
                    <%= Constants.MY_ACCESS_TOKEN%>
                </p>
            </div>     -->
        </div>

    </center>

</body>
</html>
