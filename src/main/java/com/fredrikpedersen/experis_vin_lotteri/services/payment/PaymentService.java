package com.fredrikpedersen.experis_vin_lotteri.services.payment;

public interface PaymentService {

    Boolean sendVippsPaymentRequest(final String phonenumber);
}
