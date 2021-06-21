package com.example.loans.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loan {
    @Expose
    @SerializedName("loan_id")
    private int loanId;

    @Expose
    @SerializedName("item_code")
    private String itemCode;

    @Expose
    @SerializedName("member_id")
    private String memberId;

    @Expose
    @SerializedName("loan_date")
    private String loanDate;

    @Expose
    @SerializedName("due_date")
    private String dueDate;

    @Expose
    @SerializedName("renewed")
    private int renewed;

    @Expose
    @SerializedName("loan_rules_id")
    private int loanRulesId;

    @Expose
    @SerializedName("is_lent")
    private int isLent;

    @Expose
    @SerializedName("is_return")
    private int isReturn;

    @Expose
    @SerializedName("return_date")
    private String returnDate;

    @Expose
    @SerializedName("input_date")
    private String inputDate;

    @Expose
    @SerializedName("last_update")
    private String lastUpdate;

    @Expose
    @SerializedName("uid")
    private int uid;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getRenewed() {
        return renewed;
    }

    public void setRenewed(int renewed) {
        this.renewed = renewed;
    }

    public int getLoanRulesId() {
        return loanRulesId;
    }

    public void setLoanRulesId(int loanRulesId) {
        this.loanRulesId = loanRulesId;
    }

    public Boolean isLent() {
        return isLent == 1;
    }

    public void setIsLent(int isLent) {
        this.isLent = isLent;
    }

    public Boolean isReturn() {
        return isReturn == 1;
    }

    public void setIsReturn(int isReturn) {
        this.isReturn = isReturn;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
