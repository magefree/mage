package mage.client.util;

import mage.client.MageFrame;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * GUI helper class - catch all app's unhandled errors
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class EDTExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = Logger.getLogger(EDTExceptionHandler.class);

    private static final Map<String, Integer> foundErrors = new HashMap<>();
    private static MageFrame mainApp = null; // app to show error dialogs

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
    }

    public void handle(Throwable throwable) {
        try {
            logger.fatal("MAGE Client UI error", throwable);

            // show error dialog for better users feedback about client side errors
            // with some protection from dialogs spam on screen (e.g. on render/graphic errors)
            String errorKey = throwable.toString();
            int foundCount = foundErrors.getOrDefault(errorKey, 0);
            if (foundCount < 5 && mainApp != null) {
                mainApp.showErrorDialog("CLIENT - unhandled error in GUI", throwable);
                foundCount++;
            }
            foundErrors.put(errorKey, foundCount);
        } catch (Throwable ignore) {
        }
    }

    public static void registerExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new EDTExceptionHandler());
        System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());
    }

    public static void registerMainApp(MageFrame app) {
        mainApp = app;
    }

}
