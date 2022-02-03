package qa.engineeric.store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import qa.engineeric.store.web.rest.TestUtil;

class StoreCategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreCategories.class);
        StoreCategories storeCategories1 = new StoreCategories();
        storeCategories1.setId("id1");
        StoreCategories storeCategories2 = new StoreCategories();
        storeCategories2.setId(storeCategories1.getId());
        assertThat(storeCategories1).isEqualTo(storeCategories2);
        storeCategories2.setId("id2");
        assertThat(storeCategories1).isNotEqualTo(storeCategories2);
        storeCategories1.setId(null);
        assertThat(storeCategories1).isNotEqualTo(storeCategories2);
    }
}
