package com.andrew.myticketmobile.model;


import java.util.HashSet;
import java.util.Set;

public class Orderr {

    private Set<OrderStatus> orderStatus;
    private User user;
    private Set<Ticket> ticket = new HashSet<>();
}
