package com.andrew.myticketmobile.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FullTicket {
    private Long id;
    private String title;
    private String date;
    private String place;
    private String city;
    private Integer row;
    private Integer number;
    private Integer price;
    private Set<String> ticketStatus;
    private Long orderNumber;


    public FullTicket() {
    }

    public FullTicket(Long id, String title, String date, String place, String city, Integer row, Integer number, Integer price, Set<String> ticketStatus) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.place = place;
        this.city = city;
        this.row = row;
        this.number = number;
        this.price = price;
        this.ticketStatus = ticketStatus;
    }

    public static List<FullTicket> ticketFromPer(Set<String> tickets) {
        List<FullTicket> ticketList = new ArrayList<>();
        for (String stringTicket : tickets) {
            FullTicket ticket = new FullTicket();
            String[] split = stringTicket.split(";");
                ticket.setId(Long.valueOf(split[0]));
                ticket.setTitle(split[1]);
                ticket.setDate(split[2]);
                ticket.setPlace(split[3]);
                ticket.setCity(split[4]);
                ticket.setRow(Integer.valueOf(split[5]));
                ticket.setNumber(Integer.valueOf(split[6]));
                ticket.setPrice(Integer.valueOf(split[7]));
                ticket.setTicketStatus(Collections.singleton(split[8]));
                ticket.setOrderNumber(Long.valueOf(split[9]));
            ticketList.add(ticket);
        }
        return ticketList;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<String> getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Set<String> ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
