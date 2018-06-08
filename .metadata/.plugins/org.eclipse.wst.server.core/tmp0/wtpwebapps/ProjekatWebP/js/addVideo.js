$(document).ready(function(){
	var userName = window.location.search.slice(1).split('&')[0].split('=')[1];
	var url = $('#url');
	var name = $('#name');
	var visibility = $('#visibility');
	var enableComments = $('#enableComments');
	var enableRating = $('#enableRating');
	var description = $('#description');
	var message=$('#message');
	var submit = $('#submit');
	
	$('#login').replaceWith('<a href="LogOutServlet"><span class="glyphicon glyphicon-log-out"></span> LogOut</a>')
	$('#signUp').replaceWith('<a href="UserChannel.html?userName=' + userName + '"><span>' + userName + '</span></a>')
	
	submit.on('click',function(event){
		var urlVal = url.val();
		var nameVal = name.val();
		var visibilityVal = visibility.val();
		var enableCommentsVal = enableComments.val();
		var enableRatingVal = enableRating.val();
		var descriptionVal = description.val();
		if(urlVal == "" || nameVal == ""){
			message.text("Field url and name must not be empty!");
			return false;
		}
		var params = {
				'videoUrl':urlVal,
				'videoName':nameVal,
				'visibility':visibilityVal,
				'enableComments':enableCommentsVal,
				'enableRating':enableRatingVal,
				'description':descriptionVal,
				'action':'add'
		};
		
		console.log(urlVal);
		console.log(nameVal);
		console.log(visibilityVal);
		console.log(enableCommentsVal);
		console.log(enableRatingVal);
		console.log(descriptionVal);
		
		$.post('MainVideoServlet',params,function(data){
			if(data.status == "success"){
				alert("You added a video!")
			}
		});
		event.preventDefault();
		return false;
	});
});