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

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import mage.client.MageFrame;
import mage.client.components.ColorPane;
import mage.remote.Session;
import mage.view.ChatMessage.MessageColor;

import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class ChatPanel extends javax.swing.JPanel {

	private UUID chatId;
	private Session session;

	private List<String> players = new ArrayList<String>();
	private TableModel tableModel;

	/**
	 * Chat message color for opponents.
	 */
	private static final Color OPPONENT_COLOR = Color.RED;

	/**
	 * Chat message color for client player.
	 */
	private static final Color MY_COLOR = Color.GREEN;

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
	    if (!addPlayersTab) simplifyComponents();
    }

	public void connect(UUID chatId) {
		session = MageFrame.getSession();
		this.chatId = chatId;
		if (session.joinChat(chatId)) {
			MageFrame.addChat(chatId, this);
		}
	}

	public void disconnect() {
		if (session != null)
			session.leaveChat(chatId);
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
			this.txtConversation.append(MESSAGE_COLOR, (username.isEmpty() ? "" : username + ":") + message + "\n");
		} else {
			this.txtConversation.append(TIMESTAMP_COLOR, time + " ");
			Color userColor;
			if (parentChatRef != null) {
				userColor = parentChatRef.session.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
			} else {
				userColor = session.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
			}
			this.txtConversation.append(userColor, username + ": ");
			this.txtConversation.append(MESSAGE_COLOR, message + "\n");
		}
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

	public void disableInput() {
		this.txtMessage.setVisible(false);
	}

	public void useExtendedView(VIEW_MODE extendedViewMode) {
		this.extendedViewMode = extendedViewMode;
		this.txtConversation.setExtBackgroundColor(new Color(0,0,0,100));
		this.txtConversation.setBackground(new Color(0,0,0,0));
		this.txtConversation.setForeground(new Color(255,255,255));
		this.jScrollPane1.setOpaque(false);
		this.jScrollPane1.getViewport().setOpaque(false);
	}

class TableModel extends AbstractTableModel {
    private String[] columnNames = new String[]{"Players"};
	private List<String> players = new ArrayList<String>(0);

	public void loadData(List<String> players) {
		this.players = players;
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

	public void clear() {
		this.txtConversation.selectAll();
		this.txtConversation.replaceSelection("");
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMessage = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
		//txtConversation = new JTextArea();
        txtConversation = new ColorPane();
		//txtConversation = new JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMessageKeyTyped(evt);
            }
        });

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        //txtConversation.setColumns(20);
		txtConversation.setOpaque(false);
        //txtConversation.setEditable(false);
        txtConversation.setFont(new java.awt.Font("Arial", 0, 14));
		//txtConversation.enableInputMethods(false);
        //txtConversation.setLineWrap(true);
        //txtConversation.setRows(5);
        //txtConversation.setWrapStyleWord(true);

        jScrollPane1.setViewportView(txtConversation);
		jScrollPane1.setBorder(new EmptyBorder(0,0,0,0));

        jTabbedPane1.addTab("chat", jScrollPane1);

        jTable1.setModel(this.tableModel);
        jTable1.setToolTipText("Connected players");
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(jTable1);

        jTabbedPane1.addTab("players", jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("chat");
    }// </editor-fold>//GEN-END:initComponents

	private void simplifyComponents() {
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
		jTabbedPane1 = null;
		jTable1 = null;
		jScrollPane2 = null;
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
			boolean update = false;
			int size = players.size();
			List<String> list = new ArrayList<String>(players);
			Collections.sort(list);
			if (size != this.players.size()) {
				update = true;
			} else {
				update = true;
				for (int i = 0; i < size; i++) {
					if (!list.get(i).equals(this.players.get(i))) {
						update = false;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    //private javax.swing.JTextArea txtConversation;
	private ColorPane txtConversation;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables

}
