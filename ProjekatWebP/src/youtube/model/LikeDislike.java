package youtube.model;

public class LikeDislike {
	
	private int id;
	private boolean liked;
	private String dateOfLike;
	private Video likedVideo;
	private Comment likedComment;
	private User ownerOfLike;
	
	
	
	public LikeDislike(int id, boolean liked, String dateOfLike, Video likedVideo, Comment likedComment,
			User ownerOfLike) {
		super();
		this.id = id;
		this.liked = liked;
		this.dateOfLike = dateOfLike;
		this.likedVideo = likedVideo;
		this.likedComment = likedComment;
		this.ownerOfLike = ownerOfLike;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public boolean isLiked() {
		return liked;
	}



	public void setLiked(boolean liked) {
		this.liked = liked;
	}



	public String getDateOfLike() {
		return dateOfLike;
	}



	public void setDateOfLike(String dateOfLike) {
		this.dateOfLike = dateOfLike;
	}



	public Video getLikedVideo() {
		return likedVideo;
	}



	public void setLikedVideo(Video likedVideo) {
		this.likedVideo = likedVideo;
	}



	public Comment getLikedComment() {
		return likedComment;
	}



	public void setLikedComment(Comment likedComment) {
		this.likedComment = likedComment;
	}



	public User getOwnerOfLike() {
		return ownerOfLike;
	}



	public void setOwnerOfLike(User ownerOfLike) {
		this.ownerOfLike = ownerOfLike;
	}
	
	
}
