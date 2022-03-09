package qa.engineeric.store.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.engineeric.store.service.dto.CheckoutItemDto;

@Service
@Transactional
public class OrderService {

    @Value("${stripe.privateApiKey}")
    private String apiKey;

    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData
            .builder()
            .setCurrency("qar")
            .setUnitAmount(((long) checkoutItemDto.getPrice()) * 100)
            .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(checkoutItemDto.getProductName()).build())
            .build();
    }

    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem
            .builder()
            .setPriceData(createPriceData(checkoutItemDto))
            .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
            .build();
    }

    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        String successURL = "http://localhost:9000/success";
        String failedURL = "http://localhost:9000/";

        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<SessionCreateParams.LineItem>();
        for (CheckoutItemDto checkoutItemDto : checkoutItemDtoList) {
            sessionItemsList.add(createSessionLineItem(checkoutItemDto)); //here
        }

        SessionCreateParams params = SessionCreateParams
            .builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .addShippingRate("shr_1KRjgeJCSwzHFDV5nxUsnP8v")
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setCancelUrl(failedURL)
            .addAllLineItem(sessionItemsList)
            .setSuccessUrl(successURL)
            .build();

        return Session.create(params);
    }
}
