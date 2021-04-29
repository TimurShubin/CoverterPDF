$(document).ready(function() {
	$("#uploadBtn").click(function(e) {
			e.preventDefault();
			$(this).prop('disabled',true);
	
			let from = document.getElementById('');
	
			var form = document.getElementById('upload');
			var formData = new FormData(form);
	
			var ajaxReq = $.ajax({
				url : '/uploadFile',
				type : 'post',
				data : formData,
				cache : false,
				contentType : false,
				processData : false,
				xhr: function(){
					var xhr = $.ajaxSettings.xhr() ;
	
					xhr.upload.onprogress = function(event){
						var perc = Math.round((event.loaded / event.total * 5) * 100);
						$('#progressBar').text(perc + '%');
						$('#progressBar').css('width',perc + '%');
					};
					
					return xhr ;
				},
				beforeSend: function( xhr ) {
					$('#alertMsg').text('');
					$('#progressBar').text('');
					$('#progressBar').css('width','0%');
				}
			});
	
			ajaxReq.done(function(msg) {
				$('#alertMsg').append("<a href='" + msg + "' target='_blank'>Download ZIP</a><br><i>link to download available for 5 minutes</i>");
				$('input[type=file]').val('');
				$('button[type=submit]').prop('disabled',false);
			});
	
			ajaxReq.fail(function(jqXHR) {
				$('#alertMsg').text(jqXHR.responseText + '(' + jqXHR.status +
						' - ' + jqXHR.statusText + ')');
				$('button[type=submit]').prop('disabled',false);
			});
			function updateImageDisplay() {
				
				while(preview.firstChild) {
				  preview.removeChild(preview.firstChild);
				}
		
				var curFile = input.value;
				var filename = '';
		
				if (curFile) {
				    let startIndex = (curFile.indexOf('\\') >= 0 ? curFile.lastIndexOf('\\') : curFile.lastIndexOf('/'));
				    let filename = curFile.substring(startIndex);
				    if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
				        filename = filename.substring(1);
				    }
				}
		
				console.log(filename);
		
				if(filename.length === 0) {
				      let node = document.createTextNode('Choose a file...');
				      preview.appendChild(node);
				} else {
				      let node = document.createTextNode(filename);
				      preview.appendChild(node);
				}
			}
	});
	
	$("#signin button[type='submit']").click(function(e) {
			e.preventDefault();
			$(this).prop('disabled',true);

			const form = new FormData(document.forms.signin);
			const value = Object.fromEntries(form.entries());
			console.log(JSON.stringify(value));

			var ajaxReq = $.ajax({
				url : '/signin',
				type : 'post',
				data : JSON.stringify(value),
				dataType: "json",
                contentType: "application/json",
			});

			ajaxReq.done(function(msg) {
				$('#alertMsg').append("User successfully login!");
			});

			ajaxReq.fail(function(jqXHR) {
				$('#alertMsg').text(jqXHR.responseText + '(' + jqXHR.status + ' - ' + jqXHR.statusText+')');
			});
	});
	
	$("#signup button[type='submit']").click(function(e) {
			e.preventDefault();
			$(this).prop('disabled',true);

			const form = new FormData(document.forms.signup);
			const value = Object.fromEntries(form.entries());
			console.log(JSON.stringify(value));
			
			var ajaxReq = $.ajax({
				url : '/signup',
				type : 'post',
				data : JSON.stringify(value),
				dataType: "json",
                contentType: "application/json",
			});

			ajaxReq.done(function(msg) {
				$('#alertMsg').append("User successfully created!");
			});

			ajaxReq.fail(function(jqXHR) {
				$('#alertMsg').text(jqXHR.responseText + '(' + jqXHR.status + ' - ' + jqXHR.statusText+')');
			});
	});
	
});