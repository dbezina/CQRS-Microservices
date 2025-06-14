package com.bezina.userService;

import com.bezina.core.model.PaymentDetails;
import com.bezina.core.model.User;
import com.bezina.core.query.FetchUserPaymentDetailsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//@Slf4j
public class UserEventsHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(UserEventsHandler.class);
    @QueryHandler
    public User eventHandler(FetchUserPaymentDetailsQuery query) {
        LOGGER.info("Handling query: {}", query);
        //  log
        PaymentDetails paymentDetails = null;
        User userRest = null;
        try {
            paymentDetails = new PaymentDetails.Builder()
                    .cardNumber("123Card")
                    .cvv("123")
                    .name("SERGEY KARGOPOLOV")
                    .validUntilMonth(12)
                    .validUntilYear(2030)
                    .build();
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        if (paymentDetails != null){
             userRest = new User.Builder()
                    .firstName("Sergey")
                    .lastName("Kargopolov")
                    .userId(query.getUserId())
                    .paymentDetails(paymentDetails)
                    .build();
    } else     LOGGER.error("payment details is null");
        return userRest;
    }
}
