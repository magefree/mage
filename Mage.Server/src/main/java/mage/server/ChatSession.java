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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.interfaces.callback.ClientCallback;
import mage.view.ChatMessage;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChatSession {

    private static final Logger logger = Logger.getLogger(ChatSession.class);
    private final ConcurrentHashMap<UUID, String> clients = new ConcurrentHashMap<>();
    private final UUID chatId;
    private final DateFormat timeFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);

    public ChatSession() {
        chatId = UUID.randomUUID();
    }

    public void join(UUID userId) {
        User user = UserManager.getInstance().getUser(userId);
        if (user != null && !clients.containsKey(userId)) {
            String userName = user.getName();
            clients.put(userId, userName);
            broadcast(null, new StringBuilder(userName).append(" has joined").toString(), MessageColor.BLUE, true, MessageType.STATUS);
            logger.debug(userName + " joined chat " + chatId);
        }
    }

    synchronized public void  kill(UUID userId, User.DisconnectReason reason) {
        if (userId != null && clients.containsKey(userId)) {            
            String userName = clients.get(userId);
            logger.debug(userName + " leaves chat: " + chatId);
            clients.remove(userId);
            String message;
            switch (reason) {
                case Disconnected:
                    message = " has left MAGE";
                    break;
                 case LostConnection:
                    message = " has lost connection";
                    break;
                 default:
                     message = " has left chat";
            }
            broadcast(null, new StringBuilder(userName).append(message).toString(), MessageColor.BLUE, true, MessageType.STATUS);
            logger.debug(userName + "left chat with reason " + message + " " + chatId);
        }
    }

    public boolean broadcastInfoToUser(User toUser, String message) {
        if (clients.containsKey(toUser.getId())) {
            toUser.fireCallback(new ClientCallback("chatMessage", chatId, new ChatMessage(null, message, timeFormatter.format(new Date()), MessageColor.ORANGE, MessageType.USER_INFO, null)));
            return true;
        }
        return false;
    }

    public boolean broadcastWhisperToUser(User fromUser, User toUser, String message) {
        if (clients.containsKey(toUser.getId())) {
            toUser.fireCallback(new ClientCallback("chatMessage", chatId, 
                    new ChatMessage(new StringBuilder("Whisper from ").append(fromUser.getName()).toString(), message, timeFormatter.format(new Date()), MessageColor.YELLOW, MessageType.WHISPER, SoundToPlay.PlayerWhispered)));
            if (clients.containsKey(fromUser.getId())) {
                fromUser.fireCallback(new ClientCallback("chatMessage", chatId,
                        new ChatMessage(new StringBuilder("Whisper to ").append(toUser.getName()).toString(), message, timeFormatter.format(new Date()), MessageColor.YELLOW, MessageType.WHISPER, null)));
                return true;
            }
        }
        return false;
    }

    public void broadcast(String userName, String message, MessageColor color) {
        this.broadcast(userName, message, color, true);
    }

    public void broadcast(String userName, String message, MessageColor color, boolean withTime) {
        this.broadcast(userName, message, color, withTime, MessageType.TALK);
    }

    public void broadcast(String userName, String message, MessageColor color, boolean withTime, MessageType messageType) {
        this.broadcast(userName, message, color, withTime, messageType, null);
    }

    public void broadcast(String userName, String message, MessageColor color, boolean withTime, MessageType messageType, SoundToPlay soundToPlay) {
        if (!message.isEmpty()) {            
            final String msg = message;
            final String time = (withTime ? timeFormatter.format(new Date()):"");
            final String username = userName;
            logger.trace("Broadcasting '" + msg + "' for " + chatId);
            for (UUID userId: clients.keySet()) {
                User user = UserManager.getInstance().getUser(userId);
                if (user != null) {
                    user.fireCallback(new ClientCallback("chatMessage", chatId, new ChatMessage(username, msg, time, color, messageType, soundToPlay)));
                }
                else {
                    kill(userId, User.DisconnectReason.CleaningUp);
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

}
