package mage.client.preference;

import mage.client.dialog.PreferencesDialog;
import org.junit.After;
import org.junit.Assert;
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

    @Test
    public void testGuiSizeRecommends() {
        // possible presets
        // 1366 x 768
        // 1920 x 1080
        // 2560 x 1440
        // 3840 x 2160
        PreferencesDialog.DefaultSizeSettings defaultSizeSettings = PreferencesDialog.getDefaultSizeSettings();

        String needPreset = "1366 x 768";
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 0));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 100));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 767));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 768));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 769));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 1079));

        needPreset = "1920 x 1080";
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 1080));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 1081));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 1439));

        needPreset = "2560 x 1440";
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 1440));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 1441));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 2159));

        needPreset = "3840 x 2160";
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 2160));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 5000));
        Assert.assertEquals(needPreset, defaultSizeSettings.findBestPreset(96, 5000));
    }
}