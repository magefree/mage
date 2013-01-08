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

import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddCountersSourceEffect extends OneShotEffect<AddCountersSourceEffect> {

    private Counter counter;
    protected boolean informPlayers;
    protected DynamicValue amount;

    public AddCountersSourceEffect(Counter counter) {
        this(counter, false);
    }

    public AddCountersSourceEffect(Counter counter, boolean informPlayers) {
        this(counter, new StaticValue(0), informPlayers);
    }

    /**
     * 
     * @param counter
     * @param amount this amount will be added to the counter instances
     * @param informPlayers 
     */
    public AddCountersSourceEffect(Counter counter, DynamicValue amount, boolean informPlayers) {
        super(Outcome.Benefit);
        this.counter = counter.copy();
        this.informPlayers = informPlayers;
        this.amount = amount;
        setText();
    }

    public AddCountersSourceEffect(final AddCountersSourceEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
        this.informPlayers = effect.informPlayers;
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (counter != null) {
                Counter newCounter = counter.copy();
                newCounter.add(amount.calculate(game, source));
                permanent.addCounters(newCounter, game);
                if (informPlayers) {
                    Player player = game.getPlayer(source.getControllerId());
                    if (player != null) {
                        game.informPlayers(new StringBuilder(player.getName()).append(" puts ").append(newCounter.getCount()).append(" ").append(newCounter.getName().toLowerCase()).append(" counter on ").append(permanent.getName()).toString());
                    }
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        // put a +1/+1 counter on it for each attacking creature you control.
        sb.append("put ");
        if (counter.getCount() > 1) {
            sb.append(Integer.toString(counter.getCount())).append(" ");
        } else {
            sb.append("a ");
        }
        sb.append(counter.getName().toLowerCase()).append(" counter on {this}");
        if (amount.getMessage().length() > 0) {
            sb.append(" for each ").append(amount.getMessage());
        }
        staticText = sb.toString();
    }

    @Override
    public AddCountersSourceEffect copy() {
        return new AddCountersSourceEffect(this);
    }


}
