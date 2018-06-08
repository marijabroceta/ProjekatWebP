package youtube.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import youtube.dao.UserDAO;
import youtube.dao.VideoDAO;
import youtube.model.Comment;
import youtube.model.User;
import youtube.model.Video;
import youtube.model.Video.Visibility;
import youtube.util.FormatDate;

public class MainVideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			HttpSession session = request.getSession();
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			Video video = VideoDAO.get(id);
			String status = "visitor";
			String isSubscribed = "unsubscribed";
			if(loggedInUser != null) {
				status = "loggedUser";
				int isSub = UserDAO.findSubscribed(video.getOwnerOfVideo().getUserName(), loggedInUser.getUserName());
				if(isSub > 0) {
					isSubscribed = "subscribe";
				}
			}
			int videoLikeNum = LikeDislikeDAO.getVideoLikes(video.getId());
			video.setNumberOfViews(video.getNumberOfViews() + 1);
			VideoDAO.updateVideo(video);
			ArrayList<Comment> comments = CommentDAO.getAllComments(video.getId());
			
			Map<String,Object> data = new HashMap<>();
			data.put("video", video);
			data.put("status", status);
			data.put("user",loggedInUser);
			data.put("isSubscribed",isSubscribed);
			data.put("comments",comments);
			data.put("videoLikeNum",videoLikeNum);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);
			
			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			
		}catch(Exception ex) {
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		switch(action) {
			case "add":{
				String videoUrl = request.getParameter("videoUrl");
				String videoName = request.getParameter("videoName");
				String description = request.getParameter("description");
				String visible = request.getParameter("visibility");
				boolean enableComments = Boolean.parseBoolean(request.getParameter("enableComments"));
				boolean enableRating = Boolean.parseBoolean(request.getParameter("enableRating"));
				Visibility visibility;
				
				if(visible.equals("public")) {
					visibility = Visibility.PUBLIC;
				}else if(visible.equals("private")) {
					visibility = Visibility.PRIVATE;
				}else {
					visibility = Visibility.UNLISTED;
				}
				
				Date date = new Date();
				String postDate = FormatDate.dateToStringWrite(date);
				int id = VideoDAO.getVideoId();
				Video video = new Video(id,videoUrl,"images/thumbnail.png",videoName,description,visibility,enableComments,enableRating,0,0,0,postDate,loggedInUser,false,false);
				VideoDAO.addVideo(video);
				
				Map<String,Object> data = new HashMap<>();			
				data.put("status", "success");
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
				break;
			}
			
			case "edit":{
				int id = Integer.parseInt(request.getParameter("id"));
				Video video = VideoDAO.get(id);
				String visible = request.getParameter("visibility");
				boolean enableComments = Boolean.parseBoolean(request.getParameter("enableComments"));
				boolean enableRating = Boolean.parseBoolean(request.getParameter("enableRating"));
				String blocked = request.getParameter("block");
				boolean block = false;
				if(blocked.equals("true")) {
					block = true;
				}
				
				String description = request.getParameter("description");
				Visibility visibility;
				if(visible.equals("public")) {
					visibility = Visibility.PUBLIC;
				}else if(visible.equals("private")) {
					visibility = Visibility.PRIVATE;
				}else {
					visibility = Visibility.UNLISTED;
				}
				
				video.setVisibility(visibility);
				video.setEnableComments(enableComments);
				video.setEnableRating(enableRating);
				video.setDescription(description);
				video.setBlocked(block);
				VideoDAO.updateVideo(video);
				
				Map<String,Object> data = new HashMap<>();
				data.put("status","success");
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
				break;
			}
			
			case "delete":{
				int id = Integer.parseInt(request.getParameter("videoId"));
				Video video = VideoDAO.get(id);
				video.setDeleted(true);
				VideoDAO.updateVideo(video);
				
				Map<String,Object> data = new HashMap<>();
				data.put("status","success");
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				
				break;
			}
			
			case "sort":{
				String status = "success";
				ArrayList<Video> videos = null;
				try {
					String column = request.getParameter("column");
					String ascDesc = request.getParameter("ascDesc");
					String userName = request.getParameter("userName");
					if(loggedInUser != null) {
						if(loggedInUser.getUserName().equals(userName) || loggedInUser.getRole().toString().equals("ADMIN")) {
							videos = VideoDAO.sortUsersVideo(userName, column, ascDesc);
						}
					}else {
						videos = VideoDAO.sortUsersPublicVideo(userName, column, ascDesc);
					}
					
				}catch(Exception ex) {
					status = "failure";
				}
				Map<String,Object> data = new HashMap<>();
				data.put("status",status);
				data.put("videos",videos);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				break;
			}
		}
		
	}

}
