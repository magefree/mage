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
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * 
 * @author nantuko
 */
public class SourceHasCounterCondition implements Condition {

    private final CounterType counterType;
    private int amount = 1;
    private int from = -1;
    private int to;

    public SourceHasCounterCondition(CounterType type) {
        this.counterType = type;
    }

    public SourceHasCounterCondition(CounterType type, int amount) {
        this.counterType = type;
        this.amount = amount;
    }

    public SourceHasCounterCondition(CounterType type, int from, int to) {
        this.counterType = type;
        this.from = from;
        this.to = to;
    }

    @Override
    @SuppressWarnings("null")
    public boolean apply(Game game, Ability source) {
        Card card = null;
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            card = game.getCard(source.getSourceId());
            if (card == null) {
                return false;
            }
        }
        if (from != -1) { //range compare
            int count;
            if (card != null) {
                count = card.getCounters(game).getCount(counterType);
            } else {
                count = permanent.getCounters(game).getCount(counterType);
            }
            if (to == Integer.MAX_VALUE) {
                return count >= from;
            }
            return count >= from && count <= to;
        } else { // single compare (lte)
            if (card != null) {
                return card.getCounters(game).getCount(counterType) >= amount;
            } else  {
                return permanent.getCounters(game).getCount(counterType) >= amount;
            }
        }
    }
}
