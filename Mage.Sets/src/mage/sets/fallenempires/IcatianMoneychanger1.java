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
package mage.sets.fallenempires;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public class IcatianMoneychanger1 extends CardImpl {

    public IcatianMoneychanger1(UUID ownerId) {
        super(ownerId, 152, "Icatian Moneychanger", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "FEM";
        this.subtype.add("Human");
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Icatian Moneychanger enters the battlefield with three credit counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.CREDIT.createInstance(3));
        effect.setText("with three credit counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        
        // When Icatian Moneychanger enters the battlefield, it deals 3 damage to you.
        effect = new DamageControllerEffect(3);
        effect.setText("it deals 3 damage to you");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));
        
        // At the beginning of your upkeep, put a credit counter on Icatian Moneychanger.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CREDIT.createInstance()), TargetController.YOU, false));
        
        // Sacrifice Icatian Moneychanger: You gain 1 life for each credit counter on Icatian Moneychanger. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, 
                new GainLifeEffect(new CountersSourceCount(CounterType.CREDIT)), new SacrificeSourceCost(), new IsStepCondition(PhaseStep.UPKEEP), null));
    }

    public IcatianMoneychanger1(final IcatianMoneychanger1 card) {
        super(card);
    }

    @Override
    public IcatianMoneychanger1 copy() {
        return new IcatianMoneychanger1(this);
    }
}
