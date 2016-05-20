<%--
  Created by IntelliJ IDEA.
  User: hzqiuxm
  Date: 2015/8/22
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>课程展示</title>
  <!- #include virtual="../base.html" ->
  <!- #include file="base.html" ->
  <jsp:include flush="true" page="/views/common/base.jsp" />
</head>
<body class="left-sidebar">
  <div id="content" class="mobileUI-main-content">
    <div id="content-inner">
      <div class="container">
        <div id="toolbar">
          <%--<button id="remove" class="btn btn-danger" disabled>--%>
            <%--<i class="glyphicon glyphicon-remove"></i> Delete--%>
          <%--</button>--%>
            <div class="row">
              <div id="select-button" class="btn btn-primary">我要选课</div>
              <%--<div class="lesson_yes"></div>--%>
            </div>
        </div>
        <table id="lesson_table"
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
               data-url="lesson/getLessons"
               >
          <thead>
          <tr>
            <th data-field="id" data-align="center" >课程编号</th>
            <th data-field="lesson_name" data-align="center">课程名称</th>
            <th data-field="lesson_type" data-align="center" data-sortable="true" >课程类型</th>
            <th data-field="state" data-align="center" data-formatter="stateFormatter">课程状态</th>
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
//    $.each(row, function (key, value) {
//      html.push('<p><b>' + key + ':</b> ' + value + '</p>');
//    });
    html.push('<div class="text-left"><span class="text-primary ">'+ "课程主题描述:" + '</span><div class="text-muted">' + row.lesson_des +'</div></div>' );
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
    var icon = row.state === '0' ? 'glyphicon glyphicon-ok lesson_yes' : 'glyphicon glyphicon-remove lesson_no';
    return [
      '<div class=" ' + icon + '"></div> '
    ].join('');
  }
</script>
<script src="/commons/js/lessons.js"></script>
</body>
</html>
