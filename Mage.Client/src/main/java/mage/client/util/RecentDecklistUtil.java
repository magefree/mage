package mage.client.util;

import mage.cards.decks.DeckFileFilter;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class RecentDecklistUtil {
    static private final String CLEAR_ITEM = "Clear list";

    private boolean inUpdate = false;
    final private JComboBox<String> control;

    /**
     * Construct a new utility to manage a JComboBox for choosing a decklist,
     * either by selecting a file or by choosing a recent selection.
     *
     * @param control The combobox to use
     */
    public RecentDecklistUtil(JComboBox<String> control) {
        this.control = control;
        initControl();
    }

    /**
     * Setup the combobox control
     */
    private void initControl() {
        control.setEditable(true);

        // We set some arbitrary width to prevent the combobox from growing
        // very wide for long paths.
        control.setPrototypeDisplayValue("xxxxxxxxxxxxxx");

        control.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (!inUpdate) {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        Object selection = event.getItem();
                        if (selection == CLEAR_ITEM) {
                            clear();
                        } else if (selection != null) {
                            putRecentDecklistFiles(selection.toString());
                            update();
                        }
                    }
                }
            }
        });

        // Set up tooltips for combobox items so very long paths don't get truncated.
        control.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel c = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    c.setToolTipText(value.toString());
                }

                return c;
            }
        });
    }

    /**
     * Clear all recent files.
     */
    public void clear() {
        clearRecentDecklistFiles();
        update();
    }

    /**
     * Update the control.
     */
    public void update() {
        try {
            inUpdate = true;
            control.removeAllItems();

            List<String> files = getRecentDecklistFiles();
            for (String filename : files)
                control.addItem(filename);

            if (!files.isEmpty())
                control.addItem(CLEAR_ITEM);

            if (control.getItemCount() > 0)
                control.setSelectedIndex(0);
        } finally {
             inUpdate = false;
        }
    }

    /**
     * Sets the current file to the filename given.
     * Also adds that file to the recent list.
     *
     * @param filename The filename to add
     */
    public void setFile(String filename) {
        putRecentDecklistFiles(filename);
        update();
    }

    /**
     * @return The currently entered/selected filename
     */
    public String getFile() {
        Object selection = control.getEditor().getItem();
        if (selection != null)
            return selection.toString();
        return "";
    }

    /**
     * Let the user select a file on their system by presenting
     * a file chooser.
     */
    public void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new DeckFileFilter("dck", "XMage's deck files (*.dck)"));

        Object item = control.getEditor().getItem();
        if (item != null) {
            File currentFile = new File(item.toString());
            chooser.setCurrentDirectory(currentFile);
        }

        int ret = chooser.showDialog(control, "Select Deck");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                putRecentDecklistFiles(file.getCanonicalPath());
                update();
            } catch (IOException ignore) {
            }
        }
    }

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
