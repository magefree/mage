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

import java.util.Locale;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * 702.56. Graft
 *  702.56a. Graft represents both a static ability and a triggered ability. Graft N means,
 *  "This permanent enters the battlefield with N +1/+1 counters on it" and, "Whenever
 *  another creature enters the battlefield, if this permanent has a +1/+1 counter on it,
 *  you may move a +1/+1 counter from this permanent onto that creature."
 *
 *  702.56b. If a creature has multiple instances of graft, each one works separately.
 *
 * @author LevelX2
 */

public class GraftAbility extends TriggeredAbilityImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    private int amount;
    private String cardtype;

    public GraftAbility(Card card, int amount) {
        super(Zone.BATTLEFIELD, new GraftDistributeCounterEffect(), true);
        this.amount = amount;
        StringBuilder sb = new StringBuilder();
        for (CardType theCardtype : card.getCardType()) {
            sb.append(theCardtype.toString().toLowerCase(Locale.ENGLISH)).append(" ");
        }
        this.cardtype = sb.toString().trim();
        card.addAbility(new GraftStaticAbility(amount));
    }

    public GraftAbility(GraftAbility ability) {
        super(ability);
        this.amount = ability.amount;
        this.cardtype = ability.cardtype;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent sourcePermanent = game.getPermanent(this.getSourceId());
            if (sourcePermanent == null) {
                return false;
            }
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && sourcePermanent.getCounters().containsKey(CounterType.P1P1)&& filter.match(permanent, game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public GraftAbility copy() {
        return new GraftAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Graft");
            sb.append(" ").append(amount).append(" <i>(This ").append(cardtype).append(" enters the battlefield with ")
                          .append(amount == 1 ? "a": CardUtil.numberToText(amount))
                          .append(" +1/+1 counter on it. Whenever a creature enters the battlefield, you may move a +1/+1 counter from this ")
                          .append(cardtype).append(" onto it.)</i>");
        return sb.toString();
    }

}

class GraftStaticAbility extends StaticAbility<GraftStaticAbility> {

    private String ruleText;

    public GraftStaticAbility(int amount) {
        super(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount))));
        ruleText = new StringBuilder("This enters the battlefield with ").append(amount).append(" +1/+1 counter on it.").toString();
        this.setRuleVisible(false);
    }

    public GraftStaticAbility(final GraftStaticAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public GraftStaticAbility copy() {
        return new GraftStaticAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}


class GraftDistributeCounterEffect extends OneShotEffect<GraftDistributeCounterEffect> {

    public GraftDistributeCounterEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "you may move a +1/+1 counter from this permanent onto it";
    }

    public GraftDistributeCounterEffect(final GraftDistributeCounterEffect effect) {
        super(effect);
    }

    @Override
    public GraftDistributeCounterEffect copy() {
        return new GraftDistributeCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int numberOfCounters = sourcePermanent.getCounters().getCount(CounterType.P1P1);
            if (numberOfCounters > 0) {
                Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
                if (targetCreature != null) {
                    sourcePermanent.removeCounters(CounterType.P1P1.getName(), 1, game);
                    targetCreature.addCounters(CounterType.P1P1.createInstance(1), game);
                    StringBuilder sb = new StringBuilder("Moved one +1/+1 counter from ").append(sourcePermanent.getName()).append(" to ").append(targetCreature.getName());
                    game.informPlayers(sb.toString());
                    return true;
                }
            }
        }
        return false;
    }
}
