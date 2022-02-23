package mage.client.themes;

import com.formdev.flatlaf.FlatLaf;
import com.google.common.collect.ImmutableMap;
import mage.abilities.hint.HintUtils;
import mage.abilities.icon.CardIconColor;
import org.mage.card.arcane.SvgUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author 18ths, JayDi85, NinthWorld
 */
public enum ThemeType {
    // https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html
    DEFAULT("Nimbus - Default", "", "javax.swing.plaf.nimbus.NimbusLookAndFeel",
            false, true, true, false, true, true, true, true, true,
            ImmutableMap.<String, Object>builder()
                    .put("nimbusBlueGrey", new Color(169, 176, 190))            // buttons, scrollbar background, disabled inputs
                    .put("nimbusLightBackground", new Color(255, 255, 255))     // inputs, table rows
                    .put("nimbusBase", new Color(51, 98, 140))                  // title bars, scrollbar foreground
                    .put("control", new Color(214, 217, 223))                   // window bg
                    .put("info", new Color(242, 242, 189))                      // tooltips
                    .build(),
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(204, 204, 204), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    NIMBUS_GREY("Nimbus - Grey", "grey-theme/", "javax.swing.plaf.nimbus.NimbusLookAndFeel",
            false, true, false, false, false, false, false, true, true,
            ImmutableMap.<String, Object>builder()
                    .put("nimbusBlueGrey", new Color(158, 148, 158))
                    .put("nimbusLightBackground", new Color(215, 215, 215))
                    .put("nimbusBase", new Color(102, 102, 102))
                    .put("control", new Color(212, 212, 212))
                    .put("info", new Color(189, 189, 164))
                    .build(),
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(204, 204, 204), // cardTooltipBackgroundColor
            new Color(172, 172, 172, 200), // playerPanel_inactiveBackgroundColor
            new Color(180, 234, 180, 200), // playerPanel_activeBackgroundColor
            new Color(99, 99, 99, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(158, 158, 158),
            Color.black,
            Color.black
    ),
    NIMBUS_SUNSET_VAPORWAVE("Nimbus - Vaporwave Sunset", "16bit-theme/", "javax.swing.plaf.nimbus.NimbusLookAndFeel",
            false, true, true, true, false, true, true, true, false,
            ImmutableMap.<String, Object>builder()
                    .put("nimbusBlueGrey", new Color(246, 136, 158))
                    .put("nimbusLightBackground", new Color(204, 236, 201))
                    .put("nimbusBase", new Color(106, 0, 255))
                    .put("control", new Color(243, 233, 164))
                    .put("info", new Color(117, 174, 238))
                    .build(),
            new Color(192, 166, 232),            
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(204, 204, 204), // cardTooltipBackgroundColor
            new Color(243, 233, 164), // playerPanel_inactiveBackgroundColor
            new Color(204, 236, 201), // playerPanel_inactiveBackgroundColor
            new Color(106, 0, 255), // playerPanel_inactiveBackgroundColor
            // card icons
            new Color(246, 136, 158),
            Color.black,
            new Color(106, 0, 255)
    ),
    NIMBUS_COFFEE("Nimbus - Coffee", "coffee-theme/", "javax.swing.plaf.nimbus.NimbusLookAndFeel",
            false, true, true, true, true, true, true, true, false,
            ImmutableMap.<String, Object>builder()
                    .put("nimbusBlueGrey", new Color(219, 193, 172))
                    .put("nimbusLightBackground", new Color(219, 193, 172))
                    .put("nimbusBase", new Color(97, 27, 0))
                    .put("control", new Color(182, 157, 135))
                    .put("info", new Color(219, 197, 182))
                    .build(),
            new Color(219, 193, 172), // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(204, 204, 204), // cardTooltipBackgroundColor
            new Color(219, 193, 172), // playerPanel_inactiveBackgroundColor
            new Color(204, 236, 201), // playerPanel_inactiveBackgroundColor
            new Color(99, 72, 50, 255), // playerPanel_inactiveBackgroundColor
            // card icons
            new Color(219, 193, 172),
            Color.black,
            new Color(97, 27, 0)
    ),
    NIMBUS_ISLAND("Nimbus - Island", "island-theme/", "javax.swing.plaf.nimbus.NimbusLookAndFeel",
            false, true, true, true, false, true, true, true, false,
            ImmutableMap.<String, Object>builder()
                    .put("nimbusBlueGrey", new Color(172, 197, 219))
                    .put("nimbusLightBackground", new Color(172, 197, 219))
                    .put("nimbusBase", new Color(0, 78, 97))
                    .put("control", new Color(135, 158, 182))
                    .put("info", new Color(182, 200, 219))
                    .build(),
            new Color(172, 195, 219), // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(204, 204, 204), // cardTooltipBackgroundColor
            new Color(172, 195, 219), // playerPanel_inactiveBackgroundColor
            new Color(204, 236, 201), // playerPanel_inactiveBackgroundColor
            new Color(50, 68, 99, 255), // playerPanel_inactiveBackgroundColor
            // card icons
            new Color(172, 197, 219),
            Color.black,
            new Color(0, 78, 97)
    ),
    FLATLAF_LIGHT("FlatLaf Light", "", "com.formdev.flatlaf.FlatLightLaf",
            false, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(204, 204, 204), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    FLATLAF_DARK("FlatLaf Dark", "", "com.formdev.flatlaf.FlatDarkLaf",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(60, 63, 65), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    FLATLAF_INTELLIJ("FlatLaf IntelliJ", "", "com.formdev.flatlaf.FlatIntelliJLaf",
            false, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(204, 204, 204), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    FLATLAF_DARCULA("FlatLaf Darcula", "", "com.formdev.flatlaf.FlatDarculaLaf",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(60, 63, 65), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    ARC("Arc", "", "com.formdev.flatlaf.intellijthemes.FlatArcIJTheme",
            false, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(245, 245, 245), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    ARC_ORANGE("Arc - Orange", "", "com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme",
            false, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(245, 245, 245), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    ARC_DARK("Arc Dark", "", "com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(56, 60, 74), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    ARC_DARK_ORANGE("Arc Dark - Orange", "", "com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(56, 60, 74), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    CARBON("Carbon", "", "com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(23, 32, 48), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    COBALT2("Cobalt 2", "", "com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(0, 27, 54), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    CYAN_LIGHT("Cyan Light", "", "com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme",
            false, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(228, 230, 235), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    DARK_FLAT("Dark Flat", "", "com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(56, 56, 56, 240), // gameEndBackgroundColor
            new Color(56, 56, 56), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    DARK_PURPLE("Dark Purple", "", "com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(44, 44, 59), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    DRACULA("Dracula", "", "com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(65, 68, 80), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GRADIANTO_DARK_FUCHSIA("Gradianto Dark Fuchsia", "", "com.formdev.flatlaf.intellijthemes.FlatGradiantoDarkFuchsiaIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(61, 33, 78), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GRADIANTO_DEEP_OCEAN("Gradianto Deep Ocean", "", "com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme",
            true,false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(28, 39, 57), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GRADIANTO_MIDNIGHT_BLUE("Gradianto Midnight Blue", "", "com.formdev.flatlaf.intellijthemes.FlatGradiantoMidnightBlueIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(40, 40, 57), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GRADIANTO_NATURE_GREEN("Gradianto Nature Green", "", "com.formdev.flatlaf.intellijthemes.FlatGradiantoNatureGreenIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(32, 64, 63), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GRAY("Gray", "", "com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme",
            false, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(242, 243, 245), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GRUVBOX_DARK_HARD("Gruvbox Dark Hard", "", "com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(29, 32, 33), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GRUVBOX_DARK_MEDIUM("Gruvbox Dark Medium", "", "com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkMediumIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(40, 40, 40), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GRUVBOX_DARK_SOFT("Gruvbox Dark Soft", "", "com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkSoftIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(50, 48, 47), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    HIBERBEE_DARK("Hiberbee Dark", "", "com.formdev.flatlaf.intellijthemes.FlatHiberbeeDarkIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(51, 50, 49), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    HIGH_CONTRAST("High Contrast", "", "com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme",
            true, false, false, false, true, false, false, false, true,
            ImmutableMap.<String, Object>builder()
                    .put("ToggleButton.foreground", new Color(127, 127, 127))
                    .build(), // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(0, 0, 0), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    LIGHT_FLAT("Light Flat", "", "com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme",
            false, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(238, 238, 242), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    MATERIAL_DESIGN_DARK("Material Design Dark", "", "com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(31, 41, 46), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    MONOCAI("Monocai", "", "com.formdev.flatlaf.intellijthemes.FlatMonocaiIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(45, 42, 47), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    NORD("Nord", "", "com.formdev.flatlaf.intellijthemes.FlatNordIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(46, 52, 64), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    ONE_DARK("One Dark", "", "com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(33, 37, 43), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    SOLARIZED_DARK("Solarized Dark", "", "com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(14, 60, 74), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    SOLARIZED_LIGHT("Solarized Light", "", "com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme",
            false, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(240, 240, 240, 140), // gameEndBackgroundColor
            new Color(238, 232, 213), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    SPACEGRAY("Spacegray", "", "com.formdev.flatlaf.intellijthemes.FlatSpacegrayIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(35, 40, 48), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    VUESION("Vuesion", "", "com.formdev.flatlaf.intellijthemes.FlatVuesionIJTheme",
            true, false, false, false, true, false, false, false, true,
            null, // themeDefaults
            null, // mageToolbar
            new Color(36, 37, 38, 240), // gameEndBackgroundColor
            new Color(32, 40, 49), // cardTooltipBackgroundColor
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    );

    private final String name;
    private final String path;
    private final String lookAndFeel;
    private final boolean isDark;
    private final boolean showBackground;
    private final boolean hasBackground;
    private final boolean hasLoginBackground;
    private final boolean hasBattleBackground;
    private final boolean hasSkipButtons;
    private final boolean hasPhaseIcons;
    private final boolean hasWinLossImages;
    private final boolean shortcutsVisibleForSkipButtons; // Whether or not to display skip button shortcuts
    private final ImmutableMap<String, Object> themeDefaults;
    private final Color mageToolbar;
    private final Color gameEndBackgroundColor;
    private final Color cardTooltipBackgroundColor;
    private final Color playerPanel_inactiveBackgroundColor;
    private final Color playerPanel_activeBackgroundColor;
    private final Color playerPanel_deadBackgroundColor;
    // card icons settings (example: flying icon)
    private final Color cardIconsFillColor;
    private final Color cardIconsStrokeColor;
    private final Color cardIconsTextColor;

    ThemeType(String name,
              String path,
              String lookAndFeel,
              boolean isDark,
              boolean showBackground,
              boolean hasBackground,
              boolean hasLoginBackground,
              boolean hasBattleBackground,
              boolean hasSkipButtons,
              boolean hasPhaseIcons,
              boolean hasWinLossImages,
              boolean shortcutsVisibleForSkipButtons,
              ImmutableMap<String, Object> themeDefaults,
              Color mageToolbar,
              Color gameEndBackgroundColor,
              Color cardTooltipBackgroundColor,
              Color playerPanel_inactiveBackgroundColor,
              Color playerPanel_activeBackgroundColor,
              Color playerPanel_deadBackgroundColor,
              Color cardIconsFillColor,
              Color cardIconsStrokeColor,
              Color cardIconsTextColor
    ) {
        this.name = name;
        this.path = path;
        this.lookAndFeel = lookAndFeel;
        this.isDark = isDark;
        this.showBackground = showBackground;
        this.hasBackground = hasBackground;
        this.hasLoginBackground = hasLoginBackground;
        this.hasBattleBackground = hasBattleBackground;
        this.hasSkipButtons = hasSkipButtons;
        this.hasPhaseIcons = hasPhaseIcons;
        this.hasWinLossImages = hasWinLossImages;
        this.shortcutsVisibleForSkipButtons = shortcutsVisibleForSkipButtons;
        this.themeDefaults = themeDefaults;
        this.mageToolbar = mageToolbar;
        this.gameEndBackgroundColor = gameEndBackgroundColor;
        this.cardTooltipBackgroundColor = cardTooltipBackgroundColor;
        this.playerPanel_activeBackgroundColor = playerPanel_activeBackgroundColor;
        this.playerPanel_deadBackgroundColor = playerPanel_deadBackgroundColor;
        this.playerPanel_inactiveBackgroundColor = playerPanel_inactiveBackgroundColor;
        this.cardIconsFillColor = cardIconsFillColor;
        this.cardIconsStrokeColor = cardIconsStrokeColor;
        this.cardIconsTextColor = cardIconsTextColor;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ThemeType valueByName(String value) {
        for (ThemeType themeType : values()) {
            if (themeType.name.equals(value)) {
                return themeType;
            }
        }
        return DEFAULT;
    }

    public String getName() {
        return name;
    }

    public String getLookAndFeel() {
        return lookAndFeel;
    }

    public boolean isDark() {
        return isDark;
    }

    public boolean shouldShowBackground() {
        return showBackground;
    }

    public boolean isShortcutsVisibleForSkipButtons() {
        return shortcutsVisibleForSkipButtons;
    }

    public boolean isNimbusLookAndFeel() {
        return lookAndFeel.equals("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }

    public Color getMageToolbar() {
        return mageToolbar;
    }

    public Color getGameEndBackgroundColor() {
        return gameEndBackgroundColor;
    }

    public Color getCardTooltipBackgroundColor() {
        return cardTooltipBackgroundColor;
    }

    public Color getPlayerPanel_inactiveBackgroundColor() {
        return playerPanel_inactiveBackgroundColor;
    }

    public Color getPlayerPanel_activeBackgroundColor() {
        return playerPanel_activeBackgroundColor;
    }

    public Color getPlayerPanel_deadBackgroundColor() {
        return playerPanel_deadBackgroundColor;
    }

    private String getImagePath(String imageType, String name) {
        return "/" + imageType + "/" + path + name;
    }

    public String getButtonPath(String name) {
        if (hasSkipButtons) {
            return getImagePath("buttons", name);
        } else {
            return "/buttons/" + name;
        }
    }

    public String getPhasePath(String name) {
        if (hasPhaseIcons) {
            return getImagePath("phases", name);
        } else {
            return "/phases/" + name;
        }
    }

    public String getWinlossPath(String name) {
        if (hasWinLossImages) {
            return getImagePath("winloss", name);
        } else {
            return "/winloss/" + name;
        }
    }

    public String getBackgroundPath() {
        if (hasBackground) {
            return getImagePath("background", "background.png");
        } else {
            return "/background/background.png";
        }
    }

    public String getLoginBackgroundPath() {
        if (hasLoginBackground) {
            return getImagePath("background", "login-background.png");
        } else {
            return getBackgroundPath();
        }
    }

    public String getBattleBackgroundPath() {
        if (hasBattleBackground) {
            return getImagePath("background", "battle-background.png");
        } else {
            return getBackgroundPath();
        }
    }

    public Color getCardIconsFillColor(CardIconColor cardIconColor) {
        return cardIconColor.getFillColor() != null ? cardIconColor.getFillColor() : this.cardIconsFillColor;
    }

    public Color getCardIconsStrokeColor(CardIconColor cardIconColor) {
        return cardIconColor.getStrokeColor() != null ? cardIconColor.getStrokeColor() : this.cardIconsStrokeColor;
    }

    public Color getCardIconsTextColor(CardIconColor cardIconColor) {
        return cardIconColor.getTextColor() != null ? cardIconColor.getTextColor() : this.cardIconsTextColor;
    }

    public String getCardIconsResourcePath(String resourceName) {
        return "/card/icons/" + resourceName;
    }

    public String getCardIconsCssFile(CardIconColor cardIconColor) {
        return String.format("card-icons-svg-settings-%s.css", cardIconColor.toString());
    }

    public String getCardIconsCssSettings(CardIconColor cardIconColor) {
        String fillColorVal = HintUtils.colorToHtml(this.getCardIconsFillColor(cardIconColor));
        String strokeColorVal = HintUtils.colorToHtml(this.getCardIconsStrokeColor(cardIconColor));

        return String.format(""
                        + "fill: %s;"
                        + "stroke: %s;"
                        + "stroke-width: 0.5;" // px
                        + "stroke-opacity: 0.7;", // 1 = 100%
                fillColorVal,
                strokeColorVal
        );
    }

    /**
     * Prepare theme settings and files before using. Call it on app loading or after theme changed
     */
    public void reload() {
        // reload card icons css file (run it all the time, even on svg unsupport mode)
        for (CardIconColor cardIconColor : CardIconColor.values()) {
            SvgUtils.prepareCss(this.getCardIconsCssFile(cardIconColor), this.getCardIconsCssSettings(cardIconColor), true);
        }
    }

    public void setupLookAndFeel() throws Exception {
        try {
            if (lookAndFeel.startsWith("com.formdev.flatlaf")) {
                Object lafObj = Class.forName(lookAndFeel).newInstance();
                if (lafObj instanceof FlatLaf) {
                    FlatLaf.setup((FlatLaf) lafObj);
                }
            }
        } finally {
            UIManager.setLookAndFeel(lookAndFeel);
        }

        if (themeDefaults != null) {
            for (ImmutableMap.Entry<String, Object> entry : themeDefaults.entrySet()) {
                UIManager.put(entry.getKey(), entry.getValue());
            }
        }
    }
}