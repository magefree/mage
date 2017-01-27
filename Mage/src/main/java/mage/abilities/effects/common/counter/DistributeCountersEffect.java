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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox
 */
public class DistributeCountersEffect extends OneShotEffect {

    private final CounterType counterType;
    private final int amount;
    private final boolean removeAtEndOfTurn;
    private final String targetDescription;

    public DistributeCountersEffect(CounterType counterType, int amount, boolean removeAtEndOfTurn, String targetDescription) {
        super(Outcome.BoostCreature);
        this.counterType = counterType;
        this.amount = amount;
        this.removeAtEndOfTurn = removeAtEndOfTurn;
        this.targetDescription = targetDescription;
    }

    public DistributeCountersEffect(final DistributeCountersEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.amount = effect.amount;
        this.removeAtEndOfTurn = effect.removeAtEndOfTurn;
        this.targetDescription = effect.targetDescription;
    }

    @Override
    public DistributeCountersEffect copy() {
        return new DistributeCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() > 0) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.addCounters(counterType.createInstance(multiTarget.getTargetAmount(target)), source, game);
                }
            }

            if (removeAtEndOfTurn) {
                DelayedTriggeredAbility ability = new AtTheBeginOfNextCleanupDelayedTriggeredAbility(
                        new RemoveCountersAtEndOfTurn(counterType));
                ability.getTargets().addAll(source.getTargets());
                game.addDelayedTriggeredAbility(ability, source);
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

        String name = counterType.getName();
        String text = "distribute " + CardUtil.numberToText(amount) + ' ' + name + " counters among " + targetDescription + '.';
        if (removeAtEndOfTurn) {
            text += " For each " + name + " counter you put on a creature this way, remove a "
                    + name + " counter from that creature at the beginning of the next cleanup step.";
        }
        return text;
    }
}

class RemoveCountersAtEndOfTurn extends OneShotEffect {

    private final CounterType counterType;

    public RemoveCountersAtEndOfTurn(CounterType counterType) {
        super(Outcome.Detriment);
        this.counterType = counterType;
        String name = counterType.getName();
        staticText = "For each " + name + " counter you put on a creature this way, remove a "
                + name + " counter from that creature at the beginning of the next cleanup step.";
    }

    public RemoveCountersAtEndOfTurn(final RemoveCountersAtEndOfTurn effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public RemoveCountersAtEndOfTurn copy() {
        return new RemoveCountersAtEndOfTurn(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() > 0) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.removeCounters(counterType.getName(), multiTarget.getTargetAmount(target), game);
                }
            }
            return true;
        }
        return false;
    }
}
