package mage.client.util;

import mage.client.preference.MagePreferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class IgnoreListTest {

    @Before
    public void setUp() throws Exception {
        MagePreferences.clearIgnoreList("test.com.xx");
    }

    @After
    public void tearDown() throws Exception {
        MagePreferences.clearIgnoreList("test.com.xx");
    }

    @Test
    public void ignoreListEmpty() throws Exception {
        assertEquals(IgnoreList.getIgnoreListInfo("test.com.xx"), "<font color=yellow>Current ignore list on test.com.xx (total: 0): []</font>");
    }

    @Test
    public void ignoreList() throws Exception {
        final String test = IgnoreList.ignore("test.com.xx", "test");
        assertEquals(test, "Added test to your ignore list on test.com.xx (total: 1)");

        final String kranken = IgnoreList.ignore("test.com.xx", "kranken");
        assertEquals(kranken, "Added kranken to your ignore list on test.com.xx (total: 2)");

        assertEquals(IgnoreList.getIgnoreListInfo("test.com.xx"), "<font color=yellow>Current ignore list on test.com.xx (total: 2): [kranken, test]</font>");
    }

    @Test
    public void ignore() throws Exception {
        ignore_a_user("kranken");
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9682
     */
    @Test
    public void ignoreNameWithSpaces() {
        ignore_a_user("test test test");
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9682
     */
    @Test
    public void ignoreSpaceName() {
        ignore_a_user(" ");
    }

    private void ignore_a_user(String username) {
        assertFalse(IgnoreList.userIsIgnored("test.com.xx", username));

        final String responce = IgnoreList.ignore("test.com.xx", username);
        assertEquals(responce, "Added " + username + " to your ignore list on test.com.xx (total: 1)");

        assertTrue(IgnoreList.userIsIgnored("test.com.xx", username));
    }

    @Test
    public void ignoreAgain() throws Exception {
        assertFalse(IgnoreList.userIsIgnored("test.com.xx", "kranken"));
        IgnoreList.ignore("test.com.xx", "kranken");

        final String response = IgnoreList.ignore("test.com.xx", "kranken");
        assertEquals(response, "kranken is already on your ignore list on test.com.xx");

        assertTrue(IgnoreList.userIsIgnored("test.com.xx", "kranken"));
    }

    @Test
    public void ignoreDefaultResponse() throws Exception {
        final String response1 = IgnoreList.ignore("test.com.xx", "");
        final String response2 = IgnoreList.ignore("test.com.xx", null);
        assertEquals(response1, response2);
        assertEquals(response2, "<font color=yellow>Current ignore list on test.com.xx (total: 0): []</font>");

        assertEquals(IgnoreList.getIgnoreListInfo("test.com.xx"), "<font color=yellow>Current ignore list on test.com.xx (total: 0): []</font>");
    }

    @Test
    public void ignoreMaxSize() throws Exception {
        for (int i = 0; i < 500; i++) {
            IgnoreList.ignore("test.com.xx", "" + i);
        }
        final String response = IgnoreList.ignore("test.com.xx", "lul");
        assertEquals(response, "Your ignore list is too big (max 500), remove a user to be able to add a new one.");

        assertEquals(IgnoreList.getIgnoredUsers("test.com.xx").size(), 500);
    }

    @Test
    public void unignore() throws Exception {
        assertFalse(IgnoreList.userIsIgnored("test.com.xx", "kranken"));

        IgnoreList.ignore("test.com.xx", "kranken");
        assertTrue(IgnoreList.userIsIgnored("test.com.xx", "kranken"));

        final String response = IgnoreList.unignore("test.com.xx", "kranken");
        assertEquals(response, "Removed kranken from your ignore list on test.com.xx (total: 0)");

        assertFalse(IgnoreList.userIsIgnored("test.com.xx", "kranken"));
    }
}