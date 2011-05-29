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

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.UnblockableAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AetherFigment extends CardImpl<AetherFigment> {

	public AetherFigment(UUID ownerId) {
		super(ownerId, 40, "AEther Figment", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
		this.expansionSetCode = "ZEN";
		this.subtype.add("Illusion");
		this.color.setBlue(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);

		this.addAbility(UnblockableAbility.getInstance());
		Ability ability1 = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
		KickerAbility ability2 = new KickerAbility(new GainAbilitySourceEffect(ability1, Duration.WhileOnBattlefield), false);
		ability2.addManaCost(new GenericManaCost(3));
		this.addAbility(ability2);
	}

	public AetherFigment(final AetherFigment card) {
		super(card);
	}

	@Override
	public AetherFigment copy() {
		return new AetherFigment(this);
	}

}
