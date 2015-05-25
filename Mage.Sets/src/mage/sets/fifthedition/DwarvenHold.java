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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author anonymous
 */
public class DwarvenHold extends CardImpl {

    public DwarvenHold(UUID ownerId) {
        super(ownerId, 414, "Dwarven Hold", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "5ED";

        // Dwarven Hold enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // You may choose not to untap Dwarven Hold during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // At the beginning of your upkeep, if Dwarven Hold is tapped, put a storage counter on it.
        OneShotEffect addStorageCounter = new AddCountersSourceEffect(CounterType.STORAGE.createInstance());
        Effect effect = new ConditionalOneShotEffect(addStorageCounter, SourceTappedCondition.getInstance(), "if {this} is tapped, put a storage counter on it");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false));
        // {tap}, Remove any number of storage counters from Dwarven Hold: Add {R} to your mana pool for each storage counter removed this way.
        Ability ability = new DynamicManaAbility(Mana.RedMana, new RemovedCountersForCostValue(),
                "Add {R} to your mana pool for each storage counter removed this way");
        ability.addCost(new RemoveVariableCountersSourceCost(
                CounterType.STORAGE.createInstance(), "Remove any number of storage counters from {this}"));
        this.addAbility(ability);
    }

    public DwarvenHold(final DwarvenHold card) {
        super(card);
    }

    @Override
    public DwarvenHold copy() {
        return new DwarvenHold(this);
    }
}
