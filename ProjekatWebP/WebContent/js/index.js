$(document).ready(function(){
	var content = $('.row');
	
	$.get('VideosServlet',{},function(data){
		console.log("Stigao odgovor");
		
		for(it in data.videos){
			content.append(
				'<div class="col-sm-2 video">' + 
					'<div class="picture">' +
						'<p><img src=" ' + data.videos[it].videoPicture + '"></p>' + 
						'<h5><a href="MainVideo.html?id=' + data.videos[it].id + '">' + data.videos[it].videoName + '</a></h5>' +
						'<p class="user"><a href="UserChannel.html?userName=' + data.videos[it].ownerOfVideo.userName + '">' + data.videos[it].ownerOfVideo.userName + '</a></p>' +
						'<p class="date">Datum: ' + data.videos[it].postDate + '</p>' +
						'<p class="rating">' + data.videos[it].numberOfViews + ' ' + ' pregleda</p>' +
					'</div>' +
				'</div>'
			);
		}
		if(data.user != null){
			$('#login').replaceWith('<a href="LogOutServlet"><span class="glyphicon glyphicon-log-out"></span> LogOut</a>')
			$('#signUp').replaceWith('<a href="UserChannel.html?userName=' + data.user.userName + '"><span>' + data.user.userName + '</span></a>')
		}
	});
	
	$('#searchText').keyup(function(event){
		var input = $('#searchText').val().toUpperCase();
		console.log(input);
		$(".video").each(function(){
			if($(this).html().toUpperCase().includes(input)){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
		event.preventDefault();
		return false;
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


	
