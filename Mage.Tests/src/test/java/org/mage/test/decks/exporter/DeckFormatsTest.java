package org.mage.test.decks.exporter;

import mage.cards.decks.DeckFormats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
                Assertions.fail("Default ext must be unique for each format: " + df.getExporter().getDescription());
            } else {
                extList.putIfAbsent(df.getExporter().getDefaultFileExt(), df);
            }
            // 2. must work with files
            String fileName = "C:\\xmage\\deck" + "." + df.getExporter().getDefaultFileExt();
            Assertions.assertTrue(DeckFormats.getFormatForExtension(fileName.toLowerCase(Locale.ENGLISH)).isPresent(), "Must support lower ext: " + df.getExporter().getDescription());
            Assertions.assertTrue(DeckFormats.getFormatForExtension(fileName.toUpperCase(Locale.ENGLISH)).isPresent(), "Must support upper ext: " + df.getExporter().getDescription());
        }

        // 3. wrong ext
        Assertions.assertFalse(DeckFormats.getFormatForExtension("deck").isPresent(), "Must not find empty ext");
        Assertions.assertFalse(DeckFormats.getFormatForExtension("deck.").isPresent(), "Must not find . ext");
        Assertions.assertFalse(DeckFormats.getFormatForExtension("deck.xxx").isPresent(), "Must not find unknown ext");

        // 3. double ext
        String fileName = "C:\\xmage\\deck"
                + "." + DeckFormats.XMAGE.getExporter().getDefaultFileExt()
                + "." + DeckFormats.MTG_ONLINE.getExporter().getDefaultFileExt();
        Assertions.assertEquals(DeckFormats.getFormatForExtension(fileName).get(), DeckFormats.MTG_ONLINE, "Must find mtgo");
        fileName = "C:\\xmage\\deck"
                + "." + DeckFormats.MTG_ONLINE.getExporter().getDefaultFileExt()
                + "." + DeckFormats.XMAGE.getExporter().getDefaultFileExt();
        Assertions.assertEquals(DeckFormats.getFormatForExtension(fileName).get(), DeckFormats.XMAGE, "Must find xmage");
    }
}