package qa.engineeric.store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import qa.engineeric.store.web.rest.TestUtil;

class SliderImageSliderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SliderImageSlider.class);
        SliderImageSlider sliderImageSlider1 = new SliderImageSlider();
        sliderImageSlider1.setId("id1");
        SliderImageSlider sliderImageSlider2 = new SliderImageSlider();
        sliderImageSlider2.setId(sliderImageSlider1.getId());
        assertThat(sliderImageSlider1).isEqualTo(sliderImageSlider2);
        sliderImageSlider2.setId("id2");
        assertThat(sliderImageSlider1).isNotEqualTo(sliderImageSlider2);
        sliderImageSlider1.setId(null);
        assertThat(sliderImageSlider1).isNotEqualTo(sliderImageSlider2);
    }
}
