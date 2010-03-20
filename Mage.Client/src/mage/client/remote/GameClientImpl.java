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
import mage.interfaces.GameClient;
import mage.interfaces.MageException;
import mage.util.Logging;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.GameView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameClientImpl implements GameClient {

	private final static Logger logger = Logging.getLogger(GameClientImpl.class.getName());

	private UUID gameClientId;
	private GamePanel gamePanel;
	
	public GameClientImpl(GamePanel gamePanel) {
		gameClientId = UUID.randomUUID();
		this.gamePanel = gamePanel;
		try {
			UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public synchronized UUID getId() throws RemoteException {
		return gameClientId;
	}

	public UUID getIdLocal() {
		return gameClientId;
	}

	@Override
	public synchronized void update(GameView gameView) throws RemoteException {
		logger.info("Received update event for game");
		gamePanel.updateGame(gameView);
	}

	@Override
	public synchronized void init(GameView gameView) throws RemoteException {
		gamePanel.init(gameView);
	}

	@Override
	public synchronized void ask(String question, GameView gameView) throws RemoteException {
		gamePanel.updateGame(gameView);
		gamePanel.ask(question);
	}

	@Override
	public synchronized void inform(String message, GameView gameView) throws RemoteException {
		gamePanel.inform(message, null, gameView);
	}

	@Override
	public synchronized void target(String message, CardsView cardView, boolean required, GameView gameView) throws RemoteException {
		if (required) {
			gamePanel.inform(message, cardView, gameView);
		} else {
			gamePanel.cancel(message, cardView, gameView);
		}
	}

	@Override
	public synchronized void gameOver(String message) throws RemoteException {
		gamePanel.modalMessage(message);
		gamePanel.hideGame();
	}

	@Override
	public synchronized void select(String message, GameView gameView) throws RemoteException {
		gamePanel.select(message, gameView);
	}

	@Override
	public synchronized void playMana(String message, GameView gameView) throws RemoteException {
		gamePanel.playMana(message, gameView);
	}

	@Override
	public synchronized void playXMana(String message, GameView gameView) throws RemoteException {
		gamePanel.playXMana(message, gameView);
	}

	@Override
	public synchronized void chooseAbility(AbilityPickerView abilities) throws RemoteException {
		gamePanel.pickAbility(abilities);
	}

	public void handleException(Exception ex) throws MageException {
		logger.log(Level.SEVERE, "", ex);
		throw new MageException("Server error");
	}

	@Override
	public synchronized void revealCards(String name, CardsView cards) throws RemoteException {
		gamePanel.revealCards(name, cards);
	}

	@Override
	public synchronized void getAmount(int min, int max) throws RemoteException {
		gamePanel.getAmount(min, max);
	}

	@Override
	public synchronized void choose(String message, String[] choices) throws RemoteException {
		gamePanel.getChoice(message, choices);
	}

}
