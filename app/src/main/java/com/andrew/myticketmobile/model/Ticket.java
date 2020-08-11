package com.andrew.myticketmobile.model;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.andrew.myticketmobile.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.andrew.myticketmobile.adapters.OrderAdapter.order;

public class Ticket {
    private Long id;
    private Long event;
    private Integer row;
    private Integer number;
    private Integer price;
    private Set<String> ticketStatus;
    private Long orderNumber;


    public Ticket(){
    }

    public Ticket(Long id, Long event, Integer row, Integer number, Integer price, Set<String> ticketStatus, Long orderNumber) {
        this.id = id;
        this.event = event;
        this.row = row;
        this.number = number;
        this.price = price;
        this.ticketStatus = ticketStatus;
        this.orderNumber = orderNumber;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static StringBuilder stringTicket(Ticket ticket) {
        StringBuilder stringTicket = new StringBuilder();
        Event event = MainActivity.events.get(Math.toIntExact(ticket.getEvent()-4));
        stringTicket.append(ticket.getId()+";");
        stringTicket.append(event.getTitle()+";");
        stringTicket.append(event.getDate()+";");
        stringTicket.append(event.getPlace()+";");
        stringTicket.append(event.getCityTitle()+";");
        stringTicket.append(ticket.getRow()+";");
        stringTicket.append(ticket.getNumber()+";");
        stringTicket.append(ticket.getPrice()+";");
        stringTicket.append(ticket.getTicketStatus()+";");
        stringTicket.append(order.getOrderId()+";");
        return stringTicket;
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
