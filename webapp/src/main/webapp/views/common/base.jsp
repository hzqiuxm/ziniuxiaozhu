<%--
  Created by IntelliJ IDEA.
  User: hzqiuxm
  Date: 2015/8/27
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%   
	String path = request.getContextPath();   
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<%--本地的插件包--%>
<!--<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:400,400italic,700|Open+Sans+Condensed:300,700" rel="stylesheet" />-->
<link rel="stylesheet" href="/plugins/bootstrap/css/bootstrap.min.css">
<!-- Latest compiled and minified CSS -->
<%--<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.min.css">--%>
<link rel="stylesheet" href="/plugins/bootstracptable/bootstrap-table.min.css">
<link rel="stylesheet" href="/plugins/bootstrapdatepicker/bootstrap-datepicker-1.3.1.min.css">
<link rel="stylesheet" href="/plugins/sweetalert/sweetalert.css">

<%--本地的插件包--%>
<script src="/plugins/jquery/jquery-1.11.1.min.js"></script>
<script src="/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<%--<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.min.js"></script>--%>
<script src="/plugins/bootstracptable/bootstrap-table.min.js"></script>
<script src="/plugins/bootstracptable/bootstrap-table-zh-CN.min.js"></script>
<script src="/plugins/bootstrapdatepicker/bootstrap-datepicker-1.3.1.min.js"></script>
<script src="/plugins/bootstrapdatepicker/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/plugins/bootbox/bootbox-4.3.0.min.js"></script>
<!-- Latest compiled and minified Locales -->
<%--<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/locale/bootstrap-table-zh-CN.min.js"></script>--%>

