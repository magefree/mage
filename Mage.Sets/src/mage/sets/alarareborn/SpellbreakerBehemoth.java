/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantCounterControlledEffect;
import mage.abilities.effects.common.CantCounterSourceEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpellbreakerBehemoth extends CardImpl<SpellbreakerBehemoth> {

	private static FilterSpell filter = new FilterSpell("Creature spells you control with power 5 or greater");

	static {
		filter.getCardType().add(CardType.CREATURE);
		filter.setPower(5);
	}

	public SpellbreakerBehemoth(UUID ownerId) {
		super(ownerId, "Spellbreaker Behemoth", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{G}");
		this.expansionSetCode = "ARB";
		this.color.setRed(true);
		this.color.setGreen(true);
		this.subtype.add("Beast");
		this.power = new MageInt(5);
		this.toughness = new MageInt(5);

		this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantCounterSourceEffect()));
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantCounterControlledEffect(filter, Duration.WhileOnBattlefield)));
	}

	public SpellbreakerBehemoth(final SpellbreakerBehemoth card) {
		super(card);
	}

	@Override
	public SpellbreakerBehemoth copy() {
		return new SpellbreakerBehemoth(this);
	}

	@Override
	public String getArt() {
		return "120966_typ_reg_sty_010.jpg";
	}
	
}
