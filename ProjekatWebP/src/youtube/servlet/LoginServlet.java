package youtube.servlet;

import java.io.IOException;
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

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		String status = "success";
		try {
			User user = UserDAO.get(userName);
			if(user == null) throw new Exception("Neispravno korisnicko ime i/ili lozinka");
			if(!user.getPassword().equals(password)) throw new Exception("Neispravno korisnicko ime i/ili lozinka");
			
			HttpSession session = request.getSession();
			session.setAttribute("loggedInUser", user);
		}catch(Exception ex) {
			status = "failure";
		}
		
		Map<String,Object> data = new HashMap<>();
		data.put("status",status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}

}
