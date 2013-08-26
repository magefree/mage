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

package mage.sets.odyssey;

import mage.cards.CardImpl;
import mage.constants.Rarity;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;
import mage.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.TargetPermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.Ability;
import mage.constants.Outcome;

import java.util.UUID;

public class AnimalBoneyard extends CardImpl<AnimalBoneyard> {
	
	public AnimalBoneyard(ownerID UUID) {
		super(ownerID, 4, "Animal Boneyard", Rarity.UNCOMMON, CardType[](CardType.ENCHANTMENT), "{2}{W}" );
	
	this.ExpansionSetCode = "ODY";
	this.color.setWhite(true);
	this.subtype.add("Aura");
	
	// Enchant Land
	TargetPermanent auraTarget = new TargetLandPermanent();
	this.getSpellAbility().addTarget(auraTarget);
    this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
    Ability ability = new EnchantAbility(auraTarget.getTargetName());
    this.addAbility(ability);
    
    // Enchanted Land has "{tap}: Sacrifice a creature: You gain life equal to its toughness."
    Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(new SacrificeCostCreaturesToughness()), new SacrificeTargetCost(TargetControlledCreaturePermanent()));
    this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA)));
	gainedAbility.addCost(new TapSourceCost());
	}

public AnimalBoneyard(final AnimalBoneyard card){
	super(card);
}

@Override
public AnimalBoneyard copy() {
	return new AnimalBoneyard(this);
}
}
