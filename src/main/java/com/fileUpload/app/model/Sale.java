package com.fileUpload.app.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Sale {

    private int saleId;

    private Set<SaleDetails> saleDetails;

    private  Seller seller;

    private  double total;

    public Sale(int saleId, Seller seller) {
        this(saleId, new HashSet<>(), seller, 0.0);
    }

    public Sale(int saleId, Set<SaleDetails> saleDetails, Seller seller, double total) {
        this.saleId = saleId;
        this.saleDetails = saleDetails;
        this.seller = seller;
        this.total = calculateTotal();
    }

    private  double calculateTotal(){
        if(this.saleDetails.isEmpty()) return 0.0;
        return this.saleDetails.stream()
                .mapToDouble(saleDetails1 -> saleDetails1.getAmount() * saleDetails1.getPrice()).sum();
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public Set<SaleDetails> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(Set<SaleDetails> saleDetails) {
        this.saleDetails = saleDetails;
        this.total = calculateTotal();
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public double getTotal() {
        return total;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return saleId == sale.saleId && Double.compare(sale.total, total) == 0 && Objects.equals(saleDetails, sale.saleDetails) && Objects.equals(seller, sale.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, saleDetails, seller, total);
    }
}
