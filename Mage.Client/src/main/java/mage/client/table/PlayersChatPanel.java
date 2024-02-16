package mage.client.table;

import mage.client.cards.BigCard;
import mage.client.chat.ChatPanelBasic;
import mage.client.util.GUISizeHelper;
import mage.client.util.MageTableRowSorter;
import mage.client.util.gui.TableUtil;
import mage.client.util.gui.countryBox.CountryCellRenderer;
import mage.remote.MageRemoteException;
import mage.view.RoomUsersView;
import mage.view.UsersView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static mage.client.chat.ChatPanelBasic.CHAT_ALPHA;
import static mage.client.dialog.PreferencesDialog.KEY_USERS_COLUMNS_ORDER;
import static mage.client.dialog.PreferencesDialog.KEY_USERS_COLUMNS_WIDTH;

/**
 * @author BetaSteward_at_googlemail.com, nantuko, JayDi85
 */
public class PlayersChatPanel extends javax.swing.JPanel {

    private final List<String> players = new ArrayList<>();
    private final UserTableModel userTableModel;
    private static final TableInfo tableInfo = new TableInfo()
            .addColumn(0, 20, Icon.class, "Flag", null)
            .addColumn(1, 100, String.class, "Players",
                    "<b>User name</b>"
                            + "<br>(the number behind the header text is the number of users online)")
            .addColumn(2, 40, Integer.class, "Constructed Rating", null)
            .addColumn(3, 40, Integer.class, "Limited Rating", null)
            .addColumn(4, 40, String.class, "Matches",
                    "<b>Number of matches the user played so far</b>"
                            + "<br>Q = number of matches quit"
                            + "<br>I = number of matches lost because of idle timeout"
                            + "<br>T = number of matches lost because of match timeout")
            .addColumn(5, 100, Integer.class, "MQP",
                    "<b>Percent-Ratio of matches played related to matches quit</b>"
                            + "<br>this calculation does not include tournament matches")
            .addColumn(6, 40, String.class, "Tourneys",
                    "<b>Number of tournaments the user played so far</b>"
                            + "<br>D = number of tournaments left during draft phase"
                            + "<br>C = number of tournaments left during constructing phase"
                            + "<br>R = number of tournaments left during rounds")
            .addColumn(7, 100, Integer.class, "TQP",
                    "<b>Percent-Ratio of tournament matches played related to tournament matches quit</b>"
                            + "<br>this calculation does not include non tournament matches")
            .addColumn(8, 80, String.class, "Games",
                    "<b>Current activities of the player</b>"
                            + "<BR>the header itself shows the number of currently active games"
                            + "<BR>T: = number of games threads "
                            + "<BR><i>(that can vary from active games because of sideboarding or crashed games)</i>"
                            + "<BR>limt: the maximum of games the server is configured to"
                            + "<BR><i>(if the number of started games exceed that limit, the games have to wait"
                            + "<BR>until active games end)</i>")
            .addColumn(9, 80, String.class, "Ping", null);

    public PlayersChatPanel() {
        userTableModel = new UserTableModel(); // needs to be set before initComponents();

        initComponents();
        setBackground(new Color(0, 0, 0, CHAT_ALPHA));

        jTablePlayers.setBackground(new Color(0, 0, 0, CHAT_ALPHA));
        jTablePlayers.setForeground(Color.white);
        jTablePlayers.setRowSorter(new MageTableRowSorter(userTableModel));
        setGUISize();

        TableUtil.setColumnWidthAndOrder(jTablePlayers, tableInfo.getColumnsWidth(), KEY_USERS_COLUMNS_WIDTH, KEY_USERS_COLUMNS_ORDER);

        jTablePlayers.setDefaultRenderer(Icon.class, new CountryCellRenderer());

        jScrollPaneTalk.setSystemMessagesPane(colorPaneSystem);
        jScrollPaneTalk.setOpaque(false);

        jScrollPaneSystem.getViewport().setOpaque(false);
        jScrollPaneSystem.setViewportBorder(null);

        colorPaneSystem.setExtBackgroundColor(new Color(0, 0, 0, CHAT_ALPHA)); // Alpha = 255 not transparent
        colorPaneSystem.setBorder(new EmptyBorder(5, 5, 5, 5));
        if (jScrollPanePlayers != null) {
            jScrollPanePlayers.setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPanePlayers.getViewport().setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPanePlayers.setViewportBorder(null);
        }
    }

    public ChatPanelBasic getUserChatPanel() {
        return jScrollPaneTalk;
    }

    public void cleanUp() {
        TableUtil.saveColumnWidthAndOrderToPrefs(jTablePlayers, KEY_USERS_COLUMNS_WIDTH, KEY_USERS_COLUMNS_ORDER);
        jScrollPaneTalk.cleanUp();
    }

    public void setGameData(UUID gameId, BigCard bigCard) {
        colorPaneSystem.setGameData(gameId, bigCard);
    }

    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {
        jTablePlayers.getTableHeader().setFont(GUISizeHelper.tableFont);
        jTablePlayers.setFont(GUISizeHelper.tableFont);
        jTablePlayers.setRowHeight(GUISizeHelper.getTableRowHeight());
        jScrollPanePlayers.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPanePlayers.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
        jScrollPaneSystem.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPaneSystem.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));

        jTabbedPaneText.setFont(GUISizeHelper.getTabFont());
        jSplitPane1.setDividerSize(GUISizeHelper.dividerBarSize);
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

        private UsersView[] players = new UsersView[0];

        public void loadData(Collection<RoomUsersView> roomUserInfoList) throws MageRemoteException {
            RoomUsersView roomUserInfo = roomUserInfoList.iterator().next();
            this.players = roomUserInfo.getUsersView().toArray(new UsersView[0]);
            JTableHeader th = jTablePlayers.getTableHeader();
            TableColumnModel tcm = th.getColumnModel();

            tcm.getColumn(jTablePlayers.convertColumnIndexToView(tableInfo.getColumnByName("Players").getIndex())).setHeaderValue("Players (" + this.players.length + ')');
            tcm.getColumn(jTablePlayers.convertColumnIndexToView(tableInfo.getColumnByName("Games").getIndex())).setHeaderValue("Games "
                    + roomUserInfo.getNumberActiveGames()
                    + (roomUserInfo.getNumberActiveGames() != roomUserInfo.getNumberGameThreads() ? " (T:" + roomUserInfo.getNumberGameThreads() : " (")
                    + " limit: " + roomUserInfo.getNumberMaxGames() + ')');
            th.repaint();
            this.fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return players.length;
        }

        @Override
        public int getColumnCount() {
            return tableInfo.getColumns().size();
        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            switch (colIndex) {
                case 0:
                    return players[rowIndex].getFlagName();
                case 1:
                    return players[rowIndex].getUserName();
                case 2:
                    return players[rowIndex].getConstructedRating();
                case 3:
                    return players[rowIndex].getLimitedRating();
                case 4:
                    return players[rowIndex].getMatchHistory();
                case 5:
                    return players[rowIndex].getMatchQuitRatio();
                case 6:
                    return players[rowIndex].getTourneyHistory();
                case 7:
                    return players[rowIndex].getTourneyQuitRatio();
                case 8:
                    return players[rowIndex].getInfoGames();
                case 9:
                    return players[rowIndex].getInfoPing();
                default:
                    return "";
            }
        }

        @Override
        public String getColumnName(int columnIndex) {
            return tableInfo.getColumnByIndex(columnIndex).getHeaderName();
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            return tableInfo.getColumnByIndex(columnIndex).getColClass();
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

        jSpinner1 = new javax.swing.JSpinner();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPanePlayers = new javax.swing.JScrollPane();
        jTablePlayers = new MageTable(tableInfo);
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
        jTablePlayers.setOpaque(false);
        jTablePlayers.setRequestFocusEnabled(false);
        jTablePlayers.setRowSelectionAllowed(false);
        jTablePlayers.setShowHorizontalLines(false);
        jTablePlayers.setShowVerticalLines(false);
        jTablePlayers.setUpdateSelectionOnSort(false);
        jTablePlayers.setVerifyInputWhenFocusTarget(false);
        jScrollPanePlayers.setViewportView(jTablePlayers);
        jTablePlayers.getAccessibleContext().setAccessibleDescription("");

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
    private MageTable jTablePlayers;
    // End of variables declaration//GEN-END:variables
}
