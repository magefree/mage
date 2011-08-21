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

package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class DispenseJustice extends CardImpl<DispenseJustice> {

    public DispenseJustice (UUID ownerId) {
        super(ownerId, 5, "Dispense Justice", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "SOM";
        
		this.color.setWhite(true);

        this.getSpellAbility().addEffect(new DispenseJusticeEffect());
		this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public DispenseJustice (final DispenseJustice card) {
        super(card);
    }

    @Override
    public DispenseJustice copy() {
        return new DispenseJustice(this);
    }

}

class DispenseJusticeEffect extends OneShotEffect<DispenseJusticeEffect> {

	private static final String effectText = "Target player sacrifices an attacking creature.\r\n\r\n"
			+ "Metalcraft - That player sacrifices two attacking creatures instead if you control three or more artifacts";

	private static final FilterCreaturePermanent filter;

	static {
		filter = new FilterCreaturePermanent();
		filter.setUseAttacking(true);
		filter.setAttacking(true);
	}

	DispenseJusticeEffect ( ) {
		super(Outcome.Sacrifice);
		staticText = effectText;
	}

	DispenseJusticeEffect ( DispenseJusticeEffect effect ) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		if ( MetalcraftCondition.getInstance().apply(game, source) ) {
			return new SacrificeEffect(filter, 2, effectText).apply(game, source);
		}
		else {
			return new SacrificeEffect(filter, 1, effectText).apply(game, source);
		}
	}

	@Override
	public DispenseJusticeEffect copy() {
		return new DispenseJusticeEffect(this);
	}

}
