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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CallbackServerSession {

//	private final ClientCallback callback = new ClientCallback();
//	private Boolean waiting = false;
//
//	/**
//	 *
//	 * blocks the thread until a callback is requested
//	 *
//	 * @return ClientCallback - the callback requested
//	 */
//	public ClientCallback callback() {
//		//TODO: use ReentrantLock instead
//		do {
//			callback.clear();
//			synchronized(callback) {
//				try {
//					waiting = true;
//					callback.wait();
//					waiting.notify();
//					waiting = false;
//				} catch (InterruptedException ex) {}
//			}
//		} while (callback.getMethod() == null);
//		return callback;
//	}
//
//	/**
//	 *
//	 * requests a callback
//	 *
//	 * @param call - the callback to request
//	 */
//	public void setCallback(ClientCallback call) {
//		//TODO: use ReentrantLock instead - wait until a lock is aquired by another thread before setting method and data
//		synchronized(waiting) {
//			if (!waiting) {
//				try {
//					waiting.wait();
//				} catch (InterruptedException ex) {}
//			}
//		}
//
//		synchronized(callback) {
//			callback.setMethod(call.getMethod());
//			callback.setData(call.getData());
//			callback.notify();
//		}
//	}



	private final static Logger logger = Logging.getLogger(CallbackServerSession.class.getName());

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
				logger.finer("waiting for callback");
				callbackCalled.await();
			}
			waitingForCallback = false;
			logger.finer("callback called:" + callback.getMethod());
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
				logger.finer("waiting for callback state to call:" + call.getMethod());
				waiting.await();
			}
			callback.setMethod(call.getMethod());
			callback.setData(call.getData());
			callbackCalled.signal();
		}
		finally {
			lock.unlock();
		}
	}

}
