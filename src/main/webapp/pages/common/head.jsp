<%--
  Created by IntelliJ IDEA.
  User: xzy
  Date: 2021-12-30
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--动态获取 ip + port--%>
<%
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":"
            + request.getServerPort() + request.getContextPath() + "/";
%>


<base href="<%= basePath %>">
<%--<h1><%= basePath %></h1>--%>
<%--<script src="static/js/jquery-3.5.1.min.js"></script>--%>
<script src="https://s3.pstatp.com/cdn/expire-1-M/jquery/3.3.1/jquery.min.js"></script>

<link type="text/css" rel="stylesheet" href="static/css/style.css">