<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>尚硅谷会员注册页面</title>
    <%@include file="/pages/common/head.jsp" %>
    <style type="text/css">
        .login_form {
            height: 420px;
            margin-top: 25px;
        }
    </style>

    <script type="text/javascript">
        window.onload = function () {
            // 验证用户名：必须由字母，数字下划线组成，并且长度为5到12位
            // 1.获取用户输入内容
            errorMsg = document.querySelector(".errorMsg")
            document.getElementById("sub_btn").onclick = function () {
                // 2.创建正则表达式
                let Reg = /^\w{5,12}$/;
                username = document.getElementById("username").value;
                // 3.验证,改变提示信息
                if (!Reg.test(username)) {
                    errorMsg.innerHTML = "输入用户名不合法"
                    return false;
                }

                // 验证密码：必须由字母，数字下划线组成，	并且长度为5到12位
                // 2.创建正则表达式
                password = document.getElementById("password").value;
                // 3.验证,改变提示信息
                if (!Reg.test(password)) {
                    errorMsg.innerHTML = "输入密码不合法"
                    return false;
                }

                // 验证确认密码：和密码相同
                repwd = document.getElementById("repwd").value;
                if (!(repwd === password)) {
                    errorMsg.innerHTML = "两次密码不一致"
                    return false;
                }

                // 邮箱验证：xxxxx@xxx.com
                Reg = /^\w+([\.\-]\w+)*\@\w+([\.\-]\w+)*\.\w+$/;
                email = document.getElementById("email").value;
                if (!Reg.test(email)) {
                    errorMsg.innerHTML = "输入邮箱不合法"
                    return false;
                }
                // 验证码：现在只需要验证用户已输入。因为还没讲到服务器。验证码生成。
                code = document.getElementById("code").value;
                if (code.trim() == "") {
                    errorMsg.innerHTML = "验证不正确"
                    return false;
                }
                errorMsg.innerHTML = ""

            }
            //点击切换验证码
            document.getElementById("imgcode").onclick = function () {
                this.src = this.src +"?" + new Date().getTime();
                console.log( new Date().getTime())
            }

            $

            let inputNum = document.getElementById("username")
            inputNum.onblur =  function (e) {
                $.getJSON(e.target.baseURI + "userServelet?action=ValidExistUsername&username="+e.target.value,function (data) {
                   if (data){
                       errorMsg.innerHTML = "用户名已存在"
                   }else{
                       errorMsg.innerHTML = ""
                   }
                })
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
        <span class="login_word">欢迎注册</span>
    </div>

    <div id="content">
        <div class="login_form">
            <div class="login_box">
                <div class="tit">
                    <h1>注册尚硅谷会员</h1>
                    <span class="errorMsg">
                        ${(empty requestScope.msg )? "":requestScope.msg}
                    </span>
                </div>
                <div class="form">
                    <!--" -->
                    <form action="userServelet" method="post">
                        <input type="hidden" name="action" value="regist"/>
                        <label>用户名称：</label>
                        <input value="${requestScope.username}" class="itxt" type="text" placeholder="请输入用户名"
                               autocomplete="off"
                               tabindex="1"
                               name="username" id="username"/>
                        <br/>
                        <br/>
                        <label>用户密码：</label>
                        <input value="${requestScope.password}" class="itxt" type="password" placeholder="请输入密码"
                               autocomplete="off"
                               tabindex="1"
                               name="password" id="password"/>
                        <br/>
                        <br/>
                        <label>确认密码：</label>
                        <input value="${requestScope.password}" class="itxt" type="password" placeholder="确认密码"
                               autocomplete="off"
                               tabindex="1"
                               name="repwd" id="repwd"/>
                        <br/>
                        <br/>
                        <label>电子邮件：</label>
                        <input class="itxt" type="text" placeholder="请输入邮箱地址" autocomplete="off" tabindex="1"
                               name="email" id="email" value="${requestScope.email}"/>
                        <br/>
                        <br/>
                        <label>验证码：</label>
                        <input class="itxt" name="code" type="text" style="width: 110px;" id="code"/>
                        <img alt="验证码图片" id="imgcode" src="kaptcha.jpg"
                             style="float: right; margin-right: 0;width: 120px;height: 40px">
                        <br/>
                        <br/>
                        <input type="submit" value="注册" id="sub_btn"/>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<%@include file="/pages/common/footer.jsp" %>
</body>

</html>