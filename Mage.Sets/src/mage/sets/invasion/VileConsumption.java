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
package mage.sets.invasion;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class VileConsumption extends CardImpl {

    public VileConsumption(UUID ownerId) {
        super(ownerId, 285, "Vile Consumption", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{B}");
        this.expansionSetCode = "INV";

        // All creatures have "At the beginning of your upkeep, sacrifice this creature unless you pay 1 life."
        Effect effect = new SacrificeSourceUnlessPaysEffect(new PayLifeCost(1));
        effect.setText("sacrifice this creature unless you pay 1 life");
        Effect effect2 = new GainAbilityAllEffect(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false),
            Duration.WhileOnBattlefield, new FilterCreaturePermanent("all creatures"));
        effect2.setText("All creatures have \"At the beginning of your upkeep, sacrifice this creature unless you pay 1 life.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect2));
    }

    public VileConsumption(final VileConsumption card) {
        super(card);
    }

    @Override
    public VileConsumption copy() {
        return new VileConsumption(this);
    }
}
