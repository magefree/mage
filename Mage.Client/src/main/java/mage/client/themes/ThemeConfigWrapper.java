package mage.client.themes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author NinthWorld
 */
public class ThemeConfigWrapper implements ThemeConfigSettings {

    private static final String DARK_MODE_EXT = ".dark";
    private static final ImmutableSet<String> DARK_MODE_VARIANTS = ImmutableSet.of(
            "/menu/deck_editor.png",
            "/menu/symbol.png",
            "/buttons/type_land.png",
            "/buttons/type_planeswalker.png",
            "/buttons/search_24.png",
            "/buttons/search_32.png",
            "/buttons/search_64.png",
            "/buttons/search_128.png",
            "/buttons/tourney_new.png",
            "/buttons/deck_pack.png",
            "/buttons/match_new.png",
            "/card/triggered_ability.png",
            "/card/activated_ability.png",
            "/card/copy.png",
            "/game/revealed.png",
            "/game/looked_at.png",
            "/card/token.png",
            "/card/night.png",
            "/card/day.png"
    );

    public static final String DEFAULT_NAME = "Default";
    private static final String DEFAULT_LAF_CLASSPATH = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
    private static final boolean DEFAULT_IS_DARK = false;
    private static final boolean DEFAULT_SHOW_BACKGROUND = true;
    private static final boolean DEFAULT_SHOW_LOGIN_BACKGROUND = false;
    private static final boolean DEFAULT_SHOW_BATTLE_BACKGROUND = true;
    private static final boolean DEFAULT_SHORTCUTS_VISIBLE_FOR_SKIP_BUTTONS = true;
    private static final Color DEFAULT_MAGE_TOOLBAR_BACKGROUND_COLOR = null;
    private static final Color DEFAULT_GAME_END_TEXT_BACKGROUND_COLOR = new Color(240, 240, 240, 140);
    private static final Color DEFAULT_GAME_END_TEXT_BACKGROUND_COLOR_DARK = new Color(36, 37, 38, 240);
    private static final Color DEFAULT_CARD_TOOLTIP_BACKGROUND_COLOR = new Color(204, 204, 204);
    private static final Color DEFAULT_CARD_TOOLTIP_BACKGROUND_COLOR_DARK = new Color(60, 63, 65);
    private static final Color DEFAULT_PLAYER_PANEL_INACTIVE_BACKGROUND_COLOR = new Color(200, 200, 180, 200);
    private static final Color DEFAULT_PLAYER_PANEL_ACTIVE_BACKGROUND_COLOR = new Color(200, 255, 200, 200);
    private static final Color DEFAULT_PLAYER_PANEL_DEAD_BACKGROUND_COLOR = new Color(131, 94, 83, 200);
    private static final Color DEFAULT_CARD_ICONS_FILL_COLOR = new Color(169, 176, 190);
    private static final Color DEFAULT_CARD_ICONS_STROKE_COLOR = new Color(0, 0, 0);
    private static final Color DEFAULT_CARD_ICONS_TEXT_COLOR = new Color(51, 98, 140);
    private static final Color DEFAULT_TEXT_ON_BACKGROUND_TEXT_COLOR = new Color(255, 255, 255);
    private static final ImmutableMap<String, Color> DEFAULT_DEFAULTS = ImmutableMap.of(
            "nimbusBlueGrey", new Color(169, 176, 190),
            "nimbusLightBackground", new Color(255, 255, 255),
            "nimbusBase", new Color(51, 98, 140),
            "control", new Color(214, 217, 223),
            "info", new Color(242, 242, 189)
    );

    private static final Logger logger = Logger.getLogger(ThemeManager.class);

    private final ThemeConfig config;
    private final String themePath;
    private final ImmutableMap<String, Color> themeDefaults;
    private final ImmutableMap<String, String> themeResources;

    public ThemeConfigWrapper(final ThemeConfig config, final String themePath) {
        this.config = config;
        this.themePath = themePath;
        this.themeDefaults = config == null || config.getThemeDefaults() == null ?
                ImmutableMap.of() :
                config.getThemeDefaults().getDefault().stream()
                    .collect(ImmutableMap.toImmutableMap(
                            ThemeDefault::getKey,
                            (ThemeDefault td) ->
                                    getColorFromStringOrDefault(td.getColor(), Color.BLACK)));
        this.themeResources = config == null || config.getThemeResources() == null ?
                ImmutableMap.of() :
                config.getThemeResources().getResource().stream()
                    .collect(ImmutableMap.toImmutableMap(
                            ThemeResource::getName,
                            ThemeResource::getRelPath));
    }

    @Override
    public String getName() {
        return config == null ?
                DEFAULT_NAME : config.getTheme().getName();
    }

    public String getResourcesParentThemeName() {
        return config == null ||
                config.getTheme() == null ||
                config.getTheme().getResourcesParentTheme() == null ||
                config.getTheme().getResourcesParentTheme().isEmpty() ?
                null : config.getTheme().getResourcesParentTheme();
    }

    @Override
    public String getLafClassPath() {
        return config == null || config.getTheme().getLafClassPath() == null ?
                DEFAULT_LAF_CLASSPATH : config.getTheme().getLafClassPath();
    }

    @Override
    public boolean isDark() {
        return config == null || config.getTheme().isDark() == null ?
                DEFAULT_IS_DARK : config.getTheme().isDark();
    }

    @Override
    public boolean shouldShowBackground() {
        return config == null || config.getTheme().isShowBackground() == null ?
                DEFAULT_SHOW_BACKGROUND : config.getTheme().isShowBackground();
    }

    @Override
    public boolean shouldShowLoginBackground() {
        return config == null || config.getTheme().isShowLoginBackground() == null?
                DEFAULT_SHOW_LOGIN_BACKGROUND : config.getTheme().isShowLoginBackground();
    }

    @Override
    public boolean shouldShowBattleBackground() {
        return config == null || config.getTheme().isShowBattleBackground() == null ?
                DEFAULT_SHOW_BATTLE_BACKGROUND : config.getTheme().isShowBattleBackground();
    }

    @Override
    public boolean isShortcutsVisibleForSkipButtons() {
        return config == null || config.getTheme().isShortcutsVisibleForSkipButtons() == null ?
                DEFAULT_SHORTCUTS_VISIBLE_FOR_SKIP_BUTTONS : config.getTheme().isShortcutsVisibleForSkipButtons();
    }

    @Override
    @Nullable
    public Color getMageToolbarBackgroundColor() {
        return config == null || config.getThemeColors() == null ? DEFAULT_MAGE_TOOLBAR_BACKGROUND_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getMageToolbarBackgroundColor(), DEFAULT_MAGE_TOOLBAR_BACKGROUND_COLOR);
    }

    @Override
    public Color getGameEndTextBackgroundColor() {
        return config == null || config.getThemeColors() == null ?
                isDark() ? DEFAULT_GAME_END_TEXT_BACKGROUND_COLOR_DARK : DEFAULT_GAME_END_TEXT_BACKGROUND_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getGameEndTextBackgroundColor(),
                        isDark() ? DEFAULT_GAME_END_TEXT_BACKGROUND_COLOR_DARK : DEFAULT_GAME_END_TEXT_BACKGROUND_COLOR);
    }

    @Override
    public Color getCardTooltipBackgroundColor() {
        return config == null || config.getThemeColors() == null ?
                isDark() ? DEFAULT_CARD_TOOLTIP_BACKGROUND_COLOR_DARK : DEFAULT_CARD_TOOLTIP_BACKGROUND_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getCardTooltipBackgroundColor(),
                        isDark() ? DEFAULT_CARD_TOOLTIP_BACKGROUND_COLOR_DARK : DEFAULT_CARD_TOOLTIP_BACKGROUND_COLOR);
    }

    @Override
    public Color getPlayerPanelInactiveBackgroundColor() {
        return config == null || config.getThemeColors() == null ? DEFAULT_PLAYER_PANEL_INACTIVE_BACKGROUND_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getPlayerPanelInactiveBackgroundColor(), DEFAULT_PLAYER_PANEL_INACTIVE_BACKGROUND_COLOR);
    }

    @Override
    public Color getPlayerPanelActiveBackgroundColor() {
        return config == null || config.getThemeColors() == null ? DEFAULT_PLAYER_PANEL_ACTIVE_BACKGROUND_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getPlayerPanelActiveBackgroundColor(), DEFAULT_PLAYER_PANEL_ACTIVE_BACKGROUND_COLOR);
    }

    @Override
    public Color getPlayerPanelDeadBackgroundColor() {
        return config == null || config.getThemeColors() == null ? DEFAULT_PLAYER_PANEL_DEAD_BACKGROUND_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getPlayerPanelDeadBackgroundColor(), DEFAULT_PLAYER_PANEL_DEAD_BACKGROUND_COLOR);
    }

    @Override
    public Color getCardIconsFillColor() {
        return config == null || config.getThemeColors() == null ? DEFAULT_CARD_ICONS_FILL_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getCardIconsFillColor(), DEFAULT_CARD_ICONS_FILL_COLOR);
    }

    @Override
    public Color getCardIconsStrokeColor() {
        return config == null || config.getThemeColors() == null ? DEFAULT_CARD_ICONS_STROKE_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getCardIconsStrokeColor(), DEFAULT_CARD_ICONS_STROKE_COLOR);
    }

    @Override
    public Color getCardIconsTextColor() {
        return config == null || config.getThemeColors() == null ? DEFAULT_CARD_ICONS_TEXT_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getCardIconsTextColor(), DEFAULT_CARD_ICONS_TEXT_COLOR);
    }

    @Override
    public Color getTextOnBackgroundTextColor() {
        return config == null || config.getThemeColors() == null ? DEFAULT_TEXT_ON_BACKGROUND_TEXT_COLOR :
                getColorFromStringOrDefault(config.getThemeColors().getTextOnBackgroundTextColor(), DEFAULT_TEXT_ON_BACKGROUND_TEXT_COLOR);
    }

    @Override
    public ImmutableMap<String, Color> getDefaults() {
        return config == null ? DEFAULT_DEFAULTS : themeDefaults;
    }

    /**
     * Gets the URL for given resource path (EX: "/background/background.png")
     *
     * If the theme is dark and the resource is on the DARK_MODE_VARIANTS list,
     * resource path is appended with DARK_MODE_EXT (EX: "/background/background.dark.png")
     *
     * @param name
     * @return If the theme overrides the resource, the theme's custom resource path is returned.
     *
     * If the theme doesn't override the resource and has a parent theme,
     * the parent theme's getResource is returned.
     *
     * Otherwise, the default resource is returned with getClass().getResource()
     */
    @Override
    public URL getResource(String name) {
        if (isDark() && DARK_MODE_VARIANTS.contains(name)) {
            int extIndex = name.lastIndexOf(".");
            String ext = name.substring(extIndex);
            name = name.substring(0, extIndex) + DARK_MODE_EXT + ext;
        }

        if (config != null && themePath != null && themeResources.containsKey(name)) {
            try {
                File file = Paths.get(themePath, themeResources.get(name)).toFile();
                if (file.exists() && file.isFile()) {
                    return file.toURI().toURL();
                }
            } catch (Exception e) {
                logger.error("Could not get theme resource for " + name, e);
            }
        }

        String parentThemeName = getResourcesParentThemeName();
        if (parentThemeName != null) {
            ThemeConfigSettings parentTheme = ThemeManager.getTheme(parentThemeName);
            if (parentTheme != null) {
                return parentTheme.getResource(name);
            }
        }
        return getClass().getResource(name);
    }

    @Override
    public boolean isNimbusLaf() {
        return getLafClassPath().equals(DEFAULT_LAF_CLASSPATH);
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Converts a string formatted color into a java.awt.Color object.
     * Valid formats:
     *  Hexadecimal         (EX: "#000000")
     *  RGB int 0-255       (EX: "rgb(0, 0, 0)")
     *  RGBA int 0-255      (EX: "rgba(0, 0, 0, 0)")
     *  Known color name    (EX: "red")
     *
     * @param color
     * @param defaultColor
     * @return Color parsed from string, or defaultColor if string is null, empty, or cannot be parsed.
     */
    private static Color getColorFromStringOrDefault(String color, Color defaultColor) {
        if (color == null || color.isEmpty()) {
            return defaultColor;
        }

        try {
            color = color.toLowerCase();
            if (color.startsWith("#")) {
                return Color.decode(color);
            } else if (color.startsWith("rgb(") && color.endsWith(")")) {
                String[] values = color.substring(4, color.length() - 1).split(",");
                if (values.length == 3) {
                    return new Color(
                            Integer.parseInt(values[0].trim()),
                            Integer.parseInt(values[1].trim()),
                            Integer.parseInt(values[2].trim())
                    );
                }
            } else if (color.startsWith("rgba(") && color.endsWith(")")) {
                String[] values = color.substring(5, color.length() - 1).split(",");
                if (values.length == 4) {
                    return new Color(
                            Integer.parseInt(values[0].trim()),
                            Integer.parseInt(values[1].trim()),
                            Integer.parseInt(values[2].trim()),
                            Integer.parseInt(values[3].trim())
                    );
                }
            } else {
                Field field = Class.forName("java.awt.Color").getField(color);
                Color decoded = (Color)field.get(null);
                return decoded != null ? decoded : defaultColor;
            }
        } catch (Exception e) {
            logger.error("Could not parse theme color " + color);
        }

        return defaultColor;
    }
}
