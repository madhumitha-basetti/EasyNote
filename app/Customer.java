package com.example.easynote;

import java.io.Serializable;

public class Customer implements Serializable {

    public String CustomerName, ItemName, Status,Quantity, Amount;


    public Customer() {

    }

    public Customer(String customerName, String itemName, String quantity, String amount, String status) {
        CustomerName = customerName;
        ItemName = itemName;
        Quantity = quantity;
        Amount = amount;
        Status = status;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}