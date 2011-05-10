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

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CallbackServerSession {

	private final static Logger logger = Logger.getLogger(CallbackServerSession.class);

	private final ClientCallback callback = new ClientCallback();
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition waiting  = lock.newCondition();
	private final Condition callbackCalled  = lock.newCondition();
	private boolean waitingForCallback;

	/**
	 *
	 * blocks the thread until a callback is requested
	 *
	 * @return ClientCallback - the callback requested
	 */
	public ClientCallback callback() throws InterruptedException {
		callback.clear();
		lock.lock();
		try {
			waitingForCallback = true;
			waiting.signal();
			while (callback.getMethod() == null) {
				logger.trace("waiting for callback");
				callbackCalled.await();
			}
			waitingForCallback = false;
			logger.trace("callback called:" + callback.getMethod());
			return callback;
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 *
	 * requests a callback
	 *
	 * @param call - the callback to request
	 */
	public void setCallback(ClientCallback call) throws InterruptedException {
		lock.lock();
		try {
			while (!waitingForCallback) {
				logger.trace("waiting for callback state to call:" + call.getMethod());
				waiting.await();
			}
			callback.setMethod(call.getMethod());
			callback.setData(call.getData());
			callback.setObjectId(call.getObjectId());
			callback.setMessageId(call.getMessageId());
			callbackCalled.signal();
		}
		finally {
			lock.unlock();
		}
	}

}
