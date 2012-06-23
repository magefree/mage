/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

/*
 * TablesPanel.java
 *
 * Created on 15-Dec-2009, 10:54:01 PM
 */

package mage.client.table;

import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.client.MageFrame;
import mage.client.chat.ChatPanel;
import mage.client.components.MageComponents;
import mage.client.dialog.JoinTableDialog;
import mage.client.dialog.NewTableDialog;
import mage.client.dialog.NewTournamentDialog;
import mage.client.dialog.TableWaitingDialog;
import mage.client.util.ButtonColumn;
import mage.client.util.gui.GuiDisplayUtil;
import mage.game.match.MatchOptions;
import mage.remote.MageRemoteException;
import mage.remote.Session;
import mage.view.MatchView;
import mage.view.TableView;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TablesPanel extends javax.swing.JPanel {

    private final static Logger logger = Logger.getLogger(TablesPanel.class);

    private TableTableModel tableModel;
    private MatchesTableModel matchesModel;
    private UUID roomId;
    private UpdateTablesTask updateTablesTask;
    private UpdatePlayersTask updatePlayersTask;
    private UpdateMatchesTask updateMatchesTask;
    private JoinTableDialog joinTableDialog;
    private NewTableDialog newTableDialog;
    private NewTournamentDialog newTournamentDialog;
    private GameChooser gameChooser;
    private Session session;
    private List<String> messages;
    private int currentMessage;

    /** Creates new form TablesPanel */
    public TablesPanel() {

        tableModel = new TableTableModel();
        matchesModel = new MatchesTableModel();
        gameChooser = new GameChooser();

        initComponents();

        // disable replays
        chkShowCompleted.setVisible(false);

        tableTables.createDefaultColumnsFromModel();
        chatPanel.useExtendedView(ChatPanel.VIEW_MODE.NONE);
        chatPanel.setBorder(null);

        JComponent[] components = new JComponent[] {chatPanel, jSplitPane1, jScrollPane1, jScrollPane2, jPanel1, jPanel3};
        for (JComponent component : components) {
            component.setOpaque(false);
        }

        jScrollPane1.getViewport().setBackground(new Color(255,255,255,50));
        jScrollPane2.getViewport().setBackground(new Color(255,255,255,50));

        Action joinTable = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.valueOf( e.getActionCommand() );
                UUID tableId = (UUID)tableModel.getValueAt(modelRow, 9);
                UUID gameId = (UUID)tableModel.getValueAt(modelRow, 8);
                String state = (String)tableModel.getValueAt(modelRow, 6);
                boolean isTournament = (Boolean)tableModel.getValueAt(modelRow, 7);
                String owner = (String)tableModel.getValueAt(modelRow, 1);

                if (state.equals("Join")) {
                    if (owner.equals(session.getUserName())) {
                        try {
                            JDesktopPane desktopPane = (JDesktopPane)MageFrame.getUI().getComponent(MageComponents.DESKTOP_PANE);
                            JInternalFrame[] windows = desktopPane.getAllFramesInLayer(javax.swing.JLayeredPane.DEFAULT_LAYER);
                            for (JInternalFrame frame : windows) {
                                if (frame.getTitle().equals("Waiting for players")) {
                                    frame.toFront();
                                    frame.setVisible(true);
                                    try {
                                        frame.setSelected(true);
                                    } catch (PropertyVetoException ve) {
                                        ve.printStackTrace();
                                        logger.error(ve);
                                    }
                                }

                            }
                        } catch (Exception ex) {
                            logger.error(ex);
                        }
                        return;
                    }
                    if (isTournament) {
                        logger.info("Joining tournament " + tableId);
                        session.joinTournamentTable(roomId, tableId, session.getUserName(), "Human", 1);
                    }
                    else {
                        logger.info("Joining table " + tableId);
                        joinTableDialog.showDialog(roomId, tableId);
                    }
                } else if (state.equals("Watch")) {
                    logger.info("Watching table " + tableId);
                    session.watchTable(roomId, tableId);
                } else if (state.equals("Replay")) {
                    logger.info("Replaying game " + gameId);
                    session.replayGame(gameId);
                }
            }
        };

        Action replayMatch = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.valueOf( e.getActionCommand() );
                List<UUID> games = (List<UUID>)matchesModel.getValueAt(modelRow, 6);
                if (games.size() == 1) {
                    session.replayGame(games.get(0));
                }
                else {
                    gameChooser.show(games, MageFrame.getDesktop().getMousePosition());
                }
            }
        };


        // adds buttons (don't delete this)
        new ButtonColumn(tableTables, joinTable, 6);
        new ButtonColumn(tableCompleted, replayMatch, 5);
    }

    public Map<String, JComponent> getUIComponents() {
        Map<String, JComponent> components = new HashMap<String, JComponent>();

        components.put("jScrollPane1", jScrollPane1);
        components.put("jScrollPane1ViewPort", jScrollPane1.getViewport());
        components.put("jPanel1", jPanel1);
        components.put("tablesPanel", this);

        return components;
    }

    public void updateTables(Collection<TableView> tables) {
        try {
            tableModel.loadData(tables);
            this.tableTables.repaint();
        } catch (Exception ex) {
            hideTables();
        }
    }

    public void updateMatches(Collection<MatchView> matches) {
        try {
            matchesModel.loadData(matches);
            this.tableCompleted.repaint();
        } catch (Exception ex) {
            hideTables();
        }
    }

    public void startTasks() {
        if (session != null) {
            if (updateTablesTask == null || updateTablesTask.isDone()) {
                updateTablesTask = new UpdateTablesTask(session, roomId, this);
                updateTablesTask.execute();
            }
            if (updatePlayersTask == null || updatePlayersTask.isDone()) {
                updatePlayersTask = new UpdatePlayersTask(session, roomId, this.chatPanel);
                updatePlayersTask.execute();
            }
            if (this.chkShowCompleted.isSelected()) {
                if (updateMatchesTask == null || updateMatchesTask.isDone()) {
                    updateMatchesTask = new UpdateMatchesTask(session, roomId, this);
                    updateMatchesTask.execute();
                }
            }
            else {
                   if (updateMatchesTask != null)
                    updateMatchesTask.cancel(true);
            }
        }
    }

    public void stopTasks() {
        if (updateTablesTask != null)
            updateTablesTask.cancel(true);
        if (updatePlayersTask != null)
            updatePlayersTask.cancel(true);
        if (updateMatchesTask != null)
            updateMatchesTask.cancel(true);
    }

    public void showTables(UUID roomId) {

        this.roomId = roomId;
        session = MageFrame.getSession();
        if (session != null) {
            btnQuickStart.setVisible(session.isTestMode());
            gameChooser.init(session);
        }
        if (newTableDialog == null) {
            newTableDialog = new NewTableDialog();
            MageFrame.getDesktop().add(newTableDialog, JLayeredPane.MODAL_LAYER);
        }
        if (newTournamentDialog == null) {
            newTournamentDialog = new NewTournamentDialog();
            MageFrame.getDesktop().add(newTournamentDialog, JLayeredPane.MODAL_LAYER);
        }
        if (joinTableDialog == null) {
            joinTableDialog = new JoinTableDialog();
            MageFrame.getDesktop().add(joinTableDialog, JLayeredPane.MODAL_LAYER);
        }
        UUID chatRoomId = session.getRoomChatId(roomId);
        if (chatRoomId != null) {
            this.chatPanel.connect(chatRoomId);
            startTasks();
            this.setVisible(true);
            this.repaint();
        }
        else {
            hideTables();
        }

        reloadMessages();

        MageFrame.getUI().addButton(MageComponents.NEW_GAME_BUTTON, btnNewTable);
    }

    protected void reloadMessages() {
        // reload server messages
        List<String> serverMessages = session.getServerMessages();
        synchronized (this) {
            this.messages = serverMessages;
            this.currentMessage = 0;
        }
        if (serverMessages == null || serverMessages.isEmpty()) {
            this.jPanel2.setVisible(false);
        } else {
            this.jPanel2.setVisible(true);
            this.jLabel2.setText(serverMessages.get(0));
            this.jButton1.setVisible(serverMessages.size() > 1);
        }
    }

    public void hideTables() {
        for (Component component : MageFrame.getDesktop().getComponents()) {
            if (component instanceof TableWaitingDialog) {
                ((TableWaitingDialog)component).closeDialog();
            }
        }
        stopTasks();
        this.chatPanel.disconnect();

        Component c = this.getParent();
        while (c != null && !(c instanceof TablesPane)) {
            c = c.getParent();
        }
        if (c != null)
            ((TablesPane)c).hideFrame();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNewTable = new javax.swing.JButton();
        btnQuickStart = new javax.swing.JButton();
        btnNewTournament = new javax.swing.JButton();
        chkShowCompleted = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        chatPanel = new mage.client.chat.ChatPanel(true);
        jPanel3 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTables = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCompleted = new javax.swing.JTable();

        btnNewTable.setText("New Match");
        btnNewTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTableActionPerformed(evt);
            }
        });

        btnQuickStart.setText("Quick Start");
        btnQuickStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuickStartActionPerformed(evt);
            }
        });

        btnNewTournament.setText("New Tournament");
        btnNewTournament.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTournamentActionPerformed(evt);
            }
        });

        chkShowCompleted.setSelected(true);
        chkShowCompleted.setText("Show Completed");
        chkShowCompleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowCompletedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNewTable)
                .addGap(6, 6, 6)
                .addComponent(btnNewTournament)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuickStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkShowCompleted)
                .addContainerGap(434, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTable)
                    .addComponent(btnQuickStart)
                    .addComponent(btnNewTournament)
                    .addComponent(chkShowCompleted))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setPreferredSize(new java.awt.Dimension(664, 39));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Message of the Day:");
        jLabel1.setAlignmentY(0.3F);

        jLabel2.setText("You are playing Mage version 0.7.5. Welcome! -- Mage dev team --");

        jButton1.setText("Next");
        jButton1.setMaximumSize(new java.awt.Dimension(55, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(55, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(55, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, 449, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(440, 440, 440))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setDividerSize(3);
        jSplitPane1.setResizeWeight(1.0);

        chatPanel.setMinimumSize(new java.awt.Dimension(100, 43));
        jSplitPane1.setRightComponent(chatPanel);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);

        tableTables.setModel(this.tableModel);
        jScrollPane1.setViewportView(tableTables);

        jSplitPane2.setLeftComponent(jScrollPane1);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(23, 0));

        tableCompleted.setModel(this.matchesModel);
        jScrollPane2.setViewportView(tableCompleted);

        jSplitPane2.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                    .addContainerGap(69, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 576, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

        private void btnNewTournamentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTournamentActionPerformed
            newTournamentDialog.showDialog(roomId);
}//GEN-LAST:event_btnNewTournamentActionPerformed

        private void btnQuickStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuickStartActionPerformed
            TableView table;
            try {
                MatchOptions options = new MatchOptions("1", "Two Player Duel");
                options.getPlayerTypes().add("Human");
                options.getPlayerTypes().add("Computer - minimax");
                options.setDeckType("Limited");
                options.setAttackOption(MultiplayerAttackOption.LEFT);
                options.setRange(RangeOfInfluence.ALL);
                options.setWinsNeeded(1);
                table = session.createTable(roomId,    options);
                session.joinTable(roomId, table.getTableId(), "Human", "Human", 1, DeckImporterUtil.importDeck("test.dck"));
                session.joinTable(roomId, table.getTableId(), "Computer", "Computer - minimax", 5, DeckImporterUtil.importDeck("test.dck"));
                session.startGame(roomId, table.getTableId());
            } catch (Exception ex) {
                handleError(ex);
            }
}//GEN-LAST:event_btnQuickStartActionPerformed

    private void btnNewTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTableActionPerformed
        newTableDialog.showDialog(roomId);
    }//GEN-LAST:event_btnNewTableActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        synchronized (this) {
            if (messages != null && !messages.isEmpty()) {
                currentMessage++;
                if (currentMessage >= messages.size()) {
                    currentMessage = 0;
                }
                this.jLabel2.setText(messages.get(currentMessage));
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

private void chkShowCompletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowCompletedActionPerformed
    if (this.chkShowCompleted.isSelected()) {
        this.jSplitPane2.setDividerLocation(-1);
    }
    else {
        this.jSplitPane2.setDividerLocation(this.jPanel3.getHeight());
    }
    this.startTasks();
}//GEN-LAST:event_chkShowCompletedActionPerformed

    private void handleError(Exception ex) {
        logger.fatal("Error loading deck: ", ex);
        JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Error loading deck.", "Error", JOptionPane.ERROR_MESSAGE);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewTable;
    private javax.swing.JButton btnNewTournament;
    private javax.swing.JButton btnQuickStart;
    private mage.client.chat.ChatPanel chatPanel;
    private javax.swing.JCheckBox chkShowCompleted;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable tableCompleted;
    private javax.swing.JTable tableTables;
    // End of variables declaration//GEN-END:variables

}

class TableTableModel extends AbstractTableModel {
    private String[] columnNames = new String[]{"Match Name", "Owner", "Game Type", "Deck Type", "Status", "Created", "Action"};
    private TableView[] tables = new TableView[0];
    private static final DateFormat timeFormatter = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    public void loadData(Collection<TableView> tables) throws MageRemoteException {
        this.tables = tables.toArray(new TableView[0]);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return tables.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return tables[arg0].getTableName();
            case 1:
                return tables[arg0].getControllerName();
            case 2:
                return tables[arg0].getGameType().toString();
            case 3:
                return tables[arg0].getDeckType().toString();
            case 4:
                return tables[arg0].getTableState().toString();
            case 5:
                return timeFormatter.format(tables[arg0].getCreateTime());
            case 6:
                switch (tables[arg0].getTableState()) {
                    case WAITING:
                        return "Join";
                    case DUELING:
                        return "Watch";
                    case FINISHED:
                        return "Replay";
                    default:
                        return "";
                }
            case 7:
                return tables[arg0].isTournament();
            case 8:
                if (!tables[arg0].getGames().isEmpty())
                    return tables[arg0].getGames().get(0);
                return null;
            case 9:
                return tables[arg0].getTableId();
        }
        return "";
    }

    @Override
    public String getColumnName(int columnIndex) {
        String colName = "";

        if (columnIndex <= getColumnCount())
            colName = columnNames[columnIndex];

        return colName;
    }

    @Override
    public Class getColumnClass(int columnIndex){
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex != 6)
            return false;
        return true;
    }

}

class UpdateTablesTask extends SwingWorker<Void, Collection<TableView>> {

    private Session session;
    private UUID roomId;
    private TablesPanel panel;

    private final static Logger logger = Logger.getLogger(UpdateTablesTask.class);

    private int count = 0;

    UpdateTablesTask(Session session, UUID roomId, TablesPanel panel) {
        this.session = session;
        this.roomId = roomId;
        this.panel = panel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            Collection<TableView> tables = session.getTables(roomId);
            if (tables != null) this.publish(tables);
            Thread.sleep(1000);
        }
        return null;
    }

    @Override
    protected void process(List<Collection<TableView>> view) {
        panel.updateTables(view.get(0));
        count++;
        if (count > 60) {
            count = 0;
            panel.reloadMessages();
        }
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException ex) {
            logger.fatal("Update Tables Task error", ex);
        } catch (ExecutionException ex) {
            logger.fatal("Update Tables Task error", ex);
        } catch (CancellationException ex) {}
    }

}

class UpdatePlayersTask extends SwingWorker<Void, Collection<String>> {

    private Session session;
    private UUID roomId;
    private ChatPanel chat;

    private final static Logger logger = Logger.getLogger(UpdatePlayersTask.class);

    UpdatePlayersTask(Session session, UUID roomId, ChatPanel chat) {
        this.session = session;
        this.roomId = roomId;
        this.chat = chat;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            this.publish(session.getConnectedPlayers(roomId));
            Thread.sleep(1000);
        }
        return null;
    }

    @Override
    protected void process(List<Collection<String>> players) {
        chat.setPlayers(players.get(0));
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException ex) {
            logger.fatal("Update Players Task error", ex);
        } catch (ExecutionException ex) {
            logger.fatal("Update Players Task error", ex);
        } catch (CancellationException ex) {}
    }

}

class MatchesTableModel extends AbstractTableModel {
    private String[] columnNames = new String[]{"Match Name", "Game Type", "Deck Type", "Players", "Result", "Action"};
    private MatchView[] matches = new MatchView[0];
    private static final DateFormat timeFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);

    public void loadData(Collection<MatchView> matches) throws MageRemoteException {
        this.matches = matches.toArray(new MatchView[0]);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return matches.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return matches[arg0].getName();
            case 1:
                return matches[arg0].getGameType();
            case 2:
                return matches[arg0].getDeckType();
            case 3:
                return matches[arg0].getPlayers();
            case 4:
                return matches[arg0].getResult();
            case 5:
                return "Replay";
            case 6:
                return matches[arg0].getGames();
        }
        return "";
    }

    @Override
    public String getColumnName(int columnIndex) {
        String colName = "";

        if (columnIndex <= getColumnCount())
            colName = columnNames[columnIndex];

        return colName;
    }

    @Override
    public Class getColumnClass(int columnIndex){
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex != 5)
            return false;
        return true;
    }

}

class UpdateMatchesTask extends SwingWorker<Void, Collection<MatchView>> {

    private Session session;
    private UUID roomId;
    private TablesPanel panel;

    private final static Logger logger = Logger.getLogger(UpdateTablesTask.class);

    UpdateMatchesTask(Session session, UUID roomId, TablesPanel panel) {
        this.session = session;
        this.roomId = roomId;
        this.panel = panel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            Collection<MatchView> matches = session.getFinishedMatches(roomId);
            if (matches != null) this.publish(matches);
            Thread.sleep(5000);
        }
        return null;
    }

    @Override
    protected void process(List<Collection<MatchView>> view) {
        panel.updateMatches(view.get(0));
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException ex) {
            logger.fatal("Update Matches Task error", ex);
        } catch (ExecutionException ex) {
            logger.fatal("Update Matches Task error", ex);
        } catch (CancellationException ex) {}
    }

}

class GameChooser extends JPopupMenu {

    private Session session;

    public void init(Session session) {
        this.session = session;
    }

    public void show(List<UUID> games, Point p) {
        if (p == null) return;
        this.removeAll();
        for (UUID gameId: games) {
            this.add(new GameChooserAction(gameId, gameId.toString()));
        }
        this.show(MageFrame.getDesktop(), p.x, p.y);
        GuiDisplayUtil.keepComponentInsideScreen(p.x, p.y, this);
    }

    private class GameChooserAction extends AbstractAction {

        private UUID id;

        public GameChooserAction(UUID id, String choice) {
            this.id = id;
            putValue(Action.NAME, choice);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            session.replayGame(id);
            setVisible(false);
        }

    }

}
