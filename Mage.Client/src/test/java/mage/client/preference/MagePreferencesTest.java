package mage.client.preference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        assertEquals(0, MagePreferences.ignoreList("test.com.xx").size());
        assertFalse(MagePreferences.removeIgnoredUser("test.com.xx", "test"));

        MagePreferences.addIgnoredUser("test.com.xx", "test");
        assertEquals(1, MagePreferences.ignoreList("test.com.xx").size());
        assertEquals(0, MagePreferences.ignoreList("other.com.xx").size());

        MagePreferences.addIgnoredUser("test.com.xx", "lul");
        assertEquals(2, MagePreferences.ignoreList("test.com.xx").size());

        assertTrue(MagePreferences.ignoreList("test.com.xx").contains("test"));
        assertTrue(MagePreferences.ignoreList("test.com.xx").contains("lul"));

        assertTrue(MagePreferences.removeIgnoredUser("test.com.xx", "test"));
        assertFalse(MagePreferences.removeIgnoredUser("test.com.xx", "test"));
        assertEquals(1, MagePreferences.ignoreList("test.com.xx").size());

        assertTrue(MagePreferences.removeIgnoredUser("test.com.xx", "lul"));
        assertFalse(MagePreferences.removeIgnoredUser("test.com.xx", "lul"));
        assertEquals(0, MagePreferences.ignoreList("test.com.xx").size());

        assertFalse(MagePreferences.ignoreList("test.com.xx").contains("test"));
        assertFalse(MagePreferences.ignoreList("test.com.xx").contains("lul"));
    }
}