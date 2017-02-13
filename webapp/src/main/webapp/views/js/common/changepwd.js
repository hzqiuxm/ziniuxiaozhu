/**
 * Created by ZhangYifan on 2016/8/23.
 */

var checksession = function () {
    $("#container").hide();
    $.ajax({
        url: '/weekreport/checksession',
        type: 'POST',
        dataType: 'html',
        timeout: 1000,
        error: function () {
            sessionStorage.setItem('jumpUrl', '/views/personal/changepwd.html');
            location.href = "/views/week-report.html";
        },
        success: function (result) {
            if (result == "success") {
                $("#container").show();
            } else {
                sessionStorage.setItem('jumpUrl', '/views/personal/changepwd.html');
                location.href = "/views/week-report.html";
            }
        }
    });
};
new checksession();


(function (angular) {
    'use strict';
    /**
     * 用于修改密码页面
     *
     */
    angular.module('zn-choose', [])
        .controller('chooseController', ['$scope', function ($scope) {
            $scope.choose = 6;
            $scope.four = true;
            $scope.template = {name: 'header.html', url: '/views/header.html'}

        }]);


})(angular);

var Bind = function () {
    $("#cancel").on("click", function () {//取消按钮,清空input
        $(this).parents("#center-div").children("div").children("input").val("");
    });
    var b = $("body");
    b.on("click", "#changepwd", function () {
        if ($("#inputnewpwd").val() != $("#inputnewpwd2").val()) {
            swal("两次输入的密码不同，请重新输入");
        } else {
            $.ajax({
                url: '/weekreport/changepwd',
                type: 'POST',
                data: {
                    inputoldpwd: $("#inputoldpwd").val(),
                    inputnewpwd: $("#inputnewpwd").val(),
                    inputnewpwd2: $("#inputnewpwd2").val()
                },
                dataType: 'html',
                timeout: 1000,
                error: function () {
                    sessionStorage.setItem('jumpUrl', '/views/personal/changepwd.html');
                    location.href = "/views/week-report.html";
                },
                success: function (result) {
                    try {
                        result = $.parseJSON(result);
                    } catch (e) {
                        sessionStorage.setItem('jumpUrl', '/views/personal/changepwd.html');
                        location.href = "/views/week-report.html";
                    }


                    if (result.result == 0) {//成功修改密码，重新登陆
                        swal("修改成功！");
                        location.href = "/views/week-report.html";
                    }
                    else {
                        if (result.result == 1) {
                            swal("登录超时，请重新登陆！！");
                            location.href = "/views/week-report.html";
                        } else
                            swal("修改密码出错！！");
                    }
                }
            });
        }
    });
    b.on("click", "#logout", function () {//登出
        $.ajax({
            url: '/weekreport/logout',
            type: 'POST',
            dataType: 'html',
            timeout: 1000,
            error: function () {
                swal("登出失败");
                sessionStorage.setItem('jumpUrl', '/views/personal/changepwd.html');
                location.href = "/views/week-report.html";
            },
            success: function () {
                location.href = "/views/week-report.html";
            }
        });
    });
};
var Binds = new Bind();


