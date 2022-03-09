package qa.engineeric.store.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A SliderImageSlider.
 */
@Document(collection = "slider_image_slider")
public class SliderImageSlider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("store_owner_id")
    private String storeOwnerId;

    @Field("web_key")
    private String webKey;

    @Field("image_url")
    private String imageUrl;

    @Field("alt")
    private String alt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SliderImageSlider id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreOwnerId() {
        return this.storeOwnerId;
    }

    public SliderImageSlider storeOwnerId(String storeOwnerId) {
        this.setStoreOwnerId(storeOwnerId);
        return this;
    }

    public void setStoreOwnerId(String storeOwnerId) {
        this.storeOwnerId = storeOwnerId;
    }

    public String getWebKey() {
        return this.webKey;
    }

    public SliderImageSlider webKey(String webKey) {
        this.setWebKey(webKey);
        return this;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public SliderImageSlider imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAlt() {
        return this.alt;
    }

    public SliderImageSlider alt(String alt) {
        this.setAlt(alt);
        return this;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SliderImageSlider)) {
            return false;
        }
        return id != null && id.equals(((SliderImageSlider) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SliderImageSlider{" +
            "id=" + getId() +
            ", storeOwnerId='" + getStoreOwnerId() + "'" +
            ", webKey='" + getWebKey() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", alt='" + getAlt() + "'" +
            "}";
    }
}
