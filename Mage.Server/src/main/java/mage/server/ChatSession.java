package mage.server;

import mage.game.Game;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.server.managers.ManagerFactory;
import mage.view.ChatMessage;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class ChatSession {

    private static final Logger logger = Logger.getLogger(ChatSession.class);

    private final ManagerFactory managerFactory;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(); // TODO: no needs due ConcurrentHashMap usage?

    private final ConcurrentMap<UUID, String> users = new ConcurrentHashMap<>(); // active users
    private final Set<UUID> usersHistory = new HashSet<>(); // all users that was here (need for system messages like connection problem)
    private final UUID chatId;
    private final Date createTime;
    private final String info;

    public ChatSession(ManagerFactory managerFactory, String info) {
        this.managerFactory = managerFactory;
        this.chatId = UUID.randomUUID();
        this.createTime = new Date();
        this.info = info;
    }

    public void join(UUID userId) {
        managerFactory.userManager().getUser(userId).ifPresent(user -> {
            if (!users.containsKey(userId)) {
                String userName = user.getName();
                final Lock w = lock.writeLock();
                w.lock();
                try {
                    users.put(userId, userName);
                    usersHistory.add(userId);
                } finally {
                    w.unlock();
                }
                broadcast(null, userName + " has joined", MessageColor.BLUE, true, null, MessageType.STATUS, null);
            }
        });
    }

    public void disconnectUser(UUID userId, DisconnectReason reason) {
        // user will reconnect to all chats, so no needs to keep it
        // if you kill session then kill all chats too

        try {
            String userName = users.getOrDefault(userId, null);
            if (userName == null) {
                return;
            }

            // remove from chat
            final Lock w = lock.writeLock();
            w.lock();
            try {
                users.remove(userId);
            } finally {
                w.unlock();
            }
            logger.debug(userName + " (" + reason + ')' + " removed from chatId " + chatId);

            // inform other users about disconnect (lobby, system tab)
            if (!reason.messageForUser.isEmpty()) {
                broadcast(null, userName + reason.messageForUser, MessageColor.BLUE, true, null, MessageType.STATUS, null);
            }
        } catch (Exception e) {
            logger.fatal("Chat: disconnecting user catch error: " + e, e);
        }
    }

    public void broadcastInfoToUser(User toUser, String message) {
        if (users.containsKey(toUser.getId())) {
            toUser.fireCallback(new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId,
                    new ChatMessage(null, message, new Date(), null, MessageColor.BLUE, MessageType.USER_INFO, null)));
        }
    }

    public boolean broadcastWhisperToUser(User fromUser, User toUser, String message) {
        if (users.containsKey(toUser.getId())) {
            toUser.fireCallback(new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId,
                    new ChatMessage(fromUser.getName(), message, new Date(), null, MessageColor.YELLOW, MessageType.WHISPER_FROM, SoundToPlay.PlayerWhispered)));
            if (users.containsKey(fromUser.getId())) {
                fromUser.fireCallback(new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId,
                        new ChatMessage(toUser.getName(), message, new Date(), null, MessageColor.YELLOW, MessageType.WHISPER_TO, null)));
                return true;
            }
        }
        return false;
    }

    public void broadcast(String userName, String message, MessageColor color, boolean withTime, Game game, MessageType messageType, SoundToPlay soundToPlay) {
        // TODO: is it called by single thread for all users?
        // TODO: is it freeze on someone's connection fail/freeze?
        // TODO: is it freeze on someone's connection fail/freeze with play multiple games/chats/lobby?
        // TODO: send messages in another thread?!
        if (!message.isEmpty()) {
            Set<UUID> clientsToRemove = new HashSet<>();
            ClientCallback clientCallback = new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId,
                    new ChatMessage(userName, message, (withTime ? new Date() : null), game, color, messageType, soundToPlay));
            List<UUID> chatUserIds = new ArrayList<>();
            final Lock r = lock.readLock();
            r.lock();
            try {
                chatUserIds.addAll(users.keySet());
            } finally {
                r.unlock();
            }
            for (UUID userId : chatUserIds) {
                Optional<User> user = managerFactory.userManager().getUser(userId);
                if (user.isPresent()) {
                    user.get().fireCallback(clientCallback);
                } else {
                    clientsToRemove.add(userId);
                }
            }
            if (!clientsToRemove.isEmpty()) {
                final Lock w = lock.readLock();
                w.lock();
                try {
                    users.keySet().removeAll(clientsToRemove);
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

    public boolean hasUser(UUID userId, boolean useHistory) {
        return useHistory ? usersHistory.contains(userId) : users.containsKey(userId);
    }

    public ConcurrentMap<UUID, String> getUsers() {
        return users;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getInfo() {
        return info;
    }

}
