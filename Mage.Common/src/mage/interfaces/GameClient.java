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

package mage.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.GameView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface GameClient extends Remote {

	public UUID getId() throws RemoteException;
	public void update(GameView gameView) throws RemoteException;
	public void init(GameView gameView) throws RemoteException;
	public void ask(String question, GameView gameView) throws RemoteException;
	public void inform(String message, GameView gameView) throws RemoteException;
	public void target(String message, CardsView cardView, boolean required, GameView gameView) throws RemoteException;
	public void gameOver(String message) throws RemoteException;
	public void select(String message, GameView gameView) throws RemoteException;
	public void playMana(String message, GameView gameView) throws RemoteException;
	public void playXMana(String message, GameView gameView) throws RemoteException;
	public void chooseAbility(AbilityPickerView abilityView) throws RemoteException;
	public void choose(String message, String[] choices) throws RemoteException;
	public void revealCards(String name, CardsView cards) throws RemoteException;
	public void getAmount(int min, int max) throws RemoteException;
}
