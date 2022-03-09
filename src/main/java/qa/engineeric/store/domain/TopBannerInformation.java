package qa.engineeric.store.domain;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A TopBannerInformation.
 */
@Document(collection = "top_banner_information")
public class TopBannerInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("enable_banner")
    private Boolean enableBanner;

    @Field("banner_text")
    private String bannerText;

    @Field("start_banner")
    private Instant startBanner;

    @Field("end_banner")
    private Instant endBanner;

    @Field("web_key")
    private String webKey;

    @Field("store_owner_id")
    private String storeOwnerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public TopBannerInformation id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getEnableBanner() {
        return this.enableBanner;
    }

    public TopBannerInformation enableBanner(Boolean enableBanner) {
        this.setEnableBanner(enableBanner);
        return this;
    }

    public void setEnableBanner(Boolean enableBanner) {
        this.enableBanner = enableBanner;
    }

    public String getBannerText() {
        return this.bannerText;
    }

    public TopBannerInformation bannerText(String bannerText) {
        this.setBannerText(bannerText);
        return this;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
    }

    public Instant getStartBanner() {
        return this.startBanner;
    }

    public TopBannerInformation startBanner(Instant startBanner) {
        this.setStartBanner(startBanner);
        return this;
    }

    public void setStartBanner(Instant startBanner) {
        this.startBanner = startBanner;
    }

    public Instant getEndBanner() {
        return this.endBanner;
    }

    public TopBannerInformation endBanner(Instant endBanner) {
        this.setEndBanner(endBanner);
        return this;
    }

    public void setEndBanner(Instant endBanner) {
        this.endBanner = endBanner;
    }

    public String getWebKey() {
        return this.webKey;
    }

    public TopBannerInformation webKey(String webKey) {
        this.setWebKey(webKey);
        return this;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public String getStoreOwnerId() {
        return this.storeOwnerId;
    }

    public TopBannerInformation storeOwnerId(String storeOwnerId) {
        this.setStoreOwnerId(storeOwnerId);
        return this;
    }

    public void setStoreOwnerId(String storeOwnerId) {
        this.storeOwnerId = storeOwnerId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopBannerInformation)) {
            return false;
        }
        return id != null && id.equals(((TopBannerInformation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopBannerInformation{" +
            "id=" + getId() +
            ", enableBanner='" + getEnableBanner() + "'" +
            ", bannerText='" + getBannerText() + "'" +
            ", startBanner='" + getStartBanner() + "'" +
            ", endBanner='" + getEndBanner() + "'" +
            ", webKey='" + getWebKey() + "'" +
            ", storeOwnerId='" + getStoreOwnerId() + "'" +
            "}";
    }
}
