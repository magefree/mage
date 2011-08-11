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
package mage.server.util;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Handles server messages (Messages of the Day).
 * Reloads messages every 5 minutes.
 *
 * @author nantuko
 */
public class ServerMessagesUtil {

	private static final ServerMessagesUtil instance = new ServerMessagesUtil();

	private static final Logger log = Logger.getLogger(ServerMessagesUtil.class);
	static final String SERVER_MSG_TXT_FILE = "/server.msg.txt";

	private List<String> messages = new ArrayList<String>();
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public ServerMessagesUtil() {
		timer.setInitialDelay(5000);
		timer.start();
	}

	public static ServerMessagesUtil getInstance() {
		return instance;
	}

	public List<String> getMessages() {
		lock.readLock().lock();
		try {
			return messages;
		} finally {
			lock.readLock().unlock();
		}
	}

	private void reloadMessages() {
		log.info("Reading server messages...");
		List<String> newMessages = readFromFile();
		if (newMessages != null && !newMessages.isEmpty()) {
			lock.writeLock().lock();
			try {
				messages.clear();
				messages.addAll(newMessages);
			} finally {
				lock.writeLock().unlock();
			}
		}
	}

	private List<String> readFromFile() {
		InputStream is = ServerMessagesUtil.class.getResourceAsStream(SERVER_MSG_TXT_FILE);
		if (is == null) {
			log.info("Couldn't find server.msg");
			return null;
		}
		Scanner scanner = new Scanner(is);
		List<String> messages = new ArrayList<String>();
		while (scanner.hasNextLine()) {
			String message = scanner.nextLine();
			if (!message.trim().isEmpty()) {
				messages.add(message.trim());
			}
		}
		return messages;
	}

	private Timer timer = new Timer(1000 * 60 * 5, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
			reloadMessages();
        }
    });
}
