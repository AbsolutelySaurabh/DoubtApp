package com.appsomniac.doubtapp.model;

/**
 * Created by absolutelysaurabh on 2/10/17.
 */

public class User  {

    private String name;
    private String email;
    private String phoneNum;

    private String latitude;
    private String longitude;
    private String address;

    private String stream;
    private String classOfStudy;
    private String examPreparingFor;
    private String board;

    public User(){

    }

    public User(String email, String name, String phone_num) {
        this.email = email;
        this.name = name;
        this.phoneNum = phone_num;
    }

    public User(String email, String name, String phone_num, String latitude, String longitude) {
        this.email = email;
        this.name = name;
        this.phoneNum = phone_num;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User(String email, String name, String phone_num, String stream, String classOfStudy, String examPreparingFor, String board) {
        this.email = email;
        this.name = name;
        this.phoneNum = phone_num;
        this.classOfStudy = classOfStudy;
        this.stream  =stream;
        this.board = board;
        this.examPreparingFor = examPreparingFor;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phoneNum;
    }

    public void setPhone_num(String phone_num) {
        this.phoneNum = phone_num;
    }

    public String getStream(){
        return stream;
    }

    public void setStream(String stream){
        this.stream = stream;
    }

    public String getBoard(){
        return board;
    }

    public void setBoard(String board){
        this.board = board;
    }

    public String getClassOfStudy(){
        return classOfStudy;
    }

    public void setClassOfStudy(String classOfStudy){
        this.classOfStudy = classOfStudy;
    }

    public String getExamPreparingFor(){
        return examPreparingFor;
    }

    public void setExamPreparingFor(String examPreparingFor){
        this.examPreparingFor = examPreparingFor;
    }


}