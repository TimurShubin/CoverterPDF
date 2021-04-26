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
          	
	            <div class="box">
	              <input type="file" name="file" id="file" class="custom-file-input" accept=".pdf">
	              <label for="file">
	              	<figure>
	              		<svg xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 20 17">
	              			<path d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z"></path>
	              		</svg>
	              	</figure>
					<span class="preview">Choose a file…</span></label>  
	            </div>
          		
          		<button class="more_bt" type="submit">Load PDF</button>
            </form:form>
          </div>
        </div>
      </div>
      <div class="header_right">
        <img src="/resources/images/banner-img.png" class="banner_img">
      </div>
    </div>
    
    
	<script type="text/javascript" src="/resources/js/page.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

</body>
</html>