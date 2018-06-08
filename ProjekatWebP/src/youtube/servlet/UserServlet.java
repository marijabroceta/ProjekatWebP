package youtube.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import youtube.dao.UserDAO;
import youtube.dao.VideoDAO;
import youtube.model.User;
import youtube.model.User.Role;
import youtube.model.Video;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//try {
			
			HttpSession session = request.getSession();
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			String userName=request.getParameter("userName");
			User owner = UserDAO.get(userName);
			ArrayList<Video> videos = null;
			String status="visitor";
			String isSubscribed="unsubcribed";
			if(loggedInUser != null) {
				status="loggedUser";
				int	isSub=UserDAO.findSubscribed(owner.getUserName(),loggedInUser.getUserName());
				

					if(isSub > 0) {
						isSubscribed="subscribe";
					}
					if(loggedInUser.getUserName().equals(userName) || loggedInUser.getRole().toString().equals("ADMIN")) {
						videos=VideoDAO.userVideo(owner.getUserName()); 
						
					}
					else{
						videos=VideoDAO.userPublicVideo(userName);
					}
					
			}else {
				videos=VideoDAO.userPublicVideo(userName);
			}
			System.out.println("----------");
			
			System.out.println(userName);
			System.out.println(status);
			 
			ArrayList<User> subscribers=UserDAO.subscribedOn(userName);
			int subsNumber=UserDAO.getSubsNumber(userName);
			Map<String, Object> data = new HashMap<>();
			data.put("status", status);
			data.put("owner", owner);
			data.put("subsNumber", subsNumber);
			data.put("videos", videos);
			data.put("subscribers", subscribers);
			data.put("user", loggedInUser);
			data.put("isSubscribed",isSubscribed);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			/*}catch (Exception e) {
				System.out.println("Greska");
			}*/
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action) {
			case "edit":{
				String fName = request.getParameter("fName");
				String lName = request.getParameter("lName");
				String password = request.getParameter("password");
				String description = request.getParameter("description");
				String role = request.getParameter("role");
				boolean blocked = Boolean.parseBoolean(request.getParameter("blocked"));
				String userName = request.getParameter("userName");
				Role userRole;
				
				if(role.equals("user")) {
					userRole = Role.USER;
				}else {
					userRole = Role.ADMIN;
				}
				
				User user = UserDAO.get(userName);
				user.setFirstName(fName);
				user.setLastName(lName);
				user.setPassword(password);
				user.setChannelDescription(description);
				user.setBlocked(blocked);
				user.setRole(userRole);
				UserDAO.updateUser(user);
				
				Map<String, Object> data = new HashMap<>();
				data.put("status","success");
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
				break;
			}
			
			case "delete":{
				String userName = request.getParameter("userName");
				User user = UserDAO.get(userName);
				user.setDeleted(true);
				UserDAO.updateUser(user);
				
				Map<String, Object> data = new HashMap<>();
				data.put("status","success");
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
				break;
			}
		}
	}

}