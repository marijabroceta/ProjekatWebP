package youtube.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import youtube.model.Comment;
import youtube.model.User;
import youtube.model.Video;
import youtube.util.FormatDate;

public class CommentDAO {

	public static Comment get(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comments WHERE id = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 2;
				
				String content = rset.getString(index++);
				Date date = rset.getDate(index++);
				String dateOfComment = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				int videoId = rset.getInt(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				boolean deleted = rset.getBoolean(index++);
				User ownerUserName = UserDAO.get(user);
				Video video = VideoDAO.get(videoId);
				
				return new Comment(id,content,dateOfComment,ownerUserName,video,numberOfLikes,numberOfDislikes,deleted);
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static int getIdComment() {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int id = 0;
		
		try {
			String query = "SELECT MAX(id) FROM comments";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				id = rset.getInt(1);
			}
			id++;
			return id;
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static ArrayList<Comment> getAllComments(int videoId){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Comment> comments = new ArrayList<Comment>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comments WHERE videoId = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, videoId);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				
				int id = rset.getInt(index++);
				String content = rset.getString(index++);
				Date date = rset.getDate(index++);
				String dateOfComment = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				int videosId = rset.getInt(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				boolean deleted = rset.getBoolean(index++);
				User ownerUserName = UserDAO.get(user);
				Video video = VideoDAO.get(videosId);
				
				if(ownerUserName == null || video == null) {
					continue;
				}else {
					Comment comment = new Comment(id,content,dateOfComment,ownerUserName,video,numberOfLikes,numberOfDislikes,deleted);
					comments.add(comment);
				}
				
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return comments;
	}
	
	public static ArrayList<Comment> sortComments(int videoId,String column,String ascDesc){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Comment> comments = new ArrayList<Comment>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comments WHERE videoId = ? AND deleted = ? ORDER BY " + column + " " + ascDesc;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, videoId);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String content = rset.getString(index++);
				Date date = rset.getDate(index++);
				String postDate = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerOfComment = UserDAO.get(user);
				int videosId = rset.getInt(index++);
				Video video = VideoDAO.get(videosId);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				boolean deleted = rset.getBoolean(index++);
				if(ownerOfComment == null || video == null) {
					continue;
				}else {
					Comment comment = new Comment(id,content,postDate,ownerOfComment,video,numberOfLikes,numberOfDislikes,deleted);
					comments.add(comment);
				}
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return comments;
	}
	
	public static boolean addComment(Comment comment) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes,deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, comment.getContent());
			Date date = FormatDate.stringToDateWrite(comment.getDate());
			java.sql.Date dateOfComment = new java.sql.Date(date.getTime());
			pstmt.setDate(index++, dateOfComment);
			pstmt.setString(index++, comment.getOwnerOfComment().getUserName());
			pstmt.setInt(index++, comment.getVideo().getId());
			pstmt.setInt(index++, comment.getNumberOfLikes());
			pstmt.setInt(index++, comment.getNumberOfDislikes());
			pstmt.setBoolean(index++, comment.isDeleted());
			
			return pstmt.executeUpdate() == 1;
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean updateComment(Comment comment) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE comments SET numberOfLikes = ?, numberOfDislikes = ?, deleted = ? , dateOfComment = ?, content = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			//int index = 1;
			pstmt.setInt(1, comment.getNumberOfLikes());
			pstmt.setInt(2, comment.getNumberOfDislikes());
			pstmt.setBoolean(3, comment.isDeleted());
			Date date = FormatDate.stringToDate(comment.getDate());
			String dateString = FormatDate.dateToStringWrite(date);
			Date date1 = FormatDate.stringToDateWrite(dateString);
			java.sql.Date dateOfComment = new java.sql.Date(date1.getTime());
			pstmt.setDate(4, dateOfComment);
			pstmt.setString(5, comment.getContent());
			pstmt.setInt(6, comment.getId());
			
			return pstmt.executeUpdate() == 1;
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
}
