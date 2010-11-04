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

package mage.sets.zendikar;


import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.ColoredManaSymbol;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GoblinRuinblaster extends CardImpl<GoblinRuinblaster> {

	public GoblinRuinblaster(UUID ownerId) {
		super(ownerId, 0, "Goblin Ruinblaster", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
		this.expansionSetCode = "ZEN";
		this.color.setRed(true);
		this.subtype.add("Goblin");
		this.subtype.add("Shaman");
		this.power = new MageInt(2);
		this.toughness = new MageInt(1);
		this.addAbility(HasteAbility.getInstance());
		Ability ability1 = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
		ability1.addTarget(new TargetNonBasicLandPermanent());
		KickerAbility ability2 = new KickerAbility(new GainAbilitySourceEffect(ability1, Duration.WhileOnBattlefield), false);
		ability2.addManaCost(new ColoredManaCost(ColoredManaSymbol.R));
		this.addAbility(ability1);

	}

	public GoblinRuinblaster(final GoblinRuinblaster card) {
		super(card);
	}

	@Override
	public GoblinRuinblaster copy() {
		return new GoblinRuinblaster(this);
	}

	@Override
	public String getArt() {
		return "123599_typ_reg_sty_010.jpg";
	}
}

