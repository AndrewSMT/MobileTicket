package com.andrew.myticketmobile.model;


import java.util.HashSet;
import java.util.Set;

public class Ticket {
    private Long id;
    private Long event;
    private Integer row;
    private Integer number;
    private Integer price;
    private Set<String> ticketStatus;
    private Long orderNumber;


    public Ticket(Long id, Long event, Integer row, Integer number, Integer price, Set<String> ticketStatus, Long orderNumber) {
        this.id = id;
        this.event = event;
        this.row = row;
        this.number = number;
        this.price = price;
        this.ticketStatus = ticketStatus;
        this.orderNumber = orderNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
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

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }
}
