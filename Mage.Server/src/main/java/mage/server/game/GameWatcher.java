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

package mage.server.game;

import java.rmi.RemoteException;
import java.util.UUID;
import mage.interfaces.callback.ClientCallback;
import mage.server.User;
import mage.server.UserManager;
import mage.view.GameClientMessage;
import mage.view.GameView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameWatcher {

	protected final static Logger logger = Logger.getLogger(GameWatcher.class);

	protected UUID userId;
	protected UUID gameId;
	protected boolean killed = false;

	public GameWatcher(UUID userId, UUID gameId) {
		this.userId = userId;
		this.gameId = gameId;
	}

	public boolean init(final GameView gameView) {
		if (!killed) {
			User user = UserManager.getInstance().getUser(userId);
			if (user != null) {
				user.fireCallback(new ClientCallback("gameInit", gameId, gameView));
				return true;
			}
		}
		return false;
	}

	public void update(final GameView gameView) {
		if (!killed) {
			User user = UserManager.getInstance().getUser(userId);
			if (user != null) {
				user.fireCallback(new ClientCallback("gameUpdate", gameId, gameView));
			}
		}
	}

	public void inform(final String message, final GameView gameView) {
		if (!killed) {
			User user = UserManager.getInstance().getUser(userId);
			if (user != null) {
				user.fireCallback(new ClientCallback("gameInform", gameId, new GameClientMessage(gameView, message)));
			}
		}
	}

	public void gameOver(final String message) {
		if (!killed) {
			User user = UserManager.getInstance().getUser(userId);
			if (user != null) {
				user.fireCallback(new ClientCallback("gameOver", gameId, message));
			}
		}
	}

	public void gameError(final String message) {
		if (!killed) {
			User user = UserManager.getInstance().getUser(userId);
			if (user != null) {
				user.fireCallback(new ClientCallback("gameError", gameId, message));
			}
		}
	}
	
	protected void handleRemoteException(RemoteException ex) {
		logger.fatal("GameWatcher error", ex);
		GameManager.getInstance().kill(gameId, userId);
	}
	
	public void setKilled() {
		killed = true;
	}

}
