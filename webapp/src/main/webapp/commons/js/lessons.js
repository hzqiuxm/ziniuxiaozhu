/**
 * Created by hzqiuxm on 2015/8/28.
 */
$(function () {
    //$('#lesson_table').bootstrapTable({});
    $('#select-button').on("click", function () {
        swal({
            title: "请输入你的姓名",
            type: "input",
            showCancelButton: true,
            closeOnConfirm: false,
            animation: "slide-from-top",
            inputPlaceholder: "务必输入你在紫牛小筑的真实姓名"
        }, function (inputValue) {
            if (inputValue === false) return false;
            if (inputValue === "") {
                swal.showInputError("You need to write something!");
                return false
            }
            $.ajax({
                type: "POST",
                url: "lesson/chooseLessons",
                data: {"name": inputValue},
                async: false,
                //dataType: "json",
                success: function (response) {
                    //$('#events-table').bootstrapTable('refresh');
                    if (response.result == 99) {
                        swal("操作失败", "对不起，你还不是紫牛小筑的指定讲师，请联系管理员!", "error");
                    }
                    if (response.result == 95) {
                        swal("操作失败", "你还有未完成的培训课程，完成后才可以再次选课!", "error");
                    }
                    if (response.result == 97) {
                        swal("操作失败", "你已经没有剩余选课次数!", "error");
                    }
                    var json = eval(response);
                    swal("<<" + json.lessons[0].lesson_name + ">>", "选课成功！你还剩余" + json.chooseNum + "选课次数!", "success");

                    //swal({
                    //    title: json[0].lesson_name,
                    //    text: "是否满意本次选课结果？你还剩余的选课次数为:",
                    //    type: "warning",
                    //    showCancelButton: true,
                    //    cancelButtonText: "确认",
                    //    confirmButtonColor: "#DD6B55",
                    //    confirmButtonText: "再选一次!",
                    //    closeOnConfirm: false
                    //}, function () {
                    //    //swal("Deleted!", "Your imaginary file has been deleted.", "success");
                    //    $.ajax({
                    //        type: "POST",
                    //        url: "lesson/chooseLessons",
                    //        data:{"name": inputValue},
                    //        async:false,
                    //        success:function(response) {
                    //            var json2 = eval(response);
                    //            swal("<<" + json2[0].lesson_name + ">>", "选课成功！你可以前往【原创课程】目录进行确认!", "success");
                    //        }
                    //    });
                    //});

                }

            });
            //swal("Nice!", "You wrote: " + inputValue, "success");
        });
    })
    ;
})