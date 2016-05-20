<%--
  Created by IntelliJ IDEA.
  User: hzqiuxm
  Date: 2015/10/22
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include flush="true" page="/views/common/base.jsp" />
<!DOCTYPE HTML>
<!--
Striped 1.0
-->
<html>
<head>
  <title>紫牛首页 </title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="description" content="" />
  <meta name="keywords" content="" />


</head>
<!--
    Note: Set the body element's class to "left-sidebar" to position the sidebar on the left.
    Set it to "right-sidebar" to, you guessed it, position it on the right.
-->
<body class="left-sidebar">

<!-- Wrapper -->
<div id="wrapper">

  <!-- Content -->
  <div id="content" class="mobileUI-main-content">
    <div id="content-inner">

      <!-- Post -->
      <article class="is-post is-post-excerpt">
        <header>
          <h2><a href="#">欢迎来到紫牛小筑</a></h2>
          <span class="byline">我们是一群怀有梦想的极客,欢迎你加入我们!</span>
        </header>
        <div class="info">

          <span class="date"><span class="month">Jan<span>uary</span></span> <span class="day">14</span><span class="year">, 2013</span></span>
          <!--
              Note: You can change the number of list items in "stats" to whatever you want.
          -->
          <ul class="stats">
            <li><a href="#" class="link-icon24 link-icon24-1">16</a></li>
            <li><a href="#" class="link-icon24 link-icon24-2">32</a></li>
            <li><a href="#" class="link-icon24 link-icon24-3">64</a></li>
            <li><a href="#" class="link-icon24 link-icon24-4">128</a></li>
          </ul>
        </div>
        <a href="#" class="image image-full"><img src="/images/geek01.jpg" alt="" /></a>
        <p>
          尽请期待... ...
        </p>

      </article>

      <!-- Post -->
      <article class="is-post is-post-excerpt">
        <header>
          <h2><a href="#">Hello World</a></h2>
          <span class="byline">我们都从这里开始，它对我们有着特殊的含义</span>
        </header>
        <div class="info">
          <span class="date"><span class="month">Jan<span>uary</span></span> <span class="day">8</span><span class="year">, 2013</span></span>
          <ul class="stats">
            <li><a href="#" class="link-icon24 link-icon24-1">12</a></li>
            <li><a href="#" class="link-icon24 link-icon24-2">24</a></li>
            <li><a href="#" class="link-icon24 link-icon24-3">48</a></li>
            <li><a href="#" class="link-icon24 link-icon24-4">96</a></li>
          </ul>
        </div>
        <a href="#" class="image image-full"><img src="/images/geek02.jpg" alt="" /></a>
        <p>
          敬请期待... ...
        </p>
      </article>

      <!-- Pager -->
      <div class="pager">
        <!--<a href="#" class="button previous">Previous Page</a>-->
        <div class="pages">
          <a href="#" class="active">1</a>
          <a href="#">2</a>
          <a href="#">3</a>
          <a href="#">4</a>
          <span>&hellip;</span>
          <a href="#">20</a>
        </div>
        <a href="#" class="button next">Next Page</a>
      </div>

    </div>
  </div>

</div>
<jsp:include flush="true" page="/views/common/left.jsp"/>
</body>
</html>
