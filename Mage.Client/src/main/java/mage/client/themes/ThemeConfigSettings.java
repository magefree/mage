package mage.client.themes;

import com.google.common.collect.ImmutableMap;

import java.awt.*;
import java.net.URL;

/**
 * @author NinthWorld
 */
public interface ThemeConfigSettings {
    String getName();

    String getResourcesParentThemeName();

    String getLafClassPath();

    boolean isDark();

    boolean shouldShowBackground();

    boolean shouldShowLoginBackground();

    boolean shouldShowBattleBackground();

    boolean isShortcutsVisibleForSkipButtons();

    Color getMageToolbarBackgroundColor();

    Color getGameEndTextBackgroundColor();

    Color getCardTooltipBackgroundColor();

    Color getPlayerPanelInactiveBackgroundColor();

    Color getPlayerPanelActiveBackgroundColor();

    Color getPlayerPanelDeadBackgroundColor();

    Color getCardIconsFillColor();

    Color getCardIconsStrokeColor();

    Color getCardIconsTextColor();

    Color getTextOnBackgroundTextColor();

    ImmutableMap<String, Color> getDefaults();

    URL getResource(String name);

    boolean isNimbusLaf();
}
