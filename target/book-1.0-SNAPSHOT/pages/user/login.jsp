<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>尚硅谷会员登录页面</title>
    <%@include file="/pages/common/head.jsp" %>


    <script type="text/javascript">

        window.onload = function () {

            errorMsg = document.querySelector(".errorMsg")

            document.getElementById("sub_btn").onclick = function () {
                username = document.getElementById("username").value;
                password = document.getElementById("password").value;
                if (!username.trim()) {
                    errorMsg.innerHTML = "请输入用户名"
                    errorMsg.style.color = "red"
                    return false;
                }
                if (!password.trim()) {
                    errorMsg.innerHTML = "请输入密码"
                    errorMsg.style.color = "red"
                    return false;
                }

            }
        }
    </script>
</head>
<body>
<div id="login_header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
</div>

<div class="login_banner">

    <div id="l_content">
        <span class="login_word">欢迎登录</span>
    </div>

    <div id="content">
        <div class="login_form">
            <div class="login_box">
                <div class="tit">
                    <h1>尚硅谷会员</h1>
                    <a href="pages/user/regist.jsp">立即注册</a>
                </div>
                <div class="msg_cont">
                    <b></b>
                    <span class="errorMsg"> ${ (empty requestScope.msg) ? "请输入用户名和密码" : requestScope.msg} </span>
                </div>
                <div class="form">
                    <form action="userServelet" method="post">
                        <input type="hidden" name="action" value="login"/>
                        <label>用户名称：</label>
                        <input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1"
                               name="username" value="${requestScope.username}" id="username"/>
                        <br/>
                        <br/>
                        <label>用户密码：</label>
                        <input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1"
                               name="password" value="${requestScope.password}" id="password"/>
                        <br/>
                        <br/>
                        <input type="submit" value="登录" id="sub_btn"/>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<%@include file="/pages/common/footer.jsp" %>
</body>

</html>