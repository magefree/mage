package mage.client.chat;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.util.IgnoreList;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.view.ChatMessage;

import java.util.Date;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;

public final class LocalCommands {

    /**
     * Handler for commands that do not require server interaction, i.e settings etc
     * @param chatId
     * @param text
     * @return true if the command was handled locally, else false
     */
    public static boolean handleLocalCommands(UUID chatId, String text) {
        final StringTokenizer st = new StringTokenizer(text.trim());
        final int tokens = st.countTokens();
        if (tokens == 0) {
            return false;
        }

        final String serverAddress = SessionHandler.getSession().getServerHostname().orElseGet(() -> "");
        Optional<String> response = Optional.empty();

        switch (st.nextToken()) {
            case "/ignore":
            case "\\ignore":
                final String ignoreTarget = tokens > 1 ? st.nextToken() : "";
                response = Optional.of(IgnoreList.ignore(serverAddress, ignoreTarget));
                break;
            case "/unignore":
            case "\\unignore":
                final String unignoreTarget = tokens > 1 ? st.nextToken() : "";
                response = Optional.of(IgnoreList.unignore(serverAddress, unignoreTarget));
                break;
            // TODO: move profanity settings to here
            default:
                break;
        }

        if (response.isPresent()) {
            displayLocalCommandResponse(chatId, response.get());
            return true;
        }

        return false;
    }

    private static void displayLocalCommandResponse(UUID chatId, String response) {
        final String text = new StringBuilder().append("<font color=yellow>").append(response).append("</font>").toString();
        ClientCallback chatMessage = new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId,
                new ChatMessage("", text, new Date(), ChatMessage.MessageColor.BLUE));
        MageFrame.getInstance().processCallback(chatMessage);
    }
}
