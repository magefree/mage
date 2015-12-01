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

import java.util.ArrayList;
import java.util.List;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Gal Lerman
 */
public class AddRemoveAllTimeSuspentCountersEffect extends OneShotEffect {

    private final Counter counter;
    private final Filter<Card> filter;
    private final boolean removeCounter;
    private final String actionStr;

  public AddRemoveAllTimeSuspentCountersEffect(Counter counter, Filter<Card> filter, boolean removeCounter) {
        super(Outcome.Benefit);
        this.counter = counter;
        this.filter = filter;
        this.removeCounter= removeCounter;
        actionStr = removeCounter ? " removes " : " puts ";
        setText();
    }

    public AddRemoveAllTimeSuspentCountersEffect(final AddRemoveAllTimeSuspentCountersEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
        this.filter = effect.filter.copy();
        this.removeCounter = effect.removeCounter;
        this.actionStr = effect.actionStr;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            if (counter != null) {
                List<Card> permanents = new ArrayList<Card>(game.getBattlefield().getAllActivePermanents());
                execute(game, controller, sourceObject, permanents, removeCounter);
                final List<Card> exiledCards = game.getExile().getAllCards(game);
                execute(game, controller, sourceObject, exiledCards, removeCounter);
            }
            return true;
        }        
        return false;
    }

  private void execute(final Game game, final Player controller, final MageObject sourceObject, final List<Card> cards, final boolean removeCounter) {
    for (Card card : cards) {
        if (filter.match(card, game)) {
            final String counterName = counter.getName();
            if (removeCounter) {
                final Counter existingCounterOfSameType = card.getCounters(game).get(counterName);
                final int countersToRemove = Math.min(existingCounterOfSameType.getCount(), counter.getCount());
                final Counter modifiedCounter = new Counter(counterName, countersToRemove);
                card.removeCounters(modifiedCounter, game);
            } else {
                card.addCounters(counter, game);
            }
            if (!game.isSimulation())
                game.informPlayers(new StringBuilder(sourceObject.getName()).append(": ")
                        .append(controller.getLogName()).append(actionStr)
                        .append(counter.getCount()).append(" ").append(counterName.toLowerCase())
                        .append(" counter on ").append(card.getName()).toString());
        }
    }
  }

  private void setText() {
        StringBuilder sb = new StringBuilder();
        final String actionsStr2 = removeCounter ? "remove " : " put ";
        sb.append(actionsStr2);
        if (counter.getCount() > 1) {
            sb.append(Integer.toString(counter.getCount())).append(" ").append(counter.getName().toLowerCase()).append(" counters on each ");
        } else {
            sb.append("a ").append(counter.getName().toLowerCase()).append(" counter on each ");
        }
        sb.append(filter.getMessage());
        staticText = sb.toString();
    }

    @Override
    public AddRemoveAllTimeSuspentCountersEffect copy() {
        return new AddRemoveAllTimeSuspentCountersEffect(this);
    }
}
