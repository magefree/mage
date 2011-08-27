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
package mage.sets.magic2012;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.PlayTheTopCardEffect;
import mage.abilities.effects.common.continious.PlayWithTheTopCardRevealedEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author nantuko
 */
public class GarruksHorde extends CardImpl<GarruksHorde> {

	public GarruksHorde(UUID ownerId) {
		super(ownerId, 176, "Garruk's Horde", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
		this.expansionSetCode = "M12";
		this.subtype.add("Beast");

		this.color.setGreen(true);
		this.power = new MageInt(7);
		this.toughness = new MageInt(7);

		this.addAbility(TrampleAbility.getInstance());
		// Play with the top card of your library revealed.
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
		// You may cast the top card of your library if it's a creature card.
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new PlayTheTopCardEffect(new FilterCreatureCard())));
	}

	public GarruksHorde(final GarruksHorde card) {
		super(card);
	}

	@Override
	public GarruksHorde copy() {
		return new GarruksHorde(this);
	}
}
