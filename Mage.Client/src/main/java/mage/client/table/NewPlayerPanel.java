package mage.client.table;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javax.swing.*;

import mage.cards.decks.DeckFileFilter;
import mage.client.MageFrame;
import mage.client.deck.generator.DeckGenerator;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.ClientDefaultSettings;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NewPlayerPanel extends javax.swing.JPanel {

    private final JFileChooser fcSelectDeck;

    public NewPlayerPanel() {
        initComponents();
        fcSelectDeck = new JFileChooser();
        fcSelectDeck.setAcceptAllFileFilterUsed(false);
        fcSelectDeck.addChoosableFileFilter(new DeckFileFilter("dck", "XMage's deck files (*.dck)"));
        txtPlayerName.setText(ClientDefaultSettings.computerName);
        cbPlayerDeck.setEditable(true);

        // We set some arbitrary width to prevent the combobox from growing
        // very wide for long paths.
        cbPlayerDeck.setPrototypeDisplayValue("xxxxxxxxxxxxxx");

        // Set up tooltips for combobox items so very long paths don't get truncated.
        cbPlayerDeck.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    if (c instanceof JComponent) {
                        ((JComponent) c).setToolTipText(value.toString());
                    }
                }

                return c;
            }
        });

        // Add a context menu to the combobox that allows clearing the list
        // of recent decks.
        JPopupMenu deckContextMenu = new JPopupMenu();
        JMenuItem clearItem = new JMenuItem("Clear recent decks");
        clearItem.addActionListener(event -> {
            clearRecentDeckFiles();
        });
        deckContextMenu.add(clearItem);

        cbPlayerDeck.getEditor().getEditorComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { showContext(e); }
            @Override
            public void mouseReleased(MouseEvent e) { showContext(e); }

            public void showContext(MouseEvent event) {
                if (event.isPopupTrigger()) {
                    deckContextMenu.show(event.getComponent(), event.getX(), event.getY());
                }
            }
        });

        // If the user selection changes, move the last selected item to the
        // front of the list.
        cbPlayerDeck.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object selection = event.getItem();
                if (selection != null) {
                    putRecentDeckFile(selection.toString());
                }
            }
        });

        loadRecentDeckFiles();
    }

    /**
     * Clears the list of recent deck files and removes all items from the combobox
     */
    private void clearRecentDeckFiles() {
        Preferences prefs = MageFrame.getPreferences();
        prefs.put(PreferencesDialog.KEY_RECENT_DECKLIST_FILES, "");
        cbPlayerDeck.removeAllItems();
    }

    /**
     * Get the list of recently used deck filenames.
     * Only filenames that point to readable files are returned.
     *
     * @return A list of filenames
     */
    private List<String> getRecentDeckFiles() {
        Preferences prefs = MageFrame.getPreferences();

        int limit = prefs.getInt(PreferencesDialog.KEY_MAX_RECENT_DECKLIST_FLIES, 10);
        if (limit <= 0) {
            return new ArrayList<>();
        } else {
            return Arrays.stream(prefs.get(PreferencesDialog.KEY_RECENT_DECKLIST_FILES, "").split(";"))
                    .limit(limit)
                    .filter(filename -> {
                        File file = new File(filename);
                        return file.canRead();
                    })
                    .collect(Collectors.toList());
        }
    }

    /**
     * Put a filename to the list of recent decks.
     *
     * @param filename Filename of the deck to add
     */
    private void putRecentDeckFile(String filename) {
        if (filename == null || filename.isEmpty() || filename.contains(";"))
            return;

        File file = new File(filename);
        if (!file.canRead())
            return;

        List<String> current = new ArrayList<>(getRecentDeckFiles());
        current.removeIf(filename::equals);
        current.add(0, filename);

        Preferences prefs = MageFrame.getPreferences();
        prefs.put(PreferencesDialog.KEY_RECENT_DECKLIST_FILES, String.join(";", current));
    }

    /**
     * Fill the combobox with the recent n filenames.
     */
    public void loadRecentDeckFiles() {
        cbPlayerDeck.removeAllItems();
        for (String filename : getRecentDeckFiles())
            cbPlayerDeck.addItem(filename);

        if (cbPlayerDeck.getItemCount() > 0)
            cbPlayerDeck.setSelectedIndex(0);
    }

    public void setPlayerName(String playerName) {
        txtPlayerName.setText(playerName);
        txtPlayerName.setEditable(false);
        txtPlayerName.setEnabled(false);
    }

    /**
     * Called when the user presses the [...] button to select a deck file.
     */
    protected void playerLoadDeck() {
        Object item = cbPlayerDeck.getEditor().getItem();
        if (item != null) {
            File currentFile = new File(item.toString());
            fcSelectDeck.setCurrentDirectory(currentFile);
        }

        int ret = fcSelectDeck.showDialog(this, "Select Deck");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcSelectDeck.getSelectedFile();

            try {
                putRecentDeckFile(file.getCanonicalPath());
                loadRecentDeckFiles();
            } catch (IOException ignore) {
            }
        }
    }

    protected void generateDeck() {
        String path = DeckGenerator.generateDeck();
        if (path == null) {
            return;
        }

        putRecentDeckFile(path);
        loadRecentDeckFiles();
    }

    public String getPlayerName() {
        return txtPlayerName.getText();
    }

    public String getDeckFile() {
        Object item = cbPlayerDeck.getEditor().getItem();
        if (item != null) {
            return item.toString();
        }
        return "";
    }

    public void setDeckFile(String filename) {
        putRecentDeckFile(filename);
        loadRecentDeckFiles();
    }

    public void setSkillLevel(int level) {
        spnLevel.setValue(level);
    }

    public int getSkillLevel() {
        return (Integer) spnLevel.getValue();
    }

    public void showLevel(boolean show) {
        spnLevel.setVisible(show);
        lblLevel.setVisible(show);
    }

    public void showDeckElements(boolean show) {
        lblPlayerDeck.setVisible(show);
        cbPlayerDeck.setVisible(show);
        btnGenerate.setVisible(show);
        btnPlayerDeck.setVisible(show);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPlayerName = new javax.swing.JLabel();
        txtPlayerName = new javax.swing.JTextField();
        lblPlayerDeck = new javax.swing.JLabel();
        cbPlayerDeck = new javax.swing.JComboBox<>();
        btnPlayerDeck = new javax.swing.JButton();
        btnGenerate = new javax.swing.JButton();
        lblLevel = new javax.swing.JLabel();
        spnLevel = new javax.swing.JSpinner();

        lblPlayerName.setText("Name:");

        lblPlayerDeck.setText("Deck:");

        btnPlayerDeck.setText("...");
        btnPlayerDeck.addActionListener(evt -> btnPlayerDeckActionPerformed(evt));

        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(evt -> btnGenerateActionPerformed(evt));

        lblLevel.setText("Skill:");

        spnLevel.setModel(new javax.swing.SpinnerNumberModel(2, 1, 10, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblPlayerDeck))
                    .addComponent(lblPlayerName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPlayerName, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(cbPlayerDeck, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPlayerDeck, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLevel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spnLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlayerName)
                    .addComponent(lblLevel)
                    .addComponent(spnLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlayerDeck)
                    .addComponent(cbPlayerDeck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlayerDeck, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlayerDeckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayerDeckActionPerformed
        playerLoadDeck();
}//GEN-LAST:event_btnPlayerDeckActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        generateDeck();
    }//GEN-LAST:event_btnGenerateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnPlayerDeck;
    private javax.swing.JLabel lblLevel;
    private javax.swing.JLabel lblPlayerDeck;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JSpinner spnLevel;
    private javax.swing.JComboBox<String> cbPlayerDeck;
    private javax.swing.JTextField txtPlayerName;
    // End of variables declaration//GEN-END:variables

}
