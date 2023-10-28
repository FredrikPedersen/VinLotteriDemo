package com.fredrikpedersen.experis_vin_lotteri.services.payment;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Mock of service used to send and verify Vipps payment requests.
 *
 * Would return something a bit more sophisticated than a Boolean to be able to better handle failure states.
 * I.e if the user cancels the payment that should not lead to an exception, but if the external API can't be reached
 * it should.
 */

@Log4j2
@Service
public class PaymentServiceMockImpl implements PaymentService {
    @Override
    public Boolean sendVippsPaymentRequest(final String phonenumber) {
        log.info("Sending Vipps Payment Request and awaiting user to pay...");
        log.info("Payment complete!");
        return true;
    }
}
