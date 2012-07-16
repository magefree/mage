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
 * TableWaitingDialog.java
 *
 * Created on Dec 16, 2009, 10:27:44 AM
 */

package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.chat.ChatPanel;
import mage.client.components.MageComponents;
import mage.client.components.tray.MageTray;
import mage.remote.Session;
import mage.view.SeatView;
import mage.view.TableView;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableWaitingDialog extends MageDialog {

    private final static Logger logger = Logger.getLogger(TableWaitingDialog.class);

    private UUID tableId;
    private UUID roomId;
    private boolean isTournament;
    private Session session;
    private TableWaitModel tableWaitModel;
    private UpdateSeatsTask updateTask;

    /** Creates new form TableWaitingDialog */
    public TableWaitingDialog() {

        session = MageFrame.getSession();
        tableWaitModel = new TableWaitModel();

        initComponents();

        chatPanel.useExtendedView(ChatPanel.VIEW_MODE.NONE);
        tableSeats.createDefaultColumnsFromModel();
        MageFrame.getUI().addButton(MageComponents.TABLE_WAITING_START_BUTTON, btnStart);
    }

    public void update(TableView table) {
        try {
            if (table != null) {
                switch (table.getTableState()) {
                    case STARTING:
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
                int row = this.tableSeats.getSelectedRow();
                tableWaitModel.loadData(table);
                this.tableSeats.repaint();
                this.tableSeats.getSelectionModel().setSelectionInterval(row, row);
            }
            else {
                closeDialog();
            }
        } catch (Exception ex) {
            closeDialog();
        }
    }

    public void showDialog(UUID roomId, UUID tableId, boolean isTournament) {
        this.roomId = roomId;
        this.tableId = tableId;
        this.isTournament = isTournament;
        session = MageFrame.getSession();
        updateTask = new UpdateSeatsTask(session, roomId, tableId, this);
        if (session.isTableOwner(roomId, tableId)) {
            this.btnStart.setVisible(true);
            this.btnMoveDown.setVisible(true);
            this.btnMoveUp.setVisible(true);
        } else {
            this.btnStart.setVisible(false);
            this.btnMoveDown.setVisible(false);
            this.btnMoveUp.setVisible(false);
        }
        UUID chatId = session.getTableChatId(tableId);
        if (chatId != null) {
            this.chatPanel.connect(chatId);
            updateTask.execute();
            this.setModal(false);
            this.setLocation(100, 100);
            this.setVisible(true);
        }
        else {
            closeDialog();
        }
    }

    public void closeDialog() {
        if (updateTask != null)    updateTask.cancel(true);
        this.chatPanel.disconnect();
        this.hideDialog();
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        tableSeats = new javax.swing.JTable();
        chatPanel = new mage.client.chat.ChatPanel(false);

        setClosable(false);
        setResizable(true);
        setTitle("Waiting for players");

        btnMoveUp.setText("Move Up");
        btnMoveUp.setEnabled(false);
        btnMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveUpActionPerformed(evt);
            }
        });

        btnMoveDown.setText("Move Down");
        btnMoveDown.setEnabled(false);
        btnMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveDownActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.setEnabled(false);
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        jSplitPane1.setDividerLocation(450);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setResizeWeight(1.0);

        tableSeats.setModel(tableWaitModel);
        tableSeats.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tableSeats);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 232, Short.MAX_VALUE)
                .addComponent(btnStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap())
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
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
        closeDialog();
        if (!isTournament)
            session.startGame(roomId, tableId);
        else
            session.startTournament(roomId, tableId);
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try {
            if (session.isTableOwner(roomId, tableId)) {
                session.removeTable(roomId, tableId);
            } else {
                session.leaveTable(roomId, tableId);
            }
        } catch (Exception e) {
            //swallow exception
            logger.error(e);
        }
        closeDialog();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnMoveDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveDownActionPerformed
        int row = this.tableSeats.getSelectedRow();
        if (row < this.tableSeats.getRowCount() - 1) {
            session.swapSeats(roomId, tableId, row, row + 1);
            this.tableSeats.getSelectionModel().setSelectionInterval(row + 1, row + 1);
        }

    }//GEN-LAST:event_btnMoveDownActionPerformed

    private void btnMoveUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveUpActionPerformed
        int row = this.tableSeats.getSelectedRow();
        if (row > 0) {
            session.swapSeats(roomId, tableId, row, row - 1);
            this.tableSeats.getSelectionModel().setSelectionInterval(row - 1, row - 1);
        }
    }//GEN-LAST:event_btnMoveUpActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMoveDown;
    private javax.swing.JButton btnMoveUp;
    private javax.swing.JButton btnStart;
    private mage.client.chat.ChatPanel chatPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tableSeats;
    // End of variables declaration//GEN-END:variables

}

class TableWaitModel extends AbstractTableModel {
    private String[] columnNames = new String[]{"Seat Num", "Player Name", "Player Type"};
    private SeatView[] seats = new SeatView[0];

    public void loadData(TableView table) {
        seats = table.getSeats().toArray(new SeatView[0]);
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
        }
        else {
            switch (arg1) {
                case 0:
                    return Integer.toString(arg0 + 1);
                case 1:
                    return seats[arg0].getPlayerName();
                case 2:
                    return seats[arg0].getPlayerType();
            }
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
        return false;
    }

}

class UpdateSeatsTask extends SwingWorker<Void, TableView> {

    private Session session;
    private UUID roomId;
    private UUID tableId;
    private TableWaitingDialog dialog;
    private int count = 0;

    private final static Logger logger = Logger.getLogger(TableWaitingDialog.class);

    UpdateSeatsTask(Session session, UUID roomId, UUID tableId, TableWaitingDialog dialog) {
        this.session = session;
        this.roomId = roomId;
        this.tableId = tableId;
        this.dialog = dialog;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            this.publish(session.getTable(roomId, tableId));
            Thread.sleep(1000);
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
                count = current;
                if (count > 0) {
                MageTray.getInstance().blink();
                }
            }
        }
        dialog.update(tableView);
    }

    private int getPlayersCount(TableView tableView) {
        int count = 0;
        if (tableView != null) {
            for (SeatView seatView: tableView.getSeats()) {
                if (seatView.getPlayerId() != null && seatView.getPlayerType().equals("Human")) {
                    count++;
                }
            }
        }
        return count;
    }


    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException ex) {
            logger.fatal("Update Seats Task error", ex);
        } catch (ExecutionException ex) {
            logger.fatal("Update Seats Task error", ex);
        } catch (CancellationException ex) {}
    }

}