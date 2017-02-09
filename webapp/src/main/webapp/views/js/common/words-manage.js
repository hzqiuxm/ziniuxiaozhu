/**
 * Created by yeoman on 2017/2/9.
 */
(function (angular) {
    'use strict'
    /**
     * 用于展示每日精进的句子列表
     *
     */
    angular.module('ddu-wordsmanage', [])
        .controller('daydayupController', ['$scope', function ($scope) {

        }]);

    $("#addWords").click(function () {
        var html = '<p >英文：</p><textarea class="form-control" id="en"></textarea>'
            +'<p >中文：</p><textarea class="form-control" id="ch"></textarea>'
        bootbox.dialog({
            message : html,
            title : "新增句子",
            animate : false,
            buttons : {
                success : {
                    label : "提交",
                    className : "bottom-bg2",
                    callback : function() {
                        $.ajax({
                            type: "POST",
                            url: "/daydayup/addWords",
                            async: false,
                            data: {english:$("#en").val(),chinese:$("#ch").val()},
                            success: function(result) {
                                if (result.success)
                                    $('#tabId').bootstrapTable('refresh');
                            }
                        });
                    }
                },
                "取消" : {
                    className : "bottom-bg2",
                    callback : function() {}
                }
            }
        });
    });
})(angular)