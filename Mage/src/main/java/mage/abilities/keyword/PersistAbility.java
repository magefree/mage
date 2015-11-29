/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class PersistAbility extends DiesTriggeredAbility {

    public PersistAbility() {
        super(new PersistEffect());
        this.addEffect(new ReturnSourceFromGraveyardToBattlefieldEffect(false, true));
    }

    public PersistAbility(final PersistAbility ability) {
        super(ability);
    }

    @Override
    public PersistAbility copy() {
        return new PersistAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return super.checkEventType(event, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent.getCounters().getCount(CounterType.M1M1) == 0) {
                FixedTarget fixedTarget = new FixedTarget(permanent.getId());
                fixedTarget.init(game, this);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Persist <i>(When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)</i>";
    }
}

class PersistEffect extends OneShotEffect {

    public PersistEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }

    public PersistEffect(final PersistEffect effect) {
        super(effect);
    }

    @Override
    public PersistEffect copy() {
        return new PersistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Counters countersToAdd = new Counters();
        countersToAdd.addCounter(CounterType.M1M1.createInstance());
        game.setEnterWithCounters(source.getSourceId(), countersToAdd);
        return true;
    }
}
