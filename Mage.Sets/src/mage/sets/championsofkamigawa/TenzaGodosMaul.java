/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedMatchesFilterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX
 */

public class TenzaGodosMaul extends CardImpl<TenzaGodosMaul> {

    private static final String rule1 = "As long as it's legendary, it gets an additional +2/+2";
    private static final String rule2 = "As long as it's red, it has trample.";
    
    private final static FilterCreaturePermanent legendaryFilter = new FilterCreaturePermanent("legendary");
    private final static FilterCreaturePermanent redFilter = new FilterCreaturePermanent("red");

    static {
        legendaryFilter.getSupertype().add("Legendary");
        legendaryFilter.setScopeSupertype(Filter.ComparisonScope.Any);
        redFilter.getColor().setRed(true);
        redFilter.setUseColor(true);
        redFilter.setScopeColor(Filter.ComparisonScope.Any);
    }

    public TenzaGodosMaul(UUID ownerId) {
        super(ownerId, 271, "Tenza, Godo's Maul", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");
        
        // Equipped creature gets +1/+1.  
	this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));
        // As long as it's legendary, it gets an additional +2/+2.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(
                new BoostEquippedEffect(2, 2), 
                new EquippedMatchesFilterCondition(legendaryFilter), rule1)));
        // As long as it's red, it has trample.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(
                new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT),
                new EquippedMatchesFilterCondition(redFilter), rule2)));
        // Equip {1} ({1}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(1), new TargetControlledCreaturePermanent()));
        
    }
    
    public TenzaGodosMaul(final TenzaGodosMaul card) {
        super(card);
    }

    @Override
    public TenzaGodosMaul copy() {
        return new TenzaGodosMaul(this);
    }        
}


