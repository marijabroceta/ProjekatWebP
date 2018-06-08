package youtube.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import youtube.model.User;
import youtube.model.User.Role;
import youtube.util.FormatDate;

public class UserDAO {
	
	public static User get(String userName) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE userName = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setBoolean(2, false);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 2;
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String channelDescription = rset.getString(index++);
				Role role = Role.valueOf(rset.getString(index++));
				Date date = rset.getDate(index++);
				String dateOfRegistration = FormatDate.dateToString(date);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				
				User user = new User(userName,password,firstName,lastName,email,channelDescription,dateOfRegistration,role,blocked,deleted,null,null,null);
				
				pstmt.close();
				rset.close();
				
				query = "SELECT * FROM subscribe WHERE mainUser = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, userName);
				rset = pstmt.executeQuery();
				if(rset.next()) {
					int indexSub = 2;
					String subscriber = rset.getString(indexSub++);
					user.subscribersUserNames.add(subscriber);
				}
				
				user.setSubscribers(findSubscribers(user.subscribersUserNames));
				user.setSubsNumber(getSubsNumber(userName));
				return user;
			}
			
		}catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			}catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			}catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static ArrayList<User> getAllUsers() {
		Connection conn = ConnectionManager.getConnection();
		
		ArrayList<User> users = new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int index = 1;
				String userName = rset.getString(index++);
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String channelDescription = rset.getString(index++);
				Role role = Role.valueOf(rset.getString(index++));
				Date date = rset.getDate(index++);
				String dateOfRegistration = FormatDate.dateToString(date);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				
				User user = new User(userName,password,firstName,lastName,email,channelDescription,dateOfRegistration,role,blocked,deleted,null,null,null);
				
				user.subscribersUserNames = getSubsUserName(userName);
				user.setSubscribers(findSubscribers(user.subscribersUserNames));
				user.setSubsNumber(getSubsNumber(userName));
				users.add(user);
			}
			return users;
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static ArrayList<String> getSubsUserName(String userName){
		ArrayList<String> subsUserName = new ArrayList<String>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM subscribe WHERE mainUser = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int index = 2;
				String subscriber = rset.getString(index++);
				subsUserName.add(subscriber);
				
			}
			return subsUserName;
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	
	public static int getSubsNumber(String userName) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM subscribe WHERE mainUser = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				int subs = rset.getInt(index);
				return subs;
			}
			
		}catch(Exception ex){
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {
				pstmt.close();
			}catch(SQLException ex1){
				ex1.printStackTrace();
			}
			try {
				rset.close();
			}catch(SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return 0;
	}
	
	public static ArrayList<User> subscribedOn(String userName){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<User> subscribed = new ArrayList<User>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT mainUser FROM subscribe WHERE subscriber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				String main = rset.getString(1);
			
				User user = get(main);
				subscribed.add(user);
			}
			
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch(SQLException ex1) {ex1.printStackTrace();}
		}
		
		return subscribed;
	}
	
	public static ArrayList<User> getAllSort(String column,String ascDesc){
		Connection conn = ConnectionManager.getConnection();
		
		ArrayList<User> users = new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE deleted = ? ORDER BY" + " " + column + " " + ascDesc;
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int index = 1;
				String userName = rset.getString(index++);
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String channelDescription = rset.getString(index++);
				Role role = Role.valueOf(rset.getString(index++));
				Date date = rset.getDate(index++);
				String dateOfregistration = FormatDate.dateToString(date);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				
				User user = new User(userName,password,firstName,lastName,email,channelDescription,dateOfregistration,role,blocked,deleted,null,null,null);
				
				user.subscribersUserNames = getSubsUserName(userName);
				user.setSubscribers(findSubscribers(user.subscribersUserNames));
				user.setSubsNumber(getSubsNumber(userName));
				users.add(user);
				
			}
			return users;
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1){ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1){ex1.printStackTrace();}			
		}
		return null;
	}
	
	public static boolean addUser(User user) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users(userName,userPassword,firstName,lastName,email,channelDescription,role,dateOfRegistration,blocked,deleted) VALUES (?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(query);
			System.out.println(user.getUserName());
			System.out.println(user.getPassword());
			System.out.println(user.getFirstName());
			System.out.println(user.getLastName());
			int index = 1;
			pstmt.setString(index++, user.getUserName());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getChannelDescription());
			pstmt.setString(index++, user.getRole().toString());
			Date date = FormatDate.stringToDateWrite(user.getDateOfRegistration());
			java.sql.Date dateOfRegistration = new java.sql.Date(date.getTime());
			pstmt.setDate(index++, dateOfRegistration);
			pstmt.setBoolean(index++, user.isBlocked());
			pstmt.setBoolean(index++, user.isDeleted());
			return pstmt.executeUpdate() == 1;
			
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	public static boolean updateUser(User user) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			
			String query = "UPDATE users SET userPassword = ?,firstName = ?, lastName = ?, email = ?, channelDescription = ?, role = ?, blocked = ?, deleted = ? WHERE userName = ?";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getChannelDescription());
			pstmt.setString(index++, user.getRole().toString());
			pstmt.setBoolean(index++, user.isBlocked());
			pstmt.setBoolean(index++, user.isDeleted());
			pstmt.setString(index++, user.getUserName());
			
			return pstmt.executeUpdate() == 1;
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	public static boolean addSubscribers(String mainUser,String subscriber) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO subscribe(mainUser,subscriber) VALUES (?, ?)";		
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, mainUser);
			pstmt.setString(2, subscriber);
			return pstmt.executeUpdate() == 1;
			
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean deleteSubscribers(String mainUser,String subscriber) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM subscribe WHERE mainUser = ? AND subscriber = ?";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, mainUser);
			pstmt.setString(index++, subscriber);
			return pstmt.executeUpdate() == 1;
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static int findSubscribed(String userName,String subscriber) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM subscribe WHERE mainUser = ? AND subscriber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setString(2, subscriber);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int subs = rset.getInt(1);
				return subs;
			}
			System.out.println("******");
			System.out.println(userName);
			
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1){ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1){ex1.printStackTrace();}			
		}
		return 0;
	}
	
	public static ArrayList<User> findSubscribers(ArrayList<String> subscribersUserName) {
		ArrayList<User> listUsers = new ArrayList<User>();
		if(subscribersUserName.isEmpty()) {
			return null;
		}else {
			for(String userName : subscribersUserName) {
				listUsers.add(get(userName));
			}
			return listUsers;
		}
	}
}
