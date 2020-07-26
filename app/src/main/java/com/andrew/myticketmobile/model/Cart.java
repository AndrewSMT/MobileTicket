package com.andrew.myticketmobile.model;


import java.util.HashSet;
import java.util.Set;

public class Cart {
    private Long id_cart;
    private Long id_order;
    private Set<Ticket> ticket = new HashSet<>();
}
