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

import youtube.dao.CommentDAO;
import youtube.dao.LikeDislikeDAO;
import youtube.model.Comment;
import youtube.model.LikeDislike;
import youtube.model.User;
import youtube.util.FormatDate;


public class LikeDislikeCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		Comment comment =CommentDAO.get(id);
		if(loggedInUser != null) {
			LikeDislike likeDislikeOwner = LikeDislikeDAO.getCommentLikeOwner(comment.getId(), loggedInUser.getUserName());
			if(likeDislikeOwner == null) {
				int likeId = LikeDislikeDAO.getIdLike();
				Date date = new Date();
				String dateOfLike = FormatDate.dateToStringWrite(date);
				LikeDislike likeDislike = new LikeDislike(likeId,true,dateOfLike,null,comment,loggedInUser);
				LikeDislikeDAO.addLikeDislike(likeDislike);
				LikeDislikeDAO.addCommentLikeDislike(likeDislike.getId(), comment.getId());
			}else if(likeDislikeOwner != null && likeDislikeOwner.isLiked() == false) {
				likeDislikeOwner.setLiked(true);
				LikeDislikeDAO.updateLikeDislike(likeDislikeOwner);
			}
		}
		
		int likeNum = LikeDislikeDAO.getCommentLikes(comment.getId());
		int dislikeNum = LikeDislikeDAO.getCommentDislikes(comment.getId());
		comment.setNumberOfLikes(likeNum);
		comment.setNumberOfDislikes(dislikeNum);
		CommentDAO.updateComment(comment);
		
		Map<String,Object> data = new HashMap<>();
		data.put("likeNum",likeNum);
		data.put("dislikeNum",dislikeNum);
		
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
		Comment comment =CommentDAO.get(id);
		if(loggedInUser != null) {
			LikeDislike likeDislikeOwner = LikeDislikeDAO.getCommentLikeOwner(comment.getId(), loggedInUser.getUserName());
			if(likeDislikeOwner == null) {
				int likeId = LikeDislikeDAO.getIdLike();
				Date date = new Date();
				String dateOfLike = FormatDate.dateToString(date);
				LikeDislike likeDislike = new LikeDislike(likeId,false,dateOfLike,null,comment,loggedInUser);
				LikeDislikeDAO.addLikeDislike(likeDislike);
				LikeDislikeDAO.addCommentLikeDislike(likeDislike.getId(), comment.getId());
			}else if(likeDislikeOwner != null && likeDislikeOwner.isLiked() == true) {
				likeDislikeOwner.setLiked(false);
				LikeDislikeDAO.updateLikeDislike(likeDislikeOwner);
			}
		}
		
		int likeNum = LikeDislikeDAO.getCommentLikes(comment.getId());
		int dislikeNum = LikeDislikeDAO.getCommentDislikes(comment.getId());
		comment.setNumberOfLikes(likeNum);
		comment.setNumberOfDislikes(dislikeNum);
		CommentDAO.updateComment(comment);
		
		Map<String,Object> data = new HashMap<>();
		data.put("likeNum",likeNum);
		data.put("dislikeNum",dislikeNum);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
