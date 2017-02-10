/**
 * Created by hzqiuxm on 2016/8/12 0012.
 */

(function (angular) {
    'use strict';
    /**
     * 用于选课页面
     *
     */
    angular.module('zn-choose', [])
        .controller('chooseController', ['$scope', function ($scope) {
            $scope.four = true;
            $scope.template = {name: 'header.html', url: '/views/header.html'}

        }]);


})(angular);

$(function () {
    //$('#lesson_table').bootstrapTable({});
    $('#select-button').on("click", function () {
        $.ajax({
            type: "POST",
            url: "/lesson/chooseLessons",
            async: false,
            //dataType: "json",
            error: function () {
                sessionStorage.setItem('jumpUrl', '/views/lesson/choose-lessons.html');
                location.href = "/views/week-report.html";
            },
            success: function (response) {
                //$('#events-table').bootstrapTable('refresh');
                if (response.result == 99) {
                    swal("操作失败", "对不起，你还不是紫牛小筑的指定讲师，请联系管理员!", "error");
                    return;
                }
                if (response.result == 95) {
                    swal("操作失败", "你还有未完成的培训课程，完成后才可以再次选课!", "error");
                    return;
                }
                if (response.result == 97) {
                    swal("操作失败", "你已经没有剩余选课次数!", "error");
                    return;
                }
                var json = eval(response);
                swal("<<" + json.lessons[0].lesson_name + ">>", "选课成功！你还剩余" + json.chooseNum + "选课次数!", "success");


            }

        });
    });
});

function detailFormatter(index, row) {
    var html = [];
//    $.each(row, function (key, value) {
//      html.push('<p><b>' + key + ':</b> ' + value + '</p>');
//    });
    html.push('<div class="text-left"><span class="text-primary ">' + "课程主题描述:" + '</span><div class="text-muted">' + row.lesson_des + '</div></div>');
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
    var icon = row.state === '0' ? 'glyphicon glyphicon-ok lesson_yes' : 'glyphicon glyphicon-remove lesson_no';
    return [
        '<div class=" ' + icon + '"></div> '
    ].join('');
}

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
                sessionStorage.setItem('jumpUrl', '/views/lesson/choose-lessons.html');
                location.href = "/views/week-report.html";
            },
            success: function (result) {
                if (result == "success") {
                    $("#container").show();
                } else {
                    sessionStorage.setItem('jumpUrl', '/views/lesson/choose-lessons.html');
                    location.href = "/views/week-report.html";
                }
            }
        })
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
    })
    $(".container").bind('DOMNodeInserted', function () {
        pageslider.ARClass($(".page-number.active"));
    })
});


