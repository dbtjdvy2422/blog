<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"></head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<body>


<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <!-- Brand -->
  <a class="navbar-brand" href="/blog">Blog</a>


  <!-- Links -->
  <c:choose>
  <c:when test="${empty seesionScope.principal }">
  <ul class="navbar-nav">
    <li class="nav-item"><a class="nav-link" href="/blog/user/loginForm">로그인</a></li>
    <li class="nav-item"><a class="nav-link" href="/blog/user/joinForm">회원가입</a></li>
	</ul>
  </c:when>
  
  <c:otherwise>
  <ul class="navbar-nav">
    <li class="nav-item"><a class="nav-link" href="/blog/board/writeForm">글쓰기</a></li>
    <li class="nav-item"><a class="nav-link" href="/blog/user/userForm">회원정보</a></li>
	 <li class="nav-item"><a class="nav-link" href="/blog/user/logout">로그아웃</a></li>
    </ul>
  </c:otherwise>
  </c:choose>


	
    
    <!-- Dropdown -->
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	게시판
      </a>
      <div class="dropdown-menu">
        <a class="dropdown-item" href="#">Link 1</a>
        <a class="dropdown-item" href="#">Link 2</a>
        <a class="dropdown-item" href="#">Link 3</a>
      </div>
    </li>
  </ul>
</nav>
<br>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>