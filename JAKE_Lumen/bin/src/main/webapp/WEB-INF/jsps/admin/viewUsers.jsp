<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <meta charset="ISO-8859-1">
   <link href="/css/sb-admin.css" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<title>Project Lumen - Users</title>
<style type="text/css">
footer {
  position: absolute;
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
  	 <a class="navbar-brand" href="#"><img src="../images/pl_logo_j.png" width="50" height="50"/></a> 
      <a class="navbar-brand" href="index.html">PROJECT LUMEN</a>
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
<!-- End of Navigation -->
 <div id="content-wrapper">
<br><br><br>
      <div class="container-fluid">
        <!-- Breadcrumbs-->
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="#">Dashboard</a>
          </li>
          <li class="breadcrumb-item active">Users table</li>
        </ol>

        <!-- DataTables Example -->
        <div class="card mb-3">
          <div class="card-header">
            <i class="fas fa-table"></i>
            Data Table Example</div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
              <thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Username</th>
					<th>Address</th>
					<th>Edit</th>
					<!-- <th>Delete</th>  -->
				</tr>
			</thead>
			  <tfoot>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Username</th>
					<th>Address</th>
					<th>Edit</th>
					<!-- <th>Delete</th>  -->
				</tr>
			</tfoot>
			<tbody>
<c:forEach var="User" items="${users}" >
<tr>
	<td>${User.firstName}</td>
	<td>${User.lastName}</td>
	<td>${User.username}</td>
	<td>${User.address}</td>
	<td><a class="anchor" href="<c:url value="/admin/editUser/${User.userid}"/>">Edit</a></td>
	<!--<td><a class="anchor" href="<c:url value="/admin/deleteEnabledUser/${User.userid}"/>">Delete</a></td>-->
</tr>
</c:forEach>
</tbody>
</table>
 </div>
          </div>
          <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
        </div>
</div>
      <!-- /.container-fluid -->

      <!-- Sticky Footer -->
      <footer class="sticky-footer">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright © Your Website 2019</span>
          </div>
        </div>
      </footer>

  </div>
  <!-- /#wrapper -->
</body>
</html>