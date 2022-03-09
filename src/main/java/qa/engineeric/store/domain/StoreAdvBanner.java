package qa.engineeric.store.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A StoreAdvBanner.
 */
@Document(collection = "store_adv_banner")
public class StoreAdvBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("store_owner_id")
    private String storeOwnerId;

    @Field("web_key")
    private String webKey;

    @Field("image_url")
    private String imageUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public StoreAdvBanner id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreOwnerId() {
        return this.storeOwnerId;
    }

    public StoreAdvBanner storeOwnerId(String storeOwnerId) {
        this.setStoreOwnerId(storeOwnerId);
        return this;
    }

    public void setStoreOwnerId(String storeOwnerId) {
        this.storeOwnerId = storeOwnerId;
    }

    public String getWebKey() {
        return this.webKey;
    }

    public StoreAdvBanner webKey(String webKey) {
        this.setWebKey(webKey);
        return this;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public StoreAdvBanner imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreAdvBanner)) {
            return false;
        }
        return id != null && id.equals(((StoreAdvBanner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreAdvBanner{" +
            "id=" + getId() +
            ", storeOwnerId='" + getStoreOwnerId() + "'" +
            ", webKey='" + getWebKey() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
