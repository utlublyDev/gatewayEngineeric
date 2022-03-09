package qa.engineeric.store.web.rest;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qa.engineeric.store.domain.OrderItem;
import qa.engineeric.store.domain.StripeResponse;
import qa.engineeric.store.domain.res_products;
import qa.engineeric.store.service.OrderService;
import qa.engineeric.store.service.dto.CheckoutItemDto;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<res_products> res_productsList) throws StripeException {
        //convert the list from react store to current object :)

        List<CheckoutItemDto> checkoutItemDtoList = new ArrayList<>();
        OrderItem orders = new OrderItem();
        WebResources web = new WebResources();
        double Total_price = 0;

        for (int i = 0; i < res_productsList.size(); i++) {
            CheckoutItemDto CheckoutItemDtoObj = new CheckoutItemDto();
            List<String> imagesUrl = new ArrayList<>();

            imagesUrl.add(res_productsList.get(i).getImage());
            CheckoutItemDtoObj.setImages(imagesUrl);
            CheckoutItemDtoObj.setProductId(res_productsList.get(i).getId());
            CheckoutItemDtoObj.setQuantity(1);
            CheckoutItemDtoObj.setUserId(1234);
            CheckoutItemDtoObj.setProductName(res_productsList.get(i).getTitle());
            CheckoutItemDtoObj.setPrice(res_productsList.get(i).getPrice());
            checkoutItemDtoList.add(CheckoutItemDtoObj);
            Total_price = Total_price + res_productsList.get(i).getPrice();
        }
        System.out.println("check here");
        System.out.println(checkoutItemDtoList);
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        ZonedDateTime lt = ZonedDateTime.now();
        orders.setOrderNumber(getRandomNumberString());
        orders.setPaymentId(session.getPaymentIntent());
        orders.setStatus(session.getPaymentStatus());
        orders.setDateAdded(lt);
        orders.setTotalPrice(Total_price);
        orders.setShoppingCart(checkoutItemDtoList);
        orders.setUserStoreOwnerId(res_productsList.get(0).getUserStoreOwnerId());
        orders.setUserId(res_productsList.get(0).getUserId());
        orders.setWebKey(res_productsList.get(0).getWebKey());
        web.send_Request_To_Product_MicroService(orders);

        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
