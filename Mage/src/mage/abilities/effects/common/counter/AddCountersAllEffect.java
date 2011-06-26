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

import java.util.List;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class AddCountersAllEffect extends OneShotEffect<AddCountersAllEffect> {

    private Counter counter;
    private FilterPermanent filter;

    public AddCountersAllEffect(Counter counter, FilterPermanent filter) {
        super(Outcome.Benefit);
        this.counter = counter;
        this.filter = filter;
    }

    public AddCountersAllEffect(final AddCountersAllEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        if (counter != null) {
            UUID controllerId = source.getControllerId();
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents();
            for (Permanent permanent : permanents) {
                if (filter.match(permanent, controllerId, game)) {
                    permanent.addCounters(counter.copy());
                    applied = true;
                }
            }
        }
        return applied;
    }

    @Override
    public String getText(Ability source) {
        StringBuilder sb = new StringBuilder();
        sb.append("put ");
        if (counter.getCount() > 1) {
            sb.append(Integer.toString(counter.getCount())).append(" ").append(counter.getName()).append(" counters on each ");
        } else {
            sb.append("a ").append(counter.getName()).append(" counter on each ");
        }
        sb.append(filter.getMessage());
        return sb.toString();
    }

    @Override
    public AddCountersAllEffect copy() {
        return new AddCountersAllEffect(this);
    }
}
