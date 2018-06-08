$(document).ready(function(){
	var userName = window.location.search.slice(1).split('&')[0].split('=')[1];
	var fName = $('#fname');
	var lName = $('#lname');
	var password = $('#password');
	var description = $('#description');
	var role = $('#role');
	var blocked = $('#blocked');
	var submit = $('#submit');
	var roleLbl = $('#rolelbl');
	var blockLbl = $('#blockLbl');
	
	role.hide();
	blocked.hide();
	
	roleLbl.hide();
	blockLbl.hide();
	
	$.get('UserServlet',{'userName':userName},function(data){
		$('#login').replaceWith('<a href="LogOutServlet"><span class="glyphicon glyphicon-log-out"></span> LogOut</a>');
		$('#signUp').replaceWith('<a href="UserChannel.html?userName=' + data.user.userName + '"><span>' + data.user.userName + '</span></a>');
		if(data.user.role == "ADMIN"){
			role.show();
			blocked.show();
			roleLbl.show();
			blockLbl.show();
			password.hide();
		}
		fName.val(data.owner.firstName);
		lName.val(data.owner.lastName);
		password.val(data.owner.password);
		description.val(data.owner.channelDescription);
		if(data.owner.role == "USER"){
			role.val('user');
		}else{
			role.val('admin');
		}
		
		if(data.owner.blocked == true){
			blocked.prop('checked',true);
		}else{
			blocked.prop('checked',false);
		}
	});

	submit.on('click',function(e){
		var fNameVal = fName.val();
		console.log(fNameVal);
		var lNameVal = lName.val();
		console.log(lNameVal);
		var passwordVal = password.val();
		console.log(passwordVal);
		var descriptionVal = description.val();
		var roleVal = role.val();
		console.log(roleVal);
		var blockedVal = false;
		if(blocked.is(':checked')){
			blockedVal = true;
		}
		var params = {
				'fName': fNameVal,
				'lName': lNameVal,
				'password': passwordVal,
				'description':descriptionVal,
				'role':roleVal,
				'blocked':blockedVal,
				'action':'edit',
				'userName':userName
		};
		
		$.post('UserServlet',params,function(data){
			if(data.status == "success"){
				var location = "UserChannel.html?userName=" + userName;
				window.location.replace(location);
			}
		});
		e.preventDefault();
		return false;
	});
	
	
});

function showPass(){
	var password = document.getElementById("password");
	if(password.type === "password"){
		password.type = "type";
	}else{
		password.type = "password";
	}
}