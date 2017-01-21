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
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author emerald000
 */
public class GetEnergyCountersControllerEffect extends OneShotEffect {

    private final DynamicValue value;

    public GetEnergyCountersControllerEffect(int value) {
        this(new StaticValue(value));
    }

    public GetEnergyCountersControllerEffect(DynamicValue value) {
        super(Outcome.Benefit);
        this.value = value;
        setText();
    }

    public GetEnergyCountersControllerEffect(final GetEnergyCountersControllerEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.addCounters(CounterType.ENERGY.createInstance(value.calculate(game, source, this)), game);
        }
        return false;
    }

    private void setText() {
        if (!staticText.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("you get ");
        int val = 1;
        if (value instanceof StaticValue) {
            val = ((StaticValue) value).getValue();
        }
        for (int i = 0; i < val; i++) {
            sb.append("{E}");
        }
        sb.append(" <i>(");
        sb.append(CardUtil.numberToText(val, "an"));
        sb.append(" energy counter");
        sb.append(val > 1 ? "s" : "");
        sb.append(")</i>");
        if ((value instanceof StaticValue)) {
            sb.append(".");
        } else {
            sb.append(" for each ");
            sb.append(value.getMessage());
        }
        staticText = sb.toString();
    }

    @Override
    public GetEnergyCountersControllerEffect copy() {
        return new GetEnergyCountersControllerEffect(this);
    }
}
