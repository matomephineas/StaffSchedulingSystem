package com.example.staffschedulingsystem.Models;

public class StudentModel {
    public String name,email,password,userID,courseName,courseID,department;
    public int userType;
    public StudentModel(){}

    public StudentModel(String name, String email, String password, String userID, String courseName, String courseID, String department, int userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.courseName = courseName;
        this.courseID = courseID;
        this.department = department;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
