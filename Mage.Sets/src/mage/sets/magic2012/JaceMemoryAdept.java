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
package mage.sets.magic2012;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;

/**
 * @author nantuko
 */
public class JaceMemoryAdept extends CardImpl<JaceMemoryAdept> {

	public JaceMemoryAdept(UUID ownerId) {
		super(ownerId, 58, "Jace, Memory Adept", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
		this.expansionSetCode = "M12";
		this.supertype.add("Jace");

		this.color.setBlue(true);

		this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4))));

		// +1: Draw a card. Target player puts the top card of his or her library into his or her graveyard.
		LoyaltyAbility ability1 = new LoyaltyAbility(new DrawCardControllerEffect(1), 1);
		ability1.addEffect(new PutLibraryIntoGraveTargetEffect(1));
		ability1.addTarget(new TargetPlayer());
		this.addAbility(ability1);

		// 0: Target player puts the top ten cards of his or her library into his or her graveyard.
		LoyaltyAbility ability2 = new LoyaltyAbility(new PutLibraryIntoGraveTargetEffect(10), 0);
		ability2.addTarget(new TargetPlayer());
		this.addAbility(ability2);

		// -7: Any number of target players each draw twenty cards.
		LoyaltyAbility ability3 = new LoyaltyAbility(new JaceMemoryAdeptEffect(20), -7);
		ability3.addTarget(new TargetPlayer(0)); //any number
		this.addAbility(ability3);
	}

	public JaceMemoryAdept(final JaceMemoryAdept card) {
		super(card);
	}

	@Override
	public JaceMemoryAdept copy() {
		return new JaceMemoryAdept(this);
	}
}

class JaceMemoryAdeptEffect extends DrawCardTargetEffect {

	public JaceMemoryAdeptEffect(int amount) {
		super(amount);
		staticText = "Any number of target players each draw twenty cards";
	}

	public JaceMemoryAdeptEffect(final DrawCardTargetEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		for (UUID target : targetPointer.getTargets(source)) {
			Player player = game.getPlayer(target);
			if (player != null) {
				player.drawCards(amount.calculate(game, source), game);
			}
		}
		return true;
	}

	@Override
	public String getText(Mode mode) {
		return staticText;
	}

	public JaceMemoryAdeptEffect copy() {
		return new JaceMemoryAdeptEffect(this);
	}
}


