<%--
  Created by IntelliJ IDEA.
  User: xzy
  Date: 2022-01-12
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书城图书</title>
</head>
<style>
    body {
        background-color: skyblue;
    }

    .title-box {
        margin: 200px auto;
    }
</style>

<script type="text/javascript">

    window.onload = function () {
        setTimeout(() => {
            console.log(this)
            location.href = location.origin + "/book"
        }, 3000)

    }

</script>
<body>
<div class="title-box">
    <h1>抱歉,服务处暂时出现异常,程序猿小哥正在去抢修!!!!</h1>
</div>
</body>
</html>
