package qa.engineeric.store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import qa.engineeric.store.web.rest.TestUtil;

class TopBannerInformationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopBannerInformation.class);
        TopBannerInformation topBannerInformation1 = new TopBannerInformation();
        topBannerInformation1.setId("id1");
        TopBannerInformation topBannerInformation2 = new TopBannerInformation();
        topBannerInformation2.setId(topBannerInformation1.getId());
        assertThat(topBannerInformation1).isEqualTo(topBannerInformation2);
        topBannerInformation2.setId("id2");
        assertThat(topBannerInformation1).isNotEqualTo(topBannerInformation2);
        topBannerInformation1.setId(null);
        assertThat(topBannerInformation1).isNotEqualTo(topBannerInformation2);
    }
}
