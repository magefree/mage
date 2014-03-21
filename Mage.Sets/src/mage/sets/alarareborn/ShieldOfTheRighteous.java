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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.common.BlocksCreatureAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SkipNextUntapTargetEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class ShieldOfTheRighteous extends CardImpl<ShieldOfTheRighteous> {

    public ShieldOfTheRighteous(UUID ownerId) {
        super(ownerId, 11, "Shield of the Righteous", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{W}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Equipment");

        this.color.setBlue(true);
        this.color.setWhite(true);

        // Equipped creature gets +0/+2 and has vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(0, 2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT)));
        
        // Whenever equipped creature blocks a creature, that creature doesn't untap during its controller's next untap step.
        this.addAbility(new BlocksCreatureAttachedTriggeredAbility(new SkipNextUntapTargetEffect("that creature"), "equipped", false, false, true));
        
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), new TargetControlledCreaturePermanent()));
    }

    public ShieldOfTheRighteous(final ShieldOfTheRighteous card) {
        super(card);
    }

    @Override
    public ShieldOfTheRighteous copy() {
        return new ShieldOfTheRighteous(this);
    }
}
