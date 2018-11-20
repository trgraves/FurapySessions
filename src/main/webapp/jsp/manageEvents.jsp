<%-- 
    Document   : createUpdateDeleteEvents
    Created on : Oct 25, 2018, 10:10:54 AM
    Author     : tylerbates
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
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/furapySessions.css" rel="stylesheet">


        <title>Fur-apy Sessions</title>
    </head>
    <body data-spy="scroll" data-target="#myScrollSpy" data-offset="20">
        <div class="container">
            <div id="section1">
            </div>
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
                       <li role="presentation" class="active">
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

<!--            <nav id="myScrollSpy">
                <ul class="nav nav-tabs">
                    <li><a href="#section1">Manage Events</a></li>
                    <li><a href="#section2">Manage Categories</a></li>
                </ul>
            </nav>-->

            <h2 class="text-center">Manage Events</h2>
            <hr/>
            <div class="row">
                <ul class="list-group" id="errorMessages"></ul>
                <div class="col-md-6">
                    <h2 class="text-center" style="padding-bottom: 25px">Events</h2>
                    <table id="eventTable" class="two table-hover">
                        <tr>
                            <th width="25%">Title</th>
                            <th width="25%">Location</th>
                            <th width="20%">Date</th>
                            <th width="15%"></th>
                            <th width="15%"></th>
                        </tr>
                        <c:forEach var="currentEvent" items="${allEvents}">
                            <tr>
                                <td>
                                    <c:out value="${currentEvent.title}"/>
                                </td>
                                <td>
                                    <c:out value="${currentEvent.location}"/>
                                </td>
                                <td>
                                    <c:out value="${currentEvent.start}"/>
                                </td>

                                <td>
                                    <a class="editEventBtn" onclick="editEvent(${currentEvent.eventId})">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteEvent?eventId=${currentEvent.eventId}">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    

                </div>

                <div class="col-md-6">
                    <div id="addEventDiv">
                        <h2 class="text-center" style="padding-bottom: 25px">Add Event</h2>
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="addEvent">
                            <div class="form-group">
                                <label for="title" class="col-md-4 control-label">Title:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="title" placeholder="Title" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="start" class="col-md-4 control-label">Start Date:</label>
                                <div class="col-md-8">
                                    <input type="date" class="form-control" name="start" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="end" class="col-md-4 control-label">End Date:</label>
                                <div class="col-md-8">
                                    <input type="date" class="form-control" name="end" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="description" class="col-md-4 control-label">Description:</label>
                                <div class="col-md-8">
                                    <textarea rows="4" maxlength="280" type="text" class="form-control" 
                                              name="description" placeholder="Describe the Event..." required></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="location" class="col-md-4 control-label">Location:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="location" placeholder="Location" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="private" class="col-md-4 control-label">Private:</label>
                                <div class="col-md-8">
                                    <select class="form-control" name="isPrivate" required>
                                        <option value="true">Yes</option>
                                        <option value="false">No</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="categories" class="col-md-4 control-label">Category:</label>
                                <div class="col-md-8">
                                    <select class="selectpicker" multiple name="categories" required>
                                        <option disabled>Select Multiple Categories</option>
                                        <c:forEach items="${categories}" var="cat">
                                            <option value="${cat.categoryId}">${cat.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <button class="btn btn-default" type="submit">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="col-md-6">
                    <div id="editEventDiv" hidden>
                        <h2 class="text-center" style="padding-bottom: 25px">Edit Event</h2>
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="updateEvent">
                            <input id="eventId" name="eventId" hidden/>
                            <div class="form-group">
                                <label for="title" class="col-md-4 control-label">Title</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="title" name="title" value="${eventToEdit.title}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="start" class="col-md-4 control-label">Start Date:</label>
                                <div class="col-md-8">
                                    <input type="date" class="form-control" id="start" name="start" value="${eventToEdit.start}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="end" class="col-md-4 control-label">End Date:</label>
                                <div class="col-md-8">
                                    <input type="date" class="form-control" id="end" name="end" value="${eventToEdit.end}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="description" class="col-md-4 control-label">Description:</label>
                                <div class="col-md-8">
                                    <textarea rows="4" maxlength="280" type="text" class="form-control" 
                                              id="description" name="description" >Describe the Event...</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="location" class="col-md-4 control-label">Location:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="location" name="location" placeholder="Location"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="private" class="col-md-4 control-label">Private:</label>
                                <div class="col-md-8">
                                    <select class="form-control" name="isPrivate" required>
                                        <option value="true">Yes</option>
                                        <option value="false">No</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="private" class="col-md-4 control-label">Category:</label>
                                <div class="col-md-8">
                                    <select class="selectpicker" multiple name="categories" required>
                                        <option disabled>Select Multiple Categories</option>
                                        <c:forEach items="${categories}" var="cat">
                                            <option value="${cat.categoryId}">${cat.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <button class="btn btn-default" type="submit">Submit</button>
                                    <button class="btn btn-default" type="button" id="cancelEditEventBtn">Cancel</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div id="section2">  

                <div class="row">
                    <h2 class="text-center">Manage Categories</h2>
                    <hr/>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <h2 class="text-center" style="padding-bottom: 25px;">Categories</h2>
                        <table id="categoryTable" class="two table-hover">
                            <tr>
                                <th width="40%">Category ID</th>
                                <th width="30%">Name</th>
                                <th width="15%"></th>
                                <th width="15%"></th>
                            </tr>
                            <c:forEach var="currentCat" items="${categories}">
                                <tr>
                                    <td>
                                        <c:out value="${currentCat.categoryId}"/> 
                                    </td>
                                    <td>
                                        <c:out value="${currentCat.name}"/> 
                                    </td>
                                    <td>
                                        <a class="editCategoryBtn" onclick="editCategory(${currentCat.categoryId})">
                                            Edit
                                        </a>
                                    </td>
                                    <td>
                                        <a href="deleteCategory?categoryId=${currentCat.categoryId}">
                                            Delete
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>                   
                    </div>

                    <div class="col-md-6">
                        <div id ="addCategoryDiv">
                            <h2 class="text-center" style="padding-bottom: 25px">Add Category</h2>
                            <form class="form-horizontal" 
                                  role="form" method="POST" 
                                  action="addCategory">
                                <div class="form-group">
                                    <label for="name" class="col-md-4 control-label">Name: </label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" name="name" placeholder="Category Name" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-8">
                                        <input type="submit" class="btn btn-default" value="Submit"/>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div id="editCategoryDiv" hidden>
                            <h2 class="text-center" style="padding-bottom: 25px;">Edit Category</h2>
                            <form class="form-horizontal" 
                                  role="form" method="POST" 
                                  action="updateCategory">
                                <div class="form-group">
                                    <label for="name" class="col-md-4 control-label">ID: </label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="categoryId" name="categoryId" value="${category.categoryId}" readonly/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-md-4 control-label">Name: </label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="name" name="name" placeholder="${category.name}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-8">
                                        <button class="btn btn-default" type="submit">Submit</button>
                                        <button class="btn btn-default" type="button" id="cancelEditCategoryBtn">Cancel</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row" style="padding-bottom: 100px;"></div>
        </div>
    </body>

    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/furapysessions.js"></script>
</div>
</div>
</html>
