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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class Sporogenesis extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public Sporogenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of your upkeep, you may put a fungus counter on target nontoken creature.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersTargetEffect(CounterType.FUNGUS.createInstance()), TargetController.YOU, true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Whenever a creature with a fungus counter on it dies, create a 1/1 green Saproling creature token for each fungus counter on that creature.
        this.addAbility(new SporogenesisTriggeredAbility());

        // When Sporogenesis leaves the battlefield, remove all fungus counters from all creatures.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SporogenesisRemoveCountersEffect(), false)); 
    }

    public Sporogenesis(final Sporogenesis card) {
        super(card);
    }

    @Override
    public Sporogenesis copy() {
        return new Sporogenesis(this);
    }
}

class SporogenesisTriggeredAbility extends TriggeredAbilityImpl {

    public SporogenesisTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken(), new SporogenesisCount()), false);
    }

    public SporogenesisTriggeredAbility(SporogenesisTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SporogenesisTriggeredAbility copy() {
        return new SporogenesisTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTargetId());
            if (permanent != null
                    && permanent.isCreature()
                    && permanent.getCounters(game).containsKey(CounterType.FUNGUS)) {
                Effect effect = this.getEffects().get(0);
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with a fungus counter on it dies, create a 1/1 green Saproling creature token for each fungus counter on that creature.";
    }
}

class SporogenesisCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(effect.getTargetPointer().getFirst(game, sourceAbility));
        if (permanent != null) {
            return permanent.getCounters(game).getCount(CounterType.FUNGUS);
        }
        return 0;
    }

    @Override
    public SporogenesisCount copy() {
        return new SporogenesisCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "fungus counter on that creature";
    }
}

class SporogenesisRemoveCountersEffect extends OneShotEffect {

    public SporogenesisRemoveCountersEffect() {
        super(Outcome.Neutral);
        staticText = "remove all fungus counters from all creatures";
    }

    public SporogenesisRemoveCountersEffect(final SporogenesisRemoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public SporogenesisRemoveCountersEffect copy() {
        return new SporogenesisRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
            permanent.removeCounters(CounterType.FUNGUS.createInstance(permanent.getCounters(game).getCount(CounterType.FUNGUS)), game);
        }
        return true;
    }
}
