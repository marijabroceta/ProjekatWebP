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
import youtube.model.User;


public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			ArrayList<User> users = UserDAO.getAllUsers();
			
			Map<String,Object> data = new HashMap<>();
			data.put("loggedIn",loggedInUser);
			data.put("users",users);
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}catch(Exception ex) {
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action) {
			case "delete":{
				try {
					HttpSession session = request.getSession();
					User loggedInUser = (User) session.getAttribute("loggedInUser");
					String userName = request.getParameter("userName");
					User user = UserDAO.get(userName);
					user.setDeleted(true);
					UserDAO.updateUser(user);
					
					Map<String,Object> data = new HashMap<>();
					data.put("status","success");
					data.put("loggedIn",loggedInUser);
					ObjectMapper mapper = new ObjectMapper();
					String jsonData = mapper.writeValueAsString(data);
					response.setContentType("application/json");
					response.getWriter().write(jsonData);
					
				}catch(Exception ex) {
					
				}
				break;
			}
			case "sort":{
				String status = "success";
				ArrayList<User> users = null;
				try {
					String column = request.getParameter("column");
					String ascDesc = request.getParameter("ascDesc");
					users = UserDAO.getAllSort(column, ascDesc);
				}catch(Exception ex) {
					status = "failure";
				}
				Map<String,Object> data = new HashMap<>();
				
				data.put("status",status);
				data.put("users",users);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				break;
			}
		}
		
	}

}
