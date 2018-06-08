package youtube.model;



public class Comment {
	
	private int id;
	private String content;
	private String date;
	private User ownerOfComment;
	private Video video;
	private int numberOfLikes;
	private int numberOfDislikes;
	private boolean deleted;
	
	
	public Comment(int id, String content, String date, User ownerOfComment, Video video, int numberOfLikes,
			int numberOfDislikes, boolean deleted) {
		super();
		this.id = id;
		this.content = content;
		this.date = date;
		this.ownerOfComment = ownerOfComment;
		this.video = video;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
		this.deleted = deleted;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public User getOwnerOfComment() {
		return ownerOfComment;
	}


	public void setOwnerOfComment(User ownerOfComment) {
		this.ownerOfComment = ownerOfComment;
	}


	public Video getVideo() {
		return video;
	}


	public void setVideo(Video video) {
		this.video = video;
	}


	public int getNumberOfLikes() {
		return numberOfLikes;
	}


	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}


	public int getNumberOfDislikes() {
		return numberOfDislikes;
	}


	public void setNumberOfDislikes(int numberOfDislikes) {
		this.numberOfDislikes = numberOfDislikes;
	}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	
}
