package mage.client.components;

import mage.cards.decks.DeckFileFilter;
import mage.client.deck.generator.DeckGenerator;
import mage.client.util.RecentDecklistUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DecklistChooser extends JPanel
{
    private final String CLEAR_ITEM = "Clear recent decks";

    private final JButton chooseButton;
    private final JButton generateButton;
    private final JComboBox<String> decklistCombobox;
    private boolean inUpdate = false;

    public DecklistChooser() {
        chooseButton = new JButton("...");
        chooseButton.setToolTipText("Select deck file...");

        generateButton = new JButton("Generate");
        generateButton.setToolTipText("Generate a new deck...");

        decklistCombobox = new JComboBox<>();

        setupControls();
        update();
    }

    private void setupControls() {
        chooseButton.addActionListener(e -> chooseFile());
        generateButton.addActionListener(e -> generateDeck());

        decklistCombobox.setEditable(true);

        // We set some arbitrary width to prevent the combobox from growing
        // very wide for long paths.
        decklistCombobox.setPrototypeDisplayValue("xxxxxxxxxxxxxx");

        decklistCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (!inUpdate) {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        Object selection = event.getItem();
                        if (selection == CLEAR_ITEM) {
                            clear();
                        } else if (selection != null) {
                            RecentDecklistUtil.putRecentDecklistFiles(selection.toString());
                            update();
                        }
                    }
                }
            }
        });

        // Set up tooltips for combobox items so very long paths don't get truncated.
        decklistCombobox.setRenderer(new DefaultListCellRenderer() {
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

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(decklistCombobox, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(chooseButton, gbc);

        gbc.gridx = 2;
        add(generateButton, gbc);
    }

    /**
     * Update the control.
     */
    public void update() {
        try {
            inUpdate = true;
            decklistCombobox.removeAllItems();

            List<String> files = RecentDecklistUtil.getRecentDecklistFiles();
            for (String filename : files)
                decklistCombobox.addItem(filename);

            if (!files.isEmpty())
                decklistCombobox.addItem(CLEAR_ITEM);

            if (decklistCombobox.getItemCount() > 0)
                decklistCombobox.setSelectedIndex(0);
        } finally {
            inUpdate = false;
        }
    }

    /**
     * Clear all recent files.
     */
    public void clear() {
        RecentDecklistUtil.clearRecentDecklistFiles();
        update();
    }

    /**
     * Sets the current file to the filename given.
     * Also adds that file to the recent list.
     *
     * @param filename The filename to add
     */
    public void setFile(String filename) {
        RecentDecklistUtil.putRecentDecklistFiles(filename);
        update();
    }

    /**
     * @return The currently entered/selected filename
     */
    public String getFile() {
        Object selection = decklistCombobox.getEditor().getItem();
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

        Object item = decklistCombobox.getEditor().getItem();
        if (item != null) {
            File currentFile = new File(item.toString());
            chooser.setCurrentDirectory(currentFile);
        }

        int ret = chooser.showDialog(decklistCombobox, "Select Deck");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                RecentDecklistUtil.putRecentDecklistFiles(file.getCanonicalPath());
                update();
            } catch (IOException ignore) {
            }
        }
    }

    /**
     * Generate a new deck using the “Generate Deck” dialog.
     */
    private void generateDeck() {
        String filename = DeckGenerator.generateDeck();
        if (filename != null) {
            RecentDecklistUtil.putRecentDecklistFiles(filename);
            update();
        }
    }
}
