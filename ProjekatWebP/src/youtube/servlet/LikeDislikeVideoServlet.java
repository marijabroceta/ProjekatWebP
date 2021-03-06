package youtube.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import youtube.dao.LikeDislikeDAO;
import youtube.dao.VideoDAO;
import youtube.model.LikeDislike;
import youtube.model.User;
import youtube.model.Video;
import youtube.util.FormatDate;

public class LikeDislikeVideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		Video video = VideoDAO.get(id);
		String status = "liked";
		if(loggedInUser != null) {
			LikeDislike likeDislikeOwner = LikeDislikeDAO.getVideoLikeOwner(video.getId(), loggedInUser.getUserName());
			if(likeDislikeOwner == null) {
				status = "notLiked";
				Date date = new Date();
				String dateOfLike = FormatDate.dateToStringWrite(date);
				int likeId = LikeDislikeDAO.getIdLike();
				LikeDislike likeDislike = new LikeDislike(likeId,true,dateOfLike,video,null,loggedInUser);
				LikeDislikeDAO.addLikeDislike(likeDislike);
				LikeDislikeDAO.addLikeDislikeVideo(likeDislike.getId(), video.getId());
				
			}else if(likeDislikeOwner != null && likeDislikeOwner.isLiked() == false) {
				likeDislikeOwner.setLiked(true);
				LikeDislikeDAO.updateLikeDislike(likeDislikeOwner);
			}
		}
		int likeNumVideo = LikeDislikeDAO.getVideoLikes(video.getId());
		int dislikeNumVideo = LikeDislikeDAO.getVideoDislikes(video.getId());
		video.setNumberOfLikes(likeNumVideo);
		video.setNumberOfDislikes(dislikeNumVideo);
		VideoDAO.updateVideo(video);
		
		Map<String,Object> data = new HashMap<>();
		data.put("status",status);
		data.put("likeNumVideo",likeNumVideo);
		data.put("dislikeNumVideo",dislikeNumVideo);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		Video video = VideoDAO.get(id);
		String status = "dislike";
		if(loggedInUser != null) {
			LikeDislike likeDislikeOwner = LikeDislikeDAO.getVideoLikeOwner(video.getId(), loggedInUser.getUserName());
			if(likeDislikeOwner == null) {
				status = "notDisliked";
				Date date = new Date();
				String dateOfDislike = FormatDate.dateToStringWrite(date);
				int likeId = LikeDislikeDAO.getIdLike();
				LikeDislike likeDislike = new LikeDislike(likeId,false,dateOfDislike,video,null,loggedInUser);
				LikeDislikeDAO.addLikeDislike(likeDislike);
				LikeDislikeDAO.addLikeDislikeVideo(likeDislike.getId(), video.getId());
				
			}else if(likeDislikeOwner != null && likeDislikeOwner.isLiked() == true) {
				likeDislikeOwner.setLiked(false);
				LikeDislikeDAO.updateLikeDislike(likeDislikeOwner);
			}
		}
		int dislikeNum = LikeDislikeDAO.getVideoDislikes(video.getId());
		int likeNum = LikeDislikeDAO.getVideoLikes(video.getId());
		video.setNumberOfDislikes(dislikeNum);
		video.setNumberOfLikes(likeNum);
		VideoDAO.updateVideo(video);
		
		Map<String,Object> data = new HashMap<>();
		data.put("status",status);
		data.put("likeNum",likeNum);
		data.put("dislikeNum",dislikeNum);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}

}
