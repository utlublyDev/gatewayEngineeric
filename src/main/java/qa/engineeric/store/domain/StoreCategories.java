package qa.engineeric.store.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A StoreCategories.
 */
@Document(collection = "store_categories")
public class StoreCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("user_store_owner_id")
    private String userStoreOwnerId;

    @NotNull(message = "must not be null")
    @Field("category_name")
    private String categoryName;

    @NotNull(message = "must not be null")
    @Field("description")
    private String description;

    @NotNull(message = "must not be null")
    @Field("category_name_in_arabic")
    private String categoryNameInArabic;

    @Field("description_in_arabic")
    private String descriptionInArabic;

    @Field("user_store_owner_id_image_url")
    private String userStoreOwnerIdImageUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public StoreCategories id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserStoreOwnerId() {
        return this.userStoreOwnerId;
    }

    public StoreCategories userStoreOwnerId(String userStoreOwnerId) {
        this.setUserStoreOwnerId(userStoreOwnerId);
        return this;
    }

    public void setUserStoreOwnerId(String userStoreOwnerId) {
        this.userStoreOwnerId = userStoreOwnerId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public StoreCategories categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return this.description;
    }

    public StoreCategories description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryNameInArabic() {
        return this.categoryNameInArabic;
    }

    public StoreCategories categoryNameInArabic(String categoryNameInArabic) {
        this.setCategoryNameInArabic(categoryNameInArabic);
        return this;
    }

    public void setCategoryNameInArabic(String categoryNameInArabic) {
        this.categoryNameInArabic = categoryNameInArabic;
    }

    public String getDescriptionInArabic() {
        return this.descriptionInArabic;
    }

    public StoreCategories descriptionInArabic(String descriptionInArabic) {
        this.setDescriptionInArabic(descriptionInArabic);
        return this;
    }

    public void setDescriptionInArabic(String descriptionInArabic) {
        this.descriptionInArabic = descriptionInArabic;
    }

    public String getUserStoreOwnerIdImageUrl() {
        return this.userStoreOwnerIdImageUrl;
    }

    public StoreCategories userStoreOwnerIdImageUrl(String userStoreOwnerIdImageUrl) {
        this.setUserStoreOwnerIdImageUrl(userStoreOwnerIdImageUrl);
        return this;
    }

    public void setUserStoreOwnerIdImageUrl(String userStoreOwnerIdImageUrl) {
        this.userStoreOwnerIdImageUrl = userStoreOwnerIdImageUrl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreCategories)) {
            return false;
        }
        return id != null && id.equals(((StoreCategories) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreCategories{" +
            "id=" + getId() +
            ", userStoreOwnerId='" + getUserStoreOwnerId() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", description='" + getDescription() + "'" +
            ", categoryNameInArabic='" + getCategoryNameInArabic() + "'" +
            ", descriptionInArabic='" + getDescriptionInArabic() + "'" +
            ", userStoreOwnerIdImageUrl='" + getUserStoreOwnerIdImageUrl() + "'" +
            "}";
    }
}
