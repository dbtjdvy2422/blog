<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file= "../layout/header.jsp"  %>


<form action="/auth/loginProc" method="post" class="was-validated">
  <div class="form-group">
    <label for="username">Username:</label>
    <input type="text" class="form-control" id="username" placeholder="Enter username" name="username" required>
    <input type="button" class="btn btn-primary" id="temp" value="증복확인">
    <div class="text-center small mt-2" id="checkMsg" style="color: red"></div>
    <div class="valid-feedback">Valid.</div>
    <div class="invalid-feedback">Please fill out this field.</div>
  </div>
  <div class="form-group">
    <label for="password">Password:</label>
    <input type="password" class="form-control" id="password" placeholder="Enter password" name="password" required>
    <div class="valid-feedback">Valid.</div>
    <div class="invalid-feedback">Please fill out this field.</div>
  </div>
  
  <div class="form-group form-check">
  <label class="form-check-label">
  <input name="remember" class="form-check-input" type="checkbox">Remember me
  </label>
  </div>
  
  <button id="btn_login" class="btn btn-primary">Submit</button>
  <a href="https://kauth.kakao.com/oauth/authorize?client_id=57ea8b244103264c206819e7f856ddcc&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_button.png"/></a>
</form>




  <%@include file= "../layout/footer.jsp"  %>


</body>
</html>