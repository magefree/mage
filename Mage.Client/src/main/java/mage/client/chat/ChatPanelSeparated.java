
package mage.client.chat;

import java.awt.Font;
import java.util.Date;

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
    public void receiveMessage(String username, String message, Date time, ChatMessage.MessageType messageType, ChatMessage.MessageColor color) {
        switch (messageType) {
            case TALK:
            case WHISPER_TO:
            case WHISPER_FROM:
            case USER_INFO:
                super.receiveMessage(username, message, time, messageType, color);
                return;
        }
        StringBuilder text = new StringBuilder();
        if (time != null) {
            text.append(getColoredText(TIMESTAMP_COLOR, timeFormatter.format(time) + ": "));
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
        if (color == ChatMessage.MessageColor.ORANGE) {
            textColor = "Orange";
        }
        if (color == ChatMessage.MessageColor.YELLOW) {
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
