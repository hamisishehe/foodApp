package com.example.fooddelivery;

public class OrdersModel {
    int order_id, totalprice,quantity,status;
    String image,name;

    public OrdersModel(int order_id, int totalprice, int quantity, int status, String image, String name ) {
        this.order_id = order_id;
        this.totalprice = totalprice;
        this.quantity = quantity;
        this.image = image;
        this.name = name;
        this.status = status;
    }



    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
