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
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class CantHaveMoreThanAmountCountersSourceAbility extends SimpleStaticAbility {

    private final CounterType counterType;
    private final int amount;

    public CantHaveMoreThanAmountCountersSourceAbility(CounterType counterType, int amount) {
        super(Zone.BATTLEFIELD, new CantHaveMoreThanAmountCountersSourceEffect(counterType, amount));
        this.counterType = counterType;
        this.amount = amount;
    }

    private CantHaveMoreThanAmountCountersSourceAbility(CantHaveMoreThanAmountCountersSourceAbility ability) {
        super(ability);
        this.counterType = ability.counterType;
        this.amount = ability.amount;
    }

    @Override
    public String getRule() {
        return "Rasputin can't have more than " + CardUtil.numberToText(this.amount) + " " + this.counterType.getName() + " counters on it.";
    }

    @Override
    public CantHaveMoreThanAmountCountersSourceAbility copy() {
        return new CantHaveMoreThanAmountCountersSourceAbility(this);
    }

    public CounterType getCounterType() {
        return this.counterType;
    }

    public int getAmount() {
        return this.amount;
    }
}

class CantHaveMoreThanAmountCountersSourceEffect extends ReplacementEffectImpl {

    private final CounterType counterType;
    private final int amount;

    CantHaveMoreThanAmountCountersSourceEffect(CounterType counterType, int amount) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false);
        this.counterType = counterType;
        this.amount = amount;
    }

    CantHaveMoreThanAmountCountersSourceEffect(final CantHaveMoreThanAmountCountersSourceEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.amount = effect.amount;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ADD_COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null
                && permanent.getId().equals(source.getSourceId())
                && event.getData().equals(this.counterType.getName())
                && permanent.getCounters(game).getCount(this.counterType) == this.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CantHaveMoreThanAmountCountersSourceEffect copy() {
        return new CantHaveMoreThanAmountCountersSourceEffect(this);
    }
}
