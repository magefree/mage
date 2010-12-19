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
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.MetalcraftActivatedAbility;
import mage.abilities.costs.common.MetalcraftCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MoxOpal extends CardImpl<MoxOpal> {

	public MoxOpal(UUID ownerId) {
		super(ownerId, 179, "Mox Opal", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{0}");
		this.supertype.add("Legendary");
		this.expansionSetCode = "SOM";
		Ability ability1 = new WhiteManaAbility();
		ability1.addCost(new MetalcraftCost());
		this.addAbility(ability1);
		Ability ability2 = new RedManaAbility();
		ability2.addCost(new MetalcraftCost());
		this.addAbility(ability2);
		Ability ability3 = new BlueManaAbility();
		ability3.addCost(new MetalcraftCost());
		this.addAbility(ability3);
		Ability ability4 = new BlackManaAbility();
		ability4.addCost(new MetalcraftCost());
		this.addAbility(ability4);
		Ability ability5 = new GreenManaAbility();
		ability5.addCost(new MetalcraftCost());
		this.addAbility(ability5);
	}

	public MoxOpal(final MoxOpal card) {
		super(card);
	}

	@Override
	public MoxOpal copy() {
		return new MoxOpal(this);
	}

}
