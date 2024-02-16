package mage.client.util;

import java.awt.*;
import java.lang.reflect.Method;

/**
 * @author noxx
 */
public final class MacFullscreenUtil {

    public static final String OS_NAME = "os.name";
    public static final String MAC_OS_X = "Mac OS X";

    private MacFullscreenUtil() {}

    public static boolean isMacOSX() {
        return System.getProperty(OS_NAME).contains(MAC_OS_X);
    }

    public static void enableMacOSFullScreenMode(Window window) {
        if (supportsAppleEAWT()) {
            String className = "com.apple.eawt.FullScreenUtilities";
            String methodName = "setWindowCanFullScreen";

            try {
                Class<?> clazz = Class.forName(className);
                Method method = clazz.getMethod(methodName, Window.class, boolean.class);
                method.invoke(null, window, true);
            } catch (Throwable t) {
                System.err.println("Full screen mode is not supported");
                t.printStackTrace();
            }
        } else {
            // Nothing needed. Running with Java 11+ (even when compiled for 1.8) automatically allows for full screen toggling.
        }
    }

    public static void toggleMacOSFullScreenMode(Window window) {
        if (supportsAppleEAWT()) {
            String className = "com.apple.eawt.Application";
            String methodName = "getApplication";
            String methodName2 = "requestToggleFullScreen";

            try {
                Class<?> clazz = Class.forName(className);
                Method method = clazz.getMethod(methodName);
                Object appInstance = method.invoke(clazz);

                Class[] params = new Class[]{Window.class};
                method = clazz.getMethod(methodName2, params);
                method.invoke(appInstance, window);
            } catch (Throwable t) {
                System.err.println("Full screen mode is not supported");
                t.printStackTrace();
            }
        } else {
            // TODO: Need a solution for automatically entering fullscreen under Java 11+
            //       The other approach given online does not work, it will not provide native fullscreen that you can toggle out of:
            //          GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            //          if (gd.isFullScreenSupported()) {
            //              gd.setFullScreenWindow(window);
            //          }
        }
    }

    private static boolean supportsAppleEAWT() {
        return System.getProperty("java.version").startsWith("1.8");
    }
}
