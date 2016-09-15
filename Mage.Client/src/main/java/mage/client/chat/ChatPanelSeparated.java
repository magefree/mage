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
package mage.client.chat;

import java.awt.Font;

import mage.client.SessionHandler;
import mage.client.components.ColorPane;
import mage.client.util.GUISizeHelper;
import mage.view.ChatMessage;
import org.mage.card.arcane.ManaSymbols;

/**
 *
 * @author LevelX2
 */
public class ChatPanelSeparated extends ChatPanelBasic {

    private ColorPane systemMessagesPane = null;

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
    @Override
    public void receiveMessage(String username, String message, String time, ChatMessage.MessageType messageType, ChatMessage.MessageColor color) {
        switch (messageType) {
            case TALK:
            case WHISPER:
            case USER_INFO:
                super.receiveMessage(username, message, time, messageType, color);
                return;
        }
        StringBuilder text = new StringBuilder();
        if (time != null) {
            text.append(getColoredText(TIMESTAMP_COLOR, time + ": "));
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
        if (color.equals(ChatMessage.MessageColor.ORANGE)) {
            textColor = "Orange";
        }
        if (color.equals(ChatMessage.MessageColor.YELLOW)) {
            textColor = "Yellow";
        }
        if (username != null && !username.isEmpty()) {
            text.append(getColoredText(userColor, username + userSeparator));
        }
        text.append(getColoredText(textColor, ManaSymbols.replaceSymbolsWithHTML(message, ManaSymbols.Type.CHAT)));
        this.systemMessagesPane.append(text.toString());
    }

    public ColorPane getSystemMessagesPane() {
        return systemMessagesPane;
    }

    public void setSystemMessagesPane(ColorPane systemMessagesPane) {
        this.systemMessagesPane = systemMessagesPane;
        changeGUISize(GUISizeHelper.chatFont);
    }

    @Override
    public void changeGUISize(Font font) {
        if (systemMessagesPane != null) {
            systemMessagesPane.setFont(font);
        }

        super.changeGUISize(font);
    }

}
