<%-- 
    Document   : createUpdateDeleteEmployees
    Created on : Oct 25, 2018, 10:10:33 AM
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
                        <li role="presentation" class="active">
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
                            
<!--            <nav id="myScrollSpy">
                <ul class="nav nav-tabs">
                    <li><a href="#section1">Manage Employees</a></li>
                    <li><a href="#section2">Manage Roles</a></li>
                </ul>
            </nav>-->

            <h2 class="text-center">Manage Employees</h2>
            <hr/>
            <div class="row">

                <div class="col-md-6">
                    <h2 class="text-center" style="padding-bottom: 25px;">Employees</h2>
                    <table id="contactTable" class="two table-hover">
                        <tr>
                            <!-- <th width="15%">Employee ID</th> -->
                            <th width="20%">Name</th>
                            <th width="20%">Title</th>
                            <th width="15%">Partner</th>
                            <th width="15%">Role</th>
                            <th width="15%"></th>
                            <th width="15%"></th>
                        </tr>
                        <c:forEach var="currentEmployee" items="${employees}">
                            <tr>
                                <td>
                                    ${currentEmployee.firstName} ${currentEmployee.lastName} 
                                </td>
                                <td>
                                    ${currentEmployee.title}
                                </td>
                                <td>
                                    ${currentEmployee.partner}
                                </td>
                                <td>
                                    ${currentEmployee.role.roleType}
                                </td>
                                <td>
                                    <a class="editEmployeeBtn" onclick="editEmployee(${currentEmployee.employeeId})">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteEmployee?employeeId=${currentEmployee.employeeId}">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div>

                <div class="col-md-6">
                    <div id="addEmployeeDiv">
                        <h2 class="text-center" style="padding-bottom: 25px;">Add New Employee</h2>
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="addEmployee"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="firstName" class="col-md-4 control-label">First Name: </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="firstName" placeholder="First Name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="lastName" class="col-md-4 control-label">Last Name: </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="lastName" placeholder="Last Name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="title" class="col-md-4 control-label">Title: </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="title" placeholder="Title" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="partner" class="col-md-4 control-label">Partner: </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="partner" placeholder="Partner's Name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="interests" class="col-md-4 control-label">Interests:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="interests" placeholder="Interests" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="hireDate" class="col-md-4 control-label">Hire Date:</label>
                                <div class="col-md-8">
                                    <input type="date" class="form-control" name="hireDate" placeholder="Hire Date" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="employeeRole" class="col-md-4 control-label">Role:</label>
                                <div class="col-md-8">
                                    <select class="form-control" name="employeeRole" required/>
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.roleId}">${role.roleType}</option>
                                    </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userName" class="col-md-4 control-label">User Name:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="userName" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-md-4 control-label">Password:</label>
                                <div class="col-md-8">
                                    <input type="password" class="form-control" name="password" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="picture" class="col-md-4 control-label">Upload File:</label>
                                <div class="col-md-8">
                                    <input type="file" id="picture" name="picture" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <button class="btn btn-default" type="submit">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div id="editEmployeeDiv" hidden>
                        <h3>Edit Employee</h3>
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="updateEmployee">
                            <input id="employeeId" name="employeeId" hidden/>
                            <div class="form-group">
                                <label for="firstName" class="col-md-4 control-label">First Name: </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="firstName" name="firstName" placeholder="First Name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="lastName" class="col-md-4 control-label">Last Name: </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Last Name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="title" class="col-md-4 control-label">Title: </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="title" name="title" placeholder="Title" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="partner" class="col-md-4 control-label">Partner: </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="partner" name="partner" placeholder="Partner's Name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="interests" class="col-md-4 control-label">Interests:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="interests" name="interests" placeholder="interests" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="hireDate" class="col-md-4 control-label">Hire Date:</label>
                                <div class="col-md-8">
                                    <input type="date" class="form-control" id="hireDate" name="hireDate" readonly/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="employeeRole" class="col-md-4 control-label">Role:</label>
                                <div class="col-md-8">
                                    <select class="form-control" name="employeeRole" required/>
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.roleId}">${role.roleType}</option>
                                    </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <button class="btn btn-default" type="submit">Submit</button>
                                    <button class="btn btn-default" type="button" id="cancelEditEmpBtn">Cancel</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div id="section2">
                <h2 class="text-center">Manage Roles</h2>
                <hr/>
                <div class="row">
                    <ul class="list-group" id="errorMessages"></ul>
                    <div class="col-md-6">
                        <h2 class="text-center" style="padding-bottom: 25px">Roles</h2>
                        <table id="contactTable" class="two table-hover">
                            <tr>
                                <th width="40%">Role ID</th>
                                <th width="30%">Type</th>
                                <th width="15%"></th>
                                <th width="15%"></th>
                            </tr>
                            <c:forEach var="currentRole" items="${roles}">
                                <tr>
                                    <td>
                                        <c:out value="${currentRole.roleId}"/> 
                                    </td>
                                    <td>
                                        <c:out value="${currentRole.roleType}"/> 
                                    </td>
                                    <td>
                                        <a class="editRoleBtn" onclick="editRole(${currentRole.roleId})">
                                            Edit
                                        </a>
                                    </td>
                                    <td>
                                        <a href="deleteRole?roleId=${currentRole.roleId}">
                                            Delete
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>                    
                    </div>


                    <div class="col-md-6">
                        <div id="addRoleDiv">
                            <h2 class="text-center" style="padding-bottom: 25px">Add Role</h2>

                            <form class="form-horizontal" 
                                  role="form" method="POST" 
                                  action="addRole">
                                <div class="form-group">
                                    <label for="name" class="col-md-4 control-label">Role Type: </label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" name="roleType" placeholder="Role Type" required/>

                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-8">
                                        <button class="btn btn-default" type="submit">Submit</button>
                                    </div>
                                </div>
                            </form>

                        </div>

                        <div id="editRoleDiv" hidden>
                            <h2 class="text-center" style="padding-bottom: 25px">Edit Role</h2>
                            <form class="form-horizontal" 
                                  role="form" method="POST" 
                                  action="updateRole">
                                <div class="form-group">
                                    <label for="roleId" class="col-md-4 control-label">ID: </label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="roleId" name="roleId" value="${role.roleId}"readonly required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="roleType" class="col-md-4 control-label">Role Type: </label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="roleType" name="roleType" placeholder="${role.roleType}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-4 col-md-8">
                                        <!-- <input type="submit" class="btn btn-default" value="Submit Role"/>-->
                                        <button class="btn btn-default" type="submit">Submit</button>
                                        <button class="btn btn-default" type="button" id="cancelEditRoleBtn">Cancel</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>         
                </div>
            </div>
        </div>
        <div class="row" style="padding-bottom: 100px;"></div>

    </body>

    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/furapysessions.js"></script>
</html>
