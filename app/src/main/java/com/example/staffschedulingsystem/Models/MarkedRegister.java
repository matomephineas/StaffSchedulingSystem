package com.example.staffschedulingsystem.Models;

public class MarkedRegister
{
    public String subjectName,subjectID,timeMarked,dateMarked,status,studentName,userId,address;

    public MarkedRegister() {
    }

    public MarkedRegister(String subjectName, String subjectID, String timeMarked, String dateMarked, String status, String studentName, String userId, String address) {
        this.subjectName = subjectName;
        this.subjectID = subjectID;
        this.timeMarked = timeMarked;
        this.dateMarked = dateMarked;
        this.status = status;
        this.studentName = studentName;
        this.userId = userId;
        this.address = address;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getTimeMarked() {
        return timeMarked;
    }

    public void setTimeMarked(String timeMarked) {
        this.timeMarked = timeMarked;
    }

    public String getDateMarked() {
        return dateMarked;
    }

    public void setDateMarked(String dateMarked) {
        this.dateMarked = dateMarked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
