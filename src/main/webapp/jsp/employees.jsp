<%-- 
    Document   : employees
    Created on : Oct 23, 2018, 5:40:38 PM
    Author     : Ryan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>-->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/furapySessions.css" rel="stylesheet">
        <title>Fur-apy Sessions</title>
    </head>
    <body>
        <div class="container">
            <hr/>
            <div class="row">
                <img  class="center-block" src="${pageContext.request.contextPath}/images/FurapySessionsLogo.png">
            </div>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/index">
                            Home
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/displayDogBreed">
                            Dog Breed Analysis
                        </a>
                    </li>
                    <sec:authorize access="isAuthenticated()">
                        <li role="presentation" class="active">
                            <a href="${pageContext.request.contextPath}/displayEmployees">
                                Employees
                            </a>
                        </li>
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/displayEvents">
                                Events
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_HR','ROLE_ADMIN')">
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/manageEmployees">
                                Manage Employees 
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_EVENTS', 'ROLE_ADMIN')">
                       <li role="presentation">
                            <a href="${pageContext.request.contextPath}/manageEvents">
                                Manage Events
                            </a>
                        </li>
                    </sec:authorize>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <li role="presentation">
                            <a  data-toggle="modal" data-target="#myModal">
                                Login
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <li role="presentation"><a href="<c:url value="/j_spring_security_logout"/>"> Logout</a></li>
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <h4 class="col-md-2 pull-right">User:&nbsp;&nbsp;&nbsp;${pageContext.request.userPrincipal.name}</h4>
                    </c:if>
                </ul>

            </div>

            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div id="modalDialog" class="modal-dialog" role="document">
                    <div id="modalContent" class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Login</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" 
                                  role="form" 
                                  method="post" 
                                  action="j_spring_security_check">
                                <div class="form-group">
                                    <label for="j_username" class="col-md-4 control-label">Username:</label>
                                    <div class="col-md-8">
                                        <input type="text" 
                                               class="form-control" 
                                               name="j_username" 
                                               placeholder="Username"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="j_password" class="col-md-4 control-label">Password:</label>
                                    <div class="col-md-8">
                                        <input type="password" 
                                               class="form-control" 
                                               name="j_password" 
                                               placeholder="Password"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-8">
                                        <input type="submit" 
                                               class="btn btn-default" 
                                               id="search-button" 
                                               value="Sign In"/>
                                    </div>
                                </div>
                            </form>    
                        </div>
                    </div>
                </div>
            </div>

            <h2 class="text-center">Employees</h2>
            <hr/>
            <div class="row">

                <div class="col-md-6">
                    <h2 class="text-center" style="padding-bottom: 25px;">Employees</h2>
                    <table id="employeeTable" class="two table-hover">
                        <tr>
                            <th>Employee Name</th>
                            <th>Role</th>
                            <th>Title</th>
                            <th>Partner</th>
                            <th>Hire Date</th>
                        </tr>
                        <c:forEach items="${employees}" var="currentEmployee">
                            <tr>
                                <td>${currentEmployee.lastName}, ${currentEmployee.firstName}</td>
                                <td>${currentEmployee.role.roleType}</td>
                                <td>${currentEmployee.title}</td>
                                <td>${currentEmployee.partner}</td>
                                <td>${currentEmployee.hireDate}</td>
                            </tr>
                        </c:forEach>

                        <tbody id="contentRows"/>
                    </table>                    
                </div> <!-- End col div -->
                <!-- 
                    Add col to hold the search form - have it take up the other 
                    half of the row
                -->
                <div class="col-md-6">
                    <h2 class="text-center" style="padding-bottom: 25px;">Search Employees</h2>
                    <form action="searchEmployeeByName" method="GET" class="form-horizontal" role="form" id="search-form">
                        <div class="form-group">
                            <label for="search-name" class="col-md-4 control-label">Name:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="name" id="search-first-name" placeholder="Name"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button class="btn btn-default" type="submit">Search By Name</button>
                            </div>
                        </div>
                    </form>
                    <form action="searchEmployeeByRole" method="GET" class="form-horizontal" role="form" id="search-form">
                        <div class="form-group">
                            <label for="select-role" class="col-md-4 control-label">Role: </label>
                            <div class="col-md-8">
                                <select  class="form-control" name="roles">
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.roleId}">${role.roleType}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>   
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button class="btn btn-default" type="submit">Search By Role</button>
                            </div>
                        </div>
                    </form>

                </div> <!-- End col div -->

            </div>

            <div class="row" style="padding-bottom: 100px;"></div>
        </div>
    </body>

    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/searchEmployee.js"></script>


</div>
</div>
</html>
