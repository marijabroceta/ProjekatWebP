$(document).ready(function(){
	var users = $('#users');
	var desc = $('#sortDesc');
	var asc = $('#sortAsc');
	
	$.get('AdminServlet',{},function(data){
		$('#login').replaceWith('<a href="LogOutServlet"><span class="glyphicon glyphicon-log-out"></span> LogOut</a>')
		$('#signUp').replaceWith('<a href="UserChannel.html?userName=' + data.loggedIn.userName + '"><span>' + data.loggedIn.userName + '</span></a>')
		
		for(i in data.users){
			users.append(
				'<div class="col-lg-2 col-sm-4 col-md-4 user" id="' + data.users[i].userName + '">' +
					'<div id="userImg">' +
						'<img src="images/' + data.users[i].userName + '.jpg" align="middle">' +
					'</div>' +
					'<div class="user-info">' +
						'<p id="username">' + data.users[i].userName + '</p>' + 
						'<p id="fName">' + data.users[i].firstName + '</p>' +
						'<p id="lName">' + data.users[i].lastName + '</p>' + 
						'<p id="email">' + data.users[i].email + '</p>' + 
						'<p id="role">' + data.users[i].role + '</p>' + 
						'<p id = "del">' +
							'<button id="delete" value="' + data.users[i].userName + '">Delete</button>' + 
						'</p>' +
					'</div>' +
				'</div>'	
			);
		}
		
		$('button').on('click',function(event){
			var userName = $(this).val();
			console.log(userName);
			var confirmation = confirm("Are you sure?");
			if(confirmation){
				$.post('AdminServlet',{'userName':userName,'action':"delete"},function(data){
					var id = "#" + userName;
					if(userName == data.loggedIn.userName){
						$(id).remove();
						$.get('LogOutServlet',{},function(){
							
						});
					}
					$(id).remove();
				});
			}
			event.preventDefault();
			return false;
		});
		
	});
	
	$('#sort').on('click',function(event){
		var column = $('#sortUsers').val();
		
		console.log(ascDesc);
		if(desc.is(':checked')){
			var ascDesc=desc.val();
		}
		$.post('AdminServlet',{'action':"sort",'ascDesc':ascDesc,'column':column},function(data){
			console.log(data.status);
			if(data.status == "success"){
				users.empty();
				for(i in data.users){
					users.append(
						'<div class="col-lg-2 col-sm-4 col-md-4 user" id="' + data.users[i].userName + '">' +
							'<div id="userImg">' +
								'<img src="images/' + data.users[i].userName + '.jpg" align="middle">' +
							'</div>' +
							'<div class="user-info">' +
								'<p id="username">' + data.users[i].userName + '</p>' + 
								'<p id="fName">' + data.users[i].firstName + '</p>' +
								'<p id="lName">' + data.users[i].lastName + '</p>' + 
								'<p id="email">' + data.users[i].email + '</p>' + 
								'<p id="role">' + data.users[i].role + '</p>' + 
								'<p id = "del">' +
									'<button id="delete" value="' + data.users[i].userName + '">Delete</button>' + 
								'</p>' +
							'</div>' +
						'</div>'	
					);
				}
			}
		});
		console.log(column);
		console.log(ascDesc);
		event.preventDefault();
		return false;
	});
	
	$('#searchText').keyup(function(event){
		var input = $('#searchText').val().toUpperCase();
		$(".user").each(function(){
			if($(this).html().toUpperCase().includes(input)){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
		event.preventDefault();
		return false;
	});
});

