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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantCounterSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Combust extends CardImpl<Combust> {

	private static FilterCreaturePermanent filter = new FilterCreaturePermanent("white or blue creature");

	static {
		filter.getColor().setWhite(true);
		filter.getColor().setBlue(true);
		filter.setUseColor(true);
		filter.setScopeColor(ComparisonScope.Any);
	}

	public Combust(UUID ownerId) {
		super(ownerId, 130, "Combust", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
		this.expansionSetCode = "M11";
		this.color.setRed(true);
		this.getSpellAbility().addEffect(new DamageTargetEffect(5, false));
		this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
		this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantCounterSourceEffect()));
	}

	public Combust(final Combust card) {
		super(card);
	}

	@Override
	public Combust copy() {
		return new Combust(this);
	}

	@Override
	public String getArt() {
		return "129101_typ_reg_sty_010.jpg";
	}

}