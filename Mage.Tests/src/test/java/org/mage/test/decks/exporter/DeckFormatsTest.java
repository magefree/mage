package org.mage.test.decks.exporter;

import mage.cards.decks.DeckFormats;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author JayDi85
 */
public class DeckFormatsTest {

    @Test
    public void test_FormatsExt() {
        Map<String, DeckFormats> extList = new HashMap<>();
        for (DeckFormats df : DeckFormats.values()) {
            // 1. must be unique
            if (extList.containsKey(df.getExporter().getDefaultFileExt())) {
                Assert.fail("Default ext must be unique for each format: " + df.getExporter().getDescription());
            } else {
                extList.putIfAbsent(df.getExporter().getDefaultFileExt(), df);
            }
            // 2. must work with files
            String fileName = "C:\\xmage\\deck" + "." + df.getExporter().getDefaultFileExt();
            Assert.assertTrue("Must support lower ext: " + df.getExporter().getDescription(), DeckFormats.getFormatForExtension(fileName.toLowerCase(Locale.ENGLISH)).isPresent());
            Assert.assertTrue("Must support upper ext: " + df.getExporter().getDescription(), DeckFormats.getFormatForExtension(fileName.toUpperCase(Locale.ENGLISH)).isPresent());
        }

        // 3. wrong ext
        Assert.assertFalse("Must not find empty ext", DeckFormats.getFormatForExtension("deck").isPresent());
        Assert.assertFalse("Must not find . ext", DeckFormats.getFormatForExtension("deck.").isPresent());
        Assert.assertFalse("Must not find unknown ext", DeckFormats.getFormatForExtension("deck.xxx").isPresent());

        // 3. double ext
        String fileName = "C:\\xmage\\deck"
                + "." + DeckFormats.XMAGE.getExporter().getDefaultFileExt()
                + "." + DeckFormats.MTG_ONLINE.getExporter().getDefaultFileExt();
        Assert.assertEquals("Must find mtgo", DeckFormats.getFormatForExtension(fileName).get(), DeckFormats.MTG_ONLINE);
        fileName = "C:\\xmage\\deck"
                + "." + DeckFormats.MTG_ONLINE.getExporter().getDefaultFileExt()
                + "." + DeckFormats.XMAGE.getExporter().getDefaultFileExt();
        Assert.assertEquals("Must find xmage", DeckFormats.getFormatForExtension(fileName).get(), DeckFormats.XMAGE);
    }
}