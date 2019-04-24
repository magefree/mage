package mage.client.util;

import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Set;
import mage.client.MageFrame;
import mage.client.preference.MagePreferences;
import mage.view.ChatMessage;

public final class IgnoreList {

    private static final String USAGE = "<br/><font color=yellow>\\ignore - shows your ignore list on this server."
            + "<br/>\\ignore [username] - add username to ignore list (they won't be able to chat or join to your game)."
            + "<br/>\\unignore [username] - remove a username from your ignore list on this server.</font>";

    public static final int MAX_IGNORE_LIST_SIZE = 50;
    public static final Set<ChatMessage.MessageType> IGNORED_MESSAGE_TYPES
            = ImmutableSet.of(ChatMessage.MessageType.TALK,
                    ChatMessage.MessageType.WHISPER_FROM);

    public static String usage() {
        return USAGE;
    }

    public static Set<String> ignoreList(String serverAddress) {
        return MagePreferences.ignoreList(serverAddress);
    }

    public static String ignoreListString(String serverAddress) {
        final String[] list = MagePreferences.ignoreList(serverAddress).toArray(new String[0]);
        Arrays.sort(list);
        return "<font color=yellow>Current ignore list on " + serverAddress + ": "
                + Arrays.toString(list)
                + "</font>";
    }

    public static String ignore(String serverAddress, String user) {
        if (user == null || user.isEmpty()) {
            return ignoreListString(serverAddress);
        }

        if (MagePreferences.ignoreList(serverAddress).size() >= MAX_IGNORE_LIST_SIZE) {
            return "Your ignore list is too big, remove a user to be able to add a new one.";
        }

        if (userIsIgnored(serverAddress, user)) {
            return user + " is already on your ignore list on " + serverAddress;
        }

        MagePreferences.addIgnoredUser(serverAddress, user);
        updateTablesTable();

        return "Added " + user + " to your ignore list on " + serverAddress;
    }

    private static void updateTablesTable() {
        MageFrame mageFrame = MageFrame.getInstance();
        if (mageFrame != null) {
            mageFrame.setTableFilter();
        }
    }

    public static String unignore(String serverAddress, String user) {
        if (user == null || user.isEmpty()) {
            return usage();
        }
        if (MagePreferences.removeIgnoredUser(serverAddress, user)) {
            updateTablesTable();
            return "Removed " + user + " from your ignore list on " + serverAddress;
        } else {
            return "No such user \"" + user + "\" on your ignore list on " + serverAddress;
        }
    }

    public static boolean userIsIgnored(String serverAddress, String username) {
        return MagePreferences.ignoreList(serverAddress).contains(username);
    }
}
