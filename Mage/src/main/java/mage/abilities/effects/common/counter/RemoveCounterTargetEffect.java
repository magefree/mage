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

import java.util.HashSet;
import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class RemoveCounterTargetEffect extends OneShotEffect {
    private final Counter counter;

    public RemoveCounterTargetEffect() {
        super(Outcome.UnboostCreature);
        counter = null;
    }

    public RemoveCounterTargetEffect(Counter counter) {
        super(Outcome.UnboostCreature);
        this.counter = counter;
    }

    public RemoveCounterTargetEffect(RemoveCounterTargetEffect effect) {
        super(effect);
        this.counter = effect.counter == null ? null : effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        if(p != null) {
            Counter toRemove = (counter == null ? selectCounterType(game, source, p) : counter);
            if(toRemove != null && p.getCounters(game).getCount(toRemove.getName()) >= toRemove.getCount()) {
                p.removeCounters(toRemove.getName(), toRemove.getCount(), game);
                if(!game.isSimulation())
                    game.informPlayers("Removed " + toRemove.getCount() + " " + toRemove.getName()
                        + " counter from " + p.getName());
                return true;
            }
        }
        Card c = game.getCard(targetPointer.getFirst(game, source));
        if (c != null && counter != null && c.getCounters(game).getCount(counter.getName()) >= counter.getCount()) {
            c.removeCounters(counter.getName(), counter.getCount(), game);
            if (!game.isSimulation())
                game.informPlayers(new StringBuilder("Removed ").append(counter.getCount()).append(" ").append(counter.getName())
                    .append(" counter from ").append(c.getName())
                    .append(" (").append(c.getCounters(game).getCount(counter.getName())).append(" left)").toString());
            return true;
        }
        return false;
    }

    private Counter selectCounterType(Game game, Ability source, Permanent permanent) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null && permanent.getCounters(game).size() > 0) {
            String counterName = null;
            if(permanent.getCounters(game).size() > 1) {
                Choice choice = new ChoiceImpl(true);
                Set<String> choices = new HashSet<>();
                for(Counter counter : permanent.getCounters(game).values()) {
                    if (permanent.getCounters(game).getCount(counter.getName()) > 0) {
                        choices.add(counter.getName());
                    }
                }
                choice.setChoices(choices);
                choice.setMessage("Choose a counter type to remove from " + permanent.getName());
                controller.choose(Outcome.Detriment, choice, game);
                counterName = choice.getChoice();
            } else {
                for(Counter counter : permanent.getCounters(game).values()) {
                    if(counter.getCount() > 0) {
                        counterName = counter.getName();
                    }
                }
            }
            return new Counter(counterName);
        }
        return null;
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

        String text = "remove ";
        if(counter == null) {
            text += "a counter";
        }
        else {
           text += CardUtil.numberToText(counter.getCount(), "a") + " " + counter.getName();
           text += counter.getCount() > 1 ? " counters" : " counter";
        }
        text += " from target " + mode.getTargets().get(0).getTargetName();
        return text;
    }
}
