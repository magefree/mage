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

package mage.abilities.effects.common;

import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.ExileZone;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromExileForSourceEffect extends OneShotEffect<ReturnFromExileForSourceEffect> {

	private Zone zone;
	private boolean tapped;

	public ReturnFromExileForSourceEffect(Zone zone) {
		this(zone, false);
	}

	public ReturnFromExileForSourceEffect(Zone zone, boolean tapped) {
		super(Outcome.PutCardInPlay);
		this.zone = zone;
		this.tapped = tapped;
		setText();
	}

	public ReturnFromExileForSourceEffect(final ReturnFromExileForSourceEffect effect) {
		super(effect);
		this.zone = effect.zone;
		this.tapped = effect.tapped;
	}

	@Override
	public ReturnFromExileForSourceEffect copy() {
		return new ReturnFromExileForSourceEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		UUID exileId = source.getSourceId();
		ExileZone exile = game.getExile().getExileZone(exileId);
		if (exile != null) {
			for (UUID cardId: exile) {
				Card card = game.getCard(cardId);
				card.moveToZone(zone, source.getId(), game, tapped);
			}
			exile.clear();
			return true;
		}
		return false;
	}

	private void setText() {
		StringBuilder sb = new StringBuilder();
		sb.append("return the exiled cards ");
		switch(zone) {
			case BATTLEFIELD:
				sb.append("to the battlefield under its owner's control");
				if (tapped)
					sb.append(" tapped");
				break;
			case HAND:
				sb.append("to their owner's hand");
				break;
			case GRAVEYARD:
				sb.append("to their owner's graveyard");
				break;
		}
		staticText = sb.toString();
	}

}
