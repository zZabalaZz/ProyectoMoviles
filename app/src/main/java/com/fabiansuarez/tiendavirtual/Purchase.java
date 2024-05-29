package com.fabiansuarez.tiendavirtual;

import java.util.Date;
import java.util.List;

public class Purchase {
    private Date date;
    private List<CartItem> items;
    private double totalPrice;

    public Purchase(Date date, List<CartItem> items, double totalPrice) {
        this.date = date;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
