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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class BloodHound extends CardImpl {

    public BloodHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add("Hound");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you're dealt damage, you may put that many +1/+1 counters on Blood Hound.
        this.addAbility(new BloodHoundTriggeredAbility());

        // At the beginning of your end step, remove all +1/+1 counters from Blood Hound.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.P1P1), TargetController.YOU, false));
    }

    public BloodHound(final BloodHound card) {
        super(card);
    }

    @Override
    public BloodHound copy() {
        return new BloodHound(this);
    }
}

class BloodHoundTriggeredAbility extends TriggeredAbilityImpl {

    public BloodHoundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BloodHoundEffect(), true);
    }

    public BloodHoundTriggeredAbility(final BloodHoundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodHoundTriggeredAbility copy() {
        return new BloodHoundTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you are dealt damage, you may put that many +1/+1 counters on {this}.";
    }
}

class BloodHoundEffect extends OneShotEffect {

    public BloodHoundEffect() {
        super(Outcome.Benefit);
    }

    public BloodHoundEffect(final BloodHoundEffect effect) {
        super(effect);
    }

    @Override
    public BloodHoundEffect copy() {
        return new BloodHoundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance((Integer) this.getValue("damageAmount")), source, game);
        }
        return true;
    }
}
