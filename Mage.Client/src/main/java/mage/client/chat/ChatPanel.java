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
package mage.client.chat;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.swing.Icon;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import mage.client.MageFrame;
import static mage.client.dialog.PreferencesDialog.KEY_USERS_COLUMNS_ORDER;
import static mage.client.dialog.PreferencesDialog.KEY_USERS_COLUMNS_WIDTH;
import mage.client.util.MageTableRowSorter;
import mage.client.util.gui.TableUtil;
import mage.client.util.gui.countryBox.CountryCellRenderer;
import mage.remote.MageRemoteException;
import mage.remote.Session;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.RoomUsersView;
import mage.view.UsersView;
import org.mage.card.arcane.ManaSymbols;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class ChatPanel extends javax.swing.JPanel {
        
    private UUID chatId;
    private Session session;
    private final List<String> players = new ArrayList<>();
    private final UserTableModel userTableModel;
    /**
     * Chat message color for opponents.
     */
    private static final String OPPONENT_COLOR =  "#FF7F50";
    /**
     * Chat message color for client player.
     */
    private static final String MY_COLOR = "#7FFFD4";
    /**
     * Chat message color for timestamps.
     */
    private static final String TIMESTAMP_COLOR = "#CCCC33";
    /**
     * Chat message color for messages.
     */
    private static final String MESSAGE_COLOR = "White";
    /**
     * Chat message color for personal infos.
     */
    private static final String USER_INFO_COLOR = "Yellow";
    /**
     * Chat message color for status infos.
     */
    private static final String STATUS_COLOR = "#FFCC33";
    /**
     * Alpha value for transparency (255 = not transparent)
     */
    private static final int ALPHA = 80;
    /**
     * This will be a chat that will be connected to {this} and will handle
     * redirected messages; Mostly used to redirect user messages to another
     * window.
     */
    private ChatPanel connectedChat;
    /**
     * Parent chat this chat connected to. Used to send messages using parent
     * chat as it is the only one connected to server.
     */
    private ChatPanel parentChatRef;
    /**
     * Selected extended view mode.
     */
    private VIEW_MODE extendedViewMode = VIEW_MODE.NONE;

    public enum VIEW_MODE {

        NONE, GAME, CHAT
    }
    /**
     * Controls the output start messages as the chat panel is created
     *
     */
    private ChatType chatType = ChatType.DEFAULT;

    private static final int[] defaultColumnsWidth = {20, 100, 100, 80};
    
    public enum ChatType {

        DEFAULT, GAME, TABLES, TOURNAMENT
    }
    private boolean startMessageDone = false;

    /**
     * Creates new form ChatPanel
     */
    public ChatPanel() {
        this(false);
    }

    /**
     * @param addPlayersTab if true, adds chat/players tabs
     */
    /**
     * Creates new form ChatPanel
     * @param addPlayersTab
     */
    public ChatPanel(boolean addPlayersTab) {
        userTableModel = new UserTableModel();
        initComponents();
        setBackground(new Color(0, 0, 0, ALPHA));
        jTablePlayers.setBackground(new Color(0, 0, 0, ALPHA));
        jTablePlayers.setForeground(Color.white);
        jTablePlayers.setRowSorter(new MageTableRowSorter(userTableModel));
                
        TableUtil.setColumnWidthAndOrder(jTablePlayers, defaultColumnsWidth, KEY_USERS_COLUMNS_WIDTH, KEY_USERS_COLUMNS_ORDER);
        jTablePlayers.setDefaultRenderer(Icon.class, new CountryCellRenderer());
        
        if (jScrollPaneTxt != null) {
            jScrollPaneTxt.setBackground(new Color(0, 0, 0, ALPHA));
            jScrollPaneTxt.getViewport().setBackground(new Color(0, 0, 0, ALPHA));
        }
        if (jScrollPanePlayers != null) {
            jScrollPanePlayers.setBackground(new Color(0, 0, 0, ALPHA));
            jScrollPanePlayers.getViewport().setBackground(new Color(0, 0, 0, ALPHA));
        }
        if (!addPlayersTab) {
            simplifyComponents();
        }
    }

    public void cleanUp() {
        TableUtil.saveColumnWidthAndOrderToPrefs(jTablePlayers, KEY_USERS_COLUMNS_WIDTH, KEY_USERS_COLUMNS_ORDER);        
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public boolean isStartMessageDone() {
        return startMessageDone;
    }

    public void setStartMessageDone(boolean startMessageDone) {
        this.startMessageDone = startMessageDone;
    }

    public void connect(UUID chatId) {
        session = MageFrame.getSession();
        this.chatId = chatId;
        if (session.joinChat(chatId)) {
            MageFrame.addChat(chatId, this);
        }
    }

    public void disconnect() {
        if (session != null) {
            session.leaveChat(chatId);
            MageFrame.removeChat(chatId);
        }
    }

    /**
     * Display message in the chat. Use different colors for timestamp, username
     * and message.
     *
     * @param username message sender
     * @param message message itself
     * @param time timestamp
     * @param messageType
     * @param color Preferred color. Not used.
     */
    public void receiveMessage(String username, String message, String time, MessageType messageType, MessageColor color) {
        StringBuilder text = new StringBuilder();
        if (time != null) {
            text.append(getColoredText(TIMESTAMP_COLOR, time + ": "));
            //this.txtConversation.append(TIMESTAMP_COLOR, time + " ");
        }
        String userColor;
        String textColor;
        String userSeparator = " ";
        switch (messageType) {
            case STATUS: // a message to all chat user
                textColor = STATUS_COLOR;
                userColor = STATUS_COLOR;
                break;
            case USER_INFO: // a personal message
                textColor = USER_INFO_COLOR;
                userColor = USER_INFO_COLOR;
                break;
            default:
                if (parentChatRef != null) {
                    userColor = parentChatRef.session.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
                } else {
                    userColor = session.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
                }
                textColor = MESSAGE_COLOR;
                userSeparator = ": ";
        }
        if (color.equals(MessageColor.ORANGE)) {
            textColor = "Orange";
        }
        if (color.equals(MessageColor.YELLOW)) {
            textColor = "Yellow";
        }
        if (username != null && !username.isEmpty()) {
            text.append(getColoredText(userColor, username + userSeparator));
        }              
        text.append(getColoredText(textColor, ManaSymbols.replaceSymbolsWithHTML(message, ManaSymbols.Type.PAY)));
        this.txtConversation.append(text.toString());
    }

    private String getColoredText(String color, String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("<font color='");
        sb.append(color);
        sb.append("'>");
        sb.append(text);
        sb.append("</font>");
        return sb.toString();
    }
    public String getText() {
        return txtConversation.getText();
    }

    public ChatPanel getConnectedChat() {
        return connectedChat;
    }

    public void setConnectedChat(ChatPanel connectedChat) {
        this.connectedChat = connectedChat;
    }

    public void setParentChat(ChatPanel parentChatRef) {
        this.parentChatRef = parentChatRef;
    }

    public ChatPanel getParentChatRef() {
        return parentChatRef;
    }

    public void setParentChatRef(ChatPanel parentChatRef) {
        this.parentChatRef = parentChatRef;
    }

    public void disableInput() {
        this.txtMessage.setVisible(false);
    }

    public JTextField getTxtMessageInputComponent() {
        return this.txtMessage;
    }

    public void useExtendedView(VIEW_MODE extendedViewMode) {
        this.extendedViewMode = extendedViewMode;
        int alpha = 255;
        switch (chatType) {
            case GAME:
            case TABLES:
            case DEFAULT:
                alpha = ALPHA;
        }
        this.txtConversation.setExtBackgroundColor(new Color(0, 0, 0, alpha)); // Alpha = 255 not transparent
        // this.txtConversation.setBackground(new Color(0, 0, 0, 0));
        // this.txtConversation.setForeground(new Color(255, 255, 255));
        this.txtConversation.setSelectionColor(Color.LIGHT_GRAY);
        this.jScrollPaneTxt.setOpaque(alpha == 255);
        this.jScrollPaneTxt.getViewport().setOpaque(!chatType.equals(ChatType.TABLES));
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
                
        private final String[] columnNames = new String[]{" ","Players", "Info", "Games", "Connection"};
        private UsersView[] players = new UsersView[0];

        public void loadData(Collection<RoomUsersView> roomUserInfoList) throws MageRemoteException {
            RoomUsersView roomUserInfo = roomUserInfoList.iterator().next();
            this.players = roomUserInfo.getUsersView().toArray(new UsersView[0]);
            JTableHeader th = jTablePlayers.getTableHeader();
            TableColumnModel tcm = th.getColumnModel();
            
            tcm.getColumn(1).setHeaderValue("Players (" + this.players.length + ")");
            tcm.getColumn(3).setHeaderValue(
                    "Games " + roomUserInfo.getNumberActiveGames() +
                    (roomUserInfo.getNumberActiveGames() != roomUserInfo.getNumberGameThreads() ? " (T:" + roomUserInfo.getNumberGameThreads():" (") +
                    " limit: " + roomUserInfo.getNumberMaxGames() + ")");
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
                    return players[arg0].getInfoState();
                case 3:
                    return players[arg0].getInfoGames();
                case 4:
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

    public void clear() {
        this.txtConversation.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPaneTxt = new javax.swing.JScrollPane();
        txtConversation = new mage.client.components.ColorPane();
        jScrollPanePlayers = new javax.swing.JScrollPane();
        jTablePlayers = new javax.swing.JTable();
        txtMessage = new javax.swing.JTextField();

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.25);

        jScrollPaneTxt.setBorder(null);

        txtConversation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtConversation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtConversation.setFocusCycleRoot(false);
        txtConversation.setMargin(new java.awt.Insets(2, 2, 2, 2));
        txtConversation.setOpaque(false);
        jScrollPaneTxt.setViewportView(txtConversation);

        jSplitPane1.setRightComponent(jScrollPaneTxt);

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

        txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMessageKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void simplifyComponents() {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(txtMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addComponent(jScrollPaneTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPaneTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)));
        jTablePlayers = null;
        jScrollPanePlayers = null;
    }

    private void txtMessageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (parentChatRef != null) {
                parentChatRef.session.sendChatMessage(parentChatRef.chatId, this.txtMessage.getText());
            } else {
                session.sendChatMessage(chatId, this.txtMessage.getText());
            }
            this.txtMessage.setText("");
            this.txtMessage.repaint();
        }
}//GEN-LAST:event_txtMessageKeyTyped

    public void setRoomUserInfo(List<Collection<RoomUsersView>> view) {
        try {
            userTableModel.loadData(view.get(0));
        } catch (Exception ex) {
            this.players.clear();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPanePlayers;
    private javax.swing.JScrollPane jScrollPaneTxt;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTablePlayers;
    private mage.client.components.ColorPane txtConversation;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables
}
