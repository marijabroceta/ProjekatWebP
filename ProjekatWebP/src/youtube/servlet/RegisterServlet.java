package youtube.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;

import youtube.dao.UserDAO;
import youtube.model.User;
import youtube.model.User.Role;
import youtube.util.FormatDate;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 10)
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		System.out.println(userName);
		System.out.println(password);
		System.out.println(firstName);
		System.out.println(lastName);
		
		/*try {
			String root = getServletContext().getRealPath("/images/");
			Path path = Paths.get(root + "\\" + userName + ".jpg");
			System.out.println(path);
			Part filePart = request.getPart("photo");
			InputStream fileContent = filePart.getInputStream();
			Files.copy(fileContent, path);
		}catch(Exception ex) {
			System.out.println("Greska slika"+ ex);
		}*/
		
		try {
			String status = "success";
			User user = UserDAO.get(userName);
			if(user != null) {
				status = "exist";
				Map<String, Object> data = new HashMap<>();
				data.put("status",status);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
			}else {
				try {
					String root = getServletContext().getRealPath("/images/");
					Path path = Paths.get(root + "\\" + userName + ".jpg");
					System.out.println(path);
					Part filePart = request.getPart("photo");
					InputStream fileContent = filePart.getInputStream();
					Files.copy(fileContent, path);
				}catch(Exception ex) {
					System.out.println("Greska slika"+ ex);
				}
				Date date = new Date();
				String dateString = FormatDate.dateToStringWrite(date);
				User newUser = new User(userName,password,firstName,lastName,email,"",dateString,Role.USER,false,false);
				UserDAO.addUser(newUser);
				
				Map<String, Object> data = new HashMap<>();
				data.put("status",status);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				System.out.println(jsonData);
				
				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
				//response.sendRedirect("./Index.html");
			}
				
			
		}catch(Exception ex) {
			System.out.println("Greska" + ex);
		}
		response.sendRedirect("./Index.html");
		
	}


}
