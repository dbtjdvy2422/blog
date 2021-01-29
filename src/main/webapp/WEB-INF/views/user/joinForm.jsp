<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file= "../layout/header.jsp"  %>


<form class="was-validated">
  <div class="form-group">
    <label for="username">Username:</label>
    <input type="text" class="form-control" id="username" placeholder="Enter username" name="username"required>
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
  
  <div class="form-group">
    <label for="email">email:</label>
    <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" required>
    <div class="valid-feedback">Valid.</div>
    <div class="invalid-feedback">Please fill out this field.</div>
  </div>
  

</form>

<button id="btn_save" class="btn btn-primary">Submit</button>

<script src="/js/user.js"></script>



  <%@include file= "../layout/footer.jsp"  %>


</body>
</html>