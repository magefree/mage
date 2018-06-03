

package mage.client.util;

import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EDTExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = Logger.getLogger(EDTExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
    }

    public void handle(Throwable throwable) {
        try {
            logger.fatal("MAGE Client UI error", throwable);
            // JOptionPane.showMessageDialog(MageFrame.getDesktop(), throwable, "MAGE Client UI error", JOptionPane.ERROR_MESSAGE);
        } catch (Throwable t) {}
    }

    public static void registerExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new EDTExceptionHandler());
        System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());
    }

}
