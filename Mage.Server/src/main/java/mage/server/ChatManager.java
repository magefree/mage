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
package mage.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.server.util.SystemUtil;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ChatManager {

    private static final Logger logger = Logger.getLogger(ChatManager.class);
    private static final HashMap<String, String> userMessages = new HashMap<>();

    private static final ChatManager INSTANCE = new ChatManager();

    public static ChatManager getInstance() {
        return INSTANCE;
    }

    private ChatManager() {
    }

    private final ConcurrentHashMap<UUID, ChatSession> chatSessions = new ConcurrentHashMap<>();

    public UUID createChatSession(String info) {
        ChatSession chatSession = new ChatSession(info);
        chatSessions.put(chatSession.getChatId(), chatSession);
        return chatSession.getChatId();
    }

    public void joinChat(UUID chatId, UUID userId) {
        ChatSession chatSession = chatSessions.get(chatId);
        if (chatSession != null) {
            chatSession.join(userId);
        } else {
            logger.trace("Chat to join not found - chatId: " + chatId + " userId: " + userId);
        }

    }

    public void leaveChat(UUID chatId, UUID userId) {
        ChatSession chatSession = chatSessions.get(chatId);
        if (chatSession != null && chatSession.hasUser(userId)) {
            chatSession.kill(userId, DisconnectReason.CleaningUp);
        }
    }

    public void destroyChatSession(UUID chatId) {
        if (chatId != null) {
            ChatSession chatSession = chatSessions.get(chatId);
            if (chatSession != null) {
                synchronized (chatSession) {
                    if (chatSessions.containsKey(chatId)) {
                        chatSessions.remove(chatId);
                        logger.trace("Chat removed - chatId: " + chatId);
                    } else {
                        logger.trace("Chat to destroy does not exist - chatId: " + chatId);
                    }
                }
            }
        }
    }

    public void broadcast(UUID chatId, String userName, String message, MessageColor color, boolean withTime) {
        this.broadcast(chatId, userName, message, color, withTime, MessageType.TALK);
    }

    public void broadcast(UUID chatId, String userName, String message, MessageColor color, boolean withTime, MessageType messageType) {
        this.broadcast(chatId, userName, message, color, withTime, messageType, null);
    }

    private boolean containsSwearing(String message) {
        if (message != null && message.toLowerCase().matches("^.*(anal|asshole|balls|bastard|bitch|blowjob|cock|crap|cunt|cum|damn|dick|dildo|douche|fag|fuck|idiot|moron|penis|piss|prick|pussy|rape|rapist|sex|screw|shit|slut|vagina).*$")) {
            return true;
        }
        return false;
    }

    final Pattern cardNamePattern = Pattern.compile("\\[(.*?)\\]");

    public void broadcast(UUID chatId, String userName, String message, MessageColor color, boolean withTime, MessageType messageType, SoundToPlay soundToPlay) {
        ChatSession chatSession = chatSessions.get(chatId);
        if (chatSession != null) {
            if (message.startsWith("\\") || message.startsWith("/")) {
                User user = UserManager.getInstance().getUserByName(userName);
                if (user != null) {
                    if (!performUserCommand(user, message, chatId, false)) {
                        performUserCommand(user, message, chatId, true);
                    }
                    return;
                }
            }

            if (messageType != MessageType.GAME) {
                User user = UserManager.getInstance().getUserByName(userName);
                if (message != null && userName != null && !userName.equals("")) {

                    if (message.equals(userMessages.get(userName))) {
                        // prevent identical messages
                        String informUser = "Your message appears to be identical to your last message";
                        chatSessions.get(chatId).broadcastInfoToUser(user, informUser);
                        return;
                    }

                    String messageToCheck = message;
                    Matcher matchPattern = cardNamePattern.matcher(message);
                    while (matchPattern.find()) {
                        String cardName = matchPattern.group(1);
                        CardInfo cardInfo = CardRepository.instance.findPreferedCoreExpansionCard(cardName, true);
                        if (cardInfo != null) {
                            String colour = "silver";
                            if (cardInfo.getCard().getColor(null).isMulticolored()) {
                                colour = "yellow";
                            } else if (cardInfo.getCard().getColor(null).isWhite()) {
                                colour = "white";
                            } else if (cardInfo.getCard().getColor(null).isBlue()) {
                                colour = "blue";
                            } else if (cardInfo.getCard().getColor(null).isBlack()) {
                                colour = "black";
                            } else if (cardInfo.getCard().getColor(null).isRed()) {
                                colour = "red";
                            } else if (cardInfo.getCard().getColor(null).isGreen()) {
                                colour = "green";
                            }
                            messageToCheck = messageToCheck.replaceFirst("\\[" + cardName + "\\]", "card");
                            String displayCardName = "<font bgcolor=orange color=" + colour + ">" + cardName + "</font>";
                            message = message.replaceFirst("\\[" + cardName + "\\]", displayCardName);
                        }
                    }

                    userMessages.put(userName, message);
                    if (containsSwearing(messageToCheck)) {
                        String informUser = "Your message appears to contain profanity";
                        chatSessions.get(chatId).broadcastInfoToUser(user, informUser);
                        return;
                    }
                }

                if (messageType == MessageType.TALK) {
                    if (user != null) {
                        if (user.getChatLockedUntil() != null) {
                            if (user.getChatLockedUntil().compareTo(Calendar.getInstance().getTime()) > 0) {
                                chatSessions.get(chatId).broadcastInfoToUser(user, "Your chat is muted until " + SystemUtil.dateFormat.format(user.getChatLockedUntil()));
                                return;
                            } else {
                                user.setChatLockedUntil(null);
                            }
                        }
                    }
                }

            }
            chatSession.broadcast(userName, message, color, withTime, messageType, soundToPlay);
        }
    }

    private static final String COMMANDS_LIST
            = "<br/>List of commands:"
            + "<br/>\\history or \\h [username] - shows the history of a player"
            + "<br/>\\me - shows the history of the current player"
            + "<br/>\\list or \\l - Show a list of commands"
            + "<br/>\\whisper or \\w [player name] [text] - whisper to the player with the given name"
            + "<br/>[Card Name] - Show a highlighted card name";

    private boolean performUserCommand(User user, String message, UUID chatId, boolean doError) {
        String command = message.substring(1).trim().toUpperCase(Locale.ENGLISH);
        if (doError) {
            message += new StringBuilder("<br/>Invalid User Command '" + message + "'.").append(COMMANDS_LIST).toString();
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }

        if (command.startsWith("H ") || command.startsWith("HISTORY ")) {
            message += "<br/>" + UserManager.getInstance().getUserHistory(message.substring(command.startsWith("H ") ? 3 : 9));
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }
        if (command.equals("ME")) {
            message += "<br/>" + UserManager.getInstance().getUserHistory(user.getName());
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }
        if (command.startsWith("W ") || command.startsWith("WHISPER ")) {
            String rest = message.substring(command.startsWith("W ") ? 3 : 9);
            int first = rest.indexOf(" ");
            if (first > 1) {
                String userToName = rest.substring(0, first);
                rest = rest.substring(first + 1).trim();
                User userTo = UserManager.getInstance().getUserByName(userToName);
                if (userTo != null) {
                    if (!chatSessions.get(chatId).broadcastWhisperToUser(user, userTo, rest)) {
                        message += new StringBuilder("<br/>User ").append(userToName).append(" not found").toString();
                        chatSessions.get(chatId).broadcastInfoToUser(user, message);
                    }
                } else {
                    message += new StringBuilder("<br/>User ").append(userToName).append(" not found").toString();
                    chatSessions.get(chatId).broadcastInfoToUser(user, message);
                }
                return true;
            }
        }
        if (command.equals("L") || command.equals("LIST")) {
            message += COMMANDS_LIST;
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }
        return false;
    }

    /**
     * use mainly for announcing that a user connection was lost or that a user
     * has reconnected
     *
     * @param userId
     * @param message
     * @param color
     */
    public void broadcast(UUID userId, String message, MessageColor color) {
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            for (ChatSession chat : chatSessions.values()) {
                if (chat.hasUser(userId)) {
                    chat.broadcast(user.getName(), message, color, true, MessageType.TALK, null);
                }
            }
        }
    }

    public void sendReconnectMessage(UUID userId) {
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            for (ChatSession chat : chatSessions.values()) {
                if (chat.hasUser(userId)) {
                    chat.broadcast(null, user.getName() + " has reconnected", MessageColor.BLUE, true, MessageType.STATUS, null);
                }
            }
        }
    }

    public void removeUser(UUID userId, DisconnectReason reason) {
        for (ChatSession chatSession : chatSessions.values()) {
            if (chatSession.hasUser(userId)) {
                chatSession.kill(userId, reason);
            }
        }
    }

    public ArrayList<ChatSession> getChatSessions() {
        ArrayList<ChatSession> chatSessionList = new ArrayList<>();
        chatSessionList.addAll(chatSessions.values());
        return chatSessionList;
    }
}
