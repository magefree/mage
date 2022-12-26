package mage.client.tournament;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.chat.ChatPanelBasic;
import mage.client.dialog.PreferencesDialog;
import mage.client.table.TablesButtonColumn;
import mage.client.table.TablesUtil;
import mage.client.table.TournamentMatchesTableModel;
import mage.client.util.Format;
import mage.client.util.GUISizeHelper;
import mage.client.util.gui.TableUtil;
import mage.client.util.gui.countryBox.CountryCellRenderer;
import mage.constants.PlayerAction;
import mage.view.TournamentPlayerView;
import mage.view.TournamentView;
import mage.view.UserRequestMessage;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static mage.client.dialog.PreferencesDialog.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentPanel extends javax.swing.JPanel {

    private static final Logger LOGGER = Logger.getLogger(TournamentPanel.class);

    private static final int[] DEFAULT_COLUMNS_WIDTH_PLAYERS = {30, 150, 150, 60, 400, 100};
    private static final int[] DEFAULT_COLUMNS_WIDTH_MATCHES = {60, 140, 140, 400, 80};

    private UUID tournamentId;
    private boolean firstInitDone = false;

    private final TournamentPlayersTableModel playersModel;
    private final TournamentMatchesTableModel matchesModel;
    private UpdateTournamentTask updateTask;
    private final DateFormat df;

    private final TablesButtonColumn actionButtonColumn1;

    /**
     * Creates new form TournamentPanel
     */
    public TournamentPanel() {
        playersModel = new TournamentPlayersTableModel();
        matchesModel = new TournamentMatchesTableModel();

        initComponents();
        this.restoreDividerLocations();
        btnQuitTournament.setVisible(false);

        df = DateFormat.getDateTimeInstance();

        tablePlayers.createDefaultColumnsFromModel();
        TableUtil.setColumnWidthAndOrder(tablePlayers, DEFAULT_COLUMNS_WIDTH_PLAYERS, KEY_TOURNAMENT_PLAYER_COLUMNS_WIDTH, KEY_TOURNAMENT_PLAYER_COLUMNS_ORDER);
        tablePlayers.setDefaultRenderer(Icon.class, new CountryCellRenderer());

        tableMatches.createDefaultColumnsFromModel();
        TableUtil.setColumnWidthAndOrder(tableMatches, DEFAULT_COLUMNS_WIDTH_MATCHES, KEY_TOURNAMENT_MATCH_COLUMNS_WIDTH, KEY_TOURNAMENT_MATCH_COLUMNS_ORDER);

        chatPanel1.useExtendedView(ChatPanelBasic.VIEW_MODE.NONE);
        chatPanel1.setChatType(ChatPanelBasic.ChatType.TOURNAMENT);

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchID = e.getActionCommand();
                int modelRow = TablesUtil.findTableRowFromSearchId(matchesModel, searchID);
                if (modelRow == -1) {
                    return;
                }

                String state = (String) tableMatches.getValueAt(modelRow, tableMatches.convertColumnIndexToView(2));
                String actionText = (String) tableMatches.getValueAt(modelRow, tableMatches.convertColumnIndexToView(TournamentMatchesTableModel.ACTION_COLUMN));
                UUID tableId = UUID.fromString((String) matchesModel.getValueAt(modelRow, TournamentMatchesTableModel.ACTION_COLUMN + 1));
                UUID gameId = UUID.fromString((String) matchesModel.getValueAt(modelRow, TournamentMatchesTableModel.ACTION_COLUMN + 3));

//                if (state.equals("Finished") && action.equals("Replay")) {
//                    logger.info("Replaying game " + gameId);
//                    session.replayGame(gameId);
//                }
                if (state.startsWith("Dueling") && actionText.equals("Watch")) {
                    LOGGER.info("Watching game " + gameId);
                    SessionHandler.watchTournamentTable(tableId);
                }
            }
        };

        // action button, don't delete this
        actionButtonColumn1 = new TablesButtonColumn(tableMatches, action, tableMatches.convertColumnIndexToView(TournamentMatchesTableModel.ACTION_COLUMN));
        setGUISize();

    }

    public void cleanUp() {
        this.stopTasks();
        if (this.chatPanel1 != null) {
            this.chatPanel1.disconnect();
        }

    }

    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {
        tablePlayers.getTableHeader().setFont(GUISizeHelper.tableFont);
        tablePlayers.setFont(GUISizeHelper.tableFont);
        tablePlayers.setRowHeight(GUISizeHelper.getTableRowHeight());

        tableMatches.getTableHeader().setFont(GUISizeHelper.tableFont);
        tableMatches.setFont(GUISizeHelper.tableFont);
        tableMatches.setRowHeight(GUISizeHelper.getTableRowHeight());

        jSplitPane1.setDividerSize(GUISizeHelper.dividerBarSize);
        jSplitPane2.setDividerSize(GUISizeHelper.dividerBarSize);
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));

        actionButtonColumn1.changeGUISize();
    }

    private void saveDividerLocations() {
        // save panel sizes and divider locations.
        Rectangle rec = MageFrame.getDesktop().getBounds();
        String sb = Double.toString(rec.getWidth()) + 'x' + rec.getHeight();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_MAGE_PANEL_LAST_SIZE, sb);
        PreferencesDialog.saveValue(PreferencesDialog.KEY_TOURNAMENT_DIVIDER_LOCATION_1, Integer.toString(this.jSplitPane1.getDividerLocation()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_TOURNAMENT_DIVIDER_LOCATION_2, Integer.toString(this.jSplitPane2.getDividerLocation()));
    }

    private void restoreDividerLocations() {
        Rectangle rec = MageFrame.getDesktop().getBounds();
        if (rec != null) {
            String size = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_MAGE_PANEL_LAST_SIZE, null);
            String sb = Double.toString(rec.getWidth()) + 'x' + rec.getHeight();
            // use divider positions only if screen size is the same as it was the time the settings were saved
            if (size != null && size.equals(sb)) {
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
        // MageFrame.addTournament(tournamentId, this);
        Optional<UUID> chatRoomId = SessionHandler.getTournamentChatId(tournamentId);
        if (SessionHandler.joinTournament(tournamentId) && chatRoomId.isPresent()) {
            this.chatPanel1.connect(chatRoomId.get());
            startTasks();
            this.setVisible(true);
            this.repaint();
        } else {
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
        TableUtil.saveColumnWidthAndOrderToPrefs(tablePlayers, KEY_TOURNAMENT_PLAYER_COLUMNS_WIDTH, KEY_TOURNAMENT_PLAYER_COLUMNS_ORDER);
        TableUtil.saveColumnWidthAndOrderToPrefs(tableMatches, KEY_TOURNAMENT_MATCH_COLUMNS_WIDTH, KEY_TOURNAMENT_MATCH_COLUMNS_ORDER);

        Component c = this.getParent();
        while (c != null && !(c instanceof TournamentPane)) {
            c = c.getParent();
        }
        if (c != null) {
            ((TournamentPane) c).removeTournament();
        }
    }

    public void update(TournamentView tournament) {
        if (tournament == null) {
            return;
        }
        if (!firstInitDone) {
            Component c = this.getParent();
            while (c != null && !(c instanceof TournamentPane)) {
                c = c.getParent();
            }
            if (c != null) {
                ((TournamentPane) c).setTitle("Tournament [" + tournament.getTournamentName() + ']');
            }
            txtName.setText(tournament.getTournamentName());
            txtType.setText(tournament.getTournamentType());

            txtStartTime.setText(df.format(tournament.getStartTime()));
            txtEndTime.setText("running...");

            firstInitDone = true;
        }
        switch (tournament.getTournamentState()) {
            case "Constructing":
                String timeLeft = "";
                if (tournament.getStepStartTime() != null) {
                    timeLeft = Format.getDuration(tournament.getConstructionTime() - (tournament.getServerTime().getTime() - tournament.getStepStartTime().getTime()) / 1000);
                }
                txtTournamentState.setText(new StringBuilder(tournament.getTournamentState()).append(" (").append(timeLeft).append(')').toString());
                break;
            case "Dueling":
            case "Drafting":
                String usedTime = "";
                if (tournament.getStepStartTime() != null) {
                    usedTime = Format.getDuration((tournament.getServerTime().getTime() - tournament.getStepStartTime().getTime()) / 1000);
                }
                txtTournamentState.setText(tournament.getTournamentState() + " (" + usedTime + ") " + tournament.getRunningInfo());
                break;
            default:
                txtTournamentState.setText(tournament.getTournamentState());
                break;
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
                if (player.getName().equals(SessionHandler.getUserName())) {
                    if (!player.hasQuit()) {
                        btnQuitTournament.setVisible(true);
                    }
                    break;
                }
            }
        }

    }

    public void startTasks() {
        if (SessionHandler.getSession() != null) {
            if (updateTask == null || updateTask.isDone()) {
                updateTask = new UpdateTournamentTask(tournamentId, this);
                updateTask.execute();
            }
        }
    }

    public void stopTasks() {
        if (updateTask != null) {
            updateTask.cancel(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        actionPanel = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        txtType = new javax.swing.JTextField();
        txtStartTime = new javax.swing.JTextField();
        txtEndTime = new javax.swing.JTextField();
        txtTournamentState = new javax.swing.JTextField();
        btnQuitTournament = new javax.swing.JButton();
        btnCloseWindow = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();
        lblState = new javax.swing.JLabel();
        lblStartTime = new javax.swing.JLabel();
        lblEndTime = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePlayers = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableMatches = new javax.swing.JTable();
        chatPanel1 = new mage.client.chat.ChatPanelBasic();

        setPreferredSize(new java.awt.Dimension(908, 580));

        actionPanel.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txtName.setEditable(false);
        txtName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtName.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtName.setFocusable(false);
        txtName.setMaximumSize(new java.awt.Dimension(50, 22));
        txtName.setOpaque(false);
        txtName.setRequestFocusEnabled(false);
        txtName.addActionListener(evt -> txtNameActionPerformed(evt));

        txtType.setEditable(false);
        txtType.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtType.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtType.setFocusable(false);
        txtType.setOpaque(false);
        txtType.setRequestFocusEnabled(false);

        txtStartTime.setEditable(false);
        txtStartTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStartTime.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtStartTime.setFocusable(false);
        txtStartTime.setOpaque(false);
        txtStartTime.setRequestFocusEnabled(false);

        txtEndTime.setEditable(false);
        txtEndTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEndTime.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtEndTime.setFocusable(false);
        txtEndTime.setOpaque(false);
        txtEndTime.setRequestFocusEnabled(false);

        txtTournamentState.setEditable(false);
        txtTournamentState.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTournamentState.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTournamentState.setFocusable(false);
        txtTournamentState.setOpaque(false);
        txtTournamentState.setRequestFocusEnabled(false);

        btnQuitTournament.setText("Quit Tournament");
        btnQuitTournament.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuitTournament.addActionListener(evt -> btnQuitTournamentActionPerformed(evt));

        btnCloseWindow.setText("Close Window");
        btnCloseWindow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCloseWindow.addActionListener(evt -> btnCloseWindowActionPerformed(evt));

        lblName.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblName.setText("Name:");

        lblType.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblType.setText("Type:");

        lblState.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblState.setText("State:");

        lblStartTime.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblStartTime.setText("Start time:");

        lblEndTime.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblEndTime.setText("End time:");

        javax.swing.GroupLayout actionPanelLayout = new javax.swing.GroupLayout(actionPanel);
        actionPanel.setLayout(actionPanelLayout);
        actionPanelLayout.setHorizontalGroup(
                actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(actionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblName)
                                        .addComponent(lblState)
                                        .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                                        .addComponent(txtTournamentState))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblType)
                                        .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(actionPanelLayout.createSequentialGroup()
                                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblStartTime))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblEndTime)
                                                        .addComponent(txtEndTime))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnQuitTournament, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCloseWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        actionPanelLayout.setVerticalGroup(
                actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(actionPanelLayout.createSequentialGroup()
                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(actionPanelLayout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(lblName))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblType)))
                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(actionPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnQuitTournament))
                                                .addGap(13, 13, 13)
                                                .addComponent(btnCloseWindow))
                                        .addGroup(actionPanelLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblState)
                                                        .addComponent(lblStartTime)
                                                        .addComponent(lblEndTime))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(actionPanelLayout.createSequentialGroup()
                                                                .addComponent(txtTournamentState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(actionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(txtStartTime)
                                                                .addComponent(txtEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap())
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
                        .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(actionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseWindowActionPerformed
        hideTournament();
    }//GEN-LAST:event_btnCloseWindowActionPerformed

    private void btnQuitTournamentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitTournamentActionPerformed
        UserRequestMessage message = new UserRequestMessage("Confirm quit tournament", "Are you sure you want to quit the tournament?");
        message.setButton1("No", null);
        message.setButton2("Yes", PlayerAction.CLIENT_QUIT_TOURNAMENT);
        message.setTournamentId(tournamentId);
        MageFrame.getInstance().showUserRequestDialog(message);
    }//GEN-LAST:event_btnQuitTournamentActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton btnCloseWindow;
    private javax.swing.JButton btnQuitTournament;
    private mage.client.chat.ChatPanelBasic chatPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel lblEndTime;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStartTime;
    private javax.swing.JLabel lblState;
    private javax.swing.JLabel lblType;
    private javax.swing.JTable tableMatches;
    private javax.swing.JTable tablePlayers;
    private javax.swing.JTextField txtEndTime;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtStartTime;
    private javax.swing.JTextField txtTournamentState;
    private javax.swing.JTextField txtType;
    // End of variables declaration//GEN-END:variables

}

class TournamentPlayersTableModel extends AbstractTableModel {

    private final String[] columnNames = new String[]{"Loc", "Player Name", "State", "Points", "Results", "History"};
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
                return players[arg0].getFlagName();
            case 1:
                return players[arg0].getName();
            case 2:
                return players[arg0].getState();
            case 3:
                return Integer.toString(players[arg0].getPoints());
            case 4:
                return players[arg0].getResults();
            case 5:
                return players[arg0].getHistory();
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
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Icon.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}


class UpdateTournamentTask extends SwingWorker<Void, TournamentView> {

    private final UUID tournamentId;
    private final TournamentPanel panel;

    private static final Logger logger = Logger.getLogger(UpdateTournamentTask.class);

    UpdateTournamentTask(UUID tournamentId, TournamentPanel panel) {

        this.tournamentId = tournamentId;
        this.panel = panel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            this.publish(SessionHandler.getTournament(tournamentId));
            TimeUnit.SECONDS.sleep(2);
        }
        return null;
    }

    @Override
    protected void process(List<TournamentView> view) {
        if (view != null && !view.isEmpty()) { // if user disconnects, view can be null for a short time
            panel.update(view.get(0));
        }
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException | ExecutionException ex) {
            logger.fatal("Update Tournament Task error", ex);
        } catch (CancellationException ex) {
        }
    }

}