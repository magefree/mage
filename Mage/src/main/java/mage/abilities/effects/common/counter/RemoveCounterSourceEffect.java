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
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class RemoveCounterSourceEffect extends OneShotEffect {

    private final Counter counter;

    public RemoveCounterSourceEffect(Counter counter) {
        super(Outcome.UnboostCreature);
        this.counter = counter;
        setText();
    }

    public RemoveCounterSourceEffect(RemoveCounterSourceEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getCounters(game).getCount(counter.getName()) >= counter.getCount()) {
            permanent.removeCounters(counter.getName(), counter.getCount(), game);
            if (!game.isSimulation()) {
                game.informPlayers("Removed " + counter.getCount() + " " + counter.getName() + " counter from " + permanent.getLogName());
            }
            return true;
        }
        Card card = game.getCard(source.getSourceId());
        if (card != null && card.getCounters(game).getCount(counter.getName()) >= counter.getCount()) {
            card.removeCounters(counter.getName(), counter.getCount(), game);
            if (!game.isSimulation()) {
                game.informPlayers("Removed " + counter.getCount() + " " + counter.getName()
                        + " counter from " + card.getLogName()
                        + " (" + card.getCounters(game).getCount(counter.getName()) + " left)");
            }
            return true;
        }
        return false;
    }

    @Override
    public RemoveCounterSourceEffect copy() {
        return new RemoveCounterSourceEffect(this);
    }

    private void setText() {
        if (counter.getCount() > 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("remove ").append(Integer.toString(counter.getCount())).append(" ").append(counter.getName()).append(" counters from {this}");
            staticText = sb.toString();
        } else {
            staticText = "remove a " + counter.getName() + " counter from {this}";
        }
    }
}
