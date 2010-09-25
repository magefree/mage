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
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.effects.common.CantCounterControlledEffect;
import mage.abilities.effects.common.CantTargetControlledEffect;
import mage.cards.CardImpl;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AutumnsVeil extends CardImpl<AutumnsVeil> {

	private static FilterSpell filterTarget1 = new FilterSpell("spells you control");
	private static FilterCreaturePermanent filterTarget2 = FilterCreaturePermanent.getDefault();
	private static FilterObject filterSource = new FilterObject("blue or black spells");

	public AutumnsVeil(UUID ownerId) {
		super(ownerId, "Autumn's Veil", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{G}");
		this.expansionSetCode = "M11";
		this.color.setGreen(true);
		this.getSpellAbility().addEffect(new CantCounterControlledEffect(filterTarget1, filterSource, Duration.EndOfTurn));
		this.getSpellAbility().addEffect(new CantTargetControlledEffect(filterTarget2, filterSource, Duration.EndOfTurn));
	}

	public AutumnsVeil(final AutumnsVeil card) {
		super(card);
	}

	@Override
	public AutumnsVeil copy() {
		return new AutumnsVeil(this);
	}

	@Override
	public String getArt() {
		return "129140_typ_reg_sty_010.jpg";
	}

}

