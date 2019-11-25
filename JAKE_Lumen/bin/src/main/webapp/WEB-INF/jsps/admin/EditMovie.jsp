<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <meta charset="ISO-8859-1">
  <title>Edit User</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<style type="text/css">
footer {
  bottom: 0;
  width: 100%;
  background-color: #333;
  color:#fff;
}
</style>
</head>
<body>
    
  <!-- Navigation -->
  <nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
  	 <a class="navbar-brand" href="/"><img src="../../images/pl_logo_j.png" width="50" height="50"/></a> 
      <a class="navbar-brand" href="/">PROJECT LUMEN</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
           <a class="nav-link" href="<c:url value="/admin"/>">Home</a>
          </li>
          <li class="nav-item">
          <a class="nav-link" href="<c:url value="/register"/>">Register User</a>
          </li>
          <c:set var = "result" scope = "session" value = "${username}"/>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownPortfolio" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              ${username}
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownPortfolio">
              <a class="dropdown-item" href="portfolio-1-col.html">My Profile</a>
              <a class="dropdown-item" href="mailto:bayanie@sheridancollege.ca">Issues</a>
              <a class="dropdown-item" href="<c:url value="/logout"/>">Logout</a>
            </div>
          </li>
        
        </ul>
      </div>
    </div>
  </nav>
  <!-- end of navigation -->
	 <div id="content-wrapper">
<br><br><br>
      <div class="container-fluid">
        <!-- Breadcrumbs-->
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="<c:url value="/admin"/>">Dashboard</a>
          </li>
          <li class="breadcrumb-item active">Editing User</li>
        </ol>
        </div>
        </div>
    <div class="container">
  
  			<!-- bootstrap form -->
	 <c:url var="url" value="/EditUser"></c:url>
	<form:form action="${url}" method="post" modelAttribute="user"> 

	<div class="form-group"> <!-- City-->
		<label for="city_id" class="control-label">Username</label>
		<form:input path="username" class="form-control" id="city_id" name="city" placeholder="New Project is something special."></form:input>
	</div>	
	
	<div class="form-group"> <!-- Full Name -->
		<label for="full_name_id" class="control-label">First Name:</label>
		<form:input path="firstName" class="form-control" id="full_name_id" placeholder="New Project"></form:input>
	</div>	

	<div class="form-group"> <!-- Street 1 -->
		<label for="street1_id" class="control-label">Last Name:</label>
		<form:input path="lastName" class="form-control" id="street1_id" name="street1" placeholder="12/12/2010"></form:input>
	</div>					
							
	<div class="form-group"> <!-- Street 2 -->
		<label for="street2_id" class="control-label">Address:</label>
		 <form:input path="address"  class="form-control" id="street2_id" name="street2" placeholder="12/12/2012" ></form:input> 
	</div>	
	
	<input type="hidden" name="password" value="${user.password}">								
	<input type="hidden" name="userid" value="${user.userid}">								
	
	<div class="form-group"> <!-- Submit Button -->
		 <input type="submit" class="btn btn-primary" value="Edit User"> 
	</div> 
	    
</form:form>	
        </div>
	

	
<!-- end of bootstrap form -->
  <!-- /.container -->
 


 <!-- Footer -->
  <footer class="py-5 bg-dark">
    <div class="container">
      <p class="m-0 text-center text-white">Copyright &copy; Project Lumen</p>
    </div>
    <!-- /.container -->
  </footer>
</body>
</html>