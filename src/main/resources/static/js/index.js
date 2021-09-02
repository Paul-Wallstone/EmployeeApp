$(document).ready(function () {
    $.ajax({
        type: "GET",
        dataType: 'json',
        cache: false,
        url: "/employee",

        success: function (data) {

            var table = $('<table>').addClass('table caption-top table-sm table-success table-striped table-bordered border-dark');
            table.attr("id", "tblData");
            var caption = $('<caption>').text("List of employees");
            table.append(caption);
            var thead = $('<thead>').addClass("table-secondary>");
            var tr = $('<tr>');
            var input = $('<input>').addClass("form-check-input").attr("id", "chkParent").attr("type", "checkbox").attr("style", "align-items: center").attr("value", "");
            var th0 = $('<th>').attr("scope", "col").attr("style", "text-align:center;");
            th0.append(input);
            var th1 = $('<th>').attr("scope", "col").text("#");
            var th2 = $('<th>').attr("scope", "col").text("employee_id");
            var th3 = $('<th>').attr("scope", "col").text("first_name");
            var th4 = $('<th>').attr("scope", "col").text("last_name");
            var th5 = $('<th>').attr("scope", "col").text("department_id");
            var th6 = $('<th>').attr("scope", "col").text("gender");
            var th7 = $('<th>').attr("scope", "col").text("job_title");
            var th8 = $('<th>').attr("scope", "col").text("date_of_birth");
            var th9 = $('<th>').attr("scope", "col").text("edition");
            tr.append(th0).append(th1).append(th2).append(th3).append(th4).append(th5).append(th6).append(th7).append(th8).append(th9);
            thead.append(tr);
            table.append(thead);
            var tbody = $('<tbody>');

            $.each(data, function (index, value) {
                var tr1 = $('<tr>');
                var row1 = $('<th>').attr("scope", "row").attr("style", "text-align:center;");
                var input1 = $('<input>').addClass("form-check-input").attr("type", "checkbox").attr("value", value.employeeId);
                row1.append(input1);
                var row2 = $('<td>').text(index + 1);
                var row3 = $('<td>').text(value.employeeId);
                var row4 = $('<td>').text(value.firstName);
                var row5 = $('<td>').text(value.lastName);
                var row6 = $('<td>').text(value.departmentId);
                var row7 = $('<td>').text(value.gender);
                var row8 = $('<td>').text(value.job_title);
                var row9 = $('<td>').text(value.date_of_birth);
                var row10 = $('<td>');
                var button = $('<button>').attr("type", "button").addClass("btn btn-outline-success btn-md").text("Edit")
                    .attr("data-id", value.employeeId).attr("data-firstname", value.firstName).attr("data-lastname", value.lastName)
                    .attr("data-departmentId", value.departmentId).attr("data-gender", value.gender).attr("data-jobTitle", value.job_title)
                    .attr("data-date_of_birth", value.date_of_birth);
                row10.append(button);
                tr1.append(row1).append(row2).append(row3).append(row4).append(row5).append(row6).append(row7).append(row8).append(row9).append(row10);
                tbody.append(tr1);
            });
            table.append(tbody);

            $('#here_table').append(table);

            $('.btn-outline-success').click(function () {
                var $modal = $('#exampleModal2');
                var $btn = $(this);
                var Id = $btn.attr('data-id');
                var firstname = $btn.attr('data-firstname');
                var lastname = $btn.attr('data-lastname');
                var departmentId = $btn.attr('data-departmentId');
                var gender = $btn.attr('data-gender');
                var jobTitle = $btn.attr('data-jobTitle');
                var date_of_birth = $btn.attr('data-date_of_birth');

                $modal.find("#firstName1").val(firstname);
                $modal.find("#lastName1").val(lastname);
                $modal.find("#departmentId1").val(departmentId);
                $modal.find("#job_title1").val(jobTitle);
                $modal.find("#date_of_birth1").val(date_of_birth);
                $modal.find("#gender1").val(gender);
                $modal.find("#employeeId").val(Id);
                $modal.modal('show');
            });

            $('#chkParent').click(function () {
                var isChecked = $(this).prop("checked");
                $('#tblData tr:has(td)').find('input[type="checkbox"]').prop('checked', isChecked);
            });

            $('#tblData tr:has(td)').find('input[type="checkbox"]').click(function () {
                var isChecked = $(this).prop("checked");
                var isHeaderChecked = $("#chkParent").prop("checked");
                if (isChecked == false && isHeaderChecked)
                    $("#chkParent").prop('checked', isChecked);
                else {
                    $('#tblData tr:has(td)').find('input[type="checkbox"]').each(function () {
                        if ($(this).prop("checked") == false)
                            isChecked = false;
                    });
                    console.log(isChecked);
                    $("#chkParent").prop('checked', isChecked);
                }
            });


            $("#putform").on("click", function () {
                var url = "/employee/" + $("#employeeId").val();
                var employee = {
                    employeeId: $("#employeeId").val(),
                    firstName: $("#firstName1").val(),
                    lastName: $("#lastName1").val(),
                    departmentId: $("#departmentId1").val(),
                    gender: $("#gender1").val(),
                    job_title: $("#job_title1").val(),
                    date_of_birth: $("#date_of_birth1").val()
                };
                $.ajax({
                    url: url,
                    method: "PUT",
                    dataType: 'json',
                    data: JSON.stringify(employee),
                    contentType: "application/json",
                    success: function () {
                        console.log("success");
                    },
                    error: function () {
                        console.log("error");
                    }
                });
                location.reload();
            });


            $("#del").on("click", function () {
                var url;
                $('#tblData tr:has(td)').find('input[type="checkbox"]').each(function () {
                    if ($(this).prop("checked") == true) {
                        url = "/employee/" + $(this).val();
                        $.ajax({
                            url: url,
                            method: "DELETE",
                            success: function () {
                                console.log("success");
                            },
                            error: function () {
                                console.log("error");
                            }
                        });
                    }
                });
                location.reload();
            });

            if (!localStorage.getItem("reload")) {
                localStorage.setItem("reload", "true");
                location.reload();
            } else {
                localStorage.removeItem("reload");
            }
        },
        complete: function () {
            $("#postform").on("click", function () {
                location.reload();
                var employee = {
                    firstName: $("#firstName").val(),
                    lastName: $("#lastName").val(),
                    departmentId: $("#departmentId").val(),
                    gender: $("#gender").val(),
                    job_title: $("#job_title").val(),
                    date_of_birth: $("#date_of_birth").val()
                };

                $.ajax({
                    url: "/employee/add",
                    method: "POST",
                    dataType: 'json',
                    data: JSON.stringify(employee),
                    contentType: "application/json",
                });
                location.reload();
            });
        },
    });
});

