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
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class RemoveCounterTargetEffect extends OneShotEffect<RemoveCounterTargetEffect> {
    private final Counter counter;

    public RemoveCounterTargetEffect(Counter counter) {
        super(Outcome.UnboostCreature);
        this.counter = counter;
    }

    public RemoveCounterTargetEffect(RemoveCounterTargetEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        if (p != null && p.getCounters().getCount(counter.getName()) >= counter.getCount()) {
            p.removeCounters(counter.getName(), counter.getCount(), game);
            game.informPlayers(new StringBuilder("Removed ").append(counter.getCount()).append(" ").append(counter.getName())
                    .append(" counter from ").append(p.getName()).toString());
            return true;
        }
        Card c = game.getCard(targetPointer.getFirst(game, source));
        if (c != null && c.getCounters().getCount(counter.getName()) >= counter.getCount()) {
            c.removeCounters(counter.getName(), counter.getCount(), game);
            game.informPlayers(new StringBuilder("Removed ").append(counter.getCount()).append(" ").append(counter.getName())
                    .append(" counter from ").append(c.getName())
                    .append(" (").append(c.getCounters().getCount(counter.getName())).append(" left)").toString());
            return true;
        }
        return false;
    }

    @Override
    public RemoveCounterTargetEffect copy() {
        return new RemoveCounterTargetEffect(this);
    }

    @Override
     public String getText(Mode mode) {
         if (staticText != null && !staticText.isEmpty()) {
             return staticText;
         }
        StringBuilder sb = new StringBuilder("remove ");
        sb.append(CardUtil.numberToText(counter.getCount(), "a"));
        sb.append(" ").append(counter.getName());
        sb.append(counter.getCount() > 1 ?" counters from ":" counter from ");
        sb.append(mode.getTargets().get(0).getTargetName());
        return sb.toString();
    }
}
