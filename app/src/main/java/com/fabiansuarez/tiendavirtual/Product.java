package com.fabiansuarez.tiendavirtual;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private Double price;
    private String urlImage;
    private String descripcion;

    // Constructor
    public Product(String id, String name, Double price, String urlImage, String descripcion) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
