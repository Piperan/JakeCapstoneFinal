<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<meta charset="ISO-8859-1">
<title>3RD PARTY APPROVAL</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
	
<style type="text/css">
footer {
	bottom: 0;
	width: 100%;
	background-color: #333;
	color: #fff;
}
.mid-left { 
            top: 50%; 
            left: 0; 
            transform: translateY(-50%); 
        } 
</style>
</head>
<body>
	<!-- Navigation -->
  <nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
  	 <a class="navbar-brand" href="/"><img src="/../images/pl_logo_j.png" width="50" height="50"/></a> 
      <a class="navbar-brand" href="/">PROJECT LUMEN</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
           <a class="nav-link" href="<c:url value="/user"/>">Local Registration</a>
          </li>
          <c:set var = "result" scope = "session" value = "${username}"/>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownPortfolio" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              ${username}
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownPortfolio">
              <a class="dropdown-item" href="mailto:projectlumenjake@gmail.com">Issues</a>
              <a class="dropdown-item" href="<c:url value="/logout"/>">Logout</a>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- end of navigation -->
	
	 <!-- Page Content -->
  <div class="container">

    <!-- Page Heading/Breadcrumbs -->
    <h1 class="mt-4 mb-3">My Project
      <small>Project Item</small>
    </h1>

    <ol class="breadcrumb">
      <li class="breadcrumb-item">
      <h1 class="my-3">Project Name: ${project.projectName}</h1>
      </li>
      
    </ol>

    <!-- Portfolio Item Row -->
    <div class="row">

      <div class="col-md-8">
      		 <!-- Related Projects Row -->
    <h3 class="my-4">Project Forms</h3>
    <div class="row">
    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
    	<thead>
			<tr>
				<th>File Name</th>
				<th>Completed</th>
			</tr>
		</thead>
	
		<tbody>
			<c:forEach var="forms" items="${projForms}">
				<tr>
					<td>${forms.formName}</td>
					<td>
						<c:choose>
   							<c:when test="${forms.content != null}">  <a href="<c:url value="/getpdf1/${forms.formid}"/>">Download</a> </c:when>    
    						<c:otherwise>NO </c:otherwise>
						</c:choose>
					</td>
				</tr>    
			</c:forEach>
			
		</tbody>
	</table>
	</div>
    <!-- /.row -->
      </div>

      <div class="col-md-4">
        <h3 class="my-3">Project Details</h3>
        <ul>
          <li>Project Description: ${project.projectDesc}</li>
          <li>Project Type: ${project.type}</li>
          <li>Start Date: ${project.startDate}</li>
          <li>End Date: ${project.endDate}</li>
        </ul>
        <h3 class="my-3">Project Manager Details</h3>
        <ul>
          <li>Name: ${userProj.firstName} ${userProj.lastName}</li>
          <li>Email Address: ${userProj.email}</li>
        </ul>
        <br>
       
      </div>
    </div>
    <!-- /.row -->
    
  </div>

<div class="container">
  <div class="row">
    <div class="col text-center">
      <a style="width:60%;margin:auto" 
      href="mailto:${userProj.email}?subject= Additional Forms for ${project.projectName}&body=Hi ${userProj.firstName},%0D%0A%0D%0APlease add the following forms to your project:%0D%0A "
      class="btn btn-secondary btn-lg active" role="button" aria-pressed="true">Request more forms</a>
   <br><br>
    <a  href="<c:url value="/certify/${project.projectId}"/>" style="width:60%;margin:auto" class="btn btn-secondary btn-lg active" role="button" aria-pressed="true" href="">Register Project </a>
    <br><br>
    </div>
  </div>
</div>
	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Project
				Lumen</p>
		</div>
		<!-- /.container -->
	</footer>

</body>
</html>
