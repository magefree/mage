/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PostResolveEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetDiscard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PsychicMiasma extends CardImpl<PsychicMiasma> {

	public PsychicMiasma(UUID ownerId) {
		super(ownerId, 76, "Psychic Miasma", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}");
		this.expansionSetCode = "SOM";
		this.color.setBlack(true);
		this.getSpellAbility().addTarget(new TargetPlayer());
		PsychicMiasmaEffect1 effect1 = new PsychicMiasmaEffect1();
		this.getSpellAbility().addEffect(effect1);
		this.getSpellAbility().addEffect(new PsychicMiasmaEffect2());
	}

	public PsychicMiasma(final PsychicMiasma card) {
		super(card);
	}

	@Override
	public PsychicMiasma copy() {
		return new PsychicMiasma(this);
	}

}

class PsychicMiasmaEffect1 extends OneShotEffect<PsychicMiasmaEffect1> {

	public PsychicMiasmaEffect1() {
		super(Outcome.Discard);
		staticText = "Target player discards a card";
	}
	
	public PsychicMiasmaEffect1(final PsychicMiasmaEffect1 effect) {
		super(effect);
	}
	
	@Override
	public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(source));
        if (player != null) {
			TargetDiscard target = new TargetDiscard(player.getId());
			player.choose(Outcome.Discard, target, source.getSourceId(), game);
			Card card = player.getHand().get(target.getFirstTarget(), game);
			if (card != null) {
				player.discard(card, source, game);
				game.getState().setValue(source.getId().toString(), card);
				return true;
			}
        }
        return false;
	}

	@Override
	public PsychicMiasmaEffect1 copy() {
		return new PsychicMiasmaEffect1(this);
	}
		
}

class PsychicMiasmaEffect2 extends PostResolveEffect<PsychicMiasmaEffect2> {

	public PsychicMiasmaEffect2() {
		staticText = "If a land card is discarded this way, return {this} to its owner's hand";
	}
	
	public PsychicMiasmaEffect2(final PsychicMiasmaEffect2 effect) {
		super(effect);
	}
	
	@Override
	public PsychicMiasmaEffect2 copy() {
		return new PsychicMiasmaEffect2(this);
	}

	@Override
	public void postResolve(Card card, Ability source, UUID controllerId, Game game) {
		Card discard = (Card) game.getState().getValue(source.getId().toString());
		if (discard != null && discard.getCardType().contains(CardType.LAND)) {
			card.moveToZone(Zone.HAND, source.getId(), game, false);
		}
		else {
			card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
		}
	}
	
}