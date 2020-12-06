<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    
    <sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
    </sec:authorize>
    
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
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>

<body>


<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <!-- Brand -->
  <a class="navbar-brand" href="/">Blog</a>


  <!-- Links -->
  <c:choose>
  <c:when test="${empty principal}">
  <ul class="navbar-nav">
    <li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a></li>
    <li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li>
	</ul>
  </c:when>
  
  <c:otherwise>
  <ul class="navbar-nav">
    <li class="nav-item"><a class="nav-link" href="/board/saveForm">글쓰기</a></li>
    <li class="nav-item"><a class="nav-link" href="/user/updateForm">회원정보</a></li>
	 <li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
    </ul>
  </c:otherwise>  
  </c:choose>


	
    <ul>
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


</body>
</html>