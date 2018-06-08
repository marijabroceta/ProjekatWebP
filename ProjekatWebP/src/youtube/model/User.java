package youtube.model;

import java.util.ArrayList;


public class User {
	
	public enum Role {USER,ADMIN}
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String channelDescription;
	private String dateOfRegistration;
	private Role role;
	private boolean blocked;
	private boolean deleted;
	private ArrayList<User> subscribers;
	private ArrayList<LikeDislike> likedVideos;
	private ArrayList<LikeDislike> likedComments;
	public int subsNumber;
	public ArrayList<String> subscribersUserNames = new ArrayList<String>();
	
	
	
	public User(String userName, String password, String firstName, String lastName, String email,
			String channelDescription, String dateOfRegistration, Role role, boolean blocked, boolean deleted,
			ArrayList<User> subscribers, ArrayList<LikeDislike> likedVideos, ArrayList<LikeDislike> likedComments) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.channelDescription = channelDescription;
		this.dateOfRegistration = dateOfRegistration;
		this.role = role;
		this.blocked = blocked;
		this.deleted = deleted;
		this.subscribers = subscribers;
		this.likedVideos = likedVideos;
		this.likedComments = likedComments;
		
	}

	
	public User(String userName, String password, String firstName, String lastName, String email,
			String channelDescription, String dateOfRegistration, Role role, boolean blocked, boolean deleted) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.channelDescription = channelDescription;
		this.dateOfRegistration = dateOfRegistration;
		this.role = role;
		this.blocked = blocked;
		this.deleted = deleted;
	}






	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getChannelDescription() {
		return channelDescription;
	}



	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}



	public String getDateOfRegistration() {
		return dateOfRegistration;
	}



	public void setDateOfRegistration(String dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}



	public Role getRole() {
		return role;
	}



	public void setRole(Role role) {
		this.role = role;
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



	public ArrayList<User> getSubscribers() {
		return subscribers;
	}



	public void setSubscribers(ArrayList<User> subscribers) {
		this.subscribers = subscribers;
	}



	public ArrayList<LikeDislike> getLikedVideos() {
		return likedVideos;
	}



	public void setLikedVideos(ArrayList<LikeDislike> likedVideos) {
		this.likedVideos = likedVideos;
	}



	public ArrayList<LikeDislike> getLikedComments() {
		return likedComments;
	}



	public void setLikedComments(ArrayList<LikeDislike> likedComments) {
		this.likedComments = likedComments;
	}



	public int getSubsNumber() {
		return subsNumber;
	}



	public void setSubsNumber(int subsNumber) {
		this.subsNumber = subsNumber;
	}



	public ArrayList<String> getSubscribersUserNames() {
		return subscribersUserNames;
	}



	public void setSubscribersUserNames(ArrayList<String> subscribersUserNames) {
		this.subscribersUserNames = subscribersUserNames;
	}
	
	
	
}
