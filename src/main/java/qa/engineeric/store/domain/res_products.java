package qa.engineeric.store.domain;

import java.io.Serializable;

public class res_products implements Serializable {

    private int id;
    private String title;
    private double price;
    private String description;
    private String userStoreOwnerId;
    private String webKey;
    private String userId;

    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return (
            "res_products [description=" +
            description +
            ", id=" +
            id +
            ", image=" +
            image +
            ", price=" +
            price +
            ", title=" +
            title +
            ", userStoreOwnerId=" +
            userStoreOwnerId +
            ", webKey=" +
            webKey +
            "]"
        );
    }

    public void setImage(String image) {
        this.image = image;
    }

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
     * @return String return the webKey
     */
    public String getWebKey() {
        return webKey;
    }

    /**
     * @param webKey the webKey to set
     */
    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
