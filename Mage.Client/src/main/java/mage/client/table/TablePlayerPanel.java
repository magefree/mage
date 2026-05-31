package mage.client.table;

import mage.cards.decks.importer.DeckImporter;
import mage.client.SessionHandler;
import mage.client.util.ClientDefaultSettings;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.players.PlayerType;

import javax.swing.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TablePlayerPanel extends javax.swing.JPanel {

    protected final PlayerTypeEventSource playerTypeEventSource = new PlayerTypeEventSource();

    /**
     * Creates a TablePlayerPanel and initializes its UI components.
     *
     * Configures the embedded NewPlayerPanel to allow random deck folder selection and keeps it hidden initially.
     */
    public TablePlayerPanel() {
        initComponents();
        this.newPlayerPanel.setAllowRandomDeckFolder(true);
        this.newPlayerPanel.setVisible(false);
    }

    public void init(int playerNum, PlayerType playerType, int playerSkill, String playerDeck) {
        cbPlayerType.setModel(new DefaultComboBoxModel(SessionHandler.getPlayerTypes()));
        this.lblPlayerNum.setText("Player " + playerNum);
        if (ClientDefaultSettings.otherPlayerIndex != null) {
            Integer index = Integer.parseInt(ClientDefaultSettings.otherPlayerIndex);
            if (index >= cbPlayerType.getItemCount()) {
                cbPlayerType.setSelectedIndex(cbPlayerType.getItemCount() - 1);
            } else {
                cbPlayerType.setSelectedIndex(index);
            }
        }
        if (playerType != null) {
            this.cbPlayerType.setSelectedItem(playerType);
        }
        this.newPlayerPanel.setDeckFile(playerDeck);
        this.newPlayerPanel.setSkillLevel(playerSkill);
    }

    /**
     * Builds a display name for an AI player by appending the last one or two characters of the provided label to the configured computer name.
     *
     * @param label the player label (for example "Player 1"); the method will use up to the last two characters of this label
     * @return the AI player name composed of the configured computer name, a space, and the trimmed suffix extracted from {@code label}
     */
    public static String extractAiPlayerNumberFromLabel(String label) {
        return ClientDefaultSettings.computerName + " " + label.substring(Math.max(0, label.length() - 2)).trim();
    }

    /**
     * Attempts to join the specified table in the given room using an empty set of previously used deck paths.
     *
     * @param roomId the UUID of the room to join
     * @param tableId the UUID of the table to join
     * @return true if the join operation succeeded, false otherwise
     * @throws IOException if an I/O error occurs while joining
     * @throws ClassNotFoundException if a required class cannot be found during join
     */
    public boolean joinTable(UUID roomId, UUID tableId) throws IOException, ClassNotFoundException {
        return joinTable(roomId, tableId, new HashSet<>());
    }

    /**
     * Joins the specified table using the currently selected player type and configuration, resolving an AI deck from available paths when needed.
     *
     * @param roomId        the UUID of the room to join
     * @param tableId       the UUID of the table to join
     * @param usedDeckPaths a set of deck file paths that should be avoided when selecting a random AI deck
     * @return              `true` when the selected player type is HUMAN or when joining the table succeeds; `false` otherwise
     * @throws IOException              if an I/O error occurs while importing a deck or communicating with the server
     * @throws ClassNotFoundException   if a required class cannot be found during deserialization when joining
     */
    public boolean joinTable(UUID roomId, UUID tableId, Set<String> usedDeckPaths) throws IOException, ClassNotFoundException {
        if (this.cbPlayerType.getSelectedItem() != PlayerType.HUMAN) {
            String deckFile = RandomDeckSelector.resolveDeckPath(this.newPlayerPanel.getDeckFile(), usedDeckPaths);
            return SessionHandler.joinTable(
                    roomId,
                    tableId,
                    this.newPlayerPanel.getPlayerName(),
                    (PlayerType) this.cbPlayerType.getSelectedItem(),
                    this.newPlayerPanel.getSkillLevel(),
                    DeckImporter.importDeckFromFile(deckFile, true),
                    "");
        }
        return true;
    }

    public PlayerType getPlayerType() {
        return PlayerType.getByDescription(this.cbPlayerType.getSelectedItem().toString());
    }

    public int getPlayerSkill() {
        return newPlayerPanel.getSkillLevel();
    }

    public String getPlayerDeck() {
        return newPlayerPanel.getDeckFile();
    }

    public void addPlayerTypeEventListener(Listener<Event> listener) {
        playerTypeEventSource.addListener(listener);
    }

    public void clearPlayerTypeEventListeners() {
        playerTypeEventSource.clearListeners();
    }


    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbPlayerType = new javax.swing.JLabel();
        cbPlayerType = new javax.swing.JComboBox();
        newPlayerPanel = new mage.client.table.NewPlayerPanel();
        lblPlayerNum = new javax.swing.JLabel();

        lbPlayerType.setText("Type:");

        cbPlayerType.addActionListener(evt -> cbPlayerTypeActionPerformed(evt));

        lblPlayerNum.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblPlayerNum.setText("Player #");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblPlayerNum)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbPlayerType)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbPlayerType, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newPlayerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblPlayerNum)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(cbPlayerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lbPlayerType)))
                                        .addComponent(newPlayerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbPlayerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPlayerTypeActionPerformed
        if (getPlayerType() != PlayerType.HUMAN) {
            this.newPlayerPanel.setVisible(true);
            this.newPlayerPanel.setPlayerName(extractAiPlayerNumberFromLabel(this.lblPlayerNum.getText()));
        } else {
            this.newPlayerPanel.setVisible(false);
        }
        this.revalidate();
        this.repaint();
        this.playerTypeEventSource.playerTypeChanged();
    }//GEN-LAST:event_cbPlayerTypeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbPlayerType;
    private javax.swing.JLabel lbPlayerType;
    private javax.swing.JLabel lblPlayerNum;
    private mage.client.table.NewPlayerPanel newPlayerPanel;
    // End of variables declaration//GEN-END:variables

}
