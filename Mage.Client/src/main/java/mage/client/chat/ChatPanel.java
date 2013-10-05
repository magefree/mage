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
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import mage.client.MageFrame;
import mage.remote.Session;
import mage.view.ChatMessage.MessageColor;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class ChatPanel extends javax.swing.JPanel {

    private UUID chatId;
    private Session session;

    private List<String> players = new ArrayList<String>();
    private final TableModel tableModel;

    /**
     * Chat message color for opponents.
     */
    private static final Color OPPONENT_COLOR = new Color(0, 230, 64);

    /**
     * Chat message color for client player.
     */
    private static final Color MY_COLOR = new Color(0, 230, 64);

    /**
     * Chat message color for timestamps.
     */
    private static final Color TIMESTAMP_COLOR = new Color(255, 255, 0, 120);

    /**
     * Chat message color for messages.
     */
    private static final Color MESSAGE_COLOR = Color.white;

    /**
     * This will be a chat that will be connected to {this} and will handle redirected messages;
     * Mostly used to redirect user messages to another window.
     */
    private ChatPanel connectedChat;

    /**
     * Parent chat this chat connected to.
     * Used to send messages using parent chat as it is the only one connected to server.
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

    public enum ChatType {
        DEFAULT, GAME, TABLES, TOURNAMENT
    }

    private boolean startMessageDone = false;

    /**
     * Maps message colors to {@link Color}.
     */
    private static final Map<MessageColor, Color> colorMap = new EnumMap<MessageColor, Color>(MessageColor.class);

    static {
        colorMap.put(MessageColor.BLACK, Color.black);
        colorMap.put(MessageColor.GREEN, Color.green);
        colorMap.put(MessageColor.ORANGE, Color.orange);
        colorMap.put(MessageColor.BLUE, Color.blue);
        colorMap.put(MessageColor.RED, Color.red);
    }

    /** Creates new form ChatPanel */
    public ChatPanel() {
        this(false);
    }

    /**
     * @param addPlayersTab if true, adds chat/players tabs
     */
    /** Creates new form ChatPanel */
    public ChatPanel(boolean addPlayersTab) {
        tableModel = new TableModel();
        initComponents();
        jTablePlayers.setBackground(new Color(0, 0, 0, 0));
        jTablePlayers.setForeground(Color.white);
        setBackground(new Color(0, 0, 0, 100));
        if (jScrollPaneTxt != null) {
            jScrollPaneTxt.setBackground(new Color(0, 0, 0, 100));
            jScrollPaneTxt.getViewport().setBackground(new Color(0, 0, 0, 100));
        }
        if (jScrollPanePlayers != null) {
            jScrollPanePlayers.setBackground(new Color(0, 0, 0, 100));
            jScrollPanePlayers.getViewport().setBackground(new Color(0, 0, 0, 100));
        }
        if (!addPlayersTab) {
            simplifyComponents();
        }
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
        }
    }

    /**
     * Display message in the chat.
     * Use different colors for timestamp, username and message.
     *
     * @param username message sender
     * @param message message itself
     * @param time timestamp
     * @param color Preferred color. Not used.
     */
    public void receiveMessage(String username, String message, String time, MessageColor color) {
        if (extendedViewMode.equals(VIEW_MODE.GAME)) {
            this.txtConversation.append(TIMESTAMP_COLOR, time + " ");
            Color textColor = MESSAGE_COLOR;
            if (color.equals(MessageColor.ORANGE)) {
                textColor = Color.ORANGE;
            }
            this.txtConversation.append(textColor, (username.isEmpty() ? "" : username + ":") + message + "\n");
        } else {
            this.txtConversation.append(TIMESTAMP_COLOR, time + " ");
            Color userColor;
            Color textColor = MESSAGE_COLOR;
            if (parentChatRef != null) {
                userColor = parentChatRef.session.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
            } else {
                userColor = session.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
                if (color.equals(MessageColor.ORANGE)) {
                    userColor = Color.ORANGE;
                    textColor = userColor;
                }
            }
            this.txtConversation.append(userColor, username + ": ");
            this.txtConversation.append(textColor, message + "\n");
        }
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
        this.txtConversation.setExtBackgroundColor(new Color(0,0,0,100));
        this.txtConversation.setBackground(new Color(0,0,0,0));
        this.txtConversation.setForeground(new Color(255,255,255));
        this.jScrollPaneTxt.setOpaque(false);
        this.jScrollPaneTxt.getViewport().setOpaque(false);
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

class TableModel extends AbstractTableModel {
    private String[] columnNames = new String[]{"Players"};
    private List<String> players = new ArrayList<String>(0);

    public void loadData(List<String> players) {
        this.players = players;
        JTableHeader th = jTablePlayers.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(0);
        tc.setHeaderValue(new StringBuilder("Players").append(" (").append(this.players.size()).append(")").toString());
        th.repaint();
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return players.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        return players.get(arg0);
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

    public void clear() {
        this.txtConversation.setText("");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.25);

        txtConversation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtConversation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtConversation.setFocusCycleRoot(false);
        txtConversation.setMargin(new java.awt.Insets(2, 2, 2, 2));
        txtConversation.setOpaque(false);
        jScrollPaneTxt.setViewportView(txtConversation);

        jSplitPane1.setRightComponent(jScrollPaneTxt);

        jTablePlayers.setModel(this.tableModel);
        jTablePlayers.setToolTipText("Connected players");
        jTablePlayers.setGridColor(new java.awt.Color(255, 255, 255));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void simplifyComponents() {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
            .addComponent(jScrollPaneTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPaneTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
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

    public void setPlayers(Collection<String> players) {
        if (players != null) {
            boolean update;
            int size = players.size();
            List<String> list = new ArrayList<String>(players);
            Collections.sort(list);
            if (size != this.players.size()) {
                update = true;
            } else {
                update = false;
                for (int i = 0; i < size; i++) {
                    if (!list.get(i).equals(this.players.get(i))) {
                        update = true;
                        break;
                    }
                }
            }
            if (update && list != null) {
                synchronized (tableModel) {
                    this.players = list;
                    tableModel.loadData(this.players);
                }
            }

        } else {
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
