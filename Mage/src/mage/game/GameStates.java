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

package mage.game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameStates implements Serializable {

	private final static transient Logger logger = Logger.getLogger(GameStates.class);

//	private List<byte[]> states = new LinkedList<byte[]>();
	private List<GameState> states = new LinkedList<GameState>();

	public void save(GameState gameState) {
//		states.add(new Copier<GameState>().copyCompressed(gameState));
		states.add(gameState.copy());
		logger.debug("Saved game state: " + states.size());
	}

	public int getSize() {
		return states.size();
	}

	public GameState rollback(int index) {
		if (states.size() > 0 && index < states.size()) {
			while (states.size() > index + 1) {
				states.remove(states.size() - 1);
			}
//			return new Copier<GameState>().uncompressCopy(states.get(index));
			logger.debug("Rolling back state: " + index);
			return states.get(index);
		}
		return null;
	}

	public GameState get(int index) {
		if (index < states.size())
//			return new Copier<GameState>().uncompressCopy(states.get(index));
			return states.get(index);
		return null;
	}

}
