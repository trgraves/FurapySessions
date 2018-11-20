
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Fur-apy Sessions</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"> 
        <link href="${pageContext.request.contextPath}/css/fullcalendar.css" rel="stylesheet"> 
        <link href="${pageContext.request.contextPath}/css/furapySessions.css" rel="stylesheet">
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
                    <li role="presentation" class="active">
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
                        <li role="presentation">
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
            <div class="container">
                <div class="row">
                    <div class="row">
                        <div id="calendar" class="col-md-7"></div>

                        <div id="employeeOfWeek" class="col-md-5">
                            <h2 class="text-center">EMPLOYEE OF THE WEEK</h2>
                            <img src="${pageContext.request.contextPath}/images/${EOTW.pictureFile}" 
                                 class="img-thumbnail center-block" alt="Employee of the Week Picture" width="300" height="300">
                            <div class="row">
                                <p id="eotwHead" class="text-center">${EOTW.firstName} ${EOTW.lastName}</p>
                            </div>
                            <div class="row">
                                <p id="eotwBody" class="text-center">${description}</p>
                            </div>
                        </div>
                    </div>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">         

                        <div class="row">
                            <h3 class="text-center">Change Employee of the Week</h3>
                            <hr/>
                            <form class="form-horizontal" 
                                  role="form" method="POST" 
                                  action="addEotW">
                                <div class="form-group">
                                    <label for="employeeId" class="col-md-4 control-label">Employee:</label>
                                    <div class="col-md-4">
                                        <select class="selectpicker form-control" name="employeeId" required>
                                            <option disabled>Select an Employee</option>
                                            <c:forEach items="${employees}" var="emp">
                                                <option value="${emp.employeeId}">${emp.firstName} ${emp.lastName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="description" class="col-md-4 control-label">Description: </label>
                                    <div class="col-md-4">
                                        <textarea rows="4" maxlength="280" type="text" class="form-control" 
                                                  name="description" required>Describe why this person is the Employee of the Week...</textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-4">
                                        <button type="submit" class="btn btn-default" value="Submit Employee">Submit</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </sec:authorize>
                    <div style="padding-bottom: 100px"></div>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>        
        <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/fullcalendar.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <sec:authorize access="isAnonymous()">
            <script src="${pageContext.request.contextPath}/js/calendarpublic.js"></script>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <script src="${pageContext.request.contextPath}/js/calendarprivate.js"></script>
        </sec:authorize>

    </body>
</html>

