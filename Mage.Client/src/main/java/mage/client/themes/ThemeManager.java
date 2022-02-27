package mage.client.themes;

import com.formdev.flatlaf.FlatLaf;
import com.google.common.collect.ImmutableMap;
import mage.abilities.hint.HintUtils;
import mage.abilities.icon.CardIconColor;
import mage.client.MageFrame;
import org.apache.log4j.Logger;
import org.mage.card.arcane.SvgUtils;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author NinthWorld
 */
public class ThemeManager {
    public static final String KEY_THEME = "themeSelection";

    private static final Logger logger = Logger.getLogger(ThemeManager.class);
    private static final File themesFolder = new File("themes");

    private static Unmarshaller unmarshaller;

    private static final HashMap<String, ThemeConfigSettings> loadedThemes = new HashMap<>();
    private static ThemeConfigSettings currentTheme = new ThemeConfigWrapper(null, null);
    static {
        loadedThemes.put(currentTheme.getName(), currentTheme);
    }

    /**
     * Loads all themes from the theme's directory and sets the current theme based on preferences.
     */
    public static void loadThemes() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("mage.client.themes");
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (Exception e) {
            logger.error("Could not create theme xml context", e);
            return;
        }

        if (!themesFolder.exists()) {
            if (!themesFolder.mkdirs()) {
                logger.error("Could not create themes directory.");
                return;
            }
        }

        // Load all theme packages from the theme's folder
        File[] themeDirectories = themesFolder.listFiles();
        if (themeDirectories != null) {
            for (File f : themeDirectories) {
                if (f.isDirectory()) {
                    loadThemeFromDirectory(f);
                }
            }
        }

        // Remove any themes with cyclical parent dependency
        for (final ThemeConfigSettings theme : getLoadedThemes()) {
            if (isCyclicalParentTheme(theme, new ArrayList<>())) {
                logger.error("Could not load theme " + theme.getName() + ": cyclical dependency");
                loadedThemes.remove(theme.getName());
            }
        }

        // Set the current theme from preferences
        String themePref = MageFrame.getPreferences().get(KEY_THEME, ThemeConfigWrapper.DEFAULT_NAME);
        if (themePref != null && loadedThemes.containsKey(themePref)) {
            currentTheme = loadedThemes.get(themePref);
        }

        logger.info("Using theme: " + currentTheme.getName());
    }

    public static ThemeConfigSettings getCurrentTheme() {
        return currentTheme;
    }

    public static void setCurrentTheme(ThemeConfigSettings theme) {
        currentTheme = theme;
        reload();
    }

    /**
     * @param name
     * @return ThemeConfigSettings of loaded theme with given name. If no theme is loaded with
     * the given name, null is returned.
     */
    public static ThemeConfigSettings getTheme(String name) {
        return name != null  && !name.isEmpty() ? loadedThemes.get(name) : null;
    }

    /**
     * @return Array of ThemeConfigSettings sorted alphabetically; Default always comes first.
     */
    public static ThemeConfigSettings[] getLoadedThemes() {
        return loadedThemes.values().stream()
                .sorted((a, b) ->
                        a.getName().equals(ThemeConfigWrapper.DEFAULT_NAME) ?
                                b.getName().equals(ThemeConfigWrapper.DEFAULT_NAME) ?
                                        0 : -1 :
                                b.getName().equals(ThemeConfigWrapper.DEFAULT_NAME) ?
                                        1 : a.getName().compareTo(b.getName()))
                .toArray(ThemeConfigSettings[]::new);
    }

    /**
     * Sets the UIManager look and feel to the current theme's look and feel classpath.
     * Applies any defaults the current theme has with UIManager.put()
     * @throws Exception
     */
    public static void setupLookAndFeel() throws Exception {
        try {
            if (getCurrentTheme().getLafClassPath().startsWith("com.formdev.flatlaf")) {
                Object lafObj = Class.forName(getCurrentTheme().getLafClassPath()).newInstance();
                if (lafObj instanceof FlatLaf) {
                    FlatLaf.setup((FlatLaf) lafObj);
                }
            }
        } finally {
            UIManager.setLookAndFeel(getCurrentTheme().getLafClassPath());
        }

        if (getCurrentTheme().getDefaults() != null) {
            for (ImmutableMap.Entry<String, Color> entry : getCurrentTheme().getDefaults().entrySet()) {
                UIManager.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Prepare theme settings and files before using. Call it on app loading or after theme changed
     */
    public static void reload() {
        // reload card icons css file (run it all the time, even on svg unsupport mode)
        for (CardIconColor cardIconColor : CardIconColor.values()) {
            SvgUtils.prepareCss(getCardIconsCssFile(cardIconColor), getCardIconsCssSettings(cardIconColor), true);
        }
    }

    public static String getCardIconsCssFile(CardIconColor cardIconColor) {
        return String.format("card-icons-svg-settings-%s.css", cardIconColor.toString());
    }

    public static String getCardIconsCssSettings(CardIconColor cardIconColor) {
        String fillColorVal = HintUtils.colorToHtml(cardIconColor.getFillColor() != null ? cardIconColor.getFillColor() : getCurrentTheme().getCardIconsFillColor());
        String strokeColorVal = HintUtils.colorToHtml(cardIconColor.getStrokeColor() != null ? cardIconColor.getStrokeColor() : getCurrentTheme().getCardIconsStrokeColor());
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
     * Loads theme.xml in directory, parses the XML, wraps it in a ThemeConfigWrapper, and adds it to loadedThemes map.
     * @param themeDir
     */
    private static void loadThemeFromDirectory(File themeDir) {
        final File themeXMLFile = new File(Paths.get(themeDir.toString(), "theme.xml").toString());
        if (!themeXMLFile.exists()) {
            logger.error("Could not find theme.xml in " + themeDir + "!");
            return;
        }

        try {
            final ThemeConfigSettings themeConfig = new ThemeConfigWrapper((ThemeConfig) unmarshaller.unmarshal(themeXMLFile), themeDir.toString());
            if (loadedThemes.containsKey(themeConfig.getName())) {
                logger.error("Could not load theme in " + themeDir + " as a theme named " + themeConfig.getName() + " has already been loaded!");
                return;
            }
            loadedThemes.put(themeConfig.getName(), themeConfig);
        } catch (JAXBException e) {
            logger.error("Could not load theme in " + themeDir + "!", e);
        }
    }

    /**
     * Recursively checks theme's parent theme to determine if a cyclical dependency is found.
     *
     * Cyclical dependency example: theme1.parent = theme2, theme2.parent = theme3, theme3.parent = theme1
     *
     * @param theme
     * @param visited
     * @return If no cyclical dependency is found, false. Otherwise, true.
     */
    private static boolean isCyclicalParentTheme(ThemeConfigSettings theme, ArrayList<String> visited) {
        visited.add(theme.getName());
        String parentName = theme.getResourcesParentThemeName();
        if (visited.contains(parentName)) {
            return true;
        } else if (parentName != null) {
            ThemeConfigSettings parent = loadedThemes.get(parentName);
            if (parent != null) {
                return isCyclicalParentTheme(parent, visited);
            }
        }
        return false;
    }
}
