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

import mage.remote.DisconnectReason;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
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
    private final Date createTime;
    private final String info;
    private final DateFormat timeFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);
    private final HashSet<UUID> clientsToRemove = new HashSet<>();

    public ChatSession(String info) {
        chatId = UUID.randomUUID();
        this.createTime = new Date();
        this.info = info;
    }

    public void join(UUID userId) {
        User user = UserManager.getInstance().getUser(userId);
        if (user != null && !clients.containsKey(userId)) {
            String userName = user.getName();
            clients.put(userId, userName);
            broadcast(null, userName + " has joined", MessageColor.BLUE, true, MessageType.STATUS);
            logger.trace(userName + " joined chat " + chatId);
        }
    }

    public void kill(UUID userId, DisconnectReason reason) {

        try {            
            if (reason == null) {
                logger.fatal("User kill without disconnect reason  userId: " + userId);
                reason = DisconnectReason.Undefined;
            }
            if (reason != null && userId != null && clients.containsKey(userId)) {
                String userName = clients.get(userId);
                if (!reason.equals(DisconnectReason.LostConnection)) { // for lost connection the user will be reconnected or session expire so no remove of chat yet                    
                    clients.remove(userId);
                    logger.debug(userName + "(" + reason.toString() + ")" + " removed from chatId " + chatId);                    
                }
                String message;
                switch (reason) {
                    case Disconnected:
                        message = " has left XMage";
                        break;
                    case LostConnection:
                        message = " has lost connection";
                        break;
                    case SessionExpired:
                        message = " session expired";
                        break;
                    case AdminDisconnect:
                        message = " was disconnected by the Admin";
                        break;
                    case ConnectingOtherInstance:
                        message = " reconnected and replaced still active old session";
                        break;
                    case CleaningUp:
                        message = null;
                        break;
                    default:
                        message = " left (" + reason.toString() + ")";
                }
                if (message != null) {
                    broadcast(null, userName + message, MessageColor.BLUE, true, MessageType.STATUS);
                }
            }
        } catch (Exception ex) {
            logger.fatal("exception: " + ex.toString());
        }
    }

    public boolean broadcastInfoToUser(User toUser, String message) {
        if (clients.containsKey(toUser.getId())) {
            toUser.chatMessage(chatId, new ChatMessage(null, message, timeFormatter.format(new Date()), MessageColor.BLUE, MessageType.USER_INFO, null));
            return true;
        }
        return false;
    }

    public boolean broadcastWhisperToUser(User fromUser, User toUser, String message) {
        if (clients.containsKey(toUser.getId())) {
            toUser.chatMessage(chatId, 
                    new ChatMessage(new StringBuilder("Whisper from ").append(fromUser.getName()).toString(), message, timeFormatter.format(new Date()), MessageColor.YELLOW, MessageType.WHISPER, SoundToPlay.PlayerWhispered));
            if (clients.containsKey(fromUser.getId())) {
                fromUser.chatMessage(chatId,
                        new ChatMessage(new StringBuilder("Whisper to ").append(toUser.getName()).toString(), message, timeFormatter.format(new Date()), MessageColor.YELLOW, MessageType.WHISPER, null));
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
            boolean remove = false;
            final String time = (withTime ? timeFormatter.format(new Date()):"");
            logger.trace("Broadcasting '" + message + "' for " + chatId);
            for (UUID userId: clients.keySet()) {
                User chatUser = UserManager.getInstance().getUser(userId);                
                if (chatUser != null) {
                    ServerMain.getInstance().sendChatMessage(chatUser.getSessionId(), chatId, new ChatMessage(userName, message, time, color, messageType, soundToPlay));
//                    user.fireCallback(new ClientCallback("chatMessage", chatId, new ChatMessage(username, msg, time, color, messageType, soundToPlay)));
                }
                else {
                    logger.error("User not found but connected to chat - userId: " + userId + "  chatId: " + chatId);
                    clientsToRemove.add(userId);
                    remove = true;
                }
            }
            if (remove) {
                for (UUID userIdToRemove: clientsToRemove) {
                    clients.remove(userIdToRemove);
                }
                clientsToRemove.clear();
            }
        }
    }

    public boolean performUserCommand(User user, String message, UUID chatId) {
        String command = message.substring(1).trim().toUpperCase(Locale.ENGLISH);
        if (command.equals("I") || command.equals("INFO")) {            
            user.setInfo("");
            broadcastInfoToUser(user,message);
            return true;
        }
        if (command.startsWith("I ") || command.startsWith("INFO ")) {
            user.setInfo(message.substring(command.startsWith("I ") ? 3 : 6));
            broadcastInfoToUser(user,message);
            return true;
        }
        if (command.startsWith("W ") || command.startsWith("WHISPER ")) {
            String rest = message.substring(command.startsWith("W ")? 3 : 9);
            int first = rest.indexOf(" ");
            if (first > 1) {
                String userToName = rest.substring(0,first);
                rest = rest.substring(first + 1).trim();
                User userTo = UserManager.getInstance().findUser(userToName);
                if (userTo != null) {
                    if (!broadcastWhisperToUser(user, userTo, rest)) {
                        message += new StringBuilder("<br/>User ").append(userToName).append(" not found").toString();
                        broadcastInfoToUser(user,message);
                    }
                } else {
                    message += new StringBuilder("<br/>User ").append(userToName).append(" not found").toString();
                    broadcastInfoToUser(user,message);
                }
                return true;
            }
        }
        if (command.equals("L") || command.equals("LIST")) {
            message += new StringBuilder("<br/>List of commands:")
                    .append("<br/>\\info [text] - set a info text to your player")
                    .append("<br/>\\list - Show a list of commands")
                    .append("<br/>\\whisper [player name] [text] - whisper to the player with the given name").toString();
            broadcastInfoToUser(user,message);
            return true;
        }
        return false;
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
