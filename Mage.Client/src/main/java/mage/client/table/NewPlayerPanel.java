package mage.client.table;

import java.io.File;
import java.io.IOException;
import javax.swing.*;

import mage.cards.decks.DeckFileFilter;
import mage.client.MageFrame;
import mage.client.deck.generator.DeckGenerator;
import mage.client.util.ClientDefaultSettings;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NewPlayerPanel extends javax.swing.JPanel {

    private final JFileChooser fcSelectDeck;
    private boolean allowRandomDeckFolder;

    /**
     * Constructs a NewPlayerPanel configured for creating and configuring an AI/computer player.
     *
     * Initializes Swing components, creates and configures the deck file chooser (disables "accept all"
     * filter and adds a `*.dck` filter), sets the panel to disallow folder selection for random decks,
     * clears the deck text field, and sets the player name field from the default computer name.
     */
    public NewPlayerPanel() {
        initComponents();
        fcSelectDeck = new JFileChooser();
        fcSelectDeck.setAcceptAllFileFilterUsed(false);
        fcSelectDeck.addChoosableFileFilter(new DeckFileFilter("dck", "XMage's deck files (*.dck)"));
        setAllowRandomDeckFolder(false);
        this.txtPlayerDeck.setText("");
        this.txtPlayerName.setText(ClientDefaultSettings.computerName);
    }

    public void setPlayerName(String playerName) {
        this.txtPlayerName.setText(playerName);
        this.txtPlayerName.setEditable(false);
        this.txtPlayerName.setEnabled(false);
    }
    
    /**
     * Prompts the user with a file chooser to select a deck file or (if allowed) a deck folder,
     * writes the chosen path into the player deck text field, and saves the chosen directory to preferences.
     *
     * The chooser's approve button label reflects whether folder selection is permitted. If the user approves,
     * the selected file's path is set into {@code txtPlayerDeck} and the selection's canonical path is stored
     * under the preference key "lastDeckFolder". Any {@link IOException} thrown while saving the preference is ignored.
     */
    protected void playerLoadDeck() {
        String lastFolder = MageFrame.getPreferences().get("lastDeckFolder", "");
        if (!lastFolder.isEmpty()) {
            fcSelectDeck.setCurrentDirectory(new File(lastFolder));
        }
        int ret = fcSelectDeck.showDialog(
                this,
                allowRandomDeckFolder ? "Select Deck or Folder" : "Select Deck");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcSelectDeck.getSelectedFile();
            this.txtPlayerDeck.setText(file.getPath());
            try {
                MageFrame.getPreferences().put("lastDeckFolder", file.getCanonicalPath());
            } catch (IOException ex) {
            }
        }
        fcSelectDeck.setSelectedFile(null);
    }

    protected void generateDeck() {
        String path = DeckGenerator.generateDeck();
        if (path == null) {
            return;
        }
        this.txtPlayerDeck.setText(path);
    }

    public String getPlayerName() {
        return this.txtPlayerName.getText();
    }

    public String getDeckFile() {
        return this.txtPlayerDeck.getText();
    }

    public void setDeckFile(String deckFile) {
        this.txtPlayerDeck.setText(deckFile);
    }

    public void setSkillLevel(int level) {
        this.spnLevel.setValue(level);
    }

    public int getSkillLevel() {
        return (Integer) spnLevel.getValue();
    }

    public void showLevel(boolean show) {
        this.spnLevel.setVisible(show);
        this.lblLevel.setVisible(show);
    }

    /**
     * Shows or hides the deck-related UI controls.
     *
     * When shown or hidden, this affects the deck label, deck text field, the Generate button,
     * and the deck Browse button.
     *
     * @param show true to make the deck controls visible, false to hide them
     */
    public void showDeckElements(boolean show) {
        this.lblPlayerDeck.setVisible(show);
        this.txtPlayerDeck.setVisible(show);
        this.btnGenerate.setVisible(show);
        this.btnPlayerDeck.setVisible(show);
    }

    /**
     * Configure whether the deck chooser accepts directories in addition to individual deck files.
     *
     * When enabled, the file chooser will allow selecting folders (for recursive random deck selection)
     * and the UI label/tooltips will reflect that behavior; when disabled, only individual `.dck` files
     * may be selected.
     *
     * @param allowRandomDeckFolder true to allow selecting directories for recursive random deck selection, false to allow only `.dck` files
     */
    public void setAllowRandomDeckFolder(boolean allowRandomDeckFolder) {
        this.allowRandomDeckFolder = allowRandomDeckFolder;
        this.fcSelectDeck.setFileSelectionMode(
                allowRandomDeckFolder ? JFileChooser.FILES_AND_DIRECTORIES : JFileChooser.FILES_ONLY);
        this.lblPlayerDeck.setText(allowRandomDeckFolder ? "Deck/folder:" : "Deck:");
        this.txtPlayerDeck.setToolTipText(allowRandomDeckFolder
                ? "Select a .dck file, or a folder to choose a random .dck file recursively for this AI player."
                : "Select a .dck deck file.");
        this.btnPlayerDeck.setToolTipText(this.txtPlayerDeck.getToolTipText());
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
        txtPlayerDeck = new javax.swing.JTextField();
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
                    .addComponent(txtPlayerDeck, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
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
                    .addComponent(txtPlayerDeck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JTextField txtPlayerDeck;
    private javax.swing.JTextField txtPlayerName;
    // End of variables declaration//GEN-END:variables

}
