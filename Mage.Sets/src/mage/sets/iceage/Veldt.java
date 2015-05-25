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
package mage.sets.iceage;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
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
public class Veldt extends CardImpl {

    public Veldt(UUID ownerId) {
        super(ownerId, 358, "Veldt", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ICE";

        // Veldt doesn't untap during your untap step if it has a depletion counter on it.
        Effect effect = new ConditionalContinuousRuleModifyingEffect(new DontUntapInControllersUntapStepSourceEffect(),
                new SourceHasCounterCondition(CounterType.DEPLETION, 0));
        effect.setText("{this} doesn't untap during your untap step if it has a depletion counter on it");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);
        // At the beginning of your upkeep, remove a depletion counter from Veldt.
        Ability ability2 = new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.DEPLETION.createInstance()), TargetController.YOU, false);
        this.addAbility(ability2);
        // {tap}: Add {G} or {W} to your mana pool. Put a depletion counter on Veldt.
        Ability ability3 = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana, new TapSourceCost());
        ability3.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(ability3);
        Ability ability4 = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana, new TapSourceCost());
        ability4.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(ability4);
    }

    public Veldt(final Veldt card) {
        super(card);
    }

    @Override
    public Veldt copy() {
        return new Veldt(this);
    }
}
