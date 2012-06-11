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
package mage.sets.zendikar;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author nantuko
 */
public class SorinMarkov extends CardImpl<SorinMarkov> {

    public SorinMarkov(UUID ownerId) {
        super(ownerId, 111, "Sorin Markov", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}{B}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Sorin");

        this.color.setBlack(true);

		this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4))));

        // +2: Sorin Markov deals 2 damage to target creature or player and you gain 2 life.
		LoyaltyAbility ability1 = new LoyaltyAbility(new DamageTargetEffect(2), 2);
		ability1.addEffect(new GainLifeEffect(2));
		ability1.addTarget(new TargetCreatureOrPlayer());
		this.addAbility(ability1);

        // -3: Target opponent's life total becomes 10.
		LoyaltyAbility ability2 = new LoyaltyAbility(new SorinMarkovEffect(), -3);
		ability2.addTarget(new TargetOpponent());
		this.addAbility(ability2);

        // -7: You control target player during that player's next turn.
		LoyaltyAbility ability3 = new LoyaltyAbility(new ControlTargetPlayerNextTurnEffect(), -7);
		ability3.addTarget(new TargetPlayer());
		this.addAbility(ability3);
    }

    public SorinMarkov(final SorinMarkov card) {
        super(card);
    }

    @Override
    public SorinMarkov copy() {
        return new SorinMarkov(this);
    }
}

class SorinMarkovEffect extends OneShotEffect<SorinMarkovEffect> {

	public SorinMarkovEffect() {
		super(Constants.Outcome.Benefit);
		staticText = "Target opponent's life total becomes 10";
	}

	public SorinMarkovEffect(SorinMarkovEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(targetPointer.getFirst(game, source));
		if (player != null) {
			player.setLife(10, game);
			return true;
		}
		return false;
	}

	@Override
	public SorinMarkovEffect copy() {
		return new SorinMarkovEffect(this);
	}
}
