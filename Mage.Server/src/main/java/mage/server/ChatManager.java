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
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChatManager {

    private static final Logger logger = Logger.getLogger(ChatManager.class);
    
    private static final ChatManager INSTANCE = new ChatManager();

    public static ChatManager getInstance() {
        return INSTANCE;
    }

    private ChatManager() {}

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
            logger.trace("Chat to join not found - chatId: " + chatId +" userId: " + userId);
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

    public void broadcast(UUID chatId, User user, String message, MessageColor color) {
        this.broadcast(chatId, user, message, color, true);
    }

    public void broadcast(UUID chatId, User user, String message, MessageColor color, boolean withTime) {
        this.broadcast(chatId, user, message, color, withTime, MessageType.TALK);
    }

    public void broadcast(UUID chatId, User user, String message, MessageColor color, boolean withTime, MessageType messageType) {
        this.broadcast(chatId, user, message, color, withTime, messageType, null);
    }

    public void broadcast(UUID chatId, User user, String message, MessageColor color, boolean withTime, MessageType messageType, SoundToPlay soundToPlay) {
        ChatSession chatSession = chatSessions.get(chatId);
        if (chatSession != null) {
            if (message.startsWith("\\") || message.startsWith("/")) {
                if (chatSession.performUserCommand(user, message, chatId)) {
                    return;
                }
            }
            chatSession.broadcast(user.getName(), message, color, withTime, messageType, soundToPlay);
        }
    }

    public void inform(UUID chatId, String message, MessageColor color, boolean withTime, MessageType messageType) {
        inform(chatId, message, color, withTime, messageType, null);
    }
    
    public void inform(UUID chatId, String message, MessageColor color, boolean withTime, MessageType messageType, SoundToPlay soundToPlay) {
        ChatSession chatSession = chatSessions.get(chatId);
        if (chatSession != null) {
            chatSession.broadcast("", message, color, withTime, messageType, soundToPlay);
        }
    }
    
    /**
     * 
     * use mainly for announcing that a user connection was lost or that a user has reconnected
     * 
     * @param user
     * @param message
     * @param color 
     */
    public void broadcast(User user, String message, MessageColor color) {
            for (ChatSession chat: chatSessions.values()) {
                if (chat.hasUser(user.getId())) {
                    chat.broadcast(user.getName(), message, color);
                }
            }
    }

    public void sendReconnectMessage(UUID userId) {
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            for (ChatSession chat: chatSessions.values()) {
                if (chat.hasUser(userId)) {
                    chat.broadcast(null, user.getName() + " has reconnected", MessageColor.BLUE, true, MessageType.STATUS);
                }            
            }        
        }
    }
    
    public void removeUser(UUID userId, DisconnectReason reason) {
        for (ChatSession chatSession: chatSessions.values()) {
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
