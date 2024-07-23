package mage.client.util;

import mage.client.MageFrame;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Helper class for client side app
 *
 * @author JayDi85
 */
public class AppUtil {

    private static final Logger logger = Logger.getLogger(AppUtil.class);

    /**
     * Application is active in operation system (got user focus)
     */
    public static boolean isAppActive() {
        return MageFrame.getInstance().isActive();
    }

    /**
     * Current active panel is game panel (e.g. the user sees the checking game)
     *
     * @param gameId game to check
     * @return
     */
    public static boolean isGameActive(UUID gameId) {
        return MageFrame.getInstance().isGameFrameActive(gameId);
    }

    /**
     * Save text to clipboard
     */
    public static void setClipboardData(String text) {
        try {
            StringSelection data = new StringSelection(text);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
        } catch (HeadlessException ignore) {
        }
    }

    public static void openUrlInBrowser(String url) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                URI uri = new URI(url);
                desktop.browse(uri);
            } catch (IOException | URISyntaxException e) {
                logger.error("Can't open url in browser: " + url, e);
            }
        } else {
            logger.error("Can't open url in browser: non supported desktop mode");
        }
    }
}
