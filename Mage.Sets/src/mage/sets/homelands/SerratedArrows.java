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
package mage.sets.homelands;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class SerratedArrows extends CardImpl<SerratedArrows> {

    public SerratedArrows(UUID ownerId) {
        super(ownerId, 135, "Serrated Arrows", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "HML";

        // Serrated Arrows enters the battlefield with three arrowhead counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.ARROWHEAD.createInstance(4));
        effect.setText("with three arrowhead counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        // At the beginning of your upkeep, if there are no arrowhead counters on Serrated Arrows, sacrifice it.
        effect = new ConditionalOneShotEffect(new SacrificeSourceEffect(), new SourceHasCounterCondition(CounterType.ARROWHEAD, 0, 0),
                "if there are no arrowhead counters on {this}, sacrifice it");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false, false));
        // {tap}, Remove an arrowhead counter from Serrated Arrows: Put a -1/-1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new AddCountersTargetEffect(CounterType.M1M1.createInstance()),
                new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.ARROWHEAD.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);
    }

    public SerratedArrows(final SerratedArrows card) {
        super(card);
    }

    @Override
    public SerratedArrows copy() {
        return new SerratedArrows(this);
    }
}
