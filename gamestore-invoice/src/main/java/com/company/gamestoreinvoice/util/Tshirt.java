package com.company.gamestoreinvoice.util;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

public class Tshirt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tshirt_id")
    private long id;
    private String size;
    private String color;
    private String description;
    private BigDecimal price;
    private long quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tshirt)) return false;
        Tshirt tshirt = (Tshirt) o;
        return getId() == tshirt.getId() &&
                getQuantity() == tshirt.getQuantity() &&
                Objects.equals(getSize(), tshirt.getSize()) &&
                Objects.equals(getColor(), tshirt.getColor()) &&
                Objects.equals(getDescription(), tshirt.getDescription()) &&
                Objects.equals(getPrice(), tshirt.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSize(), getColor(), getDescription(), getPrice(), getQuantity());
    }
}
