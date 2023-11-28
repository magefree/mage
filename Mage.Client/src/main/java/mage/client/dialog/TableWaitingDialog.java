package mage.client.dialog;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.swing.Icon;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.chat.ChatPanelBasic;
import mage.client.components.MageComponents;
import mage.client.components.tray.MageTray;
import mage.client.util.GUISizeHelper;
import mage.client.util.audio.AudioManager;
import mage.client.util.gui.TableUtil;
import mage.client.util.gui.countryBox.CountryCellRenderer;
import mage.client.util.gui.GuiDisplayUtil;
import mage.players.PlayerType;
import mage.remote.Session;
import mage.view.SeatView;
import mage.view.TableView;

import static mage.client.dialog.PreferencesDialog.KEY_TABLE_WAITING_COLUMNS_ORDER;
import static mage.client.dialog.PreferencesDialog.KEY_TABLE_WAITING_COLUMNS_WIDTH;
import static mage.client.dialog.PreferencesDialog.KEY_TABLES_DIVIDER_LOCATION_4;

/**
 * App GUI: waiting other players before join to a table
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableWaitingDialog extends MageDialog {

    private static final Logger LOGGER = Logger.getLogger(TableWaitingDialog.class);
    private static final int[] DEFAULT_COLUMNS_WIDTH = {20, 50, 100, 100, 100, 100};

    private UUID tableId;
    private UUID roomId;
    private boolean isTournament;
    private final TableWaitModel tableWaitModel;
    private UpdateSeatsTask updateTask;

    /**
     * Creates new form TableWaitingDialog
     */
    public TableWaitingDialog() {

        tableWaitModel = new TableWaitModel();

        initComponents();

        int prefWidth = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_TABLE_WAITING_WIDTH, "500"));
        int prefHeight = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_TABLE_WAITING_HEIGHT, "400"));
        if (prefWidth > 40 && prefHeight > 40) {
            this.setSize(prefWidth, prefHeight);
        }

        setGUISize();
        jTableSeats.createDefaultColumnsFromModel();
        jTableSeats.setDefaultRenderer(Icon.class, new CountryCellRenderer());
        TableUtil.setColumnWidthAndOrder(jTableSeats, DEFAULT_COLUMNS_WIDTH, KEY_TABLE_WAITING_COLUMNS_WIDTH, KEY_TABLE_WAITING_COLUMNS_ORDER);
        chatPanel.useExtendedView(ChatPanelBasic.VIEW_MODE.NONE);
        MageFrame.getUI().addButton(MageComponents.TABLE_WAITING_START_BUTTON, btnStart);
    }

    @Override
    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {
        jTableSeats.getTableHeader().setFont(GUISizeHelper.tableFont);
        jTableSeats.setFont(GUISizeHelper.tableFont);
        jTableSeats.setRowHeight(GUISizeHelper.getTableRowHeight());

        jSplitPane1.setDividerSize(GUISizeHelper.dividerBarSize);
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
    }

    public void update(TableView table) {
        try {
            if (table != null) {
                switch (table.getTableState()) {
                    case READY_TO_START:
                        this.btnStart.setEnabled(true);
                        this.btnMoveDown.setEnabled(true);
                        this.btnMoveUp.setEnabled(true);
                        break;
                    case WAITING:
                        this.btnStart.setEnabled(false);
                        this.btnMoveDown.setEnabled(false);
                        this.btnMoveUp.setEnabled(false);
                        break;
                    default:
                        closeDialog();
                        return;
                }
                int row = this.jTableSeats.getSelectedRow();
                if (getTitle().equals("Waiting for players")) {
                    this.title = getTitle() + " - " + table.getDeckType() + " / " + table.getGameType();
                    this.repaint();
                }
                tableWaitModel.loadData(table);
                this.jTableSeats.repaint();
                this.jTableSeats.getSelectionModel().setSelectionInterval(row, row);
            } else {
                closeDialog();
            }
        } catch (Exception ex) {
            closeDialog();
        }
    }

    public void showDialog(UUID roomId, UUID tableId, boolean isTournament) {
        Rectangle currentBounds = MageFrame.getDesktop().getBounds();
        Optional<UUID> chatId = SessionHandler.getTableChatId(tableId);
        String tournamentChatDivider = PreferencesDialog.getCachedValue(KEY_TABLES_DIVIDER_LOCATION_4, null);
        updateTask = new UpdateSeatsTask(SessionHandler.getSession(), roomId, tableId, this);

        this.roomId = roomId;
        this.tableId = tableId;
        this.isTournament = isTournament;

        if (SessionHandler.isTableOwner(roomId, tableId)) {
            this.btnStart.setVisible(true);
            this.btnMoveDown.setVisible(true);
            this.btnMoveUp.setVisible(true);
        } else {
            this.btnStart.setVisible(false);
            this.btnMoveDown.setVisible(false);
            this.btnMoveUp.setVisible(false);
        }

        if (chatId.isPresent()) {
            this.chatPanel.connect(chatId.get());
            updateTask.execute();
            this.setModal(false);
            this.setLocation(100, 100);
            this.setVisible(true);

            GuiDisplayUtil.restoreDividerLocations(currentBounds, tournamentChatDivider, jSplitPane1);
        } else {
            closeDialog();
        }
    }

    public void closeDialog() {
        if (updateTask != null) {
            updateTask.cancel(true);
        }

        this.chatPanel.cleanUp();
        MageFrame.getUI().removeButton(MageComponents.TABLE_WAITING_START_BUTTON);
        this.removeDialog();
        TableUtil.saveColumnWidthAndOrderToPrefs(jTableSeats, KEY_TABLE_WAITING_COLUMNS_WIDTH, KEY_TABLE_WAITING_COLUMNS_ORDER);
        GuiDisplayUtil.saveCurrentBoundsToPrefs();
        GuiDisplayUtil.saveDividerLocationToPrefs(KEY_TABLES_DIVIDER_LOCATION_4, this.jSplitPane1.getDividerLocation());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMoveUp = new javax.swing.JButton();
        btnMoveDown = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSeats = new javax.swing.JTable();
        chatPanel = new mage.client.chat.ChatPanelBasic();

        setResizable(true);
        setTitle("Waiting for players");

        btnMoveUp.setText("Move Up");
        btnMoveUp.setEnabled(false);
        btnMoveUp.addActionListener(evt -> btnMoveUpActionPerformed(evt));

        btnMoveDown.setText("Move Down");
        btnMoveDown.setEnabled(false);
        btnMoveDown.addActionListener(evt -> btnMoveDownActionPerformed(evt));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(evt -> btnCancelActionPerformed(evt));

        btnStart.setText("Start");
        btnStart.setEnabled(false);
        btnStart.addActionListener(evt -> btnStartActionPerformed(evt));

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setToolTipText("");

        jTableSeats.setModel(tableWaitModel);
        jTableSeats.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableSeats);

        jSplitPane1.setLeftComponent(jScrollPane1);
        jSplitPane1.setRightComponent(chatPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnMoveDown)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMoveUp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnStart)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel)
                                .addContainerGap())
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnMoveDown)
                                        .addComponent(btnMoveUp)
                                        .addComponent(btnCancel)
                                        .addComponent(btnStart))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        if (!isTournament) {
            if (SessionHandler.startMatch(roomId, tableId)) {
                closeDialog();
            }
        } else if (SessionHandler.startTournament(roomId, tableId)) {
            closeDialog();
        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try {
            if (!SessionHandler.leaveTable(roomId, tableId)) {
                return; // already started, so leave no more possible
            }
        } catch (Exception e) {
            //swallow exception
            LOGGER.error(e);
        }
        closeDialog();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnMoveDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveDownActionPerformed
        int row = this.jTableSeats.getSelectedRow();
        if (row < this.jTableSeats.getRowCount() - 1) {
            SessionHandler.swapSeats(roomId, tableId, row, row + 1);
            this.jTableSeats.getSelectionModel().setSelectionInterval(row + 1, row + 1);
        }

    }//GEN-LAST:event_btnMoveDownActionPerformed

    private void btnMoveUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveUpActionPerformed
        int row = this.jTableSeats.getSelectedRow();
        if (row > 0) {
            SessionHandler.swapSeats(roomId, tableId, row, row - 1);
            this.jTableSeats.getSelectionModel().setSelectionInterval(row - 1, row - 1);
        }
    }//GEN-LAST:event_btnMoveUpActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMoveDown;
    private javax.swing.JButton btnMoveUp;
    private javax.swing.JButton btnStart;
    private mage.client.chat.ChatPanelBasic chatPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableSeats;
    // End of variables declaration//GEN-END:variables

}

class TableWaitModel extends AbstractTableModel {

    private final String[] columnNames = new String[]{"Seat", "Loc", "Player Name", "Rating", "Player Type", "History"};
    private SeatView[] seats = new SeatView[0];
    private boolean limited;

    public void loadData(TableView table) {
        seats = table.getSeats().toArray(new SeatView[0]);
        if (limited != table.isLimited()) {
            limited = table.isLimited();
        }
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return seats.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        if (seats[arg0].getPlayerId() == null) {
            if (arg1 == 0) {
                return Integer.toString(arg0 + 1);
            }
        } else {
            switch (arg1) {
                case 0:
                    return Integer.toString(arg0 + 1);
                case 1:
                    return seats[arg0].getFlagName();
                case 2:
                    return seats[arg0].getPlayerName();
                case 3:
                    return limited ? seats[arg0].getLimitedRating() : seats[arg0].getConstructedRating();
                case 4:
                    return seats[arg0].getPlayerType();
                case 5:
                    return seats[arg0].getHistory();
            }
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
            case 1:
                return Icon.class;
            case 3:
                return Integer.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}

class UpdateSeatsTask extends SwingWorker<Void, TableView> {

    private final Session session;
    private final UUID roomId;
    private final UUID tableId;
    private final TableWaitingDialog dialog;
    private int count = 0;

    private static final Logger logger = Logger.getLogger(TableWaitingDialog.class);

    UpdateSeatsTask(Session session, UUID roomId, UUID tableId, TableWaitingDialog dialog) {
        this.session = session;
        this.roomId = roomId;
        this.tableId = tableId;
        this.dialog = dialog;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            Optional<TableView> tableView = SessionHandler.getTable(roomId, tableId);
            if (tableView.isPresent()) {
                tableView.ifPresent(this::publish);
            } else {
                dialog.closeDialog();
            }
            TimeUnit.SECONDS.sleep(1);
        }
        return null;
    }

    @Override
    protected void process(List<TableView> view) {
        TableView tableView = view.get(0);
        if (count == 0) {
            count = getPlayersCount(tableView);
        } else {
            int current = getPlayersCount(tableView);
            if (current != count) {
                if (count > 0) {
                    if (current == tableView.getSeats().size()) {
                        MageTray.instance.displayMessage("The game can start.");
                        AudioManager.playGameCanStart();
                    } else if (current > count) {
                        MageTray.instance.displayMessage("New player joined your game.");
                        AudioManager.playPlayerJoinedTable();
                    } else {
                        MageTray.instance.displayMessage("A player left your game.");
                        AudioManager.playPlayerLeft();
                    }
                    MageTray.instance.blink();
                }
                count = current;
            }
        }
        dialog.update(tableView);
    }

    private int getPlayersCount(TableView tableView) {
        int playerCount = 0;
        if (tableView != null) {
            for (SeatView seatView : tableView.getSeats()) {
                if (seatView.getPlayerId() != null && seatView.getPlayerType() == PlayerType.HUMAN) {
                    playerCount++;
                }
            }
        }
        return playerCount;
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException | ExecutionException ex) {
            logger.fatal("Update Seats Task error", ex);
        } catch (CancellationException ex) {
        }
    }

}
