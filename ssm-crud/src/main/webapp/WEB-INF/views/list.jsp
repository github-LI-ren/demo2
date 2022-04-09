<%--
  Created by IntelliJ IDEA.
  User: 党
  Date: 2022/4/5
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>员工列表</title>
    <%--    当前页面域--%>
<%

    pageContext.setAttribute("app_path",request.getContextPath());
%>
<%--    服务器路径http://localhost:3306/module--%>
    <script src="${app_path}/static/js/jquery-2.0.3.min.js"></script>
    <link href="${app_path}/static/bootstrap-3.4.1-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${app_path}/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
</head>
<body>
<%--显示页面--%>
    <div class="container">
<%--        标题--%>
        <div class="row">
            <div class="col-md-12">
                <h1>SSM-CRUD</h1>
            </div>
        </div>
<%--    按钮--%>
         <div class="row">
            <div class="col-md-4 col-md-offset-10">
                <button class="btn-primary">数据</button>
                <button class="btn-danger">删除</button>
            </div>
         </div>
<%--    数据表格--%>
        <div class="row">
            <div class="col-md-12">
                <table class="table">
                    <tr>
                        <th>#</th>
                        <th>stuName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="stu">
                        <tr>
                            <th>${stu.stuId}</th>
                            <th>${stu.stuName}</th>
                            <th>${stu.gender=="M"?"男":"女"}</th>
                            <th>${stu.email}</th>
                            <th>${stu.department.deptName}</th>

                            <th>
                                <button class="btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    编辑
                                </button>
                                <button class="btn-danger btn-sm">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    删除
                                </button>
                            </th>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>
<%--    分页信息--%>
        <div class="row">
<%--                分页文字信息--%>
            <div class="col-md-6">
                当前${pageInfo.pageNum}页,总共${pageInfo.pages}页,总共${pageInfo.total}条记录
            </div>
<%--                分页条--%>
            <div class="col-md-6">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li><a href="${app_path}/emps?pn=1">首页</a></li>
                        <c:if test="${pageInfo.pageNum != 1}">
                            <li>
                                <a href="${app_path}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach items="${pageInfo.navigatepageNums}" var="pageNum">
                            <c:if test="${pageNum == pageInfo.pageNum}">
                                <li class="active"><a href="#">${pageNum}</a></li>
                            </c:if>
                            <c:if test="${pageNum != pageInfo.pageNum}">
                                <li><a href="${app_path}/emps?pn=${pageNum}">${pageNum }</a></li>
                            </c:if>

                        </c:forEach>
                        <c:if test="${pageInfo.pageNum != pageInfo.pages}">
                            <li>
                                <a href="${app_path}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>
                            <li><a href="${app_path}/emps?pn=${pageInfo.pages}">尾页</a></li>

                    </ul>
                </nav>
            </div>
        </div>


    </div>
</body>
</html>
