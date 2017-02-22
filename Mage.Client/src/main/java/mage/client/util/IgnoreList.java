package mage.client.util;

import com.google.common.collect.ImmutableSet;
import mage.client.MageFrame;
import mage.client.preference.MagePreferences;
import mage.view.ChatMessage;

import java.util.Arrays;
import java.util.Set;

public class IgnoreList {

    private static final String USAGE = "<br/><font color=yellow>\\ignore - shows current ignore list on this server."
            + "<br/>\\ignore [username] - add a username to your ignore list on this server."
            + "<br/>\\unignore [username] - remove a username from your ignore list on this server.</font>";

    public static final int MAX_IGNORE_LIST_SIZE = 50;
    public static Set<ChatMessage.MessageType> IGNORED_MESSAGE_TYPES =
            ImmutableSet.of(ChatMessage.MessageType.TALK,
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
        if (user == null || user.length() == 0) {
            return ignoreListString(serverAddress);
        }

        if (MagePreferences.ignoreList(serverAddress).size() >= MAX_IGNORE_LIST_SIZE) {
            return "Your ignore list is too big, remove a user to be able to add a new one.";
        }

        if (userIsIgnored(serverAddress, user)) {
            return new StringBuilder()
                    .append(user)
                    .append(" is already on your ignore list on ")
                    .append(serverAddress)
                    .toString();
        }

        MagePreferences.addIgnoredUser(serverAddress, user);
        updateTablesTable();

        return new StringBuilder()
                .append("Added ")
                .append(user)
                .append(" to your ignore list on ")
                .append(serverAddress)
                .toString();
    }

    private static void updateTablesTable() {
        MageFrame mageFrame = MageFrame.getInstance();
        if (mageFrame != null) {
            mageFrame.setTableFilter();
        }
    }

    public static String unignore(String serverAddress, String user) {
        if (user == null || user.length() == 0) {
            return usage();
        }
        if (MagePreferences.removeIgnoredUser(serverAddress, user)) {
            updateTablesTable();
            return new StringBuilder()
                    .append("Removed ")
                    .append(user)
                    .append(" from your ignore list on ")
                    .append(serverAddress)
                    .toString();
        } else {
            return new StringBuilder()
                    .append("No such user \"")
                    .append(user)
                    .append("\" on your ignore list on ")
                    .append(serverAddress)
                    .toString();
        }
    }

    public static boolean userIsIgnored(String serverAddress, String username) {
        return MagePreferences.ignoreList(serverAddress).contains(username);
    }
}
