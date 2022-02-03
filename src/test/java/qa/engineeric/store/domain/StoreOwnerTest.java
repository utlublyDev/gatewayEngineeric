package qa.engineeric.store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import qa.engineeric.store.web.rest.TestUtil;

class StoreOwnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreOwner.class);
        StoreOwner storeOwner1 = new StoreOwner();
        storeOwner1.setId("id1");
        StoreOwner storeOwner2 = new StoreOwner();
        storeOwner2.setId(storeOwner1.getId());
        assertThat(storeOwner1).isEqualTo(storeOwner2);
        storeOwner2.setId("id2");
        assertThat(storeOwner1).isNotEqualTo(storeOwner2);
        storeOwner1.setId(null);
        assertThat(storeOwner1).isNotEqualTo(storeOwner2);
    }
}
