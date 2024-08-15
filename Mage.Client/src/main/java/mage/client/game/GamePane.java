package mage.client.game;

import java.awt.AWTEvent;
import java.util.UUID;
import javax.swing.*;

import mage.client.MagePane;

/**
 * Game GUI: game frame (game panel with scrolls)
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class GamePane extends MagePane {

    public GamePane() {
        initComponents();
        SwingUtilities.invokeLater(() -> {
            gamePanel.setJLayeredPane(this);
            gamePanel.installComponents();
        });
    }

    public void showGame(UUID currentTableId, UUID parentTableId, UUID gameId, UUID playerId) {
        this.setTitle("Game " + gameId);
        this.currentTableId = currentTableId;
        this.parentTableId = parentTableId;
        this.gameId = gameId;
        gamePanel.showGame(currentTableId, parentTableId, gameId, playerId, this);
    }

    @Override
    public boolean isActiveTable() {
        return this.gameId != null;
    }

    public void cleanUp() {
        gamePanel.cleanUp();
    }

    @Override
    public void changeGUISize() {
        super.changeGUISize();
        gamePanel.changeGUISize();
        this.revalidate();
        this.repaint();
    }

    public void removeGame() {
        this.cleanUp();
        this.removeFrame();
    }

    public void watchGame(UUID currentTableId, UUID parentTableId, UUID gameId) {
        this.setTitle("Watching " + gameId);
        this.currentTableId = currentTableId;
        this.parentTableId = parentTableId;
        this.gameId = gameId;
        gamePanel.watchGame(currentTableId, parentTableId, gameId, this);
    }

    public void replayGame(UUID gameId) {
        this.setTitle("Replaying " + gameId);
        this.currentTableId = null;
        this.parentTableId = null;
        this.gameId = gameId;
        gamePanel.replayGame(gameId);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        gamePanel = new mage.client.game.GamePanel();

        jScrollPane1.setViewportView(gamePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addGap(0, 400, Short.MAX_VALUE)
        );

    }

    public UUID getGameId() {
        return gameId;
    }

    @Override
    public void deactivated() {
        super.deactivated();
        gamePanel.onDeactivated();
    }

    @Override
    public void activated() {
        gamePanel.onActivated();
    }

    @Override
    public void handleEvent(AWTEvent event) {
        gamePanel.handleEvent(event);
    }

    @Override
    public UUID getSortTableId() {
        return parentTableId != null ? parentTableId : currentTableId;
    }

    private mage.client.game.GamePanel gamePanel;
    private javax.swing.JScrollPane jScrollPane1;
    private UUID currentTableId;
    private UUID parentTableId;
    private UUID gameId;
}
