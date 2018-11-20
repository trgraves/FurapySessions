$(document).ready(function () {
    $('#calendar').fullCalendar({
        // put your options and callbacks here
        defaultView: 'month',
        editable: false,
        events: "http://localhost:8080/FurapySessions/calendar/public"
//            ,
//            eventClick: function (event, jsEvent, view) {
//            $('#eventDialog').dialog({
//            modal: true,
//                    $('#title').val(event.title),
//                    $('#start').val(event.title),
//                    $('#end').val(event.title)
//            })
    });
});
