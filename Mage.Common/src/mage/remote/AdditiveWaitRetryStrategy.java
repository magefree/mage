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

/**
 * Adopted from the code by William Grosso, author of Java RMI
 * 10/17/2001
 *
 * http://www.onjava.com/pub/a/onjava/2001/10/17/rmi.html
 * oreillynet.com Copyright &copy 2000 O'Reilly & Associates, Inc.
 *
 * @author BetaSteward_at_googlemail.com
 */

/**
	The most commonly used retry strategy; it extends the waiting
	period by a constant amount with each retry.

	Note that the default version of this (e.g. the one with a 
	zero argument constructor) will make 3 calls and wind up waiting
	approximately 11 seconds (zero wait for the first call, 3 seconds
	for the second call, and 8 seconds for the third call). These
	wait times are pretty small, and are usually dwarfed by socket
	timeouts when network difficulties occur anyway. 
*/

public class AdditiveWaitRetryStrategy extends RetryStrategy {
	public static final long STARTING_WAIT_TIME = 3000;
	public static final long WAIT_TIME_INCREMENT = 5000;

	private long currentTimeToWait;
	private long waitTimeIncrement;
	
	public AdditiveWaitRetryStrategy () { 
		this(DEFAULT_NUMBER_OF_RETRIES , STARTING_WAIT_TIME, WAIT_TIME_INCREMENT);
	}

	public AdditiveWaitRetryStrategy (int numberOfRetries, long startingWaitTime, long waitTimeIncrement) {
		super(numberOfRetries);
		this.currentTimeToWait = startingWaitTime;
		this.waitTimeIncrement = waitTimeIncrement;
	}

	@Override
	protected long getTimeToWait() {
		long returnValue = currentTimeToWait;
		currentTimeToWait += waitTimeIncrement;
		return returnValue;
	}
}