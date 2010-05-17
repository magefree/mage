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

package mage.player.ai.simulators;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.ActivatedAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.GameState;
import mage.game.permanent.Permanent;
import mage.player.ai.ComputerPlayer;
import mage.player.ai.PermanentEvaluator;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ActionSimulator {

	private ComputerPlayer player;
	private List<Card> playableInstants = new ArrayList<Card>();
	private List<ActivatedAbility> playableAbilities = new ArrayList<ActivatedAbility>();

	private Game game;

	public ActionSimulator(ComputerPlayer player) {
		this.player = player;
	}

	public void simulate(Game game) {

	}

	public int evaluateState() {
		Player opponent = game.getPlayer(game.getOpponents(player.getId()).iterator().next());
		if (game.isGameOver()) {
			if (player.hasLost() || opponent.hasWon())
				return Integer.MIN_VALUE;
			if (opponent.hasLost() || player.hasWon())
				return Integer.MAX_VALUE;
		}
		int value = player.getLife();
		value -= opponent.getLife();
		PermanentEvaluator evaluator = new PermanentEvaluator();
		for (Permanent permanent: game.getBattlefield().getAllActivePermanents(player.getId())) {
			value += evaluator.evaluate(permanent, game);
		}
		for (Permanent permanent: game.getBattlefield().getAllActivePermanents(player.getId())) {
			value -= evaluator.evaluate(permanent, game);
		}
		value += player.getHand().size();
		value -= opponent.getHand().size();
		return value;
	}

}
