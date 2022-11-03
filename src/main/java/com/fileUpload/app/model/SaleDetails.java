package com.fileUpload.app.model;

import java.util.Objects;

/**
 * SaleDetails
 *
 */
public class SaleDetails {
    private int itemId;
    private int amount;
    private double price;

    public SaleDetails(int itemId, int amount, double price) {
        this.itemId = itemId;
        this.amount = amount;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleDetails that = (SaleDetails) o;
        return itemId == that.itemId && amount == that.amount && Double.compare(that.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, amount, price);
    }
}
