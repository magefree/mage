package mage.server;

import mage.remote.DisconnectReason;
import mage.game.Game;
import mage.server.managers.ManagerFactory;
import mage.view.ChatMessage;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ChatSession {

    private static final Logger logger = Logger.getLogger(ChatSession.class);
    private static final DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);

    private final ManagerFactory managerFactory;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final ConcurrentMap<UUID, String> clients = new ConcurrentHashMap<>();
    private final UUID chatId;
    private final Date createTime;
    private final String info;

    public ChatSession(ManagerFactory managerFactory, String info) {
        this.managerFactory = managerFactory;
        chatId = UUID.randomUUID();
        this.createTime = new Date();
        this.info = info;
    }

    public void join(UUID userId) {
        managerFactory.userManager().getUser(userId).ifPresent(user -> {
            if (!clients.containsKey(userId)) {
                String userName = user.getName();
                final Lock w = lock.writeLock();
                w.lock();
                try {
                    clients.put(userId, userName);
                } finally {
                    w.unlock();
                }
                broadcast(null, userName + " has joined (" + user.getClientVersion() + ')', MessageColor.BLUE, true, null, MessageType.STATUS, null);
                logger.trace(userName + " joined chat " + chatId);
            }
        });
    }

    public void kill(UUID userId, DisconnectReason reason) {

        try {
            if (reason == null) {
                logger.fatal("User kill without disconnect reason  userId: " + userId);
                reason = DisconnectReason.Undefined;
            }
            if (userId != null && clients.containsKey(userId)) {
                String userName = clients.get(userId);
                if (reason != DisconnectReason.LostConnection) { // for lost connection the user will be reconnected or session expire so no removeUserFromAllTablesAndChat of chat yet
                    final Lock w = lock.writeLock();
                    w.lock();
                    try {
                        clients.remove(userId);
                    } finally {
                        w.unlock();
                    }
                    logger.debug(userName + '(' + reason.toString() + ')' + " removed from chatId " + chatId);
                }
                String message = reason.getMessage();

                if (!message.isEmpty()) {
                    broadcast(null, userName + message, MessageColor.BLUE, true, null, MessageType.STATUS, null);
                }
            }
        } catch (Exception ex) {
            logger.fatal("exception: " + ex.toString());
        }
    }

    public boolean broadcastInfoToUser(User toUser, String message) {
        if (clients.containsKey(toUser.getId())) {
            toUser.chatMessage(chatId, new ChatMessage(null, message, new Date(), null, MessageColor.BLUE, MessageType.USER_INFO, null));
            return true;
        }
        return false;
    }

    public boolean broadcastWhisperToUser(User fromUser, User toUser, String message) {
        if (clients.containsKey(toUser.getId())) {
            toUser.chatMessage(chatId, 
                    new ChatMessage(new StringBuilder("Whisper from ").append(fromUser.getName()).toString(), message, new Date(), null, MessageColor.YELLOW, MessageType.WHISPER_FROM, SoundToPlay.PlayerWhispered));
            if (clients.containsKey(fromUser.getId())) {
                fromUser.chatMessage(chatId,
                        new ChatMessage(new StringBuilder("Whisper to ").append(toUser.getName()).toString(), message, new Date(), null, MessageColor.YELLOW, MessageType.WHISPER_TO, null));
                return true;
            }
        }
        return false;
    }
    
    public void broadcast(String userName, String message, MessageColor color, boolean withTime, Game game, MessageType messageType, SoundToPlay soundToPlay) {
        if (!message.isEmpty()) {
            Set<UUID> clientsToRemove = new HashSet<>();
            boolean remove = false;
            final String time = (withTime ? timeFormatter.format(new Date()):"");
            logger.trace("Broadcasting '" + message + "' for " + chatId);
            for (UUID userId: clients.keySet()) {
                User chatUser = managerFactory.userManager().getUser(userId).get();                
                if (chatUser != null) {
                    Main.sendChatMessage(chatUser.getSessionId(), chatId, new ChatMessage(userName, message, (withTime ? new Date() : null), game, color, messageType, soundToPlay));
                }
                else {
                    logger.error("User not found but connected to chat - userId: " + userId + "  chatId: " + chatId);
                    clientsToRemove.add(userId);
                }
            }
            if (!clientsToRemove.isEmpty()) {
                final Lock w = lock.readLock();
                w.lock();
                try {
                    clients.keySet().removeAll(clientsToRemove);
                } finally {
                    w.unlock();
                }
            }

        }
    }

    /**
     * @return the chatId
     */
    public UUID getChatId() {
        return chatId;
    }

    public boolean hasUser(UUID userId) {
        return clients.containsKey(userId);
    }

    public ConcurrentMap<UUID, String> getClients() {
        return clients;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getInfo() {
        return info;
    }

}
