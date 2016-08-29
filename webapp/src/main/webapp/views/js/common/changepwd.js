/**
 * Created by ZhangYifan on 2016/8/23.
 */

function checksession(){
    $.ajax({
        url: '/weekreport/checksession',
        type: 'POST',
        dataType: 'html',
        timeout: 1000,
        error: function(){console.log('Error loading document');},
        success: function(result){
            if(result=="success"){
            }else{
                location.href="/views/week-report.html#personal/changepwd";
            }
        }
    });
}
checksession();

(function (angular) {
    'use strict';
    /**
     * 用于修改密码页面
     *
     */
    angular.module('zn-choose', [])
        .controller('chooseController', ['$scope', function ($scope) {
            $scope.choose=6;
            $scope.four = true;
            $scope.template = { name: 'header.html', url: '/views/header.html'}

        }]);



})(angular);

var Bind=function(){
    $("#cancel").on("click",function(){//取消按钮,清空input
        $(this).parents("#center-div").children("div").children("input").val("");
    });
    b=$("body");
    b.on("click","#changepwd",function(){
        if($("#inputnewpwd").val()!=$("#inputnewpwd2").val()) {
            alert("两次输入的密码不同，请重新输入");
        }else{
            $.ajax({
                url: '/weekreport/changepwd',
                type: 'POST',
                data:{inputoldpwd:$("#inputoldpwd").val(),inputnewpwd:$("#inputnewpwd").val(),inputnewpwd2:$("#inputnewpwd2").val()},
                dataType: 'html',
                timeout: 1000,
                error: function(){console.log('Error loading document');},
                success: function(result){
                    result= $.parseJSON(result);
                    if(result.result==0){//成功修改密码，重新登陆
                        alert("修改成功！");
                        location.href="/views/week-report.html";
                    }
                    else{
                        if(result.result==1){
                            alert("登录超时，请重新登陆！！");
                            location.href="/views/week-report.html";
                        }else
                            alert("修改密码出错！！");
                    }
                }
            });
        }
    });
    b.on("click","#logout",function(){//登出
        $.ajax({
            url: '/weekreport/logout',
            type: 'POST',
            dataType: 'html',
            timeout: 1000,
            error: function(){alert("登出失败")},//todo 所有的失败提示
            success: function(){
                location.href="/views/week-report.html";
            }
        });
    });
};
var Binds=new Bind();


