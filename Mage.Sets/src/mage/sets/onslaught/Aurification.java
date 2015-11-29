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
package mage.sets.onslaught;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author andyfries
 */

public class Aurification extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature with a gold counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.GOLD));
    }

    final String rule = "Each creature with a gold counter on it is a Wall in addition to its other creature types and has defender.";

    public Aurification(UUID ownerId) {
        super(ownerId, 6, "Aurification", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "ONS";

        // Whenever a creature deals damage to you, put a gold counter on it.
        this.addAbility(new AddGoldCountersAbility());

        // Each creature with a gold counter on it is a Wall in addition to its other creature types and has defender.
        ArrayList<String> subtypes = new ArrayList<>(1);
        subtypes.add("Wall");

        BecomesSubtypeAllEffect becomesSubtypeAllEffect = new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, subtypes, filter, false);
        becomesSubtypeAllEffect.setText("");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, becomesSubtypeAllEffect));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield, filter, rule)));

        // When Aurification leaves the battlefield, remove all gold counters from all creatures.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new RemoveAllGoldCountersEffect(), false));
    }

    public Aurification(final Aurification card) {
        super(card);
    }

    @Override
    public Aurification copy() {
        return new Aurification(this);
    }

    public class AddGoldCountersAbility extends TriggeredAbilityImpl {

        public AddGoldCountersAbility() {
            super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.GOLD.createInstance()));
        }

        public AddGoldCountersAbility(final AddGoldCountersAbility ability) {
            super(ability);
        }

        @Override
        public AddGoldCountersAbility copy() {
            return new AddGoldCountersAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getPlayerId().equals(this.getControllerId())) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a creature deals damage to you, put a gold counter on it.";
        }

    }

    public  class RemoveAllGoldCountersEffect extends OneShotEffect {
        public RemoveAllGoldCountersEffect() {
            super(Outcome.Neutral);
            this.staticText = "remove all gold counters from all creatures";
        }

        public RemoveAllGoldCountersEffect(final RemoveAllGoldCountersEffect effect) {
            super(effect);
        }

        @Override
        public RemoveAllGoldCountersEffect copy() {
            return new RemoveAllGoldCountersEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
                if (permanent != null){
                    permanent.getCounters().removeAllCounters(CounterType.GOLD);
                }
            }
            return true;
        }
    }
}