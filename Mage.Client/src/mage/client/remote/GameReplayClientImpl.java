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

package mage.client.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.client.game.GamePanel;
import mage.interfaces.GameReplayClient;
import mage.util.Logging;
import mage.view.GameView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameReplayClientImpl implements GameReplayClient {

	private final static Logger logger = Logging.getLogger(GameClientImpl.class.getName());

	private UUID replayClientId;
	private GamePanel gamePanel;

	public GameReplayClientImpl(GamePanel gamePanel) {
		replayClientId = UUID.randomUUID();
		this.gamePanel = gamePanel;
		try {
			UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public UUID getId() throws RemoteException {
		return replayClientId;
	}

	@Override
	public synchronized void update(GameView game) throws RemoteException {
		gamePanel.updateGame(game);
	}

	@Override
	public synchronized void init(GameView gameView) throws RemoteException {
		gamePanel.init(gameView);
	}

	@Override
	public synchronized void gameOver(String message) throws RemoteException {
		gamePanel.modalMessage(message);
		gamePanel.hideGame();
	}

	public synchronized void message(String message) throws RemoteException {
		gamePanel.replayMessage(message);
	}

}
