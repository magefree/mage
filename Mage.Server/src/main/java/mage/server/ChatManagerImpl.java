package mage.server;

import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Constants;
import mage.game.Game;
import mage.server.game.GameController;
import mage.server.managers.ChatManager;
import mage.server.managers.ManagerFactory;
import mage.util.GameLog;
import mage.utils.SystemUtil;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class ChatManagerImpl implements ChatManager {

    private static final Logger logger = Logger.getLogger(ChatManagerImpl.class);
    private static final HashMap<String, String> lastUserMessages = new HashMap<>(); // user->chat+message

    private final ManagerFactory managerFactory;
    private final ConcurrentHashMap<UUID, ChatSession> chatSessions = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ChatManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public UUID createChatSession(String info) {
        ChatSession chatSession = new ChatSession(managerFactory, info);
        chatSessions.put(chatSession.getChatId(), chatSession);
        return chatSession.getChatId();
    }

    @Override
    public void joinChat(UUID chatId, UUID userId) {
        ChatSession chatSession = chatSessions.get(chatId);
        if (chatSession != null) {
            chatSession.join(userId);
        } else {
            logger.trace("Chat to join not found - chatId: " + chatId + " userId: " + userId);
        }
    }

    @Override
    public void leaveChat(UUID chatId, UUID userId) {
        ChatSession chatSession = chatSessions.get(chatId);
        if (chatSession != null && chatSession.hasUser(userId, false)) {
            chatSession.disconnectUser(userId, DisconnectReason.DisconnectedByUser);
        }
    }

    @Override
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

    final Pattern cardNamePattern = Pattern.compile("\\[\\[(.*?)\\]\\]"); // uses scryfall bot style from github: [[name]]

    @Override
    public void broadcast(UUID chatId, String userName, String message, MessageColor color, boolean withTime, Game game, MessageType messageType, SoundToPlay soundToPlay) {
        String finalMessage = message;
        ChatSession chatSession = chatSessions.get(chatId);
        Optional<User> user = managerFactory.userManager().getUserByName(userName);
        if (chatSession != null) {
            // special commads
            if (message.startsWith("\\") || message.startsWith("/")) {
                if (user.isPresent()) {
                    if (!performUserCommand(user.get(), message, chatId, false)) {
                        performUserCommand(user.get(), message, chatId, true);
                    }
                    return;
                }
            }

            // enrich non-game messages with popup hints and other refs
            if (messageType != MessageType.GAME && !userName.isEmpty()) {
                if (user.isPresent()) {

                    // muted by admin
                    if (messageType == MessageType.TALK) {
                        if (user.get().getChatLockedUntil() != null) {
                            if (user.get().getChatLockedUntil().compareTo(Calendar.getInstance().getTime()) > 0) {
                                // still muted
                                chatSessions.get(chatId).broadcastInfoToUser(user.get(), "Your chat is muted until "
                                        + SystemUtil.dateFormat.format(user.get().getChatLockedUntil()));
                                return;
                            } else {
                                // reset and allows
                                user.get().setChatLockedUntil(null);
                            }
                        }
                    }

                    // spam protection
                    String messageId = chatId.toString() + message;
                    if (messageId.equals(lastUserMessages.get(userName))) {
                        chatSessions.get(chatId).broadcastInfoToUser(user.get(), "Ignore duplicated message");
                        return;
                    }
                    lastUserMessages.put(userName, messageId);

                    // message limit
                    if (message.length() > Constants.MAX_CHAT_MESSAGE_SIZE) {
                        // too big messages must be ignored in parent code, so only system messages possible here
                        message = message.replaceFirst("^(.{" + Constants.MAX_CHAT_MESSAGE_SIZE + "}).*", "$1 (rest of message truncated)");
                    }

                    // insert card names with popup support
                    String messageToCheck = message;
                    Matcher matchPattern = cardNamePattern.matcher(message);
                    int foundCount = 0;
                    while (matchPattern.find()) {
                        // abuse protection
                        foundCount++;
                        if (foundCount > 5) {
                            break;
                        }

                        // TODO: add search by lower case and partly text like github bot (maybe slow and abuseable, so test before implement)
                        String searchName = matchPattern.group(1);
                        CardInfo cardInfo = CardRepository.instance.findCard(searchName, true);
                        if (cardInfo != null) {
                            String newMessagePart = GameLog.getColoredObjectIdName(
                                    cardInfo.createCard().getColor(),
                                    UUID.randomUUID(),
                                    cardInfo.getName(),
                                    "",
                                    cardInfo.getName()
                            );
                            messageToCheck = messageToCheck.replaceFirst("\\[\\[" + searchName + "\\]\\]", "[[" + newMessagePart + "]]");
                        }
                    }
                    finalMessage = messageToCheck;
                }
            }

            // all other messages
            chatSession.broadcast(userName, finalMessage, color, withTime, game, messageType, soundToPlay);
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
            + "<br/>\\ignore - shows your ignore list on this server."
            + "<br/>\\ignore [username] - add username to ignore list (they won't be able to chat or join to your game)."
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
            message += "<br/>" + managerFactory.userManager().getUserHistory(message.substring(command.startsWith("H ") ? 3 : 9));
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }
        if (command.equals("ME")) {
            message += "<br/>" + managerFactory.userManager().getUserHistory(user.getName());
            chatSessions.get(chatId).broadcastInfoToUser(user, message);
            return true;
        }
        if (command.startsWith("GAME")) {
            message += "<br/>";
            ChatSession session = chatSessions.get(chatId);
            if (session != null && session.getInfo() != null) {
                String gameId = session.getInfo();
                if (gameId.startsWith("Game ")) {
                    UUID id = java.util.UUID.fromString(gameId.substring(5));
                    for (Entry<UUID, GameController> entry : managerFactory.gameManager().getGameController().entrySet()) {
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
            message += "<br/>";
            ChatSession session = chatSessions.get(chatId);
            if (session != null && session.getInfo() != null) {
                String gameId = session.getInfo();
                if (gameId.startsWith("Game ")) {
                    UUID id = java.util.UUID.fromString(gameId.substring(5));
                    for (Entry<UUID, GameController> entry : managerFactory.gameManager().getGameController().entrySet()) {
                        if (entry.getKey().equals(id)) {
                            GameController controller = entry.getValue();
                            if (controller != null) {
                                message += controller.attemptToFixGame(user);
                                chatSessions.get(chatId).broadcastInfoToUser(user, message);
                            }
                        }
                    }

                }
            }
            return true;
        }
        if (command.equals("PINGS")) {
            message += "<br/>";
            ChatSession session = chatSessions.get(chatId);
            if (session != null && session.getInfo() != null) {
                String gameId = session.getInfo();
                if (gameId.startsWith("Game ")) {
                    UUID id = java.util.UUID.fromString(gameId.substring(5));
                    for (Entry<UUID, GameController> entry : managerFactory.gameManager().getGameController().entrySet()) {
                        if (entry.getKey().equals(id)) {
                            GameController controller = entry.getValue();
                            if (controller != null) {
                                message += controller.getPingsInfo();
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
                CardInfo cardInfo = CardRepository.instance.findPreferredCoreExpansionCard(cardName);
                if (cardInfo != null) {
                    cardInfo.getRules();
                    message = "<font color=orange>" + cardInfo.getName() + "</font>: Cost:" + cardInfo.getManaCosts(CardInfo.ManaCostSide.ALL).toString() + ",  Types:" + cardInfo.getTypes().toString() + ", ";
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
                Optional<User> userTo = managerFactory.userManager().getUserByName(userToName);
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

    @Override
    public void sendReconnectMessage(UUID userId) {
        managerFactory.userManager().getUser(userId).ifPresent(user
                -> getChatSessions()
                .stream()
                .filter(chat -> chat.hasUser(userId, true))
                .forEach(chatSession -> chatSession.broadcast(null, user.getName() + " has reconnected", MessageColor.BLUE, true, null, MessageType.STATUS, null)));

    }

    /**
     * Send message to all active waiting/tourney/game chats (but not in main lobby)
     *
     * @param userId
     * @param message
     */
    @Override
    public void sendMessageToUserChats(UUID userId, String message) {
        managerFactory.userManager().getUser(userId).ifPresent(user -> {
            List<ChatSession> chatSessions = getChatSessions().stream()
                    .filter(chat -> !chat.getChatId().equals(managerFactory.gamesRoomManager().getMainChatId())) // ignore main lobby
                    .filter(chat -> chat.hasUser(userId, true))
                    .collect(Collectors.toList());

            if (chatSessions.size() > 0) {
                logger.debug("INFORM OPPONENTS by " + user.getName() + ": " + message);
                chatSessions.forEach(chatSession -> chatSession.broadcast(null, message, MessageColor.BLUE, true, null, MessageType.STATUS, null));
            }
        });
    }

    @Override
    public void removeUser(UUID userId, DisconnectReason reason) {
        for (ChatSession chatSession : getChatSessions()) {
            if (chatSession.hasUser(userId, false)) {
                chatSession.disconnectUser(userId, reason);
            }
        }
    }

    @Override
    public List<ChatSession> getChatSessions() {
        final Lock r = lock.readLock();
        r.lock();
        try {
            return new ArrayList<>(chatSessions.values());
        } finally {
            r.unlock();
        }
    }

    private void clearUserMessageStorage() {
        lastUserMessages.clear();
    }

    @Override
    public void checkHealth() {
        //logger.info("cheching chats...");
        // TODO: add broken chats check and report (with non existing userId)

        /*
        logger.info("total chats: " + chatSessions.size());
        chatSessions.values().forEach(c -> {
            logger.info("chat " + c.getInfo() + ", " + c.getChatId());
            c.getClients().forEach((userId, userName) -> {
                logger.info(" - " + userName + ", " + userId);
            });
        });
         */

        clearUserMessageStorage();
    }
}
