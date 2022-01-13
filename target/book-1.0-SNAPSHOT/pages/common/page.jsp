<%--
  Created by IntelliJ IDEA.
  User: xzy
  Date: 2022-01-10
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--    分页--%>
<div id="page_nav">
  <%--  第一页不显示首页和上一页 --%>
  <c:if test="${requestScope.page.pageNo > 1 }">
    <a href="${requestScope.page.url}&pageNo=1">首页</a>
    <c:if test="${requestScope.page.pageNo > 1 }">
      <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo - 1 }">上一页</a>
    </c:if>
  </c:if>
  <c:if test="${requestScope.page.pageNo >=2}">
    <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo - 1 }">${requestScope.page.pageNo - 1 }</a>
  </c:if>
  【${requestScope.page.pageNo}】
  <c:if test="${requestScope.page.pageTotal != requestScope.page.pageNo}">
    <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo + 1 }">${requestScope.page.pageNo + 1}</a>
  </c:if>
  <c:if test="${requestScope.page.pageTotal - 1  >= requestScope.page.pageNo}">
    <c:if test="${requestScope.page.pageTotal != requestScope.page.pageNo}">
      <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo + 1 }">下一页</a>
    </c:if>
    <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
  </c:if>
  共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录 到第<input
        value="${requestScope.page.pageNo}" name="pn"
        id="pn_input"/>页
  <input type="button" id="searchPage" value="确定">
</div>
