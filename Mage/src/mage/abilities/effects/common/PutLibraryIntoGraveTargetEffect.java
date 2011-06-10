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

package mage.abilities.effects.common;

import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PutLibraryIntoGraveTargetEffect extends OneShotEffect<PutLibraryIntoGraveTargetEffect> {

	private DynamicValue amount;

    public PutLibraryIntoGraveTargetEffect(int amount) {
        this(new StaticValue(amount));
    }

	public PutLibraryIntoGraveTargetEffect(DynamicValue amount) {
		super(Outcome.Detriment);
		this.amount = amount;
	}

	public PutLibraryIntoGraveTargetEffect(final PutLibraryIntoGraveTargetEffect effect) {
		super(effect);
		this.amount = effect.amount.clone();
	}

	@Override
	public PutLibraryIntoGraveTargetEffect copy() {
		return new PutLibraryIntoGraveTargetEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getFirstTarget());
		if (player != null) {
			// putting cards to grave shouldn't end the game, so getting minimun available
			int cardsCount = Math.min(amount.calculate(game, source), player.getLibrary().size());
			for (int i = 0; i < cardsCount; i++) {
				Card card = player.getLibrary().removeFromTop(game);
				if (card != null)
					card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
				else
					break;
			}
		}
		return true;
	}

	@Override
	public String getText(Ability source) {
		StringBuilder sb = new StringBuilder();
		sb.append("Target ").append(source.getTargets().get(0).getTargetName());
		sb.append(" puts the top ");
		if (amount instanceof StaticValue && amount.calculate(null, null) == 1)
			sb.append(amount).append(" card ");
		else
			sb.append(amount).append(" cards ");
		sb.append("of his or her library into his or her graveyard");
        String message = amount.getMessage();
        if (message.length() > 0) {
            sb.append(" for each ");
        }
        sb.append(message);
		return sb.toString();
	}

}
