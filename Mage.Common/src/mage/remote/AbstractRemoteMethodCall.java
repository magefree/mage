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
import java.rmi.*;
import mage.MageException;
import mage.interfaces.Server;
import org.apache.log4j.Logger;

public abstract class AbstractRemoteMethodCall<T> {
	
	private final static Logger logger = Logger.getLogger(AbstractRemoteMethodCall.class);

	public T makeCall() throws ServerUnavailable, MageException {
		RetryStrategy strategy = getRetryStrategy();
		while (strategy.shouldRetry()) {
			Server server = getServer();
			if (null == server) {
				throw new ServerUnavailable();
			}
			try {
				return performRemoteCall(server);
			}
			catch (RemoteException remoteException) {
				try {
					remoteExceptionOccurred(remoteException);
					strategy.remoteExceptionOccurred();
				}
				catch (RetryException retryException) {
					handleRetryException(server);
				}
			}
		}		
		return null;
	}

/*
	The next 4 methods define the core behavior. Of these, two must
	be implemented by the subclass (and so are left abstract). The
	remaining three can be altered to provide customized retry handling.
*/

/**
	getRemoteObject is a template method which should, in most cases,
	return the stub.
*/

	protected abstract Server getServer() throws ServerUnavailable;

/**
	performRemoteCall is a template method which actually makes the remote
	method invocation.
*/
	protected abstract T performRemoteCall(Server server) throws RemoteException, MageException;


	protected RetryStrategy getRetryStrategy() {
		return new AdditiveWaitRetryStrategy();
	}

 	protected void remoteExceptionOccurred(RemoteException remoteException) {
		/* ignored in based class. */
	}

	protected void handleRetryException(Server server) throws ServerUnavailable {
		logger.fatal("Repeated attempts to communicate with " + server + " failed.");
		throw new ServerUnavailable();
	}
}