<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>
    <%@include file="/pages/common/head.jsp" %>

    <script type="text/javascript">
        window.onload = function () {
            let btn = document.getElementsByClassName("deleteClass")
            for (var i = 0; i < btn.length; i++) {
                (function (i) {
                    btn[i].onclick = () => confirm("Are you sure you want to delete ?")
                })(i);
            }

            document.getElementById("searchPage").onclick = function () {
                page = document.getElementById("pn_input").value
                if (page < 0) {
                    page = 1;
                } else if (page > ${requestScope.page.pageTotal}) {
                    page =
                    ${requestScope.page.pageTotal}

                }
                location.href = "http://localhost:8080/book/manage/bookServelet?action=pages&pageNo=" + page
            }
        }
    </script>

</head>
<body>
<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">图书管理系统</span>
    <div>
        <a href="pages/manager/book_manager.jsp">图书管理</a>
        <a href="pages/manager/order_manager.jsp">订单管理</a>
        <a href="index.jsp">返回商城</a>
    </div>
</div>

<div id="main">
    <table>
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>作者</td>
            <td>销量</td>
            <td>库存</td>
            <td colspan="2">操作</td>
        </tr>
        <c:forEach items="${ requestScope.page.items }" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${item.author}</td>
                <td>${item.sales}</td>
                <td>${item.stock}</td>
                <td><a href="manage/bookServelet?action=getBook&id=${item.id}&method=update">修改</a></td>
                <td><a class="deleteClass" href="manage/bookServelet?action=delete&id=${item.id}">删除</a></td>

            </tr>
        </c:forEach>

        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><a href="pages/manager/book_edit.jsp?&method=add">添加图书</a></td>
        </tr>
    </table>
    <%--    分页--%>
    <div id="page_nav">
        <%--  第一页不显示首页和上一页 --%>
        <c:if test="${requestScope.page.pageNo > 1 }">
            <a href="manage/bookServelet?action=pages&pageNo=1">首页</a>
            <c:if test="${requestScope.page.pageNo > 1 }">
                <a href="manage/bookServelet?action=pages&pageNo=${requestScope.page.pageNo - 1 }">上一页</a>
            </c:if>
        </c:if>
        <c:if test="${requestScope.page.pageNo >=2}">
            <a href="manage/bookServelet?action=pages&pageNo=${requestScope.page.pageNo - 1 }">${requestScope.page.pageNo - 1 }</a>
        </c:if>
        【${requestScope.page.pageNo}】
        <c:if test="${requestScope.page.pageTotal != requestScope.page.pageNo}">
            <a href="manage/bookServelet?action=pages&pageNo=${requestScope.page.pageNo + 1 }">${requestScope.page.pageNo + 1}</a>
        </c:if>
        <c:if test="${requestScope.page.pageTotal - 1  >= requestScope.page.pageNo}">
            <c:if test="${requestScope.page.pageTotal != requestScope.page.pageNo}">
                <a href="manage/bookServelet?action=pages&pageNo=${requestScope.page.pageNo + 1 }">下一页</a>
            </c:if>
            <a href="manage/bookServelet?action=pages&pageNo=${requestScope.page.pageTotal}">末页</a>
        </c:if>
        共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录 到第<input
            value="${requestScope.page.pageNo}" name="pn"
            id="pn_input"/>页
        <input type="button" id="searchPage" value="确定">
    </div>
</div>
<%@include file="/pages/common/footer.jsp" %>
</body>
</html>