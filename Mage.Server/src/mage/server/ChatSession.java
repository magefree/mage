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

import mage.server.util.ThreadExecutor;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.interfaces.ChatClient;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChatSession {

	private static ExecutorService executor = ThreadExecutor.getInstance().getRMIExecutor();
	private final static Logger logger = Logging.getLogger(ChatSession.class.getName());
	private ConcurrentHashMap<UUID, ChatClient> clients = new ConcurrentHashMap<UUID, ChatClient>();
	private UUID chatId;
	private DateFormat timeFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);

	//TODO: use sessionId for chatting - prevents sending without being part of the chat

	public ChatSession() {
		chatId = UUID.randomUUID();
	}

	public void join(ChatClient client) {
		try {
			logger.log(Level.INFO, "joining chat " + chatId);
			clients.put(client.getId(), client);
			broadcast(client.getName(), " has joined");
		} catch (RemoteException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}

	public void leave(UUID clientId) {
		if (clients.contains(clientId)) {
			String clientName = "";
			try {
				clientName = clients.get(clientId).getName();
			} catch (RemoteException ex) {
				logger.log(Level.SEVERE, null, ex);
			}
			kill(clientId);
			broadcast(clientName, " has left");
		}
	}

	public void kill(UUID clientId) {
		if (clients.contains(clientId))
			clients.remove(clientId);
	}

	public void broadcast(final String userName, final String message) {
		Calendar cal = new GregorianCalendar();
		final String msg = timeFormatter.format(cal.getTime()) + " " + userName + ":" + message;
		for (final Entry<UUID, ChatClient> entry: clients.entrySet()) {
			executor.submit(
				new Runnable() {
					public void run() {
						try {
							entry.getValue().receiveMessage(msg);
						}
						catch (RemoteException ex) {
							logger.log(Level.WARNING, ex.getMessage());
							kill(entry.getKey());
						}
					}
				}
			);
		}
	}

	/**
	 * @return the chatId
	 */
	public UUID getChatId() {
		return chatId;
	}

}
