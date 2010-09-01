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

package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.MageInt;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterLandPermanent;
import mage.sets.Magic2010;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BogWraith extends CardImpl<BogWraith> {

	private static FilterLandPermanent filter = new FilterLandPermanent("Swamp");

	static {
		filter.getSubtype().add("Swamp");
		filter.setScopeSubtype(ComparisonScope.Any);
	}

	public BogWraith(UUID ownerId) {
		super(ownerId, "Bog Wraith", new CardType[]{CardType.CREATURE}, "{3}{B}");
		this.expansionSetId = Magic2010.getInstance().getId();
		this.color.setBlack(true);
		this.subtype.add("Wraith");
		this.power = new MageInt(3);
		this.toughness = new MageInt(3);
		this.addAbility(new LandwalkAbility(filter));
	}

	public BogWraith(final BogWraith card) {
		super(card);
	}

	@Override
	public BogWraith copy() {
		return new BogWraith(this);
	}

	@Override
	public String getArt() {
		return "106215_typ_reg_sty_010.jpg";
	}

}
