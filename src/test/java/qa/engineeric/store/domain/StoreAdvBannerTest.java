package qa.engineeric.store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import qa.engineeric.store.web.rest.TestUtil;

class StoreAdvBannerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreAdvBanner.class);
        StoreAdvBanner storeAdvBanner1 = new StoreAdvBanner();
        storeAdvBanner1.setId("id1");
        StoreAdvBanner storeAdvBanner2 = new StoreAdvBanner();
        storeAdvBanner2.setId(storeAdvBanner1.getId());
        assertThat(storeAdvBanner1).isEqualTo(storeAdvBanner2);
        storeAdvBanner2.setId("id2");
        assertThat(storeAdvBanner1).isNotEqualTo(storeAdvBanner2);
        storeAdvBanner1.setId(null);
        assertThat(storeAdvBanner1).isNotEqualTo(storeAdvBanner2);
    }
}
