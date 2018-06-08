$(document).ready(function(){
	var userName = $('#userName');
	var password = $('#password');
	var firstName = $('#firstName');
	var lastName = $('#lastName');
	var email = $('#email');
	var message = $('#message');
	
	$('#submit').on('click',function(event){
		var userNameVal = userName.val();
		console.log(userNameVal);
		var passwordVal = password.val();
		var firstNameVal = firstName.val();
		var lastNameVal = lastName.val();
		var emailVal = email.val();
		
		var formData = new FormData();
		var photo = document.querySelector('input[type=file]').files[0];
		formData.append('photo',photo);
		
		
		if(userNameVal == "" || passwordVal == "" || email==""){
			message.text("Username, password and email are required");
		}
		
		var params = {
				'userName':userNameVal,
				'password':passwordVal,
				'firstName':firstNameVal,
				'lastName':lastNameVal,
				'email':emailVal,
				'photo':photo
		};
		
		console.log(userNameVal);
		console.log(passwordVal);
		console.log(firstNameVal);
		
		$.post('RegisterServlet',params,function(data){
			if(data.status == "exist"){
				message.text("User already exist");
			}else{
				window.location.replace('Index.html');
			}
		});
		event.preventDefault();
		return false;
	});
	
	$('#cancel').on('click',function(e){
		window.location.replace('Index.html');
	});
var trigger = $('.hamburger'),
    
	isClosed = false;

	trigger.click(function () {
		hamburger_cross();      
	});

	function hamburger_cross() {

		if (isClosed == true) {          
      
			trigger.removeClass('is-open');
			trigger.addClass('is-closed');
			isClosed = false;
		} else {   
      
			trigger.removeClass('is-closed');
			trigger.addClass('is-open');
			isClosed = true;
		}
	}

	$('[data-toggle="offcanvas"]').click(function () {
		$('#wrapper').toggleClass('toggled');
	});
	
});