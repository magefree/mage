package mage.client.themes;

import com.google.common.collect.ImmutableMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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

    ImmutableMap<String, Object> getDefaults();

    InputStream getResourceStream(String name) throws IOException;

    BufferedImage getResourceImage(String name);

    boolean isNimbusLaf();
}
