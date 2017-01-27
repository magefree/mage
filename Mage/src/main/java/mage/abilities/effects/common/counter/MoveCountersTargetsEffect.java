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
package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public class MoveCountersTargetsEffect extends OneShotEffect {

    private final CounterType counterType;
    private final int amount;

    public MoveCountersTargetsEffect(CounterType counterType, int amount) {
        super(Outcome.Detriment);
        this.counterType = counterType;
        this.amount = amount;

    }

    public MoveCountersTargetsEffect(final MoveCountersTargetsEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.amount = effect.amount;
    }

    @Override
    public MoveCountersTargetsEffect copy() {
        return new MoveCountersTargetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent removeTargetCreature = game.getPermanent(targetPointer.getTargets(game, source).get(0));
        Permanent addTargetCreature = game.getPermanent(targetPointer.getTargets(game, source).get(1));
        if (removeTargetCreature != null && addTargetCreature != null && removeTargetCreature.getCounters(game).getCount(counterType) >= amount) {
            removeTargetCreature.removeCounters(counterType.createInstance(amount), game);
            addTargetCreature.addCounters(counterType.createInstance(amount), source, game);
            if (!game.isSimulation()) {
                game.informPlayers("Moved " + amount + ' ' + counterType.getName() + " counter" + (amount > 1 ? "s" : "") + " from " + removeTargetCreature.getLogName() + " to " + addTargetCreature.getLogName());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }

        StringBuilder sb = new StringBuilder("move ");
        if (amount > 1) {
            sb.append(amount);
        } else {
            sb.append('a');
        }
        sb.append(' ');
        sb.append(counterType.getName());
        sb.append(" counter");
        if (amount > 1) {
            sb.append("s ");
        } else {
            sb.append(' ');
        }
        sb.append("from one target creature to another target creature");

        return sb.toString();
    }
}
