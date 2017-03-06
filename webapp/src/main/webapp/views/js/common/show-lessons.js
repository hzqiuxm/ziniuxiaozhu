/**
 * Created by ZhangYifan on 2016/8/23.
 */

(function (angular) {
    'use strict';
    /**
     * 用于显示课程页面
     *
     */
    angular.module('zn-choose', [])
        .controller('chooseController', ['$scope', function ($scope) {
            $scope.choose = 4;
            $scope.four = true;
            $scope.template = {name: 'header.html', url: '/views/header.html'}
        }]);
})(angular);

function detailFormatter(index, row) {
    var html = [];
    html.push('<div class="text-left"><span class="text-primary ">' + "课程描述:" + '</span><div class="text-muted">' + row.lesson_des + '</div></div>');
    return html.join('');
}

function getHeight() {
    return $(window).height() - $('h1').outerHeight(true);
}

function responseHandler(res) {
    $.each(res.rows, function (i, row) {
        row.state = $.inArray(row.id, selections) !== -1;
    });
    return res;
}

function stateFormatter(value, row, index) {
    var icon = row.state === '0' ? 'glyphicon glyphicon-remove lesson_no' : 'glyphicon glyphicon-ok lesson_yes';
    return [
        '<div class=" ' + icon + '"></div> '
    ].join('');
}

$(function () {
    $('#lesson_plan').on('dbl-click-row.bs.table', function (e, row, $element) {
        if (row.state === '0') {
            bootbox.dialog({
                message: "<div><input type='text' id='lesson_title' class='form-control' placeholder='课程名称'></div>" +
                "<div style='margin-top:15px;'>课程描述:<textarea id='lesson_des' class='form-control' rows='3'></textarea></div>",
                title: "完善课程信息",
                buttons: {
                    success: {
                        label: "确定",
                        callback: function () {
                            var lessonTitle = $("#lesson_title").val();
                            var lessonDes = $("#lesson_des").val();
                            $.ajax({
                                url: '/lessonPlan/modifyPlan',
                                type: 'POST',
                                data: {'id': row.id, 'lessonTitle': lessonTitle, 'lessonDes': lessonDes},
                                error: function () {
                                    sessionStorage.setItem('jumpUrl', '/views/lesson/show-lessons.html');
                                    location.href = "/views/week-report.html";
                                }
                            }).done(function (datas) {
                                if (datas.usercheck == false) {//如果不是本人的课程
                                    swal("操作失败", "对不起，只能修改自己的课程内容！", "error");
                                } else {
                                    if (datas.success) {
                                        window.location.reload();
                                    } else {
                                        swal({title:"操作失败", text:"出现未知修改错误！尝试重新登陆！</br>如果依旧出错，请联系管理员！",html:true,type:"error"},function(isConfirm){
                                            sessionStorage.setItem('jumpUrl', '/views/lesson/show-lessons.html');
                                            location.href="/views/week-report.html";
                                        });
                                    }
                                }
                            });
                        }
                    },
                    "取消": {
                        callback: function () {
                        }
                    }
                }
            });
        }
    })
});


var PageSlider = function () {
};
PageSlider.prototype = {
    ARClass: function (li) {//传入的是被选中的li
        var prev = li.prev('.page-number');
        var next = li.next('.page-number');
        var number = $('.page-number');
        //清除其他
        number.css({width: '8px', height: '8px'}).children('a').css({cssText: 'display:none!important'});
        li.css({width: '16px', height: '16px', color: '#fff'}).children('a').css({cssText: 'display:block!important'});
        prev.css({width: '12px', height: '12px'});
        next.css({width: '12px', height: '12px'});
    },
    checksession: function () {
        $("#container").hide();
        $.ajax({
            url: '/weekreport/checksession',
            type: 'POST',
            dataType: 'html',
            timeout: 1000,
            error: function () {
                sessionStorage.setItem('jumpUrl', '/views/lesson/show-lessons.html');
                location.href = "/views/week-report.html";
            },
            success: function (result) {
                if (result == "success") {
                    $("#container").show();
                } else {
                    sessionStorage.setItem('jumpUrl','/views/lesson/show-lessons.html');
                    location.href = "/views/week-report.html";
                }
            }
        });
    }
};


var pageslider = new PageSlider();
pageslider.checksession();

$(document).ready(function () {
    $("body").on("mouseover", ".page-number", function () {
        pageslider.ARClass($(this));
    }).on("mouseleave", "ul.pagination", function () {//离开ul
        var number = $('.page-number');
        pageslider.ARClass(number.eq(number.index($(".page-number.active"))));
    });
    $(".container").bind('DOMNodeInserted', function () {
        pageslider.ARClass($(".page-number.active"));
    });
});
