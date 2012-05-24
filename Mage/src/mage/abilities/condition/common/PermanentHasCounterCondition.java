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
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.counters.CounterType;
import mage.game.Game;
import mage.filter.FilterPermanent;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author jeffwadsworth
 */
public class PermanentHasCounterCondition implements Condition {

    public static enum CountType {MORE_THAN, FEWER_THAN, EQUAL_TO}

    ;
    private CounterType counterType;
    private int amount;
    private FilterPermanent filter;
    private CountType type;

    public PermanentHasCounterCondition(CounterType counterType, int amount, FilterPermanent filter) {
        this(counterType, amount, filter, CountType.EQUAL_TO);
    }

    public PermanentHasCounterCondition(CounterType counterType, int amount, FilterPermanent filter, CountType type) {
        this.counterType = counterType;
        this.amount = amount;
        this.filter = filter;
        this.type = type;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(this.filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            switch (this.type) {
                case FEWER_THAN:
                    if (permanent.getCounters().getCount(this.counterType) < this.amount) {
                        conditionApplies = true;
                        break;
                    }
                    break;
                case MORE_THAN:
                    if (permanent.getCounters().getCount(this.counterType) > this.amount) {
                        conditionApplies = true;
                        break;
                    }
                    break;
                case EQUAL_TO:
                    if (permanent.getCounters().getCount(this.counterType) == this.amount) {
                        conditionApplies = true;
                        break;
                    }
                    break;
            }
        }
        return conditionApplies;
    }
}
