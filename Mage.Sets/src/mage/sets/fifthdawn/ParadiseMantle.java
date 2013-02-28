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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;

/**
 *
 * @author LevelX2
 */
public class ParadiseMantle extends CardImpl<ParadiseMantle> {

    public ParadiseMantle(UUID ownerId) {
        super(ownerId, 142, "Paradise Mantle", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "5DN";
        this.subtype.add("Equipment");

        // Equipped creature has "{tap}: Add one mana of any color to your mana pool."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(new AnyColorManaAbility(new TapSourceCost()), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield)));
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    public ParadiseMantle(final ParadiseMantle card) {
        super(card);
    }

    @Override
    public ParadiseMantle copy() {
        return new ParadiseMantle(this);
    }
}
