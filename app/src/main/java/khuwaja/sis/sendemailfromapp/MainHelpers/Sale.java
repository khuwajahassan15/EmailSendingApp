package com.sis.clightapp.model.GsonModel;

public class Sale {

    private String label;
    private String bolt11;
    private String payment_hash;
    private double msatoshi;
    private String amount_msat;
    private String status;
    private double pay_index;
    private double msatoshi_received;
    private String amount_received_msat;
    private long  paid_at;
    private String payment_preimage;
    private String description;
    private long expires_at;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBolt11() {
        return bolt11;
    }

    public void setBolt11(String bolt11) {
        this.bolt11 = bolt11;
    }

    public String getPayment_hash() {
        return payment_hash;
    }

    public void setPayment_hash(String payment_hash) {
        this.payment_hash = payment_hash;
    }

    public double getMsatoshi() {
        return msatoshi;
    }

    public void setMsatoshi(double msatoshi) {
        this.msatoshi = msatoshi;
    }

    public String getAmount_msat() {
        return amount_msat;
    }

    public void setAmount_msat(String amount_msat) {
        this.amount_msat = amount_msat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPay_index() {
        return pay_index;
    }

    public void setPay_index(double pay_index) {
        this.pay_index = pay_index;
    }

    public double getMsatoshi_received() {
        return msatoshi_received;
    }

    public void setMsatoshi_received(double msatoshi_received) {
        this.msatoshi_received = msatoshi_received;
    }

    public String getAmount_received_msat() {
        return amount_received_msat;
    }

    public void setAmount_received_msat(String amount_received_msat) {
        this.amount_received_msat = amount_received_msat;
    }

    public long getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(long paid_at) {
        this.paid_at = paid_at;
    }

    public String getPayment_preimage() {
        return payment_preimage;
    }

    public void setPayment_preimage(String payment_preimage) {
        this.payment_preimage = payment_preimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(long expires_at) {
        this.expires_at = expires_at;
    }
}
