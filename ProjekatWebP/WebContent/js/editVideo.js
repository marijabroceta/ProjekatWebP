$(document).ready(function(){
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	var visibility = $('#visibility');
	var enableComments = $('#enableComments');
	var enableRating = $('#enableRating');
	var blockLbl = $('#blockLbl');
	var block = $('#block');
	var description = $('#description');
	var submit = $('#submit');
	
	//blockLbl.hide();
	
	console.log(id);
	
	
	$.get('MainVideoServlet',{'id':id},function(data){
		
		console.log(data.user.userName);
		
		$('#login').replaceWith('<a href="LogOutServlet"><span class="glyphicon glyphicon-log-out"></span> LogOut</a>');
		$('#signUp').replaceWith('<a href="UserChannel.html?userName=' + data.user.userName + '"><span>' + data.user.userName + '</span></a>');
		
		console.log(data.user.role);
		if(data.user.role == "ADMIN"){
			blockLbl.show();
		}
		if(data.video.visibility == "PUBLIC"){
			visibility.val('public');
		}else if(data.video.visibility == "PRIVATE"){
			visibility.val('private');
		}else{
			visibility.val('unlisted');
		}
		
		if(data.video.enableComments == true){
			enableComments.val('true');
		}else{
			enableComments.val('false');
		}
		
		if(data.video.enableRating == true){
			enableRating.prop('checked',true);
		}else{
			enableRating.prop('checked',false);
		}
		
		if(data.video.block == true){
			block.prop('checked',true);
		}else{
			block.prop('checked',false);
		}
		
		description.val(data.video.description);
	});
	
	submit.on('click',function(e){
		
		console.log(id);
		var visibilityVal = visibility.val();
		var enableCommentsVal = enableComments.val();
		var enableRatingVal = false;
		
		if(enableRating.is(':checked')){
			enableRatingVal = true;
		}
		console.log(enableRatingVal);
		
		var blockVal = false;
		if(block.is(':checked')){
			blockVal = true;
		}
		console.log(blockVal);
		
		var descriptionVal = description.val();
		var params = {
				'id':id,
				'visibility':visibilityVal,
				'enableComments':enableCommentsVal,
				'enableRating':enableRatingVal,
				'description':descriptionVal,
				'block':blockVal,
				'action':'edit'
		};
		console.log(id);
		$.post('MainVideoServlet',params,function(data){
			if(data.status == "success"){
				var location = "MainVideo.html?id=" + id;
				window.location.replace(location);
			}
		});
		e.preventDefault();
		return false;
	});
});