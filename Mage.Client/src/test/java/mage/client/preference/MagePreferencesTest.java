package mage.client.preference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MagePreferencesTest {
    @Before
    public void setUp() {
        MagePreferences.ignoreList("test.com.xx").clear();
    }

    @After
    public void tearDown() {
        MagePreferences.ignoreList("test.com.xx").clear();
    }

    @Test
    public void testIgnoreList() throws Exception {
        assertThat(MagePreferences.ignoreList("test.com.xx").size(), is(0));
        assertThat(MagePreferences.removeIgnoredUser("test.com.xx", "test"), is(false));

        MagePreferences.addIgnoredUser("test.com.xx", "test");
        assertThat(MagePreferences.ignoreList("test.com.xx").size(), is(1));
        assertThat(MagePreferences.ignoreList("other.com.xx").size(), is(0));

        MagePreferences.addIgnoredUser("test.com.xx", "lul");
        assertThat(MagePreferences.ignoreList("test.com.xx").size(), is(2));

        assertThat(MagePreferences.ignoreList("test.com.xx").contains("test"), is(true));
        assertThat(MagePreferences.ignoreList("test.com.xx").contains("lul"), is(true));

        assertThat(MagePreferences.removeIgnoredUser("test.com.xx", "test"), is(true));
        assertThat(MagePreferences.removeIgnoredUser("test.com.xx", "test"), is(false));
        assertThat(MagePreferences.ignoreList("test.com.xx").size(), is(1));

        assertThat(MagePreferences.removeIgnoredUser("test.com.xx", "lul"), is(true));
        assertThat(MagePreferences.removeIgnoredUser("test.com.xx", "lul"), is(false));
        assertThat(MagePreferences.ignoreList("test.com.xx").size(), is(0));

        assertThat(MagePreferences.ignoreList("test.com.xx").contains("test"), is(false));
        assertThat(MagePreferences.ignoreList("test.com.xx").contains("lul"), is(false));
    }
}