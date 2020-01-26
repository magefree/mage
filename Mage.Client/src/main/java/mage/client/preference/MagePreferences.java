package mage.client.preference;

import com.google.common.collect.Sets;
import mage.client.MageFrame;
import mage.client.util.ClientDefaultSettings;

import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

// TODO: Move all preference related logic from MageFrame and PreferencesDialog to this class.
public final class MagePreferences {

    private static final String KEY_SERVER_ADDRESS = "serverAddress";
    private static final String KEY_SERVER_PORT = "serverPort";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_AUTO_CONNECT = "autoConnect";
    private static final String NODE_KEY_IGNORE_LIST = "ignoreListString";

    private static String lastServerAddress = "";
    private static int lastServerPort = 0;
    private static String lastServerUser = "";
    private static String lastServerPassword = "";

    private static Preferences prefs() {
        // TODO: Move MageFrame.prefs to this class.
        return MageFrame.getPreferences();
    }

    public static String getServerAddress() {
        return prefs().get(KEY_SERVER_ADDRESS, "");
    }

    public static String getServerAddressWithDefault(String defaultValue) {
        return prefs().get(KEY_SERVER_ADDRESS, defaultValue);
    }

    public static void setServerAddress(String serverAddress) {
        prefs().put(KEY_SERVER_ADDRESS, serverAddress);
    }

    public static int getServerPort() {
        return prefs().getInt(KEY_SERVER_PORT, 0);
    }

    public static int getServerPortWithDefault(int defaultValue) {
        return prefs().getInt(KEY_SERVER_PORT, defaultValue);
    }

    public static void setServerPort(int port) {
        prefs().putInt(KEY_SERVER_PORT, port);
    }

    private static String prefixedKey(String prefix, String key) {
        return prefix + '/' + key;
    }

    public static String getUserName(String serverAddress) {
        String userName = prefs().get(prefixedKey(serverAddress, KEY_USER_NAME), "");

        if (!userName.isEmpty()) {
            return userName;
        }
        // For clients older than 1.4.7, userName is stored without a serverAddress prefix.
        return prefs().get(KEY_USER_NAME, "");
    }

    public static String getUserNames() {
        StringBuilder userIds = new StringBuilder();
        try {
            String[] keys = prefs().keys();
            for (String key : keys) {
                if (key.matches(".*userName$")) {
                    userIds.append(',').append(prefs().get(key, null));
                }
                if (key.matches(".*DeckPath.*")) {
                    userIds.append(',').append(prefs().get(key, null));
                }
            }
        } catch (BackingStoreException ex) {
        }
        return userIds.toString();
    }

    public static void setUserName(String serverAddress, String userName) {
        prefs().put(prefixedKey(serverAddress, KEY_USER_NAME), userName);
    }

    public static String getPassword(String serverAddress) {
        return prefs().get(prefixedKey(serverAddress, KEY_PASSWORD), "");
    }

    public static void setPassword(String serverAddress, String password) {
        prefs().put(prefixedKey(serverAddress, KEY_PASSWORD), password);
    }

    public static String getEmail(String serverAddress) {
        return prefs().get(prefixedKey(serverAddress, KEY_EMAIL), "");
    }

    public static void setEmail(String serverAddress, String userName) {
        prefs().put(prefixedKey(serverAddress, KEY_EMAIL), userName);
    }

    public static boolean getAutoConnect() {
        return prefs().getBoolean(KEY_AUTO_CONNECT, false);
    }

    public static void setAutoConnect(boolean autoConnect) {
        prefs().putBoolean(KEY_AUTO_CONNECT, autoConnect);
    }

    public static void addIgnoredUser(String serverAddress, String username) {
        ignoreListNode(serverAddress).putBoolean(username, true);
    }

    public static boolean removeIgnoredUser(String serverAddress, String username) {
        Preferences ignoreList = ignoreListNode(serverAddress);
        boolean exists = ignoreList.getBoolean(username, false);
        if (exists) {
            ignoreList.remove(username);
        }

        return exists;
    }

    public static Set<String> ignoreList(String serverAddress) {
        try {
            return Sets.newHashSet(ignoreListNode(serverAddress).keys());
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        return Sets.newHashSet();
    }

    public static void clearIgnoreList(String serverAddress) throws BackingStoreException {
        ignoreListNode(serverAddress).clear();
    }

    private static Preferences ignoreListNode(String serverAddress) {
        return prefs().node(NODE_KEY_IGNORE_LIST).node(serverAddress);
    }

    public static void saveLastServer() {
        lastServerAddress = getServerAddressWithDefault(ClientDefaultSettings.serverName);
        lastServerPort = getServerPortWithDefault(ClientDefaultSettings.port);
        lastServerUser = getUserName(lastServerAddress);
        lastServerPassword = getPassword(lastServerAddress);
    }

    public static String getLastServerAddress() {
        return lastServerAddress.isEmpty() ? getServerAddress() : lastServerAddress;
    }

    public static int getLastServerPort() {
        return lastServerPort == 0 ? getServerPort() : lastServerPort;
    }

    public static String getLastServerUser() {
        return lastServerUser.isEmpty() ? getUserName(getLastServerAddress()) : lastServerUser;
    }

    public static String getLastServerPassword() {
        return lastServerPassword.isEmpty() ? getPassword(getLastServerAddress()) : lastServerPassword;
    }
}
