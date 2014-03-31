/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

/*
 * TournamentPanel.java
 *
 * Created on 20-Jan-2011, 9:18:30 PM
 */

package mage.client.tournament;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import mage.client.MageFrame;
import mage.client.chat.ChatPanel;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.ButtonColumn;
import mage.remote.Session;
import mage.view.RoundView;
import mage.view.TournamentGameView;
import mage.view.TournamentPlayerView;
import mage.view.TournamentView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentPanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(TournamentPanel.class);

    private UUID tournamentId;
    private boolean firstInitDone = false;
    private Session session;
    private final TournamentPlayersTableModel playersModel;
    private TournamentMatchesTableModel matchesModel;
    private UpdateTournamentTask updateTask;
    private final DateFormat df;

    /** Creates new form TournamentPanel */
    public TournamentPanel() {
        playersModel = new TournamentPlayersTableModel();
        matchesModel = new TournamentMatchesTableModel();

        initComponents();
        this.restoreDividerLocations();
        btnQuitTournament.setVisible(false);
        
        df = DateFormat.getDateTimeInstance();

        tablePlayers.createDefaultColumnsFromModel();
        tableMatches.createDefaultColumnsFromModel();

        chatPanel1.useExtendedView(ChatPanel.VIEW_MODE.NONE);
        chatPanel1.setChatType(ChatPanel.ChatType.TOURNAMENT);

        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.valueOf( e.getActionCommand() );
                
                String state = (String)tableMatches.getValueAt(modelRow, 2);
                String actionText = (String)tableMatches.getValueAt(modelRow, TournamentMatchesTableModel.ACTION_COLUMN);
                UUID tableId = UUID.fromString((String)matchesModel.getValueAt(modelRow, TournamentMatchesTableModel.ACTION_COLUMN +1));
                UUID gameId = UUID.fromString((String)matchesModel.getValueAt(modelRow, TournamentMatchesTableModel.ACTION_COLUMN +3));

                
//                if (state.equals("Finished") && action.equals("Replay")) {
//                    logger.info("Replaying game " + gameId);
//                    session.replayGame(gameId);
//                }
                if (state.equals("Dueling") && actionText.equals("Watch")) {
                    logger.info("Watching game " + gameId);
                    session.watchTournamentTable(tableId);
                }
            }
        };

        // action button, don't delete this
        ButtonColumn buttonColumn = new ButtonColumn(tableMatches, action, TournamentMatchesTableModel.ACTION_COLUMN);

    }

    public void cleanUp() {
        this.stopTasks();
        if (this.chatPanel1 != null) {
            this.chatPanel1.disconnect();
        }
    }

    private void saveDividerLocations() {
        // save panel sizes and divider locations.
        Rectangle rec = MageFrame.getDesktop().getBounds();
        StringBuilder sb = new StringBuilder(Double.toString(rec.getWidth())).append("x").append(Double.toString(rec.getHeight()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_MAGE_PANEL_LAST_SIZE, sb.toString());
        PreferencesDialog.saveValue(PreferencesDialog.KEY_TOURNAMENT_DIVIDER_LOCATION_1, Integer.toString(this.jSplitPane1.getDividerLocation()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_TOURNAMENT_DIVIDER_LOCATION_2, Integer.toString(this.jSplitPane2.getDividerLocation()));
    }

    private void restoreDividerLocations() {
        Rectangle rec = MageFrame.getDesktop().getBounds();
        if (rec != null) {
            String size = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_MAGE_PANEL_LAST_SIZE, null);
            StringBuilder sb = new StringBuilder(Double.toString(rec.getWidth())).append("x").append(Double.toString(rec.getHeight()));
            // use divider positions only if screen size is the same as it was the time the settings were saved
            if (size != null && size.equals(sb.toString())) {
                String location = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_TOURNAMENT_DIVIDER_LOCATION_1, null);
                if (location != null && jSplitPane1 != null) {
                    jSplitPane1.setDividerLocation(Integer.parseInt(location));
                }
                location = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_TOURNAMENT_DIVIDER_LOCATION_2, null);
                if (location != null && jSplitPane2 != null) {
                    jSplitPane2.setDividerLocation(Integer.parseInt(location));
                }
            }
        }
    }

    public synchronized void showTournament(UUID tournamentId) {
        this.tournamentId = tournamentId;
        session = MageFrame.getSession();
        // MageFrame.addTournament(tournamentId, this);
        UUID chatRoomId = session.getTournamentChatId(tournamentId);
        if (session.joinTournament(tournamentId) && chatRoomId != null) {
            this.chatPanel1.connect(chatRoomId);
            startTasks();
            this.setVisible(true);
            this.repaint();
        }
        else {
            hideTournament();
        }

    }

    public UUID getTournamentId() {
        return tournamentId;
    }

    public void hideTournament() {
        stopTasks();
        this.chatPanel1.disconnect();
        this.saveDividerLocations();
        Component c = this.getParent();
        while (c != null && !(c instanceof TournamentPane)) {
            c = c.getParent();
        }
        if (c != null) {
            ((TournamentPane)c).removeTournament();
        }
    }

    public void update(TournamentView tournament) {

        if (!firstInitDone) {
            Component c = this.getParent();
            while (c != null && !(c instanceof TournamentPane)) {
                c = c.getParent();
            }
            if (c != null) {
                ((TournamentPane)c).setTitle("Tournament [" + tournament.getTournamentName() +"]");
            }
            txtName.setText(tournament.getTournamentName());
            txtType.setText(tournament.getTournamentType());

            txtStartTime.setText(df.format(tournament.getStartTime()));
            txtEndTime.setText("running...");
            
            firstInitDone = true;
        }

        if (txtEndTime == null) {
            return;
        }
        if (txtEndTime.getText().equals("running...") && tournament.getEndTime() != null) {
            txtEndTime.setText(df.format(tournament.getEndTime()));
        }
        
        playersModel.loadData(tournament);
        matchesModel.loadData(tournament);
        this.tablePlayers.repaint();
        this.tableMatches.repaint();

        // player is active in tournament
        btnQuitTournament.setVisible(false);
        if (tournament.getEndTime() == null) {
            for (TournamentPlayerView player : tournament.getPlayers()) {
                if (player.getName().equals(session.getUserName())) {
                    if (!player.hasQuit()) {
                        btnQuitTournament.setVisible(true);
                    }
                    break;
                }
            }
        }

    }

    public void startTasks() {
        if (session != null) {
            if (updateTask == null || updateTask.isDone()) {
                updateTask = new UpdateTournamentTask(session, tournamentId, this);
                updateTask.execute();
            }
        }
    }

    public void stopTasks() {
        if (updateTask != null) {
            updateTask.cancel(true);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        actionPanel = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblType = new javax.swing.JLabel();
        txtType = new javax.swing.JTextField();
        lblStartTime = new javax.swing.JLabel();
        txtStartTime = new javax.swing.JTextField();
        lablEndTime = new javax.swing.JLabel();
        txtEndTime = new javax.swing.JTextField();
        btnQuitTournament = new javax.swing.JButton();
        btnCloseWindow = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePlayers = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableMatches = new javax.swing.JTable();
        chatPanel1 = new mage.client.chat.ChatPanel();

        setPreferredSize(new java.awt.Dimension(908, 580));

        lblName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblName.setText("Name:");

        txtName.setEditable(false);
        txtName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtName.setText("tournament name");
        txtName.setFocusable(false);
        txtName.setMaximumSize(new java.awt.Dimension(50, 22));

        lblType.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblType.setText("Type:");

        txtType.setEditable(false);
        txtType.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtType.setText("tournament type");
        txtType.setFocusable(false);

        lblStartTime.setText("Start time:");

        txtStartTime.setEditable(false);
        txtStartTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStartTime.setText("jTextField1");
        txtStartTime.setFocusable(false);

        lablEndTime.setText("End time:");

        txtEndTime.setEditable(false);
        txtEndTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEndTime.setText("jTextField2");
        txtEndTime.setFocusable(false);

        btnQuitTournament.setText("Quit Tournament");
        btnQuitTournament.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuitTournament.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitTournamentActionPerformed(evt);
            }
        });

        btnCloseWindow.setText("Close Window");
        btnCloseWindow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCloseWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseWindowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout actionPanelLayout = new javax.swing.GroupLayout(actionPanel);
        actionPanel.setLayout(actionPanelLayout);
        actionPanelLayout.setHorizontalGroup(
            actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblName)
                    .addComponent(lblType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(txtType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(actionPanelLayout.createSequentialGroup()
                        .addComponent(lablEndTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEndTime))
                    .addGroup(actionPanelLayout.createSequentialGroup()
                        .addComponent(lblStartTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCloseWindow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuitTournament, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        actionPanelLayout.setVerticalGroup(
            actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStartTime)
                    .addComponent(txtStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuitTournament))
                .addGap(9, 9, 9)
                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblType)
                    .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lablEndTime)
                    .addComponent(txtEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCloseWindow))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jSplitPane2.setResizeWeight(1.0);
        jSplitPane2.setToolTipText("");

        jSplitPane1.setDividerLocation(230);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 100));

        tablePlayers.setModel(this.playersModel);
        jScrollPane1.setViewportView(tablePlayers);

        jSplitPane1.setTopComponent(jScrollPane1);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 100));

        tableMatches.setModel(matchesModel);
        jScrollPane2.setViewportView(tableMatches);

        jSplitPane1.setBottomComponent(jScrollPane2);

        jSplitPane2.setLeftComponent(jSplitPane1);
        jSplitPane2.setRightComponent(chatPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(actionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(actionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseWindowActionPerformed
        hideTournament();
    }//GEN-LAST:event_btnCloseWindowActionPerformed

    private void btnQuitTournamentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitTournamentActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the tournament?", "Confirm quit tournament", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            MageFrame.getSession().quitTournament(tournamentId);
        }

    }//GEN-LAST:event_btnQuitTournamentActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton btnCloseWindow;
    private javax.swing.JButton btnQuitTournament;
    private mage.client.chat.ChatPanel chatPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel lablEndTime;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStartTime;
    private javax.swing.JLabel lblType;
    private javax.swing.JTable tableMatches;
    private javax.swing.JTable tablePlayers;
    private javax.swing.JTextField txtEndTime;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtStartTime;
    private javax.swing.JTextField txtType;
    // End of variables declaration//GEN-END:variables

}

class TournamentPlayersTableModel extends AbstractTableModel {
    private final String[] columnNames = new String[]{"Player Name", "State",  "Points", "Results"};
    private TournamentPlayerView[] players = new TournamentPlayerView[0];

    public void loadData(TournamentView tournament) {
        players = tournament.getPlayers().toArray(new TournamentPlayerView[0]);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return players.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return players[arg0].getName();
            case 1:
                return players[arg0].getState();
            case 2:
                return Integer.toString(players[arg0].getPoints());
            case 3:
                return players[arg0].getResults();
        }
        return "";
    }

    @Override
    public String getColumnName(int columnIndex) {
        String colName = "";

        if (columnIndex <= getColumnCount()) {
            colName = columnNames[columnIndex];
        }

        return colName;
    }

    @Override
    public Class getColumnClass(int columnIndex){
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}

class TournamentMatchesTableModel extends AbstractTableModel {

    public static final int ACTION_COLUMN = 4; // column the action is located

    private final String[] columnNames = new String[]{"Round Number", "Players", "State", "Result", "Action"};
    private TournamentGameView[] games = new TournamentGameView[0];
    private boolean watchingAllowed;

    public void loadData(TournamentView tournament) {
        List<TournamentGameView> views = new ArrayList<>();
        watchingAllowed = tournament.isWatchingAllowed();
        for (RoundView round: tournament.getRounds()) {
            for (TournamentGameView game: round.getGames()) {
                views.add(game);
            }
        }
        games = views.toArray(new TournamentGameView[0]);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return games.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return Integer.toString(games[arg0].getRoundNum());
            case 1:
                return games[arg0].getPlayers();
            case 2:
                return games[arg0].getState();
            case 3:
                return games[arg0].getResult();
            case 4:
//                if (games[arg0].getState().equals("Finished")) {
//                    return "Replay";
//                }
                if (watchingAllowed && games[arg0].getState().equals("Dueling")) {
                    return "Watch";
                }
                return "";
            case 5:
                return games[arg0].getTableId().toString();
            case 6:
                return games[arg0].getMatchId().toString();
            case 7:
                return games[arg0].getGameId().toString();

        }
        return "";
    }

    @Override
    public String getColumnName(int columnIndex) {
        String colName = "";

        if (columnIndex <= getColumnCount()) {
            colName = columnNames[columnIndex];
        }

        return colName;
    }

    @Override
    public Class getColumnClass(int columnIndex){
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == ACTION_COLUMN;
    }

}

class UpdateTournamentTask extends SwingWorker<Void, TournamentView> {

    private final Session session;
    private final UUID tournamentId;
    private final TournamentPanel panel;

    private static final Logger logger = Logger.getLogger(UpdateTournamentTask.class);

    UpdateTournamentTask(Session session, UUID tournamentId, TournamentPanel panel) {
        this.session = session;
        this.tournamentId = tournamentId;
        this.panel = panel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            this.publish(session.getTournament(tournamentId));    
            Thread.sleep(2000);
        }
        return null;
    }

    @Override
    protected void process(List<TournamentView> view) {
        if (view != null) { // if user disconnects, view can be null for a short time
            panel.update(view.get(0));
        }
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException ex) {
            logger.fatal("Update Tournament Task error", ex);
        } catch (ExecutionException ex) {
            logger.fatal("Update Tournament Task error", ex);
        } catch (CancellationException ex) {}
    }

}
