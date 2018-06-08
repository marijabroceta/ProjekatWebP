package youtube.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;



import youtube.model.User;
import youtube.model.Video;
import youtube.model.Video.Visibility;
import youtube.util.FormatDate;

public class VideoDAO {

	public static Video get(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 2;
				String videoUrl = rset.getString(index++);
				String videoPicture = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean enableComment = rset.getBoolean(index++);
				boolean enableRating = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int numberOfViews = rset.getInt(index++);
				Date date = rset.getDate(index++);
				String postDate = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerUserName = UserDAO.get(user);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				
				Video video = new Video(id,videoUrl,videoPicture,videoName,description,visibility,enableComment,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted);
				return video;
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static ArrayList<Video> getAllVideo(){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE (visibility = ? OR visibility = ?) AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setString(2, "PRIVATE");
			pstmt.setBoolean(3, false);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String videoPicture = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean enableComment = rset.getBoolean(index++);
				boolean enableRating = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int numberOfViews = rset.getInt(index++);
				Date date = rset.getDate(index++);
				String postDate = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerUserName = UserDAO.get(user);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				if(ownerUserName == null) {
					continue;
				}else {
					Video video = new Video(id,videoUrl,videoPicture,videoName,description,visibility,enableComment,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted);
					videos.add(video);
				}
				
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return videos;
	}
	
	public static ArrayList<Video> getPublicVideo(){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE visibility = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String videoPicture = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean enableComment = rset.getBoolean(index++);
				boolean enableRating = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int numberOfViews = rset.getInt(index++);
				Date date = rset.getDate(index++);
				String postDate = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerUserName = UserDAO.get(user);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				if(ownerUserName == null) {
					continue;
				}else {
					Video video = new Video(id,videoUrl,videoPicture,videoName,description,visibility,enableComment,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted);
					videos.add(video);
				}
				
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return videos;
	}
	
	public static ArrayList<Video> userVideo(String userName){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM video WHERE ownerUserName = ? AND deleted = ? ORDER BY postDate DESC";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String videoPicture = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean enableComment = rset.getBoolean(index++);
				boolean enableRating = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int numberOfViews = rset.getInt(index++);
				Date date = rset.getDate(index++);
				String postDate = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerUserName = UserDAO.get(user);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				
				if(ownerUserName == null) {
					continue;
				}else {
					Video video = new Video(id,videoUrl,videoPicture,videoName,description,visibility,enableComment,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted);
					videos.add(video);
				}
			}
			
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return videos;
	}
	
	public static ArrayList<Video> userPublicVideo(String userName){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM video WHERE visibility = ? AND ownerUserName = ? AND deleted = ? ORDER BY postDate DESC";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setString(2, userName);
			pstmt.setBoolean(3, false);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String videoPicture = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean enableComment = rset.getBoolean(index++);
				boolean enableRating = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int numberOfViews = rset.getInt(index++);
				Date date = rset.getDate(index++);
				String postDate = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerUserName = UserDAO.get(user);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				
				if(ownerUserName == null) {
					continue;
				}else {
					Video video = new Video(id,videoUrl,videoPicture,videoName,description,visibility,enableComment,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted);
					videos.add(video);
				}
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return videos;
	}
	
	public static int getVideoId() {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int id = 0;
		try {
			String query = "SELECT MAX(id) FROM video";
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
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static ArrayList<Video> sortUsersVideo(String userName,String column,String ascDesc){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM video WHERE ownerUserName = ? AND deleted = ? ORDER BY " + column + " " + ascDesc;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setBoolean(2,false);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String videoPicture = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean enableComments = rset.getBoolean(index++);
				boolean enableRating = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int numberOfViews = rset.getInt(index++);
				Date date = rset.getDate(index++);
				String postDate = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerUserName = UserDAO.get(user);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				if(ownerUserName == null) {
					continue;
				}else {
					Video video = new Video(id,videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted);
					videos.add(video);
				}
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return videos;
	}
	
	public static ArrayList<Video> sortUsersPublicVideo(String userName,String column,String ascDesc){
		Connection conn = ConnectionManager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE visibility = ? AND ownerUserName = ? AND deleted = ? ORDER BY " + column + " " + ascDesc;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setString(2, userName);
			pstmt.setBoolean(3, false);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String videoPicture = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean enableComments = rset.getBoolean(index++);
				boolean enableRating = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int numberOfViews = rset.getInt(index++);
				Date date = rset.getDate(index++);
				String postDate = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerUserName = UserDAO.get(user);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				if(ownerUserName == null) {
					continue;
				}else {
					Video video = new Video(id,videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted);
					videos.add(video);
				}
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return videos;
	}
	
	public static boolean addVideo(Video video) {
		Connection conn = ConnectionManager.getConnection();
				
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO video(videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted)"
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, video.getVideoUrl());
			pstmt.setString(index++, video.getVideoPicture());
			pstmt.setString(index++, video.getVideoName());
			pstmt.setString(index++, video.getDescription());
			pstmt.setString(index++, video.getVisibility().toString());
			pstmt.setBoolean(index++, video.isEnableComments());
			pstmt.setBoolean(index++, video.isEnableRating());
			pstmt.setInt(index++, video.getNumberOfLikes());
			pstmt.setInt(index++, video.getNumberOfDislikes());
			pstmt.setInt(index++, video.getNumberOfViews());
			Date date = FormatDate.stringToDateWrite(video.getPostDate());
			java.sql.Date postDate = new java.sql.Date(date.getTime());
			pstmt.setDate(index++, postDate);
			pstmt.setString(index++, video.getOwnerOfVideo().getUserName());
			pstmt.setBoolean(index++,video.isBlocked());
			pstmt.setBoolean(index++, video.isDeleted());
			
			return pstmt.executeUpdate() == 1;
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}			
		}
		return false;
	}
	
	public static boolean updateVideo(Video video) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE video SET description = ?, visibility = ?, enableComments = ?, enableRating = ?, numberOfLikes  = ?, numberOfDislikes = ?, numberOfViews = ?, blocked = ?, deleted = ? WHERE id = ? ";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, video.getDescription());
			pstmt.setString(index++, video.getVisibility().toString());
			pstmt.setBoolean(index++, video.isEnableComments());
			pstmt.setBoolean(index++, video.isEnableRating());
			pstmt.setInt(index++, video.getNumberOfLikes());
			pstmt.setInt(index++, video.getNumberOfDislikes());
			pstmt.setInt(index++, video.getNumberOfViews());
			pstmt.setBoolean(index++, video.isBlocked());
			pstmt.setBoolean(index++, video.isDeleted());
			pstmt.setInt(index++, video.getId());
			
			return pstmt.executeUpdate() == 1;
			
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
}
