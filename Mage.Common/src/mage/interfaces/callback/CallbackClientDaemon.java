/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.interfaces.callback;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mage.remote.Connection;
import mage.remote.method.Ack;
import mage.remote.method.Callback;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CallbackClientDaemon extends Thread {

	private final static Logger logger = Logger.getLogger(CallbackClientDaemon.class);

	private static ExecutorService callbackExecutor = Executors.newCachedThreadPool();
	private final CallbackClient client;
	private final Connection connection;
	private final UUID id;
	private boolean end = false;

	public CallbackClientDaemon(UUID id, CallbackClient client, Connection connection) {
		this.client = client;
		this.connection = connection;
		this.id = id;
		setDaemon(true);
		start();
	}

	@Override
	public void run() {
		try {
	        while(!end) {
				try {
					Callback callbackMethod = new Callback(connection, id);
					final ClientCallback callback = callbackMethod.makeDirectCall();
					Ack ackMethod = new Ack(connection, id, callback.getMessageId());
					ackMethod.makeCall();
					callbackExecutor.submit(
						new Runnable() {
							@Override
							public void run() {
								try {
									client.processCallback(callback);
								}
								catch (Exception ex) {
									logger.fatal("CallbackClientDaemon error ", ex);
								}
							}
						}
					);
				} catch (CallbackException ex) {
					logger.fatal("Callback failed ", ex);
				}
			}
		} catch(Exception ex) {
			logger.fatal("CallbackClientDaemon error ", ex);
			stopDaemon();
		}
	}
	
	public void stopDaemon() {
		end = true;
		callbackExecutor.shutdown();
	}

}
