<%-- 
    Document   : main
    Created on : Feb 3, 2016, 9:19:13 AM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>


  </head>
  
  <body class="hold-transition skin-blue sidebar-mini">
      <!-- header -->
      <%@include file="header.jsp"%>
      <!-- Left side column. contains the logo and sidebar -->
      <%@include file="sidemenu.jsp"%>
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Mass Upload of Employee records
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div id="inner">
	     <form id="myForm" action="uploadEmp" method="post" enctype="multipart/form-data">
		Files to upload (Only in CSV):
		<br/>
		<input type="file" size="50" name="empFile"/>
               
		<br/>
		<button class="btn btn-primary"  type="submit" id="upload" name="upload">Submit</button>
 	    </form>
            </div><!-- /.row -->
          <!-- Main row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->

    
      <%@include file="footer.jsp" %>
  </body>
</html>