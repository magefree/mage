/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.remote;

import org.apache.log4j.Logger;

/**
 * Adopted from the code by William Grosso, author of Java RMI
 * 10/17/2001
 *
 * http://www.onjava.com/pub/a/onjava/2001/10/17/rmi.html
 * oreillynet.com Copyright &copy 2000 O'Reilly & Associates, Inc.
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class RetryStrategy {
	public static final int DEFAULT_NUMBER_OF_RETRIES = 3;
	private int _numberOfTriesLeft;
	private final static Logger logger = Logger.getLogger(RetryStrategy.class);

	public RetryStrategy() {
		this(DEFAULT_NUMBER_OF_RETRIES);
	}

	public RetryStrategy(int numberOfRetries){
		_numberOfTriesLeft = numberOfRetries;
	}

	public boolean shouldRetry() {
		return (0 < _numberOfTriesLeft);
	}

	public void remoteExceptionOccurred() throws RetryException {
		_numberOfTriesLeft --;
		if (!shouldRetry()) {
			throw new RetryException();
		}
		waitUntilNextTry();
	}

	protected abstract long getTimeToWait();

	private void waitUntilNextTry() {
		long timeToWait = getTimeToWait();
		logger.warn("Error calling server, waiting " + timeToWait + "ms before retrying");
		try {
			Thread.sleep(timeToWait );
		} 
		catch (InterruptedException ignored) {}
	}
}