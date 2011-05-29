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

package mage.sets.tenth;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Flashfreeze extends CardImpl<Flashfreeze> {

	private static final FilterSpell filter = new FilterSpell("red or green spell");

	static {
		filter.getColor().setRed(true);
		filter.getColor().setGreen(true);
		filter.setScopeColor(ComparisonScope.Any);
		filter.setUseColor(true);
	}

	public Flashfreeze(UUID ownerId) {
		super(ownerId, 84, "Flashfreeze", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
		this.expansionSetCode = "10E";
		this.color.setBlue(true);
		this.getSpellAbility().addTarget(new TargetSpell(filter));
		this.getSpellAbility().addEffect(new CounterTargetEffect());
	}

	public Flashfreeze(final Flashfreeze card) {
		super(card);
	}

	@Override
	public Flashfreeze copy() {
		return new Flashfreeze(this);
	}

}
