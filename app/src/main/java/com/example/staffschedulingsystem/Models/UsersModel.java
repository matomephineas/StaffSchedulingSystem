package com.example.staffschedulingsystem.Models;

public class UsersModel {
    public String name,email,phone,password,userID,studentNumber,image,department;
    public int userType;
    public UsersModel(){}

    public UsersModel(String name, String email, String phone, String password, String userID, String studentNumber, String image, String department, int userType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userID = userID;
        this.studentNumber = studentNumber;
        this.image = image;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
