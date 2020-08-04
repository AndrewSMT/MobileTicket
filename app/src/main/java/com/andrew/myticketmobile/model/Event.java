package com.andrew.myticketmobile.model;


public class Event {
    private Long id;
    private String place;
    private String date;
    private String title;
    private String description;
    private String picture;
    private String cityTitle;



    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public Event(Long id, String place, String date, String title, String description, String picture, String cityTitle) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.cityTitle = cityTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
