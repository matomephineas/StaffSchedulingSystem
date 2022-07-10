package com.example.staffschedulingsystem.Models;

public class ScheduledSubjects {
    private String day,department,startTime,endTime,staffID,subjectName,staffName,subjectID,venue;

    public ScheduledSubjects() {
    }

    public ScheduledSubjects(String day, String department, String startTime, String endTime, String staffID, String subjectName, String staffName, String subjectID, String venue) {
        this.day = day;
        this.department = department;
        this.startTime = startTime;
        this.endTime = endTime;
        this.staffID = staffID;
        this.subjectName = subjectName;
        this.staffName = staffName;
        this.subjectID = subjectID;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
