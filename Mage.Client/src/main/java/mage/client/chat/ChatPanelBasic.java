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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.UUID;
import javax.swing.JTextField;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.util.GUISizeHelper;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import org.mage.card.arcane.ManaSymbols;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class ChatPanelBasic extends javax.swing.JPanel {

    protected UUID chatId;
    /**
     * Chat message color for opponents.
     */
    protected static final String OPPONENT_COLOR = "#FF7F50";
    /**
     * Chat message color for client player.
     */
    protected static final String MY_COLOR = "#7FFFD4";
    /**
     * Chat message color for timestamps.
     */
    protected static final String TIMESTAMP_COLOR = "#CCCC33";
    /**
     * Chat message color for messages.
     */
    protected static final String MESSAGE_COLOR = "White";
    /**
     * Chat message color for personal infos.
     */
    protected static final String USER_INFO_COLOR = "Yellow";
    /**
     * Chat message color for status infos.
     */
    protected static final String STATUS_COLOR = "#FFCC33";
    /**
     * Alpha value for transparency (255 = not transparent)
     */
    public static final int CHAT_ALPHA = 80;
    /**
     * This will be a chat that will be connected to {this} and will handle
     * redirected messages; Mostly used to redirect user messages to another
     * window.
     */
    protected ChatPanelBasic connectedChat;
    /**
     * Parent chat this chat connected to. Used to send messages using parent
     * chat as it is the only one connected to server.
     */
    protected ChatPanelBasic parentChatRef;
    /**
     * Selected extended view mode.
     */
    protected VIEW_MODE extendedViewMode = VIEW_MODE.NONE;

    public enum VIEW_MODE {

        NONE, GAME, CHAT
    }
    /**
     * Controls the output start messages as the chat panel is created
     *
     */
    protected ChatType chatType = ChatType.DEFAULT;

    public enum ChatType {

        DEFAULT, GAME, TABLES, TOURNAMENT
    }
    protected boolean startMessageDone = false;

    /**
     *
     * Creates new form ChatPanel
     *
     */
    public ChatPanelBasic() {
        initComponents();
        setBackground(new Color(0, 0, 0, CHAT_ALPHA));
        changeGUISize(GUISizeHelper.chatFont);
        if (jScrollPaneTxt != null) {
            jScrollPaneTxt.setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPaneTxt.getViewport().setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPaneTxt.setViewportBorder(null);
        }
    }

    public void cleanUp() {

    }

    public void changeGUISize(Font font) {
        txtConversation.setFont(font);
        txtMessage.setFont(font);
        if (jScrollPaneTxt != null) {
            jScrollPaneTxt.setFont(font);
            jScrollPaneTxt.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
            jScrollPaneTxt.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
        }
        int height = 30;
        if (font.getSize() > 20) {
            height = 30 + Math.min(font.getSize() - 10, 30);
        }
        txtMessage.setMinimumSize(new Dimension(20, height));
        txtMessage.setMaximumSize(new Dimension(txtMessage.getWidth(), height));
        txtMessage.setPreferredSize(new Dimension(txtMessage.getWidth(), height));
        txtMessage.setSize(new Dimension(txtMessage.getWidth(), height));
        if (connectedChat != null) {
            connectedChat.changeGUISize(font);
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
        this.chatId = chatId;
        if (SessionHandler.joinChat(chatId)) {
            MageFrame.addChat(chatId, this);
        }
    }

    public void disconnect() {
        if (SessionHandler.getSession() != null) {
            SessionHandler.leaveChat(chatId);
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
                    userColor = SessionHandler.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
                } else {
                    userColor = SessionHandler.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
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
        text.append(getColoredText(textColor, ManaSymbols.replaceSymbolsWithHTML(message, ManaSymbols.Type.CHAT)));
        this.txtConversation.append(text.toString());
    }

    protected String getColoredText(String color, String text) {
        return "<font color='" + color + "'>" + text + "</font>";
    }

    public String getText() {
        return txtConversation.getText();
    }

    public ChatPanelBasic getConnectedChat() {
        return connectedChat;
    }

    public void setConnectedChat(ChatPanelBasic connectedChat) {
        this.connectedChat = connectedChat;
    }

    public void setParentChat(ChatPanelBasic parentChatRef) {
        this.parentChatRef = parentChatRef;
    }

    public ChatPanelBasic getParentChatRef() {
        return parentChatRef;
    }

    public void setParentChatRef(ChatPanelBasic parentChatRef) {
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
                alpha = CHAT_ALPHA;
        }
        this.txtConversation.setExtBackgroundColor(new Color(0, 0, 0, alpha)); // Alpha = 255 not transparent
        this.txtConversation.setSelectionColor(Color.LIGHT_GRAY);
        this.jScrollPaneTxt.setOpaque(alpha == 255);
        this.jScrollPaneTxt.getViewport().setOpaque(!chatType.equals(ChatType.TABLES));
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

        jScrollPaneTxt = new javax.swing.JScrollPane();
        txtConversation = new mage.client.components.ColorPane();
        txtMessage = new javax.swing.JTextField();

        jScrollPaneTxt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPaneTxt.setPreferredSize(new java.awt.Dimension(32767, 32767));

        txtConversation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtConversation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtConversation.setFocusCycleRoot(false);
        txtConversation.setMargin(new java.awt.Insets(2, 2, 2, 2));
        txtConversation.setOpaque(false);
        jScrollPaneTxt.setViewportView(txtConversation);

        txtMessage.setMaximumSize(new java.awt.Dimension(5000, 70));
        txtMessage.setMinimumSize(new java.awt.Dimension(6, 70));
        txtMessage.setName(""); // NOI18N
        txtMessage.setPreferredSize(new java.awt.Dimension(6, 70));
        txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMessageKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPaneTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void handleKeyTyped(java.awt.event.KeyEvent evt) {
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (parentChatRef != null) {
                SessionHandler.sendChatMessage(parentChatRef.chatId, this.txtMessage.getText());
            } else {
                SessionHandler.sendChatMessage(chatId, this.txtMessage.getText());
            }
            this.txtMessage.setText("");
            this.txtMessage.repaint();
        }
    }
    
    public void enableHyperlinks() {
        txtConversation.enableHyperlinks();
    }

    private void txtMessageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyTyped
        handleKeyTyped(evt);
}//GEN-LAST:event_txtMessageKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPaneTxt;
    private mage.client.components.ColorPane txtConversation;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables
}
