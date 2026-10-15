package mage.client.util.gui;

import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/** Pin the upstream size policy independently of the layout and display scale. */
public class GuiFontDefaultsTest extends GuiScalingTestSupport {
    private static final String[] SIZE_KEYS = {
            PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, PreferencesDialog.KEY_GUI_CHAT_FONT_SIZE,
            PreferencesDialog.KEY_GUI_CARD_EDITOR_SIZE, PreferencesDialog.KEY_GUI_TOOLTIP_SIZE,
            PreferencesDialog.KEY_GUI_PLAYER_PANEL_SIZE, PreferencesDialog.KEY_GUI_CARD_BATTLEFIELD_SIZE,
            PreferencesDialog.KEY_GUI_CARD_HAND_SIZE, PreferencesDialog.KEY_GUI_CARD_OTHER_ZONES_SIZE
    };

    @Test
    public void missingPreferencesUseOriginalDefaultsWithoutSavingThem() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            GUISizeHelper.calculateGUISizes();
            assertEquals(new Font("Arial", Font.PLAIN, 14), GUISizeHelper.dialogFont);
            assertEquals(GUISizeHelper.dialogFont, GUISizeHelper.tableFont);
            assertEquals(new Font("Arial", Font.PLAIN, 14), GUISizeHelper.chatFont);
            assertEquals(new Font("Arial", Font.PLAIN, 17), GUISizeHelper.cardTooltipFont);
            assertEquals(1.0f, GUISizeHelper.dialogGuiScale, 0.0f);
            assertEquals(1.0f, GUISizeHelper.playerPanelGuiScale, 0.0f);
            assertEquals(new Dimension(104, 148), GUISizeHelper.editorCardDimension);
            assertEquals(new Dimension(104, 148), GUISizeHelper.handCardDimension);
            assertEquals(new Dimension(104, 148), GUISizeHelper.otherZonesCardDimension);
            assertEquals(new Dimension(52, 74), GUISizeHelper.battlefieldCardMinDimension);
            assertEquals(new Dimension(156, 222), GUISizeHelper.battlefieldCardMaxDimension);
            for (String key : SIZE_KEYS) {
                assertNull("Calculating sizes must not save " + key, MageFrame.getPreferences().get(key, null));
            }
        });
    }

    @Test
    public void originalPresetValuesAndKeyOrderAreUnchanged() {
        PreferencesDialog.DefaultSizeSettings defaults = PreferencesDialog.getDefaultSizeSettings();
        assertArrayEquals(new String[]{"guiDialogFontSize", "guiChatFontSize", "guiCardEditorSize", "guiTooltipSize",
                "guiPlayerPanelSize", "guiCardBattlefieldSize", "guiCardHandSize", "guiCardOtherZonesSize"}, SIZE_KEYS);
        assertEquals(Arrays.asList("1366 x 768", "1920 x 1080", "2560 x 1440", "3840 x 2160"),
                defaults.getAllPresets());
        assertEquals(Arrays.asList(10, 15, 17, 15, 10, 22, 14, 13), defaults.getPresetValues("1366 x 768"));
        assertEquals(Arrays.asList(17, 18, 23, 20, 14, 30, 22, 21), defaults.getPresetValues("1920 x 1080"));
        assertEquals(Arrays.asList(23, 25, 35, 31, 18, 42, 31, 28), defaults.getPresetValues("2560 x 1440"));
        assertEquals(Arrays.asList(34, 37, 50, 55, 27, 64, 50, 44), defaults.getPresetValues("3840 x 2160"));
        for (int i = 0; i < SIZE_KEYS.length; i++) {
            assertEquals(SIZE_KEYS[i], defaults.getSettingsKeyByIndex(i));
        }
    }

    @Test
    public void nativeDpiDoesNotEnlargeThePresetForALogicalDesktop() {
        PreferencesDialog.DefaultSizeSettings defaults = PreferencesDialog.getDefaultSizeSettings();
        for (int dpi : new int[]{96, 120, 144, 192}) {
            // Java supplies logical screen dimensions; 3840x2160 at 200% has height 1080.
            assertEquals("1920 x 1080", defaults.findBestPreset(dpi, 1080));
            assertEquals("1366 x 768", defaults.findBestPreset(dpi, 768));
        }
    }

    @Test
    public void everySavedSizeSurvivesStartupPresetsAndThemeRefresh() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // Include both the original defaults and deliberately independent user choices.
            for (int[] sizes : new int[][]{{14, 14, 14, 17, 14, 14, 14, 14},
                    {34, 19, 21, 17, 23, 31, 18, 16}}) {
                for (int i = 0; i < SIZE_KEYS.length; i++) {
                    PreferencesDialog.saveValue(SIZE_KEYS[i], String.valueOf(sizes[i]));
                }
                for (String preset : PreferencesDialog.getDefaultSizeSettings().getAllPresets()) {
                    PreferencesDialog.getDefaultSizeSettings().applyPresetIfMissing(preset);
                    GUISizeHelper.calculateGUISizes();
                    GuiDisplayUtil.refreshThemeSettings();
                    assertEquals(sizes[0], GUISizeHelper.dialogFont.getSize());
                    assertEquals(sizes[1], GUISizeHelper.chatFont.getSize());
                    assertEquals(sizes[3], GUISizeHelper.cardTooltipFont.getSize());
                    assertEquals(sizes[4] / 14.0f, GUISizeHelper.playerPanelGuiScale, 0.001f);
                    for (int i = 0; i < SIZE_KEYS.length; i++) {
                        assertEquals("Stored " + SIZE_KEYS[i] + " after " + preset,
                                String.valueOf(sizes[i]), MageFrame.getPreferences().get(SIZE_KEYS[i], null));
                        assertEquals("Cached " + SIZE_KEYS[i] + " after " + preset,
                                sizes[i], PreferencesDialog.getCachedValue(SIZE_KEYS[i], 0));
                    }
                }
            }
        });
    }

    @Test
    public void startupFillsOnlyMissingSizesFromTheLogicalDesktopPreset() {
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "14");
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_TOOLTIP_SIZE, "17");
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_PLAYER_PANEL_SIZE, "23");
        PreferencesDialog.DefaultSizeSettings defaults = PreferencesDialog.getDefaultSizeSettings();
        defaults.applyPresetIfMissing(defaults.findBestPreset(192, 1080));
        List<Integer> expected = Arrays.asList(14, 18, 23, 17, 23, 30, 22, 21);
        for (int i = 0; i < SIZE_KEYS.length; i++) {
            assertEquals("Stored " + SIZE_KEYS[i], expected.get(i).toString(),
                    MageFrame.getPreferences().get(SIZE_KEYS[i], null));
            assertEquals("Cached " + SIZE_KEYS[i], expected.get(i).intValue(),
                    PreferencesDialog.getCachedValue(SIZE_KEYS[i], 0));
        }
    }
}
