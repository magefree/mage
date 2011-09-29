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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

/**
 *
 * @author BetaSteward
 */
public class ParallelLives extends CardImpl<ParallelLives> {

    public ParallelLives(UUID ownerId) {
        super(ownerId, 199, "Parallel Lives", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // If an effect would put one or more tokens onto the battlefield under your control, it puts twice that many of those tokens onto the battlefield instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ParallelLivesEffect()));
    }

    public ParallelLives(final ParallelLives card) {
        super(card);
    }

    @Override
    public ParallelLives copy() {
        return new ParallelLives(this);
    }
}

class ParallelLivesEffect extends ReplacementEffectImpl<ParallelLivesEffect> {

	public ParallelLivesEffect() {
		super(Duration.WhileOnBattlefield, Outcome.Copy);
		staticText = "If an effect would put one or more tokens onto the battlefield under your control, it puts twice that many of those tokens onto the battlefield instead";
	}

	public ParallelLivesEffect(final ParallelLivesEffect effect) {
		super(effect);
	}

	@Override
	public ParallelLivesEffect copy() {
		return new ParallelLivesEffect(this);
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		switch (event.getType()) {
			case CREATE_TOKEN:
				StackObject spell = game.getStack().getStackObject(event.getSourceId());
				if (spell != null && spell.getControllerId().equals(source.getControllerId())) {
					event.setAmount(event.getAmount() * 2);
				}
		}
		return false;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return apply(game, source);
	}

}