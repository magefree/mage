package mage.util;

/**
 * Apply default java settings for better compatibility in different environments
 *
 * It's still possible to change default settings by JVM params like -Djava.net.preferIPv4Stack=false
 *
 * @author JayDi85
 */
public class JavaUtil {

    private static void applyDefaultSettings() {
        // workaround for broken connection:
        // current network stack (jboss remoting) support only ipv4
        // so make sure it will be used all around
        // (some new systems uses ipv6 by default and clients can't connect to server due diff stack)
        if (System.getProperty("java.net.preferIPv4Stack") == null) {
            System.setProperty("java.net.preferIPv4Stack", "true");
        }
    }

    public static void applyDefaultServerSettings() {
        applyDefaultSettings();
    }

    public static void applyDefaultClientSettings() {
        applyDefaultSettings();

        // workaround for bad folders:
        // some windows has bad folders structure with same names but diff dirs
        // it's allow to skip arror on open such dirs in file chooser
        // see #13126
        // TODO: remove after migrate to java 21+
        if (System.getProperty("java.util.Arrays.useLegacyMergeSort") == null) {
            System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        }

        // workaround for broken images download:
        // there are some images links with non-ascii chars, so non-utf8 systems 
        // can't use it (example: encoding windows-1251 in old russian windows)
        if (System.getProperty("file.encoding") == null) {
            System.setProperty("file.encoding", "UTF-8");
        }
        if (System.getProperty("sun.jnu.encoding") == null) {
            System.setProperty("sun.jnu.encoding", "UTF-8");
        }
    }
}
