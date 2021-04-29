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

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="/resources/js/page.js"></script>

	<link rel="stylesheet" type="text/css" href="/resources/css/style.css">
	<link rel="stylesheet" href="/resources/css/responsive.css">

</head>
<body>

    <div class="header_section">
      <div class="header_left">
        <ul class="navbar">
          <li><button href="#signInModal" class="btn btn-primary btn-sm"data-toggle="modal">Sign In</button></li>
          <li>API</li>
          <li>About</li>
          <li>Contact us</li>
        </ul> 
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
		    
          	<form:form method="post" id="upload" enctype="multipart/form-data">
          	
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
          		
          		<button class="more_bt" id="uploadBtn" type="submit">Load PDF</button>
            </form:form>
          </div>
        </div>
      </div>
      <div class="header_right">
        <img src="/resources/images/banner-img.png" class="banner_img">
      </div>
    </div>
    
<div id="signInModal" class="modal fade">
  <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <ul class="nav nav-tabs md-tabs tabs-2 light-blue darken-3" role="tablist">
            <li class="nav-item">
              <a class="nav-link active" data-toggle="tab" href="#panel7" role="tab">Sign In</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" data-toggle="tab" href="#panel8" role="tab">Sign Up</a>
            </li>
          </ul>
        </div>
        <div class="modal-body">
          <div class="tab-content">

            <div class="tab-pane fade in show active" id="panel7" role="tabpanel">
              <form:form id="signin" method="post">
                  <h3>Sign In form</h3>
                  <i>Please fill in this form to sign in an account!</i>
                  <hr>
                  <div class="form-group">
                    <div class="input-group">
                      <div class="input-group-prepend">
                        <span class="input-group-text">
                          <i class="fa fa-paper-plane"></i>
                        </span>                    
                      </div>
                      <input type="email" class="form-control" name="username" placeholder="Email Address" required="required">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="input-group">
                      <div class="input-group-prepend">
                        <span class="input-group-text">
                          <i class="fa fa-lock"></i>
                        </span>                    
                      </div>
                      <input type="text" class="form-control" name="password" placeholder="Password" required="required">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <button type="submit" id="signInBtn" class="btn btn-primary btn-block btn-lg">Log in</button>
                  </div>
                </form:form>
            </div>
            
            <div class="tab-pane fade" id="panel8" role="tabpanel">
              <form:form id="signup" method="post">
                <h3>Sign Up form</h3>
                <i>Please fill in this form to create an account!</i>
                <hr>
                <div class="form-group">
                  <div class="input-group">
                    <div class="input-group-prepend">
                      <span class="input-group-text">
                        <span class="fa fa-user"></span>
                      </span>                    
                    </div>
                    <input type="text" class="form-control" name="username" placeholder="Username" required="required">
                  </div>
                    </div>
                    <div class="form-group">
                  <div class="input-group">
                    <div class="input-group-prepend">
                      <span class="input-group-text">
                        <i class="fa fa-paper-plane"></i>
                      </span>                    
                    </div>
                    <input type="email" class="form-control" name="email" placeholder="Email Address" required="required">
                  </div>
                    </div>
                <div class="form-group">
                  <div class="input-group">
                    <div class="input-group-prepend">
                      <span class="input-group-text">
                        <i class="fa fa-lock"></i>
                      </span>                    
                    </div>
                    <input type="text" class="form-control" name="password" placeholder="Password" required="required">
                  </div>
                </div>

                <div class="form-group">
                  <label class="form-check-label"><input type="checkbox" required="required"> I accept the <a href="#">Terms of Use</a> &amp; <a href="#">Privacy Policy</a></label>
                </div>
                <div class="form-group">
                  <button type="submit" id="signUpBtn" class="btn btn-primary btn-block btn-lg">Sign Up</button>
                </div>
              </form:form>
            </div>

          </div>
        </div>

        <div class="modal-footer">
          <p>Footer</p>
        </div>
      </div>
    </div>
</div>

</body>
</html>