package mage.client.util;

import mage.client.preference.MagePreferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
        assertThat(IgnoreList.ignoreListString("test.com.xx"), is("<font color=yellow>Current ignore list on test.com.xx: []</font>"));
    }

    @Test
    public void ignoreList() throws Exception {
        final String test = IgnoreList.ignore("test.com.xx", "test");
        final String kranken = IgnoreList.ignore("test.com.xx", "kranken");
        assertThat(IgnoreList.ignoreListString("test.com.xx"), is("<font color=yellow>Current ignore list on test.com.xx: [kranken, test]</font>"));
        assertThat(test, is("Added test to your ignore list on test.com.xx"));
        assertThat(kranken, is("Added kranken to your ignore list on test.com.xx"));
    }

    @Test
    public void ignore() throws Exception {
        assertThat(IgnoreList.userIsIgnored("test.com.xx", "kranken"), is(false));
        final String r = IgnoreList.ignore("test.com.xx", "kranken");
        assertThat(IgnoreList.userIsIgnored("test.com.xx", "kranken"), is(true));
        assertEquals(r, "Added kranken to your ignore list on test.com.xx");
    }

    @Test
    public void ignoreAgain() throws Exception {
        assertThat(IgnoreList.userIsIgnored("test.com.xx", "kranken"), is(false));
        IgnoreList.ignore("test.com.xx", "kranken");
        final String r = IgnoreList.ignore("test.com.xx", "kranken");
        assertThat(IgnoreList.userIsIgnored("test.com.xx", "kranken"), is(true));
        assertEquals(r, "kranken is already on your ignore list on test.com.xx");
    }

    @Test
    public void ignoreDefaultResponse() throws Exception {
        final String r1 = IgnoreList.ignore("test.com.xx", "");
        final String r2 = IgnoreList.ignore("test.com.xx", null);
        assertThat(IgnoreList.ignoreListString("test.com.xx"), is("<font color=yellow>Current ignore list on test.com.xx: []</font>"));
        assertEquals(r1, r2);
        assertEquals(r2, "<font color=yellow>Current ignore list on test.com.xx: []</font>");
    }

    @Test
    public void ignoreMaxSize() throws Exception {
        for (int i = 0; i < 50; i++) {
            IgnoreList.ignore("test.com.xx", "" + i);
        }
        final String r = IgnoreList.ignore("test.com.xx", "lul");
        assertEquals(r, "Your ignore list is too big, remove a user to be able to add a new one.");
        assertThat(IgnoreList.ignoreListString("test.com.xx"), is("<font color=yellow>Current ignore list on test.com.xx: [0, 1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 2, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 3, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 4, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 5, 6, 7, 8, 9]</font>"));
    }

    @Test
    public void unignore() throws Exception {
        assertThat(IgnoreList.userIsIgnored("test.com.xx", "kranken"), is(false));
        IgnoreList.ignore("test.com.xx", "kranken");
        assertThat(IgnoreList.userIsIgnored("test.com.xx", "kranken"), is(true));
        final String r = IgnoreList.unignore("test.com.xx", "kranken");
        assertThat(IgnoreList.userIsIgnored("test.com.xx", "kranken"), is(false));
        assertEquals(r, "Removed kranken from your ignore list on test.com.xx");

    }

}