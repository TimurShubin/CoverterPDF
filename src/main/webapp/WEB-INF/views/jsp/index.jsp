<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	
	<title>Converter PDF to JPEG</title>
	
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css">
	<link rel="stylesheet" href="/resources/css/responsive.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
	<script type="text/javascript">
		$(function() {
		  $('button[type=submit]').click(function(e) {
		    e.preventDefault();
		    //Disable submit button
		    $(this).prop('disabled',true);
		    
		    var form = document.forms[0];
		    var formData = new FormData(form);

		    // Ajax call for file uploaling
		    var ajaxReq = $.ajax({
		      url : '/uploadFile',
		      type : 'POST',
		      data : formData,
		      cache : false,
		      contentType : false,
		      processData : false,
		      xhr: function(){
		        //Get XmlHttpRequest object
		         var xhr = $.ajaxSettings.xhr() ;
		        
		        //Set onprogress event handler 
		         xhr.upload.onprogress = function(event){
		          	var perc = Math.round((event.loaded / event.total * 5) * 100);
		          	$('#progressBar').text(perc + '%');
		          	$('#progressBar').css('width',perc + '%');
		         };
		         return xhr ;
		    	},
		    	beforeSend: function( xhr ) {
		    		//Reset alert message and progress bar
		    		$('#alertMsg').text('');
		    		$('#progressBar').text('');
		    		$('#progressBar').css('width','0%');
		              }
		    });
		  
		    // Called on success of file upload
		    ajaxReq.done(function(msg) {
		      $('#alertMsg').append("<a href='" + msg + "' target='_blank'>Download ZIP</a><br><i>link to download available for 5 minutes</i>");
		      $('input[type=file]').val('');
		      $('button[type=submit]').prop('disabled',false);
		    });
		    
		    // Called on failure of file upload
		    ajaxReq.fail(function(jqXHR) {
		      $('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+
		      		' - '+jqXHR.statusText+')');
		      $('button[type=submit]').prop('disabled',false);
		    });
		  });
		});
	</script>
</head>
<body>

    <div class="header_section">
      <div class="header_left">
        
        <div class="banner_main">
          <h1 class="banner_taital">CONVERTER <br>PDF to JPEG</h1>
          <p class="banner_text">Load your pdf document and get archive with images.</p>
          <div class="btn_main">
          
		    <!-- Bootstrap Progress bar -->
		    <div class="progress">
		      <div id="progressBar" class="progress-bar progress-bar-success" role="progressbar"
		        aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
		    </div>
		
		    <!-- Alert -->
		    <div id="alertMsg" style="color: red;font-size: 18px;"></div>
		    
          	<form:form method="POST" enctype="multipart/form-data" id="form" action="/uploadFile">
          		<input type="file" name="file" id="filename" accept=".pdf" />
          		<button class="more_bt" type="submit">Load PDF</button>
            </form:form>
          </div>
        </div>
      </div>
      <div class="header_right">
        <img src="/resources/images/banner-img.png" class="banner_img">
      </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

</body>
</html>