$(document).ready(function(){
	var userNameInput = $('#korIme');
	var passwordInput = $('#pass');
	var messageParagraph = $('#message');
	
	$('#submit').on('click',function(event){
		var userName = userNameInput.val();
		var password = passwordInput.val();
		
		
		console.log('userName: ' + userName);
		console.log('password: ' + password);
		
		$.post('LoginServlet',{'userName':userName,'password': password},function(data){
			console.log('stigao odgovor');
			console.log(data);
			
			if(data.status == 'success'){
				window.location.replace('UserChannel.html?userName=' + userName);
			}
			if(data.status == 'failure'){
				messageParagraph.text("Uneli se pogresno korisnicko ime i/ili lozinku");	
			}
		});
		
		console.log('poslat zahtev');
		
		event.preventDefault();
		return false;
	});
});