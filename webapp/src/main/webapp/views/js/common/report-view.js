/**
 * Created by ZhangYifan on 2016/8/23.
 */
(function (angular) {
    'use strict';
    /**
     * 用于周报页面
     *
     */
    angular.module('zn-choose', [])
        .controller('chooseController', ['$scope', function ($scope) {
            $scope.choose = 6;

            $scope.six = true;
            $scope.template = {name: 'header.html', url: '/views/header.html'}

        }]);


})(angular);


$(document).ready(function () {
    new Bind();
    ReportViews.LoadHtml();
    ReportViews.GetNames();
});

var Bind = function () {
    var b = $("body");

    b.on("mousedown", ".judge-reply", function () {
        $(this).parent().next().show();
    });

    b.on("mousedown", ".report-change-head-close", function () {
        $(".report-change").hide();
    });

    b.on("click", "#show-more", function () {//加载更多
        ReportViews.LoadReports("5");
    });

    b.on("click", "#flash", function () {
        //清空条件
        ReportViews.Clear().LoadReports("5");
    });

    b.on("click", "#logout", function () {//登出
        $.ajax({
            url: '/weekreport/logout',
            type: 'POST',
            dataType: 'html',
            timeout: 1000,
            error: function () {
                swal("登出失败");
                ReportViews.JumpTo();
            },
            success: function () {
                ReportViews.JumpTo();
            }
        });
    });

    b.on("mousedown", "#share", function () {//周报提交
        $.ajax({
            url: '/weekreport/insertreport',
            type: 'POST',
            data: {thisweek: $("#thisweek").val(), nextweek: $("#nextweek").val(), difficulty: $("#difficulty").val()},
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }


                if (result.result == 0) {//重新加载周报
                    ReportViews.Clear().LoadReports("5");
                } else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        ReportViews.JumpTo();
                    } else
                        swal("周报提交出错！！");
                }
            }
        });
    });

    b.on("mousedown", ".delete", function () {//删除周报
        var id = $(this).parents(".one-report").attr("id");//获取到该周报id
        var datanumber = Number($("#show-more").attr("data-number"));
        $.ajax({
            url: '/weekreport/deletereport',
            type: 'POST',
            data: {reportid: id},
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result == 0) {//重新加载周报
                    var div = document.getElementById(id);
                    $("#show-more").attr("data-number", datanumber - 1);
                    div.remove();
                } else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        ReportViews.JumpTo();
                    } else
                        swal("周报提交出错！！");
                }
            }
        });
    });

    b.on("mousedown", ".reply", function () {//回复
        var report = $(this).parents(".judge").prev();
        var message = $(this).parent().prev().val();
        var reportid = report.parent().attr("id");
        $.ajax({
            url: '/weekreport/insertcomment',
            type: 'POST',
            data: {message: message, reportid: reportid, replyname: ""},
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result == 0)
                    $("#" + reportid).replaceWith(ReportViews.MakeWholeReport(result.data));
                else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        ReportViews.JumpTo();
                    } else
                        swal("周报提交出错！！");
                }
            }
        });
    });

    b.on("mousedown", ".reply-textarea-span", function () {//回复评论
        var reply = $(this).parents(".replies");
        var reportid = $(this).parents(".one-report").attr("id");//用户
        var replyname = reply.children(".replies-main").children("span").eq(0).html();//被回复的人
        var message = $(this).parent().prev().val();//回复内容
        $.ajax({
            url: '/weekreport/insertcomment',
            type: 'POST',
            data: {message: message, replyname: replyname, reportid: reportid},
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result == 0)
                    $("#" + reportid).replaceWith(ReportViews.MakeWholeReport(result.data));
                else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        ReportViews.JumpTo();
                    } else {
                        swal("评论出错！！");
                    }
                }
            }
        });
    });

    b.on("mousedown", ".judge-delete", function () {//删除评论
        var commentid = $(this).parents(".replies").attr("data-judgeid");
        var reportid = $(this).parents(".one-report").attr("id");
        $.ajax({
            url: '/weekreport/deletecomment',
            type: 'POST',
            data: {commentid: commentid, reportid: reportid},
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result == 0)
                    $("#" + reportid).replaceWith(ReportViews.MakeWholeReport(result.data));
                else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        ReportViews.JumpTo();
                    } else {
                        swal("删除评论出错！！");
                    }
                }
            }
        });
    });

    b.on("mousedown", ".change", function () {//修改周报  获取周报内容
        $(".report-change-control").attr("id", $(this).parents(".one-report").attr("id"));
        $.ajax({
            url: '/weekreport/getreport',
            type: 'POST',
            data: {id: $(this).parents(".one-report").attr("id")},
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result == 0) {
                    var obj = result.data;
                    $("#this-week-change").val(obj.thisweek);
                    $("#next-week-change").val(obj.nextweek);
                    $("#difficulty-change").val(obj.difficulty);
                }
                else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        ReportViews.JumpTo();
                    } else {
                        swal("获取周报内容出错！！");
                    }
                }

            }
        });
        $(".report-change").show();
    });

    b.on("mousedown", ".report-change-change", function () {
        var id = $(this).parents(".report-change-control").attr("id");//周报的Id
        $.ajax({
            url: '/weekreport/updatereport',
            type: 'POST',
            data: {
                id: id,
                thisweek: $("#this-week-change").val(),
                nextweek: $("#next-week-change").val(),
                difficulty: $("#difficulty-change").val()
            },
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result == 0) {
                    $("#" + id).replaceWith(ReportViews.MakeWholeReport(result.data));
                    $(".report-change").hide();
                }
                else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        ReportViews.JumpTo();
                    } else {
                        swal("删除评论出错！！");
                    }
                }
            }
        });
    });

    b.on("input", ".judge-textarea", function () {
        var input = 1000 - $(this).val().length;
        if (input > 0)
            $(this).next().children(".can-input").html("还可输入:" + input + "个字符");
        else
            $(this).next().children(".can-input").html("已经超出:" + (-input) + "个字符");
    });

    b.on("input", "#search-name", function () {
        ReportViews.Search();
    });

    $("#search-datetime").change(function () {
        ReportViews.Search();
    });
};

var ReportView = function () {
    this.showmore = $("#show-more");
    this.searchname = $("#search-name");
    this.searchdatetime = $("#search-datetime");
    this.reports = $("#reports");
    this.lastweek = $("#last-week");
};
ReportView.prototype = {
    checksession: function () {
        $("#container").hide();
        $.ajax({
            url: '/weekreport/checksession',
            type: 'POST',
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                if (result == "success") {
                    $("#container").show();
                } else {
                    ReportViews.JumpTo();
                }
            }
        });
    },
    LoadReports: function (number) {//周报拉取 number 是获取数量
        var datanumber = Number(this.showmore.attr("data-number"));
        var name = this.showmore.attr("data-name");
        var time = this.showmore.attr("data-time");
        var rshowmore = this.showmore;
        $.ajax({
            url: '/weekreport/getreports',
            type: 'POST',
            data: {
                name: name,
                time: time,
                start: datanumber,
                number: number
            },
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result != 0) {//获取失败
                    swal("登录超时，请重新登陆！！");
                    ReportViews.JumpTo();
                }
                else {//获取成功
                    var data = result.data;
                    var report = ReportViews.MakeWholeReport(data);
                    $("#reports").append(report);
                    rshowmore.attr("data-number", datanumber + data.length);
                    if (data.length < number) {
                        rshowmore.html("没有更多了！");
                        $("body").off("click", "#show-more");
                    } else if (rshowmore.html() == "没有更多了！") {
                        rshowmore.html("查看更多动态");
                        $("body").on("click", "#show-more", function () {//加载更多
                            ReportViews.LoadReports("5");
                        })
                    }
                }
            }
        });
        return this;
    },
    Clear: function () {
        this.showmore.attr("data-number", "0");
        this.showmore.attr("data-name", "");
        this.showmore.attr("data-time", "");
        this.searchname.val("");
        this.searchdatetime.val("");
        this.reports.empty();
        return this;
    },
    Search: function () {
        var name = this.searchname.val();
        var time = this.searchdatetime.val();
        this.showmore.attr("data-number", "0");
        this.showmore.attr("data-name", name);
        this.showmore.attr("data-time", time);
        this.reports.empty();
        ReportViews.LoadReports("5");
    },
    LoadHtml: function () {//加载整个周报提交页面
        ReportViews.LoadReports("5");//加载周报
        var rlastweek = this.lastweek;
        $.ajax({//上周周报未提交加载
            url: '/weekreport/lastweek',
            type: 'POST',
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result == 0) {
                    var data = result.data;
                    data.forEach(function (div) {
                        rlastweek.append("<span>" + div + "</span>\n");
                    });
                }
                else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        location.href = "/views/week-report.html";
                    } else {
                        swal("删除评论出错！！");
                    }
                }
            }
        });
    },
    MakeWholeReport: function (data) {//加载周报，data是返回的json数据
        var report = "";
        data.forEach(function (e) {
            report += ReportViews.MakeReportDiv(e);
            var comments = e.comment;
            comments.forEach(function (comment) {
                report += ReportViews.MakeCommentDiv(comment);
            });
            report += ('</div>');
        });
        return report;

    },
    MakeReportDiv: function (report) {
        var str = "";
        str += '<div class="one-report" id="' + report.id + '">';
        str += '<div class="report">';
        str += '<div class="report-head">';
        str += '<div class="report-head-left"><span>' + report.realname + '</span>:</div>';
        if (report.owner == "1") {
            str += '<div class="report-head-right"><div class="hide"></div>';
            str += '<div class="right">';
            str += '<div class="font"><i class="array"></i></div>';
            str += '<div class="report-head-control">';
            str += '<span class="change">修改</span>';
            str += '<span class="delete">删除</span>';
            str += '</div>';
            str += '</div>';
            str += '</div>';
        }
        str += '</div><div class="report-main"><span>本周工作</span>' + report.thisweekreport + '<span>下周计划</span>' + report.nextweekreport + '<span>遇到问题与解决办法</span>' + report.difficulty + '</div>';
        str += '<div class="report-foot">';
        str += '<div class="report-time">' + report.time + '</div>';
        str += '</div>';
        str += '</div> <div class="judge">';
        str += '<div class="judge-textarea-div" tabindex="0" hidefocus="true">';
        str += '<textarea name="judge-textarea" class="judge-textarea" id="judge-textarea-' + report.id + '" cols="30" rows="10"></textarea>';
        str += '<div class="judge-toolbar">';
        str += '<span class="reply">回复</span>';
        str += '<span class="can-input"></span>';
        str += '</div>';
        str += '</div>';
        str += '</div>';

        return str;
    },
    MakeCommentDiv: function (comment) {
        var str = '';
        str += '<div class="replies" data-judgeid="' + comment.id + '">';
        str += '<div class="replies-main">';
        str += '<span>' + comment.critic;
        if (comment.reply != "")
            str += '</span>回复<span>' + comment.reply;
        str += '</span>:' + comment.message;
        str += '</div>';
        str += '<div class="replies-foot">';
        str += '<span class="time">' + comment.time + '</span>';
        str += '<div class="replies-control">';
        if (comment.delete == "1")
            str += '<span class="judge-delete">删除</span>';
        str += '<span class="judge-reply">回复</span>';
        str += '</div>';
        str += '<div class="reply-textarea" style="display:none;" tabindex="0" hidefocus="true">';
        str += '<textarea name="" class="judge-textarea" id="" cols="30" rows="10"></textarea>';
        str += '<div class="judge-toolbar">';
        str += '<span class="reply-textarea-span">回复</span>';
        str += '<span class="can-input"></span>';
        str += '</div>';
        str += '</div>';
        str += '</div>';
        str += '</div>';
        return str;
    },
    GetNames: function () {
        var str = "";
        var now = new Date(), hour = now.getHours();
        if (hour < 6 && hour > 4)str = "凌晨好！";
        else if (hour < 9)str = "早上好！";
        else if (hour < 12)str = "上午好！";
        else if (hour < 14)str = "中午好！";
        else if (hour < 17)str = "下午好！";
        else if (hour < 19)str = "傍晚好！";
        else if (hour < 22)str = "晚上好！";
        else str = "深夜好！";


        $.ajax({//上周周报未提交加载
            url: '/weekreport/getuser',
            type: 'POST',
            dataType: 'html',
            timeout: 1000,
            error: function () {
                ReportViews.JumpTo();
            },
            success: function (result) {
                try {
                    result = $.parseJSON(result);
                } catch (e) {
                    sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
                    location.href = "/views/week-report.html";
                }
                if (result.result == 0) {
                    $("#user-text").html(str + result.data + "。");
                }
                else {
                    if (result.result == 1) {
                        swal("登录超时，请重新登陆！！");
                        location.href = "/views/week-report.html";
                    } else {
                        swal("删除评论出错！！");
                    }
                }
            }
        });
    },
    JumpTo: function () {
        sessionStorage.setItem('jumpUrl', '/views/week-report/report-view.html');
        location.href = "/views/week-report.html";
    }
};

var ReportViews = new ReportView();
ReportViews.checksession();