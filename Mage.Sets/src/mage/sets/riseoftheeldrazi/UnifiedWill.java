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

package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class UnifiedWill extends CardImpl<UnifiedWill> {

	public UnifiedWill(UUID ownerId) {
		super(ownerId, 92, "Unified Will", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
		this.expansionSetCode = "ROE";
		this.color.setBlue(true);
		this.getSpellAbility().addTarget(new TargetSpell());
		this.getSpellAbility().addEffect(new UnifiedWillEffect());
	}

	public UnifiedWill(final UnifiedWill card) {
		super(card);
	}

	@Override
	public UnifiedWill copy() {
		return new UnifiedWill(this);
	}

}

class UnifiedWillEffect extends CounterTargetEffect {

	private static FilterCreaturePermanent filter = FilterCreaturePermanent.getDefault();

	public UnifiedWillEffect() {}

	public UnifiedWillEffect(final UnifiedWillEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
		if (stackObject != null) {
			if (game.getBattlefield().countAll(filter, source.getControllerId()) > game.getBattlefield().countAll(filter, stackObject.getControllerId())) {
				return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
			}
			return true;
		}
		return false;
	}

	@Override
	public UnifiedWillEffect copy() {
		return new UnifiedWillEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "Counter target spell if you control more creatures than that spell's controller";
	}

}