$(document).ready(function () {

    $('.editRoleBtn').click(function (event) {
        $('#editRoleDiv').show();
        $('#addRoleDiv').hide();
    });

    $('#cancelEditRoleBtn').click(function (event) {
        $('#editRoleDiv').hide();
        $('#addRoleDiv').show();
    });

    $('.editCategoryBtn').click(function (event) {
        $('#editCategoryDiv').show();
        $('#addCategoryDiv').hide();
    });

    $('#cancelEditCategoryBtn').click(function (event) {
        $('#editCategoryDiv').hide();
        $('#addCategoryDiv').show();
    });

    $('.editEventBtn').click(function (event) {
        $('#editEventDiv').show();
        $('#addEventDiv').hide();
    });

    $('#cancelEditEventBtn').click(function (event) {
        $('#editEventDiv').hide();
        $('#addEventDiv').show();
    });

    $('.editEmployeeBtn').click(function (event) {
        $('#editEmployeeDiv').show();
        $('#addEmployeeDiv').hide();
    });

    $('#cancelEditEmpBtn').click(function (event) {
        $('#editEmployeeDiv').hide();
        $('#addEmployeeDiv').show();
    });
});

function editRole(roleId) {
    clearEditRoleForm();

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/FurapySessions/editRole?roleId=' + roleId,
        success: function (roleToEdit) {
            var roleId = roleToEdit.roleId;
            var roleType = roleToEdit.roleType;
            $('#roleId').val(roleId);
            $('#roleType').val(roleType);
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service. Please try again later.'));
        }
    });
}

function clearEditRoleForm() {
    $('#roleId').empty;
    $('#roleType').empty;
}

function editCategory(categoryId) {
    clearEditCategoryForm();

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/FurapySessions/editCategory?categoryId=' + categoryId,
        success: function (categoryToEdit) {
            var categoryId = categoryToEdit.categoryId;
            var name = categoryToEdit.name;
            $('#categoryId').val(categoryId);
            $('#name').val(name);
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service. Please try again later.'));
        }
    });
}

function clearEditCategoryForm() {
    $('#categoryId').empty();
    $('#name').empty();
}

function editEvent(eventId) {
    clearEditEventForm();

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/FurapySessions/editEvent?eventId=' + eventId,
        success: function (eventToEdit) {
            var eventId = eventToEdit.eventId;
            var title = eventToEdit.title;
            var start = eventToEdit.start;
            var end = eventToEdit.end;
            var description = eventToEdit.eventDescription;
            var location = eventToEdit.location;
            $('#eventId').val(eventId);
            $('#title').val(title);
            $('#start').val(start);
            $('#end').val(end);
            $('#description').val(description);
            $('#location').val(location);
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service. Please try again later.'));
        }
    });
}

function clearEditEventForm() {
    $('#eventId').empty();
    $('#title').empty();
    $('#start').empty();
    $('#end').empty();
    $('#description').empty();
    $('#location').empty();
}

function editEmployee(employeeId) {
    clearEditEmployeeForm();

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/FurapySessions/editEmployee?employeeId=' + employeeId,
        success: function (empToEdit) {
            var employeeId = empToEdit.employeeId;
            var firstName = empToEdit.firstName;
            var lastName = empToEdit.lastName;
            var title = empToEdit.title;
            var partner = empToEdit.partner;
            var interests = empToEdit.interests;
            var hireDate = empToEdit.hireDate;
            
            var date = hireDate.year;
            
            var month;
            if(hireDate.monthValue < 10){
                month = '-0' + hireDate.monthValue;
            } else {
                month = '-' + hireDate.monthValue;
            }
            
            var day;
            if(hireDate.dayOfMonth < 10){
                day = '-0' + hireDate.dayOfMonth;
            } else {
                day = '-' + hireDate.dayOfMonth;
            }
            date += month + day;
            
            $('#employeeId').val(employeeId);
            $('#firstName').val(firstName);
            $('#lastName').val(lastName);
            $('#title').val(title);
            $('#partner').val(partner);
            $('#interests').val(interests);
            $('#hireDate').val(date);
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service. Please try again later.'));
        }
    });
}

function clearEditEmployeeForm() {
    $('#employeeId').empty();
    $('#firstName').empty();
    $('#lastName').empty();
    $('#title').empty();
    $('#partner').empty();
    $('#interests').empty();
    $('#hireDate').empty();
}