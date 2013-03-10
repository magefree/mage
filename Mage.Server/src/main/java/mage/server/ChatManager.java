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

import mage.view.ChatMessage.MessageColor;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChatManager {

    private static final ChatManager INSTANCE = new ChatManager();

    public static ChatManager getInstance() {
        return INSTANCE;
    }

    private ChatManager() {}

    private ConcurrentHashMap<UUID, ChatSession> chatSessions = new ConcurrentHashMap<UUID, ChatSession>();

    public UUID createChatSession() {
        ChatSession chatSession = new ChatSession();
        chatSessions.put(chatSession.getChatId(), chatSession);
        return chatSession.getChatId();
    }

    public void joinChat(UUID chatId, UUID userId) {
        chatSessions.get(chatId).join(userId);
    }

    public void leaveChat(UUID chatId, UUID userId) {
        chatSessions.get(chatId).kill(userId);
    }

    public void destroyChatSession(UUID chatId) {
        chatSessions.remove(chatId);
    }

    public void broadcast(UUID chatId, String userName, String message, MessageColor color) {
        chatSessions.get(chatId).broadcast(userName, message, color);
    }

    /**
     * 
     * use mainly for announcing that a user connection was lost or that a user has reconnected
     * 
     * @param userId
     * @param message
     * @param color 
     */
    public void broadcast(UUID userId, String message, MessageColor color) {
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            for (ChatSession chat: chatSessions.values()) {
                if (chat.hasUser(userId))
                    chat.broadcast(user.getName(), message, color);
            }
        }
    }

    public void removeUser(UUID userId) {
        for (ChatSession chat: chatSessions.values()) {
            chat.kill(userId);
        }
    }
}
