
package mage.server;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.server.exceptions.UserNotFoundException;
import mage.server.game.GameController;
import mage.server.game.GameManager;
import mage.server.util.SystemUtil;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum ChatManager {

    instance;
    private static final Logger logger = Logger.getLogger(ChatManager.class);
    private static final HashMap<String, String> userMessages = new HashMap<>();

    private final ConcurrentHashMap<UUID, ChatSession> chatSessions = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

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

    public void clearUserMessageStorage() {
        userMessages.clear();
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
                if (chatSessions.containsKey(chatId)) {
                    final Lock w = lock.writeLock();
                    w.lock();
                    try {
                        chatSessions.remove(chatId);
                    } finally {
                        w.unlock();
                    }
                    logger.trace("Chat removed - chatId: " + chatId);
                } else {
                    logger.trace("Chat to destroy does not exist - chatId: " + chatId);
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

    final Pattern cardNamePattern = Pattern.compile("\\[(.*?)\\]");

    public void broadcast(UUID chatId, String userName, String message, MessageColor color, boolean withTime, MessageType messageType, SoundToPlay soundToPlay) {
        ChatSession chatSession = chatSessions.get(chatId);
        if (chatSession != null) {
            if (message.startsWith("\\") || message.startsWith("/")) {
                Optional<User> user = UserManager.instance.getUserByName(userName);
                if (user.isPresent()) {
                    if (!performUserCommand(user.get(), message, chatId, false)) {
                        performUserCommand(user.get(), message, chatId, true);
                    }
                    return;
                }
            }

            if (messageType != MessageType.GAME && !userName.isEmpty()) {
                Optional<User> u = UserManager.instance.getUserByName(userName);
                if (u.isPresent()) {

                    User user = u.get();
                    if (message.equals(userMessages.get(userName))) {
                        // prevent identical messages
                        String informUser = "Your message appears to be identical to your last message";
                        chatSessions.get(chatId).broadcastInfoToUser(user, informUser);
                        return;
                    }

                    if (message.length() > 500) {
                        message = message.replaceFirst("^(.{500}).*", "$1 (rest of message truncated)");
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
                            String displayCardName = "<font bgcolor=orange color=" + colour + '>' + cardName + "</font>";
                            message = message.replaceFirst("\\[" + cardName + "\\]", displayCardName);
                        }
                    }

                    userMessages.put(userName, message);

                    if (messageType == MessageType.TALK) {
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
            + "<br/>\\card Card Name - Print oracle text for card"
            + "<br/>[Card Name] - Show a highlighted card name"
            + "<br/>\\ignore - shows current ignore list on this server."
            + "<br/>\\ignore [username] - add a username to your ignore list on this server."
            + "<br/>\\unignore [username] - remove a username from your ignore list on this server.";

    final Pattern getCardTextPattern = Pattern.compile("^.card *(.*)");

    private boolean performUserCommand(User user, String message, UUID chatId, boolean doError) {
        String command = message.substring(1).trim().toUpperCase(Locale.ENGLISH);
        if (doError) {
            message += new StringBuilder("<br/>Invalid User Command '" + message + "'.").append(COMMANDS_LIST).toString();
            message += "<br/>Type <font color=green>\\w " + user.getName() + " profanity 0 (or 1 or 2)</font> to use/not use the profanity filter";
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }

        if (command.startsWith("H ") || command.startsWith("HISTORY ")) {
            message += "<br/>" + UserManager.instance.getUserHistory(message.substring(command.startsWith("H ") ? 3 : 9));
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }
        if (command.equals("ME")) {
            message += "<br/>" + UserManager.instance.getUserHistory(user.getName());
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }
        if (command.startsWith("GAME")) {
            message += "<br/>" + GameManager.instance.getChatId(chatId);
            ChatSession session = chatSessions.get(chatId);
            if (session != null && session.getInfo() != null) {
                String gameId = session.getInfo();
                if (gameId.startsWith("Game ")) {
                    UUID id = java.util.UUID.fromString(gameId.substring(5, gameId.length()));
                    for (Entry<UUID, GameController> entry : GameManager.instance.getGameController().entrySet()) {
                        if (entry.getKey().equals(id)) {
                            GameController controller = entry.getValue();
                            if (controller != null) {
                                message += controller.getGameStateDebugMessage();
                                chatSessions.get(chatId).broadcastInfoToUser(user, message);
                            }
                        }
                    }

                }
            }
            return true;
        }
        if (command.startsWith("FIX")) {
            message += "<br/>" + GameManager.instance.getChatId(chatId);
            ChatSession session = chatSessions.get(chatId);
            if (session != null && session.getInfo() != null) {
                String gameId = session.getInfo();
                if (gameId.startsWith("Game ")) {
                    UUID id = java.util.UUID.fromString(gameId.substring(5, gameId.length()));
                    for (Entry<UUID, GameController> entry : GameManager.instance.getGameController().entrySet()) {
                        if (entry.getKey().equals(id)) {
                            GameController controller = entry.getValue();
                            if (controller != null) {
                                message += controller.attemptToFixGame();
                                chatSessions.get(chatId).broadcastInfoToUser(user, message);
                            }
                        }
                    }

                }
            }
            return true;
        }        
        if (command.startsWith("CARD ")) {
            Matcher matchPattern = getCardTextPattern.matcher(message.toLowerCase(Locale.ENGLISH));
            if (matchPattern.find()) {
                String cardName = matchPattern.group(1);
                CardInfo cardInfo = CardRepository.instance.findPreferedCoreExpansionCard(cardName, true);
                if (cardInfo != null) {
                    cardInfo.getRules();
                    message = "<font color=orange>" + cardInfo.getName() + "</font>: Cost:" + cardInfo.getManaCosts().toString() + ",  Types:" + cardInfo.getTypes().toString() + ", ";
                    for (String rule : cardInfo.getRules()) {
                        message = message + rule;
                    }
                } else {
                    message = "Couldn't find: " + cardName;

                }
            }
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }
        if (command.startsWith("W ") || command.startsWith("WHISPER ")) {
            String rest = message.substring(command.startsWith("W ") ? 3 : 9);
            int first = rest.indexOf(' ');
            if (first > 1) {
                String userToName = rest.substring(0, first);
                rest = rest.substring(first + 1).trim();
                Optional<User> userTo = UserManager.instance.getUserByName(userToName);
                if (userTo.isPresent()) {
                    if (!chatSessions.get(chatId).broadcastWhisperToUser(user, userTo.get(), rest)) {
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
            message += "<br/>Type <font color=green>\\w " + user.getName() + " profanity 0 (or 1 or 2)</font> to use/not use the profanity filter";
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
     * @throws mage.server.exceptions.UserNotFoundException
     */
    public void broadcast(UUID userId, String message, MessageColor color) throws UserNotFoundException {
        UserManager.instance.getUser(userId).ifPresent(user -> {
            getChatSessions()
                    .stream()
                    .filter(chat -> chat.hasUser(userId))
                    .forEach(session -> session.broadcast(user.getName(), message, color, true, MessageType.TALK, null));

        });
    }

    public void sendReconnectMessage(UUID userId) {
        UserManager.instance.getUser(userId).ifPresent(user
                -> getChatSessions()
                .stream()
                .filter(chat -> chat.hasUser(userId))
                .forEach(chatSession -> chatSession.broadcast(null, user.getName() + " has reconnected", MessageColor.BLUE, true, MessageType.STATUS, null)));

    }

    public void sendLostConnectionMessage(UUID userId, DisconnectReason reason) {
        UserManager.instance.getUser(userId).ifPresent(user
                -> getChatSessions()
                .stream()
                .filter(chat -> chat.hasUser(userId))
                .forEach(chatSession -> chatSession.broadcast(null, user.getName() + reason.getMessage(), MessageColor.BLUE, true, MessageType.STATUS, null)));

    }

    public void removeUser(UUID userId, DisconnectReason reason) {
        for (ChatSession chatSession : getChatSessions()) {
            if (chatSession.hasUser(userId)) {
                chatSession.kill(userId, reason);
            }
        }
    }

    public List<ChatSession> getChatSessions() {
        final Lock r = lock.readLock();
        r.lock();
        try {
            return new ArrayList<>(chatSessions.values());
        } finally {
            r.unlock();
        }
    }

}
