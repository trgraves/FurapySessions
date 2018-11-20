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
        <sec:authorize access="hasRole('ROLE_HR')">
            <li role="presentation">
                <a href="${pageContext.request.contextPath}/manageEmployees">
                    Manage Employees 
                </a>
            </li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_EVENTS')">
            <li role="presentation">
                <a href="${pageContext.request.contextPath}/manageEvents">
                    Manage Events
                </a>
            </li>
        </sec:authorize>
        <li role="presentation"">
            <a  data-toggle="modal" data-target="#myModal">
                Login
            </a>
        </li>
    </ul>    
</div>
<!-- Button trigger modal -->
<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#myModal">
    Login
</button>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
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
                            <input type="text" 
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
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h4>Hello : ${pageContext.request.userPrincipal.name}
        | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
    </h4>
</c:if>
<div id="eventDialog" hidden>
    <p id="title"></p>
    <p id="start"></p>
    <p id="end"></p>
</div>   