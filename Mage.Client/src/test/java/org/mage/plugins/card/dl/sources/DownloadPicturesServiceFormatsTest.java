package org.mage.plugins.card.dl.sources;

import mage.client.dialog.DownloadImagesDialog;
import mage.client.util.sets.ConstructedFormats;
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
    private final Map<String, List<String>> originalFormats = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        onEdt(() -> {
            service = new DownloadPicturesService();
            dialog = (DownloadImagesDialog) field("uiDialog").get(service);
            field("selectedSource").set(service, new ScryfallImageSource() {
                @Override
                public List<String> getSupportedSets() {
                    return Arrays.asList("XLN", "LEA", "UNH", "SWS");
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
            replaceFormat(ConstructedFormats.HISTORIC, "XLN", "UNSUPPORTED");
            replaceFormat(ConstructedFormats.VINTAGE_LEGACY, "LEA", "XLN", "UNSUPPORTED");

            CardDownloadData token = card("Treasure", "XLN");
            token.setToken(true);
            cards("cardsMissing").addAll(Arrays.asList(
                    card("Historic card", "XLN"), token, card("Vintage card", "LEA"),
                    card("Joke card", "UNH"), card("Custom card", "SWS"),
                    card("Unsupported set", "UNSUPPORTED"), card("Unavailable image", "XLN")
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
            originalFormats.forEach((format, sets) -> {
                List<String> current = ConstructedFormats.getSetsByFormat(format);
                current.clear();
                current.addAll(sets);
            });
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
        assertSelection("- HISTORIC images (can be slow)", "XLN",
                "Historic card", "Treasure");
    }

    @Test
    public void vintageSelectionIncludesOlderSetsAndExcludesUnsupportedImages() throws Exception {
        assertSelection("- VINTAGE images (can be slow)", "LEA,XLN",
                "Historic card", "Treasure", "Vintage card");
    }

    private void assertSelection(String mode, String expectedSets, String... expectedCards) throws Exception {
        onEdt(() -> {
            dialog.getSetsCombo().setSelectedItem(mode);
            assertEquals(Arrays.asList(expectedSets.split(",")), field("selectedSets").get(service));
            List<String> names = new ArrayList<>();
            for (CardDownloadData card : cards("cardsDownloadQueue")) {
                names.add(card.getName());
            }
            assertEquals(Arrays.asList(expectedCards), names);
            assertEquals(expectedCards.length, dialog.getProgressBar().getMaximum());
            return null;
        });
    }

    private void replaceFormat(String format, String... sets) {
        List<String> current = ConstructedFormats.getSetsByFormat(format);
        originalFormats.put(format, new ArrayList<>(current));
        current.clear();
        current.addAll(Arrays.asList(sets));
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
