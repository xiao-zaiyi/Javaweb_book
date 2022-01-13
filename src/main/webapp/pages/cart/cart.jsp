<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <%@include file="/pages/common/head.jsp" %>
</head>

<script type="text/javascript">

    window.onload = function () {
        let inputNum = document.getElementsByClassName("numBook")
        for (let i = 0; i < inputNum.length; i++) {
            (function (i) {
                inputNum[i].onblur =  function (e) {
                   let bookId = this.getAttribute("bookid")
                    location.href = e.target.baseURI + "carServelet?action=updateCount&id="+bookId+"&itemNum="+ e.target.value
                };
            })(i);
        }
    }


</script>

<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">购物车</span>
    <%@include file="/pages/common/welcome.jsp" %>
</div>

<div id="main">
    <table>
        <%--        购物车为空时--%>
        <c:if test="${empty sessionScope.cart.items}">
            <tr>
                <td style="width: 300px"><a href="index.jsp" style="text-decoration: none; width: 300px">
                    亲,当前购物车为空~~~ </a></td>
            </tr>
        </c:if>
        <c:if test="${not empty sessionScope.cart.items}">
            <tr>
                <td>商品名称</td>
                <td>数量</td>
                <td>单价</td>
                <td>金额</td>
                <td>操作</td>
            </tr>
        </c:if>

        <c:forEach items="${sessionScope.cart.items}" var="books">
            <tr>
                <td>${books.value.name}</td>
                <td><input class="numBook" type="number" name="count" value="${books.value.count}" style="width: 80px"
                           min="1" bookId="${books.value.id}"/></td>
                <td>${books.value.price}</td>
                <td>${books.value.totalPrice}</td>
                <td><a href="carServelet?action=deleteItem&id=${books.value.id}">删除</a></td>
            </tr>
        </c:forEach>
    </table>

    <%--    购物车非空时显示--%>
    <c:if test="${not empty sessionScope.cart.items}">
        <div class="cart_info">
            <span class="cart_span">购物车中共有<span class="b_count">${ sessionScope.cart.totalCount}</span>件商品</span>
            <span class="cart_span">总金额<span class="b_price">${ sessionScope.cart.totalPrice}</span>元</span>
            <span class="cart_span"><a href="carServelet?action=clear">清空购物车</a></span>
            <span class="cart_span"><a href="orderServelet?action=createOrder">去结账</a></span>
        </div>
    </c:if>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>