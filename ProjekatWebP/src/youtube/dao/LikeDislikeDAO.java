package youtube.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import youtube.model.LikeDislike;
import youtube.model.User;
import youtube.model.Video;
import youtube.util.FormatDate;

public class LikeDislikeDAO {
	
	public static int getVideoLikes(int videoId) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM likeDislikeVideo ldv JOIN likeDislike ld on ldv.likeId = ld.id WHERE liked = ? AND ldv.videoId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, videoId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int likeNumber = rset.getInt(1);
				return likeNumber;
			}
		}catch(Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static int getVideoDislikes(int videoId) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM likeDislikeVideo ldv JOIN likeDislike ld on ldv.likeId = ld.id WHERE liked = ? AND ldv.videoId = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setBoolean(1, false);
			pstmt.setInt(2, videoId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int dislikeNum = rset.getInt(1);
				return dislikeNum;
			}
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static int getIdLike() {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset =null;
		
		int id = 0;
		try {
			String query = "SELECT MAX(id) FROM likeDislike";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				id = rset.getInt(1);
			}
			id++;
			return id;
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static LikeDislike getVideoLikeOwner(int videoId,String owner) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM likeDislikeVideo ldv JOIN likeDislike ld ON ldv.likeId = ld.id WHERE ldv.videoId = ? AND ld.ownerUserName = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,videoId);
			pstmt.setString(2,owner);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 2;
				int videosId = rset.getInt(index++);
				Video video = VideoDAO.get(videosId);
				int likeId = rset.getInt(index++);
				boolean isLike = rset.getBoolean(index++);
				Date date = rset.getDate(index++);
				String dateOfLike = FormatDate.dateToString(date);
				String ownerUserName = rset.getString(index++);
				User ownerOfLike = UserDAO.get(ownerUserName);
				
				return new LikeDislike(likeId,isLike,dateOfLike,video,null,ownerOfLike);
				
			}
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static boolean addLikeDislike(LikeDislike likeDislike) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO likeDislike(liked,dateOfLike,ownerUserName) VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setBoolean(index++, likeDislike.isLiked());
			Date date = FormatDate.stringToDateWrite(likeDislike.getDateOfLike());
			java.sql.Date dateOfLike = new java.sql.Date(date.getTime());
			pstmt.setDate(index++, dateOfLike);
			pstmt.setString(index++, likeDislike.getOwnerOfLike().getUserName());
			
			return pstmt.executeUpdate() == 1;
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean updateLikeDislike(LikeDislike likeDislike) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE likeDislike SET liked = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setBoolean(index++, likeDislike.isLiked());
			pstmt.setInt(index++, likeDislike.getId());
			
			return pstmt.executeUpdate() == 1;
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean addLikeDislikeVideo(int likeId, int videoId) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO likeDislikeVideo(likeId,videoId) VALUES (?, ?)";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, likeId);
			pstmt.setInt(index++, videoId);
			
			return pstmt.executeUpdate() == 1;
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
			try {conn.rollback();}catch(SQLException ex1) {ex.printStackTrace();}
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {conn.setAutoCommit(true);}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static LikeDislike getCommentLikeOwner(int commentId,String owner) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset= null;
		
		try {
			String query = "SELECT * FROM likeDislikeComment ldc JOIN likeDislike ld ON ldc.likeId = ld.id  WHERE ldc.commentId = ? AND ld.ownerUserName = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, commentId);
			pstmt.setString(2, owner);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 2;
				int videoId = rset.getInt(index++);
				Video video = VideoDAO.get(videoId);
				int likeId = rset.getInt(index++);
				boolean isLike = rset.getBoolean(index++);
				Date date = rset.getDate(index++);
				String dateOfLike = FormatDate.dateToString(date);
				String user = rset.getString(index++);
				User ownerOfLike = UserDAO.get(user);
				
				return new LikeDislike(likeId,isLike,dateOfLike,video,null,ownerOfLike);
				
			}
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static boolean addCommentLikeDislike(int likeId,int commentId) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt =null;
		try {
			String query = "INSERT INTO likeDislikeComment(likeId,commentId) VALUES(?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, likeId);
			pstmt.setInt(2, commentId);
			
			return pstmt.executeUpdate() == 1;
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
			try {conn.rollback();}catch(SQLException ex1) {ex.printStackTrace();}
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {conn.setAutoCommit(true);}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	public static int getCommentLikes(int commentId) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM likeDislikeComment ldc JOIN likeDislike ld ON ldc.likeId = ld.id WHERE liked = ? AND ldc.commentId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, commentId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int likeNum = rset.getInt(1);
				return likeNum;
			}
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
	
	public static int getCommentDislikes(int commentId) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM likeDislikeComment ldc JOIN likeDislike ld ON ldc.likeId = ld.id WHERE liked = ? AND ldc.commentId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setInt(2, commentId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int dislikeNum = rset.getInt(1);
				return dislikeNum;
			}
		}catch(SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		}finally {
			try {pstmt.close();}catch(SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();}catch(SQLException ex1) {ex1.printStackTrace();}
		}
		return 0;
	}
}
