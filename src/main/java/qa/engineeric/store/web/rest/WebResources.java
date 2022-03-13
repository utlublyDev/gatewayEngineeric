package qa.engineeric.store.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import qa.engineeric.store.domain.OrderItem;
import qa.engineeric.store.web.rest.vm.LoginVM;

@RestController
@RequestMapping("/api")
public class WebResources {

    private LoginVM login = new LoginVM();

    RestTemplate restTemplate = new RestTemplate();

    private String JWT() {
        login.setUsername("admin");
        login.setPassword("admin");
        login.setRememberMe(false);
        ResponseEntity<JsonNode> AuthResponse = restTemplate.postForEntity(
            "http://173.231.224.25:8099/api/authenticate",
            login,
            JsonNode.class
        );
        JsonNode jsonNode = AuthResponse.getBody();
        JsonNode jsonNodeFiltered = jsonNode.get("id_token");
        String filteredToken = jsonNodeFiltered.toString().replaceAll("^\"|\"$", "");
        return filteredToken;
    }

    @GetMapping("/endPoint/store/web/public/{userStoreOwnerId}/{webKey}")
    public String GetProductDataToStore(@PathVariable String userStoreOwnerId, @PathVariable String webKey) {
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders = new HttpHeaders();
        reqHeaders.set("Accept", "application/json");
        reqHeaders.set("Authorization", "Bearer " + JWT());
        HttpEntity HTTPentity = new HttpEntity(reqHeaders);
        return restTemplate
            .exchange(
                "http://173.231.224.25:8099/services/product/api/product-categories/store/web/" + userStoreOwnerId + "/" + webKey,
                HttpMethod.GET,
                HTTPentity,
                String.class
            )
            .getBody();
    }

    @GetMapping("/categories-web/store/web/public/Categories/{userStoreOwnerId}/{webKey}")
    public String GetCategoriesDataToStore(@PathVariable String userStoreOwnerId, @PathVariable String webKey) {
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders = new HttpHeaders();
        reqHeaders.set("Accept", "application/json");
        reqHeaders.set("Authorization", "Bearer " + JWT());
        HttpEntity HTTPentity = new HttpEntity(reqHeaders);
        return restTemplate
            .exchange(
                "http://173.231.224.25:8099/services/product/api/product-categories/store/web/categoriesNames/" +
                userStoreOwnerId +
                "/" +
                webKey,
                HttpMethod.GET,
                HTTPentity,
                String.class
            )
            .getBody();
    }

    public void send_Request_To_Product_MicroService(OrderItem orderItem) {
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders = new HttpHeaders();
        reqHeaders.set("Accept", "application/json");
        reqHeaders.set("Authorization", "Bearer " + JWT());
        HttpEntity HTTPentity = new HttpEntity(orderItem, reqHeaders);

        ResponseEntity<OrderItem> SendRequestToMicroServiceOrderObject = restTemplate.postForEntity(
            "http://173.231.224.25:8099/services/product/api/order-items",
            HTTPentity,
            OrderItem.class
        );
    }

    public OrderItem send_Request_To_Product_MicroService_To_GetCurrent_Payment(String Id) {
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders = new HttpHeaders();
        reqHeaders.set("Accept", "application/json");
        reqHeaders.set("Authorization", "Bearer " + JWT());
        HttpEntity HTTPentity = new HttpEntity(reqHeaders);
        return restTemplate
            .exchange(
                "http://173.231.224.25:8099/services/product/api/order-items/by/id/" + Id,
                HttpMethod.GET,
                HTTPentity,
                OrderItem.class
            )
            .getBody();
    }

    public ResponseEntity<String> send_Request_To_Product_MicroService_To_UpdateCurrent_Payment(OrderItem orders) {
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders = new HttpHeaders();
        reqHeaders.set("Accept", "application/json");
        reqHeaders.set("Authorization", "Bearer " + JWT());

        HttpEntity HTTPentity = new HttpEntity(orders, reqHeaders);

        return restTemplate.exchange(
            "http://173.231.224.25:8099/services/product/api/order-items/" + orders.getId(),
            HttpMethod.PUT,
            HTTPentity,
            String.class
        );
    }
}
