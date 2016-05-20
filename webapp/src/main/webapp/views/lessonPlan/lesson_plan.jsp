<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>原创课程</title>
  <jsp:include flush="true" page="/views/common/base.jsp" />
</head>
<body class="left-sidebar">
  <div id="content" class="mobileUI-main-content">
    <div id="content-inner">
      <div class="container">
          <div id="toolbar">
              <div class="row">
                  <div><div class="bg-success">提示:双击数据行可以编辑课程详情</div></div>
              </div>
          </div>
        <table id="lesson_plan"
               class="table table-striped"
               data-toggle="table"
               data-toolbar="#toolbar"
               data-search="true"
               data-show-refresh="true"
               data-show-toggle="true"
               data-show-columns="true"
               data-show-export="true"
               data-detail-view="true"
               data-detail-formatter="detailFormatter"
               data-minimum-count-columns="2"
               data-show-pagination-switch="true"
               data-page-list="[10, 15, 20, 50, All]"
               data-pagination="true"
               data-page-size="10"
               data-id-field="id"
               data-show-footer="true"
               data-url="lessonPlan/getLessonPlan">
          <thead>
          <tr>
            <th data-field="lesson_teacher" data-align="center" >主讲教师</th>
            <th data-field="lesson_name" data-align="center">选题名称</th>
            <th data-field="lesson_title" data-align="center">课程名称</th>
            <th data-field="lesson_type" data-align="center" data-sortable="true" >课程类型</th>
            <th data-field="state" data-align="center" data-formatter="stateFormatter">讲课状态</th>
            <th data-field="lesson_time">讲课时间</th>
          </tr>
          </thead>
        </table>
      </div>
    </div>
  </div>

  <jsp:include flush="true" page="/views/common/left.jsp"/>
<script>
  function detailFormatter(index, row) {
    var html = [];
    html.push('<div class="text-left"><span class="text-primary ">'+ "课程描述:" + '</span><div class="text-muted">' + row.lesson_des +'</div></div>' );
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
  
  function stateFormatter(value, row, index){
    var icon = row.state === '0' ? 'glyphicon glyphicon-remove lesson_no' : 'glyphicon glyphicon-ok lesson_yes';
    return [
      '<div class=" ' + icon + '"></div> '
    ].join('');
  }
  
  $(function() {
  	$('#lesson_plan').on('dbl-click-row.bs.table', function (e, row, $element) {
	    if(row.state==='0'){
	    	bootbox.dialog({
				message : "<div><input type='text' id='lesson_title' class='form-control' placeholder='课程名称'></div>"+
					"<div style='margin-top:15px;'>课程描述:<textarea id='lesson_des' class='form-control' rows='3'></textarea></div>",
				title : "完善课程信息",
				buttons : {
					success : {
						label : "确定",
						callback : function() {
							var lessonTitle = $("#lesson_title").val();
							var lessonDes = $("#lesson_des").val();
		                    $.ajax({
		                        url: 'lessonPlan/modifyPlan',
		                        type: 'POST',
		                        data: {'id':row.id ,'lessonTitle':lessonTitle, 'lessonDes': lessonDes}
		                    }).done(function (datas) {
		                        if(datas.success){
		                        	window.location.reload();
		                        }
		                    });
						}
					},
					"取消" : {
						callback : function() {}
					}
				}
			});
	    } 
	})
  })
</script>
</body>
</html>
