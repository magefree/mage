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

import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.view.ChatMessage;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ChatSession {

    private static final Logger logger = Logger.getLogger(ChatSession.class);
    private static final DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);

    private final ConcurrentHashMap<UUID, String> clients = new ConcurrentHashMap<>();
    private final UUID chatId;
    private final Date createTime;
    private final String info;

    public ChatSession(String info) {
        chatId = UUID.randomUUID();
        this.createTime = new Date();
        this.info = info;
    }

    public void join(UUID userId) {
        UserManager.instance.getUser(userId).ifPresent(user -> {
            if (!clients.containsKey(userId)) {
                String userName = user.getName();
                clients.put(userId, userName);
                broadcast(null, userName + " has joined (" + user.getClientVersion() + ')', MessageColor.BLUE, true, MessageType.STATUS, null);
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
                if (reason != DisconnectReason.LostConnection) { // for lost connection the user will be reconnected or session expire so no remove of chat yet
                    clients.remove(userId);
                    logger.debug(userName + '(' + reason.toString() + ')' + " removed from chatId " + chatId);
                }
                String message = reason.getMessage();

                if (!message.isEmpty()) {
                    broadcast(null, userName + message, MessageColor.BLUE, true, MessageType.STATUS, null);
                }
            }
        } catch (Exception ex) {
            logger.fatal("exception: " + ex.toString());
        }
    }

    public boolean broadcastInfoToUser(User toUser, String message) {
        if (clients.containsKey(toUser.getId())) {
            toUser.fireCallback(new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId, new ChatMessage(null, message, timeFormatter.format(new Date()), MessageColor.BLUE, MessageType.USER_INFO, null)));
            return true;
        }
        return false;
    }

    public boolean broadcastWhisperToUser(User fromUser, User toUser, String message) {
        if (clients.containsKey(toUser.getId())) {
            toUser.fireCallback(new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId,
                    new ChatMessage(fromUser.getName(), message, timeFormatter.format(new Date()), MessageColor.YELLOW, MessageType.WHISPER_FROM, SoundToPlay.PlayerWhispered)));
            if (clients.containsKey(fromUser.getId())) {
                fromUser.fireCallback(new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId,
                        new ChatMessage(toUser.getName(), message, timeFormatter.format(new Date()), MessageColor.YELLOW, MessageType.WHISPER_TO, null)));
                return true;
            }
        }
        return false;
    }

    public void broadcast(String userName, String message, MessageColor color, boolean withTime, MessageType messageType, SoundToPlay soundToPlay) {
        if (!message.isEmpty()) {
            HashSet<UUID> clientsToRemove = new HashSet<>();
            ClientCallback clientCallback = new ClientCallback(ClientCallbackMethod.CHATMESSAGE, chatId, new ChatMessage(userName, message, (withTime ? timeFormatter.format(new Date()) : ""), color, messageType, soundToPlay));
            for (UUID userId : clients.keySet()) {
                Optional<User> user = UserManager.instance.getUser(userId);
                if (user.isPresent()) {
                    user.get().fireCallback(clientCallback);
                } else {
                    clientsToRemove.add(userId);
                }
            }
            clients.keySet().removeAll(clientsToRemove);

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

    public ConcurrentHashMap<UUID, String> getClients() {
        return clients;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getInfo() {
        return info;
    }

}
