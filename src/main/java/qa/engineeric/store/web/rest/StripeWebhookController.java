package qa.engineeric.store.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.engineeric.store.domain.OrderItem;

@RestController
@RequestMapping("/api")
public class StripeWebhookController {

    @Value("${stripe.privateApiKey}")
    String stripeApiKey;

    @Value("${stripe.webhook}")
    String stripeWebhookSecret;

    @ResponseBody
    @RequestMapping(consumes = "application/json", produces = "application/json", method = RequestMethod.POST, value = "/webhook")
    public HttpStatus stripeWebhookEndpoint(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.reader();
        WebResources web = new WebResources();
        JsonNode node = reader.readTree(json);

        JsonNode jsonNodeFiltered = node.get("data");
        JsonNode jsonNodeFilteredObject = jsonNodeFiltered.get("object");
        JsonNode jsonNodeFilteredIsPaid = jsonNodeFilteredObject.get("payment_status");
        if (jsonNodeFilteredObject != null) {
            JsonNode jsonNodeFilteredPayment = jsonNodeFilteredObject.get("payment_intent");
            OrderItem order = new OrderItem();

            order = web.send_Request_To_Product_MicroService_To_GetCurrent_Payment(jsonNodeFilteredPayment.asText());
            order.setStatus(jsonNodeFilteredIsPaid.asText());
            try {
                web.send_Request_To_Product_MicroService_To_UpdateCurrent_Payment(order);
            } catch (Exception e) {}
        }

        return HttpStatus.OK;
    }
}
