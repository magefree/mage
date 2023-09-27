package mage.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EDTExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(EDTExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
    }

    public void handle(Throwable throwable) {
        try {
            logger.error("MAGE Client UI error", throwable);
            // JOptionPane.showMessageDialog(MageFrame.getDesktop(), throwable, "MAGE Client UI error", JOptionPane.ERROR_MESSAGE);
        } catch (Throwable t) {}
    }

    public static void registerExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new EDTExceptionHandler());
        System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());
    }

}
