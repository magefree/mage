package mage.client.chat;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.util.IgnoreList;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.view.ChatMessage;

import java.util.*;

public final class LocalCommands {


    private LocalCommands() {
    }

    private static String getRemainingTokens(StringTokenizer st) {
        List<String> list = new ArrayList<>();
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return String.join(" ", list);
    }

    /**
     * Handler for commands that do not require server interaction, i.e settings etc
     *
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

        final String serverAddress = SessionHandler.getSession().getServerHostname().orElse("");
        Optional<String> response = Optional.empty();

        String command = st.nextToken();
        switch (command) {
            case "/ignore":
            case "\\ignore":
                final String ignoreTarget = getRemainingTokens(st);
                response = Optional.of(IgnoreList.ignore(serverAddress, ignoreTarget));
                break;
            case "/unignore":
            case "\\unignore":
                final String unignoreTarget = getRemainingTokens(st);
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
                new ChatMessage("", text, new Date(), null, ChatMessage.MessageColor.BLUE));
        MageFrame.getInstance().processCallback(chatMessage);
    }
}
