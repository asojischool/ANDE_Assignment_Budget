package com.example.ande_assignment_budget.Model;

public class BillsModel {
    private String billName;
    private String billAmt;
    private int billDueDay;
    private String billStatus;

    public BillsModel(String billName, String billAmt, int billDueDay, String billStatus) {
        this.billName = billName;
        this.billAmt = billAmt;
        this.billDueDay = billDueDay;
        this.billStatus = billStatus;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public int getBillDueDay() {
        return billDueDay;
    }

    public void setBillDueDay(int billDueDay) {
        this.billDueDay = billDueDay;
    }

    public void setBillAmt(String billAmt) {
        this.billAmt = billAmt;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillName() {
        return billName;
    }

    public String getBillAmt() {
        return billAmt;
    }


    public String getBillStatus() {
        return billStatus;
    }
}
