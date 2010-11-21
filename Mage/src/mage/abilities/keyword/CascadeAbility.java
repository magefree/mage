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

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CascadeAbility extends TriggeredAbilityImpl<CascadeAbility> {
	//20091005 - 702.82

	public CascadeAbility() {
		super(Zone.STACK, new CascadeEffect());
	}

	public CascadeAbility(CascadeAbility ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.SPELL_CAST && event.getTargetId().equals(this.getSourceId()) ) {
			trigger(game, event.getPlayerId());
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Cascade";
	}

	@Override
	public CascadeAbility copy() {
		return new CascadeAbility(this);
	}
}

class CascadeEffect extends OneShotEffect<CascadeEffect> {

	public CascadeEffect() {
		super(Outcome.PutCardInPlay);
	}

	public CascadeEffect(CascadeEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Card card;
		Player player = game.getPlayer(source.getControllerId());
		ExileZone exile = game.getExile().createZone(source.getSourceId(), player.getName() + " Cascade");
		int sourceCost = game.getObject(source.getSourceId()).getManaCost().convertedManaCost();
		do {
			card = player.getLibrary().removeFromTop(game);
			if (card == null)
				break;
			card.moveToExile(exile.getId(), exile.getName(), game);
		} while (card.getCardType().contains(CardType.LAND) || card.getManaCost().convertedManaCost() >= sourceCost);

		if (card != null) {
			if (player.chooseUse(outcome, "Use cascade effect on " + card.getName() + "?", game)) {
				player.cast(card.getSpellAbility(), game, true);
				exile.remove(card.getId());
			}
		}

		while (exile.size() > 0) {
			card = exile.getRandom(game);
			exile.remove(card.getId());
			card.moveToZone(Zone.LIBRARY, game, false);
		}

		return true;
	}

	@Override
	public CascadeEffect copy() {
		return new CascadeEffect(this);
	}

}