/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */


public class TributeAbility extends EntersBattlefieldAbility{

    private int tributeValue;

    public TributeAbility(int tributeValue){
        super(new TributeEffect(tributeValue), false);
        this.tributeValue = tributeValue;
    }

    public TributeAbility(final TributeAbility ability){
        super(ability);
        this.tributeValue = ability.tributeValue;
    }


    @Override
    public EntersBattlefieldAbility copy() {
        return new TributeAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Tribute ");
        sb.append(tributeValue);
        sb.append(" <i>(As this creature enters the battlefield, an opponent of your choice may put ");
        sb.append(tributeValue);
        sb.append(" +1/+1 counter on it.)</i>");
        return sb.toString();
    }
}

class TributeEffect extends OneShotEffect<TributeEffect> {

    private int tributeValue;

    public TributeEffect(int tributeValue) {
        super(Outcome.Detriment);
        this.tributeValue = tributeValue;
    }

    public TributeEffect(final TributeEffect effect) {
        super(effect);
        this.tributeValue = effect.tributeValue;
    }

    @Override
    public TributeEffect copy() {
        return new TributeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            UUID opponentId;
            if (game.getOpponents(controller.getId()).size() == 1) {
                opponentId = game.getOpponents(controller.getId()).iterator().next();
            } else {
                Target target = new TargetOpponent(true);
                controller.choose(outcome, target, source.getSourceId(), game);
                opponentId = target.getFirstTarget();
            }
            if (opponentId != null) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    StringBuilder sb = new StringBuilder("Pay tribute to ");
                    sb.append(sourcePermanent.getName());
                    sb.append(" (add ").append(CardUtil.numberToText(tributeValue)).append(" +1/+1 counter");
                    sb.append(tributeValue > 1 ? "s":"").append(" to it)?");
                    if (opponent.chooseUse(outcome, sb.toString(), game)) {
                        game.informPlayers(new StringBuilder(opponent.getName()).append(" pays tribute to ").append(sourcePermanent.getName()).toString());
                        game.getState().setValue(new StringBuilder("tributeValue").append(source.getSourceId()).toString(), "yes");
                        return new AddCountersSourceEffect(CounterType.P1P1.createInstance(tributeValue), true).apply(game, source);
                    } else {
                        game.informPlayers(new StringBuilder(opponent.getName()).append(" does not pay tribute to ").append(sourcePermanent.getName()).toString());
                        game.getState().setValue(new StringBuilder("tributeValue").append(source.getSourceId()).toString(), "no");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
