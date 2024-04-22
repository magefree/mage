package mage.client.chat;

import mage.client.SessionHandler;
import mage.client.components.ColorPane;
import mage.client.util.GUISizeHelper;
import mage.view.ChatMessage;
import org.mage.card.arcane.ManaSymbols;

import java.awt.*;
import java.util.Date;

/**
 * @author LevelX2
 */
public class ChatPanelSeparated extends ChatPanelBasic {

    private ColorPane systemMessagesPane = null;

    @Override
    public void cleanUp() {
        super.cleanUp();
        if (this.systemMessagesPane != null) {
            this.systemMessagesPane.cleanUp();
        }
    }

    /**
     * Display message in the chat. Use different colors for timestamp, username
     * and message.
     *
     * @param username    message sender
     * @param message     message itself
     * @param time        timestamp
     * @param turnInfo    game turn info, can be null for non game messages
     * @param messageType
     * @param color       Preferred color. Not used.
     */
    @Override
    public void receiveMessage(String username, String message, Date time, String turnInfo, ChatMessage.MessageType messageType, ChatMessage.MessageColor color) {
        String userColor;
        String textColor;
        String userSeparator = " ";
        StringBuilder text = new StringBuilder();
        switch (messageType) {
            case TALK:
            case WHISPER_TO:
            case WHISPER_FROM:
            case USER_INFO:
                // message in main chat
                super.receiveMessage(username, message, time, turnInfo, messageType, color);
                return;
            case STATUS:
                textColor = STATUS_COLOR;
                userColor = STATUS_COLOR;
                break;
            default:
            case GAME:
                userColor = SessionHandler.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
                textColor = MESSAGE_COLOR;
                userSeparator = ": ";
                break;
        }

        // message in game log
        if (time != null) {
            text.append(getColoredText(TIMESTAMP_COLOR, timeFormatter.format(time) + getTurnInfoPart(turnInfo) + ": "));
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
