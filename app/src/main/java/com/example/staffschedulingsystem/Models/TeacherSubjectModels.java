package com.example.staffschedulingsystem.Models;

public class TeacherSubjectModels {
    private String day,department,endTime,startTime,staffID,staffName,subjectID,subjectName,venue;

    public TeacherSubjectModels() {
    }

    public TeacherSubjectModels(String day, String department, String endTime, String startTime, String staffID, String staffName, String subjectID, String subjectName, String venue) {
        this.day = day;
        this.department = department;
        this.endTime = endTime;
        this.startTime = startTime;
        this.staffID = staffID;
        this.staffName = staffName;
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.venue = venue;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
