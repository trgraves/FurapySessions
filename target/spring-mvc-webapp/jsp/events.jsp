<%-- 
    Document   : events
    Created on : Oct 23, 2018, 5:40:21 PM
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
        <title>Events</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
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
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/displayEmployees">
                                Employees
                            </a>
                        </li>
                        <li role="presentation" class="active">
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

            <h2 class="text-center">Events</h2>
            <hr/>
            <div class="row">
                <div class="col-md-8">
                    <h2 class="text-center" style="padding-bottom: 25px;">Events</h2>
                    <table id="eventTable" class="two table-hover">
                        <tr>
                            <th width="25%">Event Title</th>
                            <th width="13%">Start</th>
                            <th width="13%">End</th>
                            <th width="25%">Description</th>
                            <th width="12%">Location</th>
                            <th width="12%">Category </th>

                        </tr>

                        <c:forEach var="currentEvent" items="${eventList}">
                            <tr>
                                <td>
                                    <c:out value="${currentEvent.title}"/>
                                </td>
                                <td>
                                    <c:out value="${currentEvent.start}"/>
                                </td>
                                <td>
                                    <c:out value="${currentEvent.end}"/>
                                </td>
                                <td>
                                    <c:out value="${currentEvent.eventDescription}"/>
                                </td>
                                <td>
                                    <c:out value="${currentEvent.location}"/>
                                </td>
                                <td>
                                    <c:forEach var="currentCategory" items="${currentEvent.categories}">

                                        <c:out value="${currentCategory.name}"/>

                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>

                        <tbody id="contentRows"/>
                    </table>  
                </div>
                <div class="col-md-4">
                    <h2 class="text-center" style="padding-bottom: 25px;">Search Events</h2>
                    <form class="form-horizontal" role="form" id="searchTitle" method="GET" action="searchTitle">
                        <div class="form-group">
                            <label for="searchEventTitle" class="col-md-4 control-label">Title:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="searchEventTitle" name="searchTitle" placeholder="Event Title"/>                               
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button class="btn btn-default" type="submit">Search By Title</button>
                            </div>
                        </div>
                    </form>
                    <form class="form-horizontal" role="form" id="searchStart" method="GET" action="searchStart">
                        <div class="form-group">
                            <label for="searchStart" class="col-md-4 control-label">Event Start:</label>
                            <div class="col-md-8">
                                <input type="date" class="form-control" id="searchStart" name="searchStart" placeholder="Start"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button class="btn btn-default" type="submit">Search By Start Date</button>
                            </div>
                        </div>
                    </form>
                    <form class="form-horizontal" role="form" id="searchCategory" method="GET" action="searchCategory">
                        <div class="form-group">
                            <label for="searchCategory" class="col-md-4 control-label">Category</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="searchCategory" name="searchCategory" placeholder="Category"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button class="btn btn-default" type="submit">Search By Category</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row" style="padding-bottom: 100px;"></div>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
