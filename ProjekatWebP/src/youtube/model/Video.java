package youtube.model;



public class Video {
	
	public enum Visibility {PUBLIC,PRIVATE,UNLISTED}
	
	private int id;
	private String videoUrl;
	private String videoPicture;
	private String videoName;
	private String description;
	private Visibility visibility;
	private boolean enableComments;
	private boolean enableRating;
	private int numberOfLikes;
	private int numberOfDislikes;
	private int numberOfViews;
	private String postDate;
	private User ownerOfVideo;
	private boolean blocked;
	private boolean deleted;
	
	
	
	public Video(int id, String videoUrl, String videoPicture, String videoName, String description,
			Visibility visibility, boolean enableComments, boolean enableRating, int numberOfLikes,
			int numberOfDislikes, int numberOfViews, String postDate, User ownerOfVideo, boolean blocked,
			boolean deleted) {
		super();
		this.id = id;
		this.videoUrl = videoUrl;
		this.videoPicture = videoPicture;
		this.videoName = videoName;
		this.description = description;
		this.visibility = visibility;
		this.enableComments = enableComments;
		this.enableRating = enableRating;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
		this.numberOfViews = numberOfViews;
		this.postDate = postDate;
		this.ownerOfVideo = ownerOfVideo;
		this.blocked = blocked;
		this.deleted = deleted;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getVideoUrl() {
		return videoUrl;
	}



	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}



	public String getVideoPicture() {
		return videoPicture;
	}



	public void setVideoPicture(String videoPicture) {
		this.videoPicture = videoPicture;
	}



	public String getVideoName() {
		return videoName;
	}



	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Visibility getVisibility() {
		return visibility;
	}



	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}



	public boolean isEnableComments() {
		return enableComments;
	}



	public void setEnableComments(boolean enableComments) {
		this.enableComments = enableComments;
	}



	public boolean isEnableRating() {
		return enableRating;
	}



	public void setEnableRating(boolean enableRating) {
		this.enableRating = enableRating;
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



	public int getNumberOfViews() {
		return numberOfViews;
	}



	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}



	public String getPostDate() {
		return postDate;
	}



	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}



	public User getOwnerOfVideo() {
		return ownerOfVideo;
	}



	public void setOwnerOfVideo(User ownerOfVideo) {
		this.ownerOfVideo = ownerOfVideo;
	}



	public boolean isBlocked() {
		return blocked;
	}



	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}



	public boolean isDeleted() {
		return deleted;
	}



	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}



	
	
}
