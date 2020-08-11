package com.andrew.myticketmobile.model;


import java.util.HashSet;
import java.util.Set;

public class Order {
    private Long orderId;
    private Set<String> orderStatus;
    private Set<Ticket> ticket = new HashSet<>();

    public Order(Long orderId, Set<String> orderStatus, Set<Ticket> ticket) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.ticket = ticket;
    }

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Set<String> getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Set<String> orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Set<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(Set<Ticket> ticket) {
        this.ticket = ticket;
    }
}
