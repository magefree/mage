package org.mage.plugins.card.dl.sources;

import mage.client.dialog.DownloadImagesDialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.images.DownloadPicturesService;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import static org.junit.Assert.*;

public class DownloadPicturesServiceFormatsTest {

    private DownloadPicturesService service;
    private DownloadImagesDialog dialog;

    @Before
    public void setUp() throws Exception {
        onEdt(() -> {
            service = new DownloadPicturesService();
            dialog = (DownloadImagesDialog) field("uiDialog").get(service);
            field("selectedSource").set(service, new ScryfallImageSource() {
                @Override
                public List<String> getSupportedSets() {
                    return Arrays.asList("XLN", "LEA", "UNH", "SWS", "YDMU", "CMB1",
                            "JMP", "STA", "LTR", "MH3", "M3C", "KTK", "J25", "MB2");
                }

                @Override
                public boolean isCardImageProvided(String setCode, String cardName) {
                    return !cardName.equals("Unavailable image");
                }

                @Override
                public boolean isTokenImageProvided(String setCode, String cardName, Integer tokenNumber) {
                    return true;
                }
            });
            CardDownloadData token = card("Treasure", "XLN");
            token.setToken(true);
            cards("cardsMissing").addAll(Arrays.asList(
                    card("Historic card", "XLN"), token, card("Vintage card", "LEA"),
                    card("Mixed-set image", "UNH"), card("Custom card", "SWS"),
                    card("Arena-only image", "YDMU"), card("Playtest image", "CMB1"),
                    card("Unsupported set", "FCA"), card("Unavailable image", "XLN")
            ));
            Method choices = DownloadPicturesService.class.getDeclaredMethod("getSetsForCurrentImageSource");
            choices.setAccessible(true);
            dialog.getSetsCombo().setModel(new DefaultComboBoxModel<>((Object[]) choices.invoke(service)));
            return null;
        });
    }

    @After
    public void tearDown() throws Exception {
        onEdt(() -> {
            if (dialog != null) {
                dialog.dispose();
            }
            return null;
        });
    }

    @Test
    public void formatChoicesKeepTheExistingDownloadModes() throws Exception {
        onEdt(() -> {
            JComboBox choices = dialog.getSetsCombo();
            List<Object> modes = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                modes.add(choices.getItemAt(i));
            }
            assertEquals(Arrays.asList(
                    "- ALL images from selected source (can be slow)",
                    "- MODERN images (can be slow)", "- STANDARD images",
                    "- HISTORIC images (can be slow)", "- VINTAGE images (can be slow)",
                    "- BASIC LAND images", "- TOKEN images"
            ), modes);
            return null;
        });
    }

    @Test
    public void historicSelectionIncludesItsCardsAndTokensOnly() throws Exception {
        assertSelection("- HISTORIC images (can be slow)",
                "Historic card", "Treasure", "Arena-only image");
    }

    @Test
    public void vintageSelectionIncludesOlderSetsAndExcludesUnsupportedImages() throws Exception {
        assertSelection("- VINTAGE images (can be slow)",
                "Historic card", "Treasure", "Vintage card", "Mixed-set image");
    }

    @Test
    public void historicSelectionIncludesSupplementalAndPartialArenaReleases() throws Exception {
        onEdt(() -> {
            cards("cardsMissing").clear();
            for (String code : Arrays.asList("JMP", "STA", "LTR", "MH3", "M3C", "KTK", "J25")) {
                cards("cardsMissing").add(card(code + " image", code));
            }
            return null;
        });
        assertSelection("- HISTORIC images (can be slow)", "JMP image", "STA image", "LTR image",
                "MH3 image", "M3C image", "KTK image", "J25 image");
    }

    @Test
    public void vintageSelectionDownloadsAllImagesFromAMixedRelease() throws Exception {
        onEdt(() -> {
            cards("cardsMissing").clear();
            cards("cardsMissing").add(card("Regular reprint", "MB2"));
            cards("cardsMissing").add(card("Playtest printing in a mixed set", "MB2"));
            cards("cardsMissing").add(card("Pure playtest set", "CMB1"));
            return null;
        });
        assertSelection("- VINTAGE images (can be slow)",
                "Regular reprint", "Playtest printing in a mixed set");
    }

    private void assertSelection(String mode, String... expectedCards) throws Exception {
        onEdt(() -> {
            dialog.getSetsCombo().setSelectedItem(mode);
            assertFalse(((List<?>) field("selectedSets").get(service)).contains("FCA"));
            List<String> names = new ArrayList<>();
            for (CardDownloadData card : cards("cardsDownloadQueue")) {
                names.add(card.getName());
            }
            assertEquals(Arrays.asList(expectedCards), names);
            assertEquals(expectedCards.length, dialog.getProgressBar().getMaximum());
            return null;
        });
    }

    private static CardDownloadData card(String name, String set) {
        return new CardDownloadData(name, set, "1", false, 0);
    }

    @SuppressWarnings("unchecked")
    private List<CardDownloadData> cards(String name) throws Exception {
        return (List<CardDownloadData>) field(name).get(service);
    }

    private static Field field(String name) throws Exception {
        Field field = DownloadPicturesService.class.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private static <T> T onEdt(Callable<T> action) throws Exception {
        FutureTask<T> task = new FutureTask<>(action);
        SwingUtilities.invokeAndWait(task);
        return task.get();
    }
}
