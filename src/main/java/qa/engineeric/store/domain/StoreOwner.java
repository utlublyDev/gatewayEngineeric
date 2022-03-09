package qa.engineeric.store.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A StoreOwner.
 */
@Document(collection = "store_owner")
public class StoreOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("user_store_owner_id")
    private String userStoreOwnerId;

    @NotNull(message = "must not be null")
    @Field("store_name")
    private String storeName;

    @NotNull(message = "must not be null")
    @Field("store_name_in_arabic")
    private String storeNameInArabic;

    @NotNull(message = "must not be null")
    @Field("address")
    private String address;

    @NotNull(message = "must not be null")
    @Field("address_in_arabic")
    private String addressInArabic;

    @NotNull(message = "must not be null")
    @Field("longitude")
    private Double longitude;

    @NotNull(message = "must not be null")
    @Field("latitude")
    private Double latitude;

    @NotNull(message = "must not be null")
    @Field("is_busy")
    private Boolean isBusy;

    @NotNull(message = "must not be null")
    @Field("city")
    private String city;

    @NotNull(message = "must not be null")
    @Field("city_in_arabic")
    private String cityInArabic;

    @Field("description")
    private String description;

    @Field("description_in_arabic")
    private String descriptionInArabic;

    @Field("store_contact_number")
    private String storeContactNumber;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("store_logo_url")
    private String storeLogoUrl;

    @Field("is_active")
    private Boolean isActive;

    @NotNull(message = "must not be null")
    @Field("has_delivery")
    private Boolean hasDelivery;

    @Field("has_free_delivery")
    private Boolean hasFreeDelivery;

    @NotNull(message = "must not be null")
    @Field("available_date_time")
    private ZonedDateTime availableDateTime;

    @Field("shop_opeining_time")
    private Instant shopOpeiningTime;

    @Field("shop_closing_time")
    private Instant shopClosingTime;

    @Field("currency")
    private String currency;

    @Field("delivery_cost")
    private Double deliveryCost;

    @Field("web_key")
    private String webKey;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public StoreOwner id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserStoreOwnerId() {
        return this.userStoreOwnerId;
    }

    public StoreOwner userStoreOwnerId(String userStoreOwnerId) {
        this.setUserStoreOwnerId(userStoreOwnerId);
        return this;
    }

    public void setUserStoreOwnerId(String userStoreOwnerId) {
        this.userStoreOwnerId = userStoreOwnerId;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public StoreOwner storeName(String storeName) {
        this.setStoreName(storeName);
        return this;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNameInArabic() {
        return this.storeNameInArabic;
    }

    public StoreOwner storeNameInArabic(String storeNameInArabic) {
        this.setStoreNameInArabic(storeNameInArabic);
        return this;
    }

    public void setStoreNameInArabic(String storeNameInArabic) {
        this.storeNameInArabic = storeNameInArabic;
    }

    public String getAddress() {
        return this.address;
    }

    public StoreOwner address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressInArabic() {
        return this.addressInArabic;
    }

    public StoreOwner addressInArabic(String addressInArabic) {
        this.setAddressInArabic(addressInArabic);
        return this;
    }

    public void setAddressInArabic(String addressInArabic) {
        this.addressInArabic = addressInArabic;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public StoreOwner longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public StoreOwner latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getIsBusy() {
        return this.isBusy;
    }

    public StoreOwner isBusy(Boolean isBusy) {
        this.setIsBusy(isBusy);
        return this;
    }

    public void setIsBusy(Boolean isBusy) {
        this.isBusy = isBusy;
    }

    public String getCity() {
        return this.city;
    }

    public StoreOwner city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityInArabic() {
        return this.cityInArabic;
    }

    public StoreOwner cityInArabic(String cityInArabic) {
        this.setCityInArabic(cityInArabic);
        return this;
    }

    public void setCityInArabic(String cityInArabic) {
        this.cityInArabic = cityInArabic;
    }

    public String getDescription() {
        return this.description;
    }

    public StoreOwner description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionInArabic() {
        return this.descriptionInArabic;
    }

    public StoreOwner descriptionInArabic(String descriptionInArabic) {
        this.setDescriptionInArabic(descriptionInArabic);
        return this;
    }

    public void setDescriptionInArabic(String descriptionInArabic) {
        this.descriptionInArabic = descriptionInArabic;
    }

    public String getStoreContactNumber() {
        return this.storeContactNumber;
    }

    public StoreOwner storeContactNumber(String storeContactNumber) {
        this.setStoreContactNumber(storeContactNumber);
        return this;
    }

    public void setStoreContactNumber(String storeContactNumber) {
        this.storeContactNumber = storeContactNumber;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public StoreOwner createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getStoreLogoUrl() {
        return this.storeLogoUrl;
    }

    public StoreOwner storeLogoUrl(String storeLogoUrl) {
        this.setStoreLogoUrl(storeLogoUrl);
        return this;
    }

    public void setStoreLogoUrl(String storeLogoUrl) {
        this.storeLogoUrl = storeLogoUrl;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public StoreOwner isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getHasDelivery() {
        return this.hasDelivery;
    }

    public StoreOwner hasDelivery(Boolean hasDelivery) {
        this.setHasDelivery(hasDelivery);
        return this;
    }

    public void setHasDelivery(Boolean hasDelivery) {
        this.hasDelivery = hasDelivery;
    }

    public Boolean getHasFreeDelivery() {
        return this.hasFreeDelivery;
    }

    public StoreOwner hasFreeDelivery(Boolean hasFreeDelivery) {
        this.setHasFreeDelivery(hasFreeDelivery);
        return this;
    }

    public void setHasFreeDelivery(Boolean hasFreeDelivery) {
        this.hasFreeDelivery = hasFreeDelivery;
    }

    public ZonedDateTime getAvailableDateTime() {
        return this.availableDateTime;
    }

    public StoreOwner availableDateTime(ZonedDateTime availableDateTime) {
        this.setAvailableDateTime(availableDateTime);
        return this;
    }

    public void setAvailableDateTime(ZonedDateTime availableDateTime) {
        this.availableDateTime = availableDateTime;
    }

    public Instant getShopOpeiningTime() {
        return this.shopOpeiningTime;
    }

    public StoreOwner shopOpeiningTime(Instant shopOpeiningTime) {
        this.setShopOpeiningTime(shopOpeiningTime);
        return this;
    }

    public void setShopOpeiningTime(Instant shopOpeiningTime) {
        this.shopOpeiningTime = shopOpeiningTime;
    }

    public Instant getShopClosingTime() {
        return this.shopClosingTime;
    }

    public StoreOwner shopClosingTime(Instant shopClosingTime) {
        this.setShopClosingTime(shopClosingTime);
        return this;
    }

    public void setShopClosingTime(Instant shopClosingTime) {
        this.shopClosingTime = shopClosingTime;
    }

    public String getCurrency() {
        return this.currency;
    }

    public StoreOwner currency(String currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getDeliveryCost() {
        return this.deliveryCost;
    }

    public StoreOwner deliveryCost(Double deliveryCost) {
        this.setDeliveryCost(deliveryCost);
        return this;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getWebKey() {
        return this.webKey;
    }

    public StoreOwner webKey(String webKey) {
        this.setWebKey(webKey);
        return this;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreOwner)) {
            return false;
        }
        return id != null && id.equals(((StoreOwner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreOwner{" +
            "id=" + getId() +
            ", userStoreOwnerId='" + getUserStoreOwnerId() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", storeNameInArabic='" + getStoreNameInArabic() + "'" +
            ", address='" + getAddress() + "'" +
            ", addressInArabic='" + getAddressInArabic() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", isBusy='" + getIsBusy() + "'" +
            ", city='" + getCity() + "'" +
            ", cityInArabic='" + getCityInArabic() + "'" +
            ", description='" + getDescription() + "'" +
            ", descriptionInArabic='" + getDescriptionInArabic() + "'" +
            ", storeContactNumber='" + getStoreContactNumber() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", storeLogoUrl='" + getStoreLogoUrl() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", hasDelivery='" + getHasDelivery() + "'" +
            ", hasFreeDelivery='" + getHasFreeDelivery() + "'" +
            ", availableDateTime='" + getAvailableDateTime() + "'" +
            ", shopOpeiningTime='" + getShopOpeiningTime() + "'" +
            ", shopClosingTime='" + getShopClosingTime() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", deliveryCost=" + getDeliveryCost() +
            ", webKey='" + getWebKey() + "'" +
            "}";
    }
}
