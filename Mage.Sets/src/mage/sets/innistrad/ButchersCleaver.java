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
package mage.sets.innistrad;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalStaticAbility;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.UnblockableAbility;
import mage.cards.CardImpl;
import mage.sets.magic2010.Lifelink;

/**
 *
 * @author nantuko
 */
public class ButchersCleaver extends CardImpl<ButchersCleaver> {

    private static final String staticText = "As long as equipped creature is a Human, it has lifelink";

    public ButchersCleaver(UUID ownerId) {
        super(ownerId, 217, "Butcher's Cleaver", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Equipment");

        // Equip {3}
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(3)));

        // Equipped creature gets +3/+0.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(3, 0)));

        // As long as equipped creature is a Human, it has lifelink.
        this.addAbility(new ConditionalStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), Constants.AttachmentType.EQUIPMENT), new EquippedHasSubtypeCondition("Human"), staticText));
    }

    public ButchersCleaver(final ButchersCleaver card) {
        super(card);
    }

    @Override
    public ButchersCleaver copy() {
        return new ButchersCleaver(this);
    }
}
