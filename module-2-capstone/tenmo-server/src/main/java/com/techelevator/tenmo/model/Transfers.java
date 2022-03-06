package com.techelevator.tenmo.model;

public class Transfers {
    private int id;
    private int typeId;
    private int statusId;
    private int accountFrom;
    private int accountTo;
    private double amount;

    public Transfers() {

    }

    public Transfers(int id, int type, int status, int from, int to, double amount) {
        this.id = id;
        this.typeId = type;
        this.statusId = status;
        this.accountFrom = from;
        this.accountTo = to;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
