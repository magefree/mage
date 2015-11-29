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
package mage.sets.weatherlight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox
 */
public class RocHatchling extends CardImpl {

    public RocHatchling(UUID ownerId) {
        super(ownerId, 113, "Roc Hatchling", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "WTH";
        this.subtype.add("Bird");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Roc Hatchling enters the battlefield with four shell counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.SHELL.createInstance(4)), "with four shell counters on it"));
        // At the beginning of your upkeep, remove a shell counter from Roc Hatchling.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.SHELL.createInstance()), TargetController.YOU, false));
        // As long as Roc Hatchling has no shell counters on it, it gets +3/+2 and has flying.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(3, 2, Duration.WhileOnBattlefield),
            new SourceHasCounterCondition(CounterType.SHELL, 0, 0),
            "As long as {this} has no shell counters on it, it gets +3/+2"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()),
            new SourceHasCounterCondition(CounterType.SHELL, 0, 0), "and has flying"));
        this.addAbility(ability);
    }

    public RocHatchling(final RocHatchling card) {
        super(card);
    }

    @Override
    public RocHatchling copy() {
        return new RocHatchling(this);
    }
}
