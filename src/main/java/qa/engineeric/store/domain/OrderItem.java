package qa.engineeric.store.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Field;

public class OrderItem implements Serializable {

    private String id;
    private String userStoreOwnerId;
    private String webKey;

    private String userId;

    private Integer quantity;

    private double totalPrice;

    private String status;

    private String paymentId;

    private String orderNumber;

    private ZonedDateTime dateAdded;

    private List ShoppingCart;

    /**
     * @return String return the userStoreOwnerId
     */
    public String getUserStoreOwnerId() {
        return userStoreOwnerId;
    }

    /**
     * @param userStoreOwnerId the userStoreOwnerId to set
     */
    public void setUserStoreOwnerId(String userStoreOwnerId) {
        this.userStoreOwnerId = userStoreOwnerId;
    }

    /**
     * @return String return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return Integer return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return BigDecimal return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return String return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return String return the paymentId
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId the paymentId to set
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return Integer return the orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return List return the ShoppingCart
     */
    public List getShoppingCart() {
        return ShoppingCart;
    }

    /**
     * @param ShoppingCart the ShoppingCart to set
     */
    public void setShoppingCart(List ShoppingCart) {
        this.ShoppingCart = ShoppingCart;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }
}
