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
import youtube.dao.UserDAO;
import youtube.dao.VideoDAO;
import youtube.model.Comment;
import youtube.model.User;
import youtube.model.Video;
import youtube.util.FormatDate;

public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		String status = "success";
		ArrayList<Comment> comments = null;
		try {
			String column = request.getParameter("column");
			String ascDesc = request.getParameter("ascDesc");
			System.out.println(ascDesc);
			int id = Integer.parseInt(request.getParameter("id"));
			
			comments = CommentDAO.sortComments(id, column, ascDesc);
		}catch(Exception ex) {
			status = "failure";
		}
		
		Map<String,Object> data = new HashMap<>();
		data.put("status",status);
		data.put("user", loggedInUser);
		data.put("comments",comments);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action) {
			case "add":{
				String ownerOfComment = request.getParameter("ownerOfComment");
				User user = UserDAO.get(ownerOfComment);
				String content = request.getParameter("content");
				int videoId = Integer.parseInt(request.getParameter("video"));
				Video video = VideoDAO.get(videoId);
				Date date = new Date();
				String dateOfComment = FormatDate.dateToStringWrite(date);
				int id = CommentDAO.getIdComment();
				Comment comment = new Comment(id, content, dateOfComment, user, video, 0, 0, false);
				CommentDAO.addComment(comment); 
				
				Map<String,Object> data = new HashMap<>();
				data.put("action","success");
				data.put("ownerOfComment", ownerOfComment);
				data.put("dateOfComment", dateOfComment);
				data.put("content",content);
				data.put("id", comment.getId());
				data.put("numberOfLikes", 0);
				data.put("numberOfDislikes",0);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
							
				break;
			}
			case "delete":{
				String status = "success";
				try {
					int id = Integer.parseInt(request.getParameter("id"));
					Comment comment = CommentDAO.get(id);
					comment.setDeleted(true);
					CommentDAO.updateComment(comment);
				}catch(Exception ex) {
					status = "failure";
				}
				
				Map<String,Object> data = new HashMap<>();
				data.put("status", status);
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
				break;
			}
			case "edit":{
				String status = "success";
				String newDate = "";
				try {
					int id = Integer.parseInt(request.getParameter("id"));
					String newContent = request.getParameter("content");
					Comment comment = CommentDAO.get(id);
					if(newContent != null) {
						comment.setContent(newContent);
						Date date = new Date();
						newDate = FormatDate.dateToString(date);
						System.out.println(newDate);
						CommentDAO.updateComment(comment);
					}
				}catch(Exception ex) {
					status = "failure";
				}
				Map<String,Object> data = new HashMap<>();
				data.put("status", status);
				data.put("newDate", newDate);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
			}
		}
	}

}
