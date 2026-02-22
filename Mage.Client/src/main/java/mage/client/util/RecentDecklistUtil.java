package mage.client.util;

import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class RecentDecklistUtil {
    /**
     * @return The most recent decklist directory
     */
    public static String getRecentDecklistDir() {
        List<String> list = getRecentDecklistFiles();
        if (!list.isEmpty()) {
            File file = new File(list.get(0));
            return file.getParent();
        }
        return "";
    }

    /**
     * Get the list of recently used deck filenames.
     * Only filenames that point to readable files are returned.
     *
     * @return A list of filenames
     */
    public static List<String> getRecentDecklistFiles() {
        final int limit = 10;

        Preferences prefs = MageFrame.getPreferences();
        return Arrays.stream(prefs.get(PreferencesDialog.KEY_RECENT_DECKLIST_FILES, "").split(";"))
                .limit(limit)
                .filter(filename -> {
                    File file = new File(filename);
                    return file.canRead() && file.isFile();
                })
                .collect(Collectors.toList());
    }

    /**
     * Clears the list of recent deck files and removes all items from the combobox
     */
    public static void clearRecentDecklistFiles() {
        Preferences prefs = MageFrame.getPreferences();
        prefs.put(PreferencesDialog.KEY_RECENT_DECKLIST_FILES, "");
    }

    /**
     * Put a filename to the list of recent decks.
     *
     * @param filename Filename of the deck to add
     */
    public static void putRecentDecklistFiles(String filename) {
        if (filename == null || filename.isEmpty() || filename.contains(";"))
            return;

        File file = new File(filename);
        if (!file.canRead())
            return;

        List<String> current = new ArrayList<>(getRecentDecklistFiles());
        current.removeIf(filename::equals);
        current.add(0, filename);

        Preferences prefs = MageFrame.getPreferences();
        prefs.put(PreferencesDialog.KEY_RECENT_DECKLIST_FILES, String.join(";", current));
    }
}
