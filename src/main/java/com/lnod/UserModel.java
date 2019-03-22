package com.lnod;

public class UserModel {
	private String emailId;
	private String classroomSessionAttended;
	private String onlineCoursesEnrolled;
	
	public UserModel() {}
	
	public UserModel(String emailId, String classroomSessionAttended, String onlineCoursesEnrolled) {
		super();
		this.emailId = emailId;
		this.classroomSessionAttended = classroomSessionAttended;
		this.onlineCoursesEnrolled = onlineCoursesEnrolled;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getClassroomSessionAttended() {
		return classroomSessionAttended;
	}
	public void setClassroomSessionAttended(String classroomSessionAttended) {
		this.classroomSessionAttended = classroomSessionAttended;
	}
	public String getOnlineCoursesEnrolled() {
		return onlineCoursesEnrolled;
	}
	public void setOnlineCoursesEnrolled(String onlineCoursesEnrolled) {
		this.onlineCoursesEnrolled = onlineCoursesEnrolled;
	}
	
	
}
