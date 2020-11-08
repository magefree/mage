package mage.client.util;

import com.google.common.collect.ImmutableSet;
import mage.client.MageFrame;
import mage.client.preference.MagePreferences;
import mage.view.ChatMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class IgnoreList {

    private static final String USAGE = ""
            + "<br><font color=yellow>\\ignore - shows your ignore list on this server."
            + "<br>\\ignore username - add username to ignore list (they won't be able to chat or join to your new game)."
            + "<br>\\unignore username - remove a username from your ignore list on this server.</font>";

    public static final int MAX_IGNORE_LIST_SIZE = 500;
    public static final Set<ChatMessage.MessageType> IGNORED_MESSAGE_TYPES
            = ImmutableSet.of(ChatMessage.MessageType.TALK,
            ChatMessage.MessageType.WHISPER_FROM);

    public static String usage(String serverAddress) {
        return "<br>Your ignored list on server " + serverAddress + ": " + getIgnoredUsers(serverAddress).size()
                + USAGE;
    }

    public static Set<String> getIgnoredUsers(String serverAddress) {
        return MagePreferences.ignoreList(serverAddress);
    }

    public static String getIgnoreListInfo(String serverAddress) {
        List<String> list = new ArrayList<>(getIgnoredUsers(serverAddress));
        Collections.sort(list);
        return "<font color=yellow>Current ignore list on " + serverAddress + " (total: " + list.size() + "): "
                + "[" + String.join(", ", list) + "]"
                + "</font>";
    }

    public static String ignore(String serverAddress, String user) {
        if (user == null || user.isEmpty()) {
            return getIgnoreListInfo(serverAddress);
        }

        if (MagePreferences.ignoreList(serverAddress).size() >= MAX_IGNORE_LIST_SIZE) {
            return "Your ignore list is too big (max " + MAX_IGNORE_LIST_SIZE + "), remove a user to be able to add a new one.";
        }

        if (userIsIgnored(serverAddress, user)) {
            return user + " is already on your ignore list on " + serverAddress;
        }

        MagePreferences.addIgnoredUser(serverAddress, user);
        updateTablesTable();

        return "Added " + user + " to your ignore list on " + serverAddress + " (total: " + getIgnoredUsers(serverAddress).size() + ")";
    }

    private static void updateTablesTable() {
        MageFrame mageFrame = MageFrame.getInstance();
        if (mageFrame != null) {
            mageFrame.setTableFilter();
        }
    }

    public static String unignore(String serverAddress, String user) {
        if (user == null || user.isEmpty()) {
            return usage(serverAddress);
        }
        if (MagePreferences.removeIgnoredUser(serverAddress, user)) {
            updateTablesTable();
            return "Removed " + user + " from your ignore list on " + serverAddress + " (total: " + getIgnoredUsers(serverAddress).size() + ")";
        } else {
            return "No such user \"" + user + "\" on your ignore list on " + serverAddress + " (total: " + getIgnoredUsers(serverAddress).size() + ")";
        }
    }

    public static boolean userIsIgnored(String serverAddress, String username) {
        return MagePreferences.ignoreList(serverAddress).contains(username);
    }
}
