package mage.client.util;

import mage.client.MageFrame;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class AppUtil {

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
}
