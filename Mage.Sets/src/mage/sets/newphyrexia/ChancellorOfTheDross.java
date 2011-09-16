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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward
 */
public class ChancellorOfTheDross extends CardImpl<ChancellorOfTheDross> {

    private static String abilityText = "at the beginning of the first upkeep, each opponent loses 3 life, then you gain life equal to the life lost this way";
    
    public ChancellorOfTheDross(UUID ownerId) {
        super(ownerId, 54, "Chancellor of the Dross", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Vampire");

        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, each opponent loses 3 life, then you gain life equal to the life lost this way.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheDrossDelayedTriggeredAbility(), abilityText));
        
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }

    public ChancellorOfTheDross(final ChancellorOfTheDross card) {
        super(card);
    }

    @Override
    public ChancellorOfTheDross copy() {
        return new ChancellorOfTheDross(this);
    }
}

class ChancellorOfTheDrossDelayedTriggeredAbility extends DelayedTriggeredAbility<ChancellorOfTheDrossDelayedTriggeredAbility> {

	ChancellorOfTheDrossDelayedTriggeredAbility () {
		super(new ChancellorOfTheDrossEffect());
	}

	ChancellorOfTheDrossDelayedTriggeredAbility(ChancellorOfTheDrossDelayedTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
			return true;
		}
		return false;
	}
	@Override
	public ChancellorOfTheDrossDelayedTriggeredAbility copy() {
		return new ChancellorOfTheDrossDelayedTriggeredAbility(this);
	}
}

class ChancellorOfTheDrossEffect extends OneShotEffect<ChancellorOfTheDrossEffect> {

	ChancellorOfTheDrossEffect () {
		super(Outcome.Benefit);
		staticText = "each opponent loses 3 life, then you gain life equal to the life lost this way";
	}

	ChancellorOfTheDrossEffect(ChancellorOfTheDrossEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
        int loseLife = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            loseLife += game.getPlayer(opponentId).loseLife(3, game);
        }
        if (loseLife > 0)
            game.getPlayer(source.getControllerId()).gainLife(loseLife, game);
        return true;
	}

	@Override
	public ChancellorOfTheDrossEffect copy() {
		return new ChancellorOfTheDrossEffect(this);
	}

}
