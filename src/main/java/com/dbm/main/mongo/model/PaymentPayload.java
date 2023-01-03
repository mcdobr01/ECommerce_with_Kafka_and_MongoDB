package com.dbm.main.mongo.model;

import java.util.Date;
import java.util.Collection;

public class PaymentPayload {
    private Date paymentCompletedDate;
    private double paymentAmount;

    public PaymentPayload(){}

    public PaymentPayload(Date paymentCompletedDate, double paymentAmount) {
        this.paymentCompletedDate = paymentCompletedDate;
        this.paymentAmount = paymentAmount;
    }

    public Date getPaymentCompletedDate() {
        return paymentCompletedDate;
    }

    public void setPaymentCompletedDate(Date paymentCompletedDate) {
        this.paymentCompletedDate = paymentCompletedDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
