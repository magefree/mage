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

package mage.abilities.keyword;

import mage.Constants.Zone;
import mage.abilities.Mode;
import mage.abilities.StaticAbility;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class KickerAbility extends StaticAbility<KickerAbility> {

	protected boolean kicked = false;
	protected boolean replaces = false;

	public KickerAbility(Effect effect, boolean replaces) {
		super(Zone.STACK, effect);
		this.replaces = replaces;
	}

	public KickerAbility(final KickerAbility ability) {
		super(ability);
		this.kicked = ability.kicked;
		this.replaces = ability.replaces;
	}

	@Override
	public KickerAbility copy() {
		return new KickerAbility(this);
	}

	@Override
	public boolean activate(Game game, boolean noMana) {
		Player player = game.getPlayer(this.getControllerId());

		String message = "Use kicker - " + getRule() + "?";
		Card card = game.getCard(sourceId);
		// replace by card name or just plain "this"
		String text = card == null ? "this" : card.getName();
		message = message.replace("{this}", text).replace("{source}", text);
		if (player.chooseUse(getEffects().get(0).getOutcome(), message, game)) {
			int bookmark = game.bookmarkState();
			if (super.activate(game, noMana)) {
				game.removeBookmark(bookmark);
				kicked = true;
			}
			else {
				game.restoreState(bookmark);
				kicked = false;
			}
			return kicked;
		}
		return false;
	}

	public boolean isKicked() {
		return kicked;
	}

	public boolean isReplaces() {
		return replaces;
	}

	@Override
	public String getRule() {
		StringBuilder sb = new StringBuilder();
		sb.append("Kicker");
		if (manaCosts.size() > 0) {
			sb.append(manaCosts.getText());
			if (costs.size() > 0)
				sb.append(",");
		}
		if (costs.size() > 0)
			sb.append(costs.getText());
		sb.append(":").append(modes.getText());
		if (replaces)
			sb.append(" instead");
		return sb.toString();
	}

}
