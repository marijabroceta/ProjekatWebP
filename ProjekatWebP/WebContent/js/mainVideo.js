$(document).ready(function(){
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(id);
	var video = $('#videoFrame');
	var videoName = $('#videoName');
	var videoViews = $('#videoViews');
	var numberOfLikes = $('#likeVideoNumber');
	var numberOfDislikes = $('#dislikeVideoNumber');
	var userName = $('#userName');
	var date = $('#date');
	var description = $('#description');
	var subscribe = $('#subscribeBtn');
	var unsubscribe = $('#unsubscribeBtn');
	var comments = $('.comments');
	var submitComm = $('#komentarisi');
	var cancelComm = $('#odustani');
	var contentComm = $('#textArea');
	var addComment = $('.addComm');
	var likeVideo = $('#likeVideoBtn');
	var dislikeVideo = $('#dislikeVideoBtn');
	var userPhoto = $('#userImage');
	var desc = $('#sortDesc');
	var asc = $('#sortAsc');
	var commentLikeNum = $('#commLikeNum');
	var commentDislikeNum = $('#commDislikeNum');
	var userComImage = $('#userComImage');
	
	var deleteVideo = $('#deleteVideo');
	var subscribed = false;
	
	
	deleteVideo.hide();
	unsubscribe.hide();
	
	$('.overlay').hide();
	
	

	
	
	$.get('MainVideoServlet',{'id':id},function(data){
		if(data.user != null){
			$('#login').replaceWith('<a href="LogOutServlet"><span class="glyphicon glyphicon-log-out"></span> LogOut</a>')
			$('#signUp').replaceWith('<a href="UserChannel.html?userName=' + data.user.userName + '"><span>' + data.user.userName + '</span></a>')
			console.log(data.user.userName);
		}
		console.log(data.video);
		video.attr('src',data.video.videoUrl + '?rel=0&autoplay=1');
		videoName.text(data.video.videoName);
		videoViews.text(data.video.numberOfViews + " " + "pregleda.");
		numberOfLikes.text(data.video.numberOfLikes);
		numberOfDislikes.text(data.video.numberOfDislikes);
		userName.text(data.video.ownerOfVideo.userName);
		userName.attr("href",'UserChannel.html?userName=' + data.video.ownerOfVideo.userName);
		date.text(data.video.postDate);
		description.text(data.video.description);
		var src = 'images/' + data.video.ownerOfVideo.userName + '.jpg';
		userPhoto.attr('src',src);
		if(data.user != null){
			userComImage.attr('src','images/' + data.user.userName + '.jpg');
		}
		
		console.log(src);
		
		for(it in data.comments){
			if(data.comments[it].ownerOfComment != null){
				comments.append(
						'<div class="col-md-12 col-sm-12 comm" id="' + data.comments[it].id + '">' + 
							'<div id="ownerComm">' +
								'<img src="images/' + data.comments[it].ownerOfComment.userName + '.jpg" id="userImgComm">' + 
								'<a href="UserChannel.html?userName=' + data.comments[it].ownerOfComment.userName + '" id="userName">' + data.comments[it].ownerOfComment.userName + '</a>' +
							'</div>' +
							'<div class="likeDislike">' +
								'<div id="dislikeComm">' +
									'<button id="commDislikeBtn" name="dislike" value="' + data.comments[it].id + '"><img src="images/like.svg" id="dislike"></button>' + 
									'<h6 id="commDislikeNum" class="' + data.comments[it].id +'">' + data.comments[it].numberOfDislikes + '</h6>' + 
								'</div>' +
								'<div id="likeComm">' +
									'<button id="commLikeBtn" name="like" value="' + data.comments[it].id + '"><img src="images/like.svg" id="like"></button>' + 
									'<h6 id="commLikeNum" class="' + data.comments[it].id + '">' + data.comments[it].numberOfLikes + '</h6>' + 
								'</div>' +
							'</div>' + 
							'<h6 id="date">' + data.comments[it].date + '</h6>' + 
							'<p id="commContent">' + data.comments[it].content + '</p>' + 
							'<input type="button" id="editComm" class="' + data.comments[it].ownerOfComment.userName + '" name="' + data.comments[it].id + '" value="Edit">' + 
							'<input type="button" id="deleteComm" class="' + data.comments[it].ownerOfComment.userName + '" name="' + data.comments[it].id + '" value="Delete">' + 
						'</div>'							
				);
				
			}
			
			if(data.user != null){
				if(data.user.role == "ADMIN"){
					continue;
				}
				console.log(data.comments[it].ownerOfComment.userName);
				console.log(data.user.userName);
				if(data.comments[it].ownerOfComment.userName != data.user.userName){
					var userN = 'input[type=button]' + '.' + data.comments[it].ownerOfComment.userName;
					
					$(userN).hide();
					console.log(userN);
				}
			}else{
				$('input[type=button]').hide();
			}
		}
		
		
		
		if(data.video.blocked == true || data.video.ownerOfVideo.blocked == true){
			$('.videoYouTube').hide();
			$('.komentari').hide();
			$('.overlay').fadeIn();
			video.attr("src","");
		}
		
		if(data.video.blocked == true || data.video.ownerOfVideo.blocked == true){
			if(data.user != null){
				if(data.user.userName == data.video.ownerOfVideo.userName || data.user.role == "ADMIN"){
					$('.videoYouTube').show();
					$('.komentari').show();
					$('.overlay').hide();
					video.attr("src",data.video.videoUrl + "?rel=0&autoplay=1");
				}
			}
		}
		
		$('#sort').on('click',function(e){
			var column = $('#sortSelect').val();
			var ascDesc = asc.val();
			if(desc.is(':checked')){
				ascDesc = desc.val();
			}
			console.log(column);
			console.log(ascDesc);
			$.get('CommentServlet',{'id':id,'ascDesc':ascDesc,'column':column},function(data){
				if(data.status == "success"){
					comments.empty();
					for(it in data.comments){
						if(data.comments[it].ownerOfComment != null){
							comments.append(
									'<div class="col-md-12 col-sm-12 comm" id="' + data.comments[it].id + '">' +
										'<div id="ownerComm">' +
											'<img src="images/' + data.comments[it].ownerOfComment.userName + '.jpg" id="userImgComm">' + 
											'<a href="UserChannel.html?userName=' + data.comments[it].ownerOfComment.userName + '" id="userName">' + data.comments[it].ownerOfComment.userName + '</a>' +
										'</div>' +
										'<div class="likeDislike">' +
											'<div id="dislikeComm">' +
												'<button id="commDislikeBtn" name="dislike" value="' + data.comments[it].id + '"><img src="images/like.svg" id="dislike"></button>' + 
												'<h6 id="commDislikeNum" class="' + data.comments[it].id +'">' + data.comments[it].numberOfDislikes + '</h6>' + 
											'</div>' +
											'<div id="likeComm">' +
												'<button id="commLikeBtn" name="like" value="' + data.comments[it].id + '"><img src="images/like.svg" id="like"></button>' + 
												'<h6 id="commLikeNum" class="' + data.comments[it].id + '">' + data.comments[it].numberOfLikes + '</h6>' + 
											'</div>' +
										'</div>' +
										'<h6 id="date">' + data.comments[it].date + '</h6>' + 
										'<p id="commContent">' + data.comments[it].content + '</p>' + 
										'<input type="button" id="editComm" class="' + data.comments[it].ownerOfComment.userName + '" name="' + data.comments[it].id + '" value="Edit">' + 
										'<input type="button" id="deleteComm" class="' + data.comments[it].ownerOfComment.userName + '" name="' + data.comments[it].id + '" value="Delete">' + 
									'</div>'
							);
						}
						
						if(data.user != null){
							if(data.user.role == "ADMIN"){
								continue;
							}
							if(data.comments[it].ownerOfComment.userName != data.user.userName){
								var userN = 'input[type=button]' + '.' + data.comments[it].ownerOfComment.userName;
								$(userN).hide();
								console.log(userN);
							}
						}else{
							$('input[type=button]').hide();
						}
					}
				}
			});
			e.preventDefault();
			false;
		});
		
		if(data.video.enableComments == false){
			addComment.replaceWith('<h3 id="commDisabled">Comments are disabled for this video</h3>');
			comments.hide();
			$('.collapsible').hide();
		}
		
		if(data.video.enableRating == false){
			numberOfLikes.hide();
			numberOfDislikes.hide();
		}
		
		if(data.status == "visitor" || data.user.blocked == true){
			addComment.replaceWith('<h3 id="commDisabled">You are not <a href="Login.html"> logged in</a> or you are blocked</h3>');
			subscribe.hide();
			
		}
		
		if(data.user.userName != data.video.ownerOfVideo.userName){
			if(data.video.visibility == "PRIVATE"){
				$('.videoYouTube').hide();
				$('.komentari').hide();
				$('.overlay').fadeIn();
				video.attr("src","");
			}
		}
		
		
		if(data.user != null){
			if(data.user.userName == data.video.ownerOfVideo.userName || data.user.role == "ADMIN"){
				comments.show();
				numberOfLikes.show();
				numberOfDislikes.show();
				likeVideo.show();
				dislikeVideo.show();
			}
			
			if(data.video.enableComments == true && data.user.blocked == false){
				addComment.show();
				submitComm.on('click',function(e){
					var content = contentComm.val();
					console.log(data.video.id);
					$.post('CommentServlet',{'content':content,'ownerOfComment':data.user.userName,'video':data.video.id,'action':"add"},function(data){
						if(data.action == "success"){
							comments.append(
									'<div class="col-md-12 col-sm-12 comm" id="' + data.id + '">' +
										'<div id="ownerComm">' +
											'<img src="images/' + data.ownerOfComment + '.jpg" id="userImgComm">' + 
											'<a href="UserChannel.html?userName=' + data.ownerOfComment + '" id="userName">' + data.ownerOfComment + '</a>' +
										'</div>' +
										'<div class="likeDislike">' +
											'<div id="dislikeComm">' +
												'<button id="commDislikeBtn" name="dislike" value="' + data.id + '"><img src="images/like.svg" id="dislike"></button>' + 
												'<h6 id="commDislikeNum" class="' + data.id +'">' + data.numberOfDislikes + '</h6>' + 
											'</div>' +
											'<div id="likeComm">' +
												'<button id="commLikeBtn" name="like" value="' + data.id + '"><img src="images/like.svg" id="like"></button>' + 
												'<h6 id="commLikeNum" class="' + data.id + '">' + data.numberOfLikes + '</h6>' + 
											'</div>' +
										'</div>' +
										'<h6 id="date">' + data.date + '</h6>' + 
										'<p id="commContent">' + data.content + '</p>' + 
										'<input type="button" id="editComm" class="' + data.ownerOfComment + '" name="' + data.id + '" value="Edit">' + 
										'<input type="button" id="deleteComm" class="' + data.ownerOfComment + '" name="' + data.id + '" value="Delete">' + 
									'</div>'
							);
						}
					});
					e.preventDefault();
					return false;
				});
			}
			if(data.user.blocked == false){
				$('input[type=button]#deleteComment').on('click',function(e){
					var id = $(this).attr('name');
					console.log(id);
					var confirmation = confirm("Are you sure?");
					if(confirmation){
						$.post('CommentServlet',{'id':id,'action':"delete"},function(data){
							if(data.status == "success"){
								var forRemove = '.comm#' + id;
								console.log(forRemove);
								$(forRemove).remove();
							}
						});
					}
					e.preventDefault();
					return false;
				});
				
				$('input[type=button]#editComm').on('click',function(e){
					var id = $(this).attr('name');
					console.log(id);
					var choice = '#' + id + ' #commContent';
					var dateChoice = '#' + id + ' #date';
					console.log(choice);
					var oldContent = $(choice).text();
					console.log(oldContent);
					$('#textAreaEdit').val(oldContent);
					$('.editComment').fadeIn();
					
					$('.editComment #editCommSubmit').on('click',function(e){
						var content = $('#textAreaEdit').val();
						console.log(content);
						$.post('CommentServlet',{'id':id,'action':"edit",'content': content},function(data){
							if(data.status == "success"){
								console.log(status);
								var oldContent = $(choice).text(content);
								var oldDate = $(dateChoice).text(data.newDate);
								console.log(oldContent);
								console.log(oldDate);
								$('.editComment').fadeOut();
							}
						});
						e.preventDefault();
						return false;
					});
					
					$('.editComment #cancel').on('click',function(e){
						$('.editComment').fadeOut();
						
						e.preventDefault();
						return false;
					});
					e.preventDefault();
					return false;
				});
				
				likeVideo.on('click',function(e){
					$.get('LikeDislikeVideoServlet',{'id':data.video.id},function(data){
						numberOfLikes.text(data.likeNumVideo);
						numberOfDislikes.text(data.dislikeNumVideo);
					});
					e.preventDefault();
					return false;
				});
				dislikeVideo.on('click',function(e){
					$.post('LikeDislikeVideoServlet',{'id':data.video.id},function(data){
						numberOfDislikes.text(data.dislikeNum);
						numberOfLikes.text(data.likeNum);
					});
					e.preventDefault();
					return false;
				});
				
				
				$('button#commLikeBtn').on('click',function(e){
					var id = $(this).val();
					$.get('LikeDislikeCommentServlet',{'id':id},function(data){
						var getLike = "#commLikeNum." + id;
						var getDislike = "#commDislikeNum." + id;
						console.log(getLike);
						$(getLike).text(data.likeNum);
						$(getDislike).text(data.dislikeNum);
					});
					e.preventDefault();
					return false;
				});
				
				$('button#commDislikeBtn').on('click',function(e){
					var id = $(this).val();
					$.post('LikeDislikeCommentServlet',{'id':id},function(data){
						var getLike = "#commLikeNum." + id;
						var getDislike = "#commDislikeNum." + id;
						$(getDislike).text(data.dislikeNum);
						$(getLike).text(data.likeNum);
					});
				});
				
				/*$('button').on('click',function(e){
					var name = $(this).attr("name");
					var id = $(this).val();
					console.log(id);
					
					if(name == "like"){
						$.get('LikeDislikeCommentServlet',{'id':id},function(data){
							var getLike = "#commLikeNum." + id;
							var getDislike = "#commDislikeNum." + id;
							console.log(getLike);
							$(getLike).text(data.likeNum);
							$(getDislike).text(data.dislikeNum);
						});
						e.preventDefault();
						return false;
					}else{
						$.post('LikeDislikeCommentServlet',{'id':id},function(data){
							var getLike = "#commLikeNum." + id;
							var getDislike = "#commDislikeNum." + id;
							$(getDislike).text(data.dislikeNum);
							$(getLike).text(data.likeNum);
						});
						e.preventDefault();
						return false;
					}
				});*/
				if(data.isSubscribed == "subscribe"){
					$(unsubscribe).css("width","120px");
					unsubscribe.show();
					subscribe.hide();
				}
				if(data.user.userName != data.video.ownerOfVideo.userName){
					subscribe.on('click',function(e){
						$.post('SubscribersServlet',{'channel':data.video.ownerOfVideo.userName,'subscriber':data.user.userName},function(data){
							console.log("stigao odgovor");
							if(data.status == "success"){
								alert("You are successfully subscribed!");
								$(unsubscribe).css("width","120px");
								unsubscribe.show();
								subscribe.hide();
							}
						});
						e.preventDefault();
						return false;
					});
					
					unsubscribe.on('click',function(e){
						$.get('SubscribersServlet',{'channel':data.video.ownerOfVideo.userName,'subscriber':data.user.userName},function(data){
							console.log("stigao odgovor");
							if(data.status == "success"){
								alert("You are successfully unsubscribed!");
								subscribe.show();
								unsubscribe.hide();
							}
						});
						e.preventDefault();
						return false;
					});
				}
			}
			if(data.user.userName == data.video.ownerOfVideo.userName){
				console.log(data.video.id);
				$('.sidebar-nav').append(
					'<li><a href="EditVideo.html?id=' + data.video.id + '">Edit video</a></li>'
				);
			
				
				$(deleteVideo).show();
				
				$(deleteVideo).on('click',function(e){
					console.log(id);
					var confirmation = confirm("Are you sure?");
					if(confirmation){
						$.post('MainVideoServlet',{'videoId':id,'action':"delete"},function(data){
							
							window.location.replace('Index.html');
						});
					}
					e.preventDefault();
					return false;
				});
				
			}
			if(data.user.role == 'ADMIN'){
				$('.sidebar-nav').append(
						'<li><a href="EditVideo.html?id=' + data.video.id + '">Edit video</a></li>'
					);
					
				$(deleteVideo).show();
				
				$(deleteVideo).on('click',function(e){
					console.log(id);
					var confirmation = confirm("Are you sure?");
					if(confirmation){
						$.post('VideosServlet',{'videoId':id,'status':"delete"},function(data){
							window.location.replace('Index.html');
						});
					}
					e.preventDefault();
					return false;
				});
			}
		}
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

