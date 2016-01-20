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
 * ChatPanel.java
 *
 * Created on 15-Dec-2009, 11:04:31 PM
 */
package mage.client.table;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.Icon;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import mage.client.chat.ChatPanelBasic;
import static mage.client.chat.ChatPanelBasic.CHAT_ALPHA;
import static mage.client.dialog.PreferencesDialog.KEY_USERS_COLUMNS_ORDER;
import static mage.client.dialog.PreferencesDialog.KEY_USERS_COLUMNS_WIDTH;
import mage.client.util.MageTableRowSorter;
import mage.client.util.gui.TableUtil;
import mage.client.util.gui.countryBox.CountryCellRenderer;
import mage.remote.MageRemoteException;
import mage.view.RoomUsersView;
import mage.view.UsersView;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class PlayersChatPanel extends javax.swing.JPanel {

    private final List<String> players = new ArrayList<>();
    private final UserTableModel userTableModel;
    private static final int[] defaultColumnsWidth = {20, 100, 100, 100, 80, 80};


    /*
     * Creates new form ChatPanel
     *
     */
    public PlayersChatPanel() {
        userTableModel = new UserTableModel(); // needs to be set before initComponents();

        initComponents();
        setBackground(new Color(0, 0, 0, CHAT_ALPHA));

        jTablePlayers.setBackground(new Color(0, 0, 0, CHAT_ALPHA));
        jTablePlayers.setForeground(Color.white);
        jTablePlayers.setRowSorter(new MageTableRowSorter(userTableModel));

        TableUtil.setColumnWidthAndOrder(jTablePlayers, defaultColumnsWidth, KEY_USERS_COLUMNS_WIDTH, KEY_USERS_COLUMNS_ORDER);
        jTablePlayers.setDefaultRenderer(Icon.class, new CountryCellRenderer());

        jScrollPaneTalk.setSystemMessagesPane(colorPaneSystem);
        jScrollPaneTalk.setOpaque(false);

        jScrollPaneSystem.getViewport().setOpaque(false);
        colorPaneSystem.setExtBackgroundColor(new Color(0, 0, 0, CHAT_ALPHA)); // Alpha = 255 not transparent
        colorPaneSystem.setBorder(new EmptyBorder(5, 5, 5, 5));
        if (jScrollPanePlayers != null) {
            jScrollPanePlayers.setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPanePlayers.getViewport().setBackground(new Color(0, 0, 0, CHAT_ALPHA));
        }

    }

    public ChatPanelBasic getUserChatPanel() {
        return jScrollPaneTalk;
    }

    public void cleanUp() {
        TableUtil.saveColumnWidthAndOrderToPrefs(jTablePlayers, KEY_USERS_COLUMNS_WIDTH, KEY_USERS_COLUMNS_ORDER);
        jScrollPaneTalk.cleanUp();
    }

    public void setSplitDividerLocation(int location) {
        if (jSplitPane1 != null) {
            jSplitPane1.setDividerLocation(location);
        }
    }

    public int getSplitDividerLocation() {
        if (jSplitPane1 == null) {
            return 0;
        }
        return this.jSplitPane1.getDividerLocation();
    }

    class UserTableModel extends AbstractTableModel {

        private final String[] columnNames = new String[]{"Loc", "Players", "History", "Info", "Games", "Connection"};
        private UsersView[] players = new UsersView[0];

        public void loadData(Collection<RoomUsersView> roomUserInfoList) throws MageRemoteException {
            RoomUsersView roomUserInfo = roomUserInfoList.iterator().next();
            this.players = roomUserInfo.getUsersView().toArray(new UsersView[0]);
            JTableHeader th = jTablePlayers.getTableHeader();
            TableColumnModel tcm = th.getColumnModel();

            tcm.getColumn(jTablePlayers.convertColumnIndexToView(1)).setHeaderValue("Players (" + this.players.length + ")");
            tcm.getColumn(jTablePlayers.convertColumnIndexToView(4)).setHeaderValue(
                    "Games " + roomUserInfo.getNumberActiveGames()
                    + (roomUserInfo.getNumberActiveGames() != roomUserInfo.getNumberGameThreads() ? " (T:" + roomUserInfo.getNumberGameThreads() : " (")
                    + " limit: " + roomUserInfo.getNumberMaxGames() + ")");
            th.repaint();
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
                    return players[arg0].getUserName();
                case 2:
                    return players[arg0].getHistory();
                case 3:
                    return players[arg0].getInfoState();
                case 4:
                    return players[arg0].getInfoGames();
                case 5:
                    return players[arg0].getInfoPing();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSpinner1 = new javax.swing.JSpinner();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPanePlayers = new javax.swing.JScrollPane();
        jTablePlayers = new javax.swing.JTable();
        jTabbedPaneText = new javax.swing.JTabbedPane();
        jScrollPaneTalk = new mage.client.chat.ChatPanelSeparated();
        jScrollPaneSystem = new javax.swing.JScrollPane();
        colorPaneSystem = new mage.client.components.ColorPane();

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.25);

        jScrollPanePlayers.setBorder(null);

        jTablePlayers.setModel(this.userTableModel);
        jTablePlayers.setToolTipText("Connected players");
        jTablePlayers.setAutoscrolls(false);
        jTablePlayers.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTablePlayers.setFocusable(false);
        jTablePlayers.setGridColor(new java.awt.Color(255, 255, 255));
        jTablePlayers.setOpaque(false);
        jTablePlayers.setRequestFocusEnabled(false);
        jTablePlayers.setRowSelectionAllowed(false);
        jTablePlayers.setUpdateSelectionOnSort(false);
        jTablePlayers.setVerifyInputWhenFocusTarget(false);
        jScrollPanePlayers.setViewportView(jTablePlayers);

        jSplitPane1.setTopComponent(jScrollPanePlayers);

        jTabbedPaneText.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPaneText.addTab("Talk", jScrollPaneTalk);

        jScrollPaneSystem.setBorder(null);
        jScrollPaneSystem.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneSystem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPaneSystem.setFocusable(false);
        jScrollPaneSystem.setOpaque(false);

        colorPaneSystem.setEditable(false);
        colorPaneSystem.setBackground(new java.awt.Color(0, 0, 0));
        colorPaneSystem.setBorder(null);
        colorPaneSystem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        colorPaneSystem.setMargin(new java.awt.Insets(0, 0, 0, 0));
        colorPaneSystem.setOpaque(false);
        jScrollPaneSystem.setViewportView(colorPaneSystem);

        jTabbedPaneText.addTab("System", jScrollPaneSystem);

        jSplitPane1.setRightComponent(jTabbedPaneText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPane1)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setRoomUserInfo(List<Collection<RoomUsersView>> view) {
        try {
            userTableModel.loadData(view.get(0));
        } catch (Exception ex) {
            this.players.clear();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.components.ColorPane colorPaneSystem;
    private javax.swing.JScrollPane jScrollPanePlayers;
    private javax.swing.JScrollPane jScrollPaneSystem;
    private mage.client.chat.ChatPanelSeparated jScrollPaneTalk;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPaneText;
    private javax.swing.JTable jTablePlayers;
    // End of variables declaration//GEN-END:variables
}
