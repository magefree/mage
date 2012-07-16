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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class HamletbackGoliath extends CardImpl<HamletbackGoliath> {

    public HamletbackGoliath(UUID ownerId) {
        super(ownerId, 136, "Hamletback Goliath", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{R}");
        this.expansionSetCode = "M13";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever another creature enters the battlefield, you may put X +1/+1 counters on Hamletback Goliath, where X is that creature's power.
        this.addAbility(new HamletbackGoliathTriggeredAbility());
    }

    public HamletbackGoliath(final HamletbackGoliath card) {
        super(card);
    }

    @Override
    public HamletbackGoliath copy() {
        return new HamletbackGoliath(this);
    }
}

class HamletbackGoliathTriggeredAbility extends TriggeredAbilityImpl<HamletbackGoliathTriggeredAbility> {
    HamletbackGoliathTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new HamletbackGoliathEffect(), true);
    }

    HamletbackGoliathTriggeredAbility(final HamletbackGoliathTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HamletbackGoliathTriggeredAbility copy() {
        return new HamletbackGoliathTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            UUID targetId = event.getTargetId();
            Permanent permanent = game.getPermanent(targetId);
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD
                    && permanent.getCardType().contains(CardType.CREATURE)
                    && !(targetId.equals(this.getSourceId()))) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield, you may put X +1/+1 counters on Hamletback Goliath, where X is that creature's power.";
    }
}

class HamletbackGoliathEffect extends OneShotEffect<HamletbackGoliathEffect> {
    HamletbackGoliathEffect() {
        super(Constants.Outcome.BoostCreature);
    }

    HamletbackGoliathEffect(final HamletbackGoliathEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent HamletbackGoliath = game.getPermanent(source.getSourceId());
        if (creature == null) {
            creature = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Constants.Zone.BATTLEFIELD);
        }
        if (creature != null) {
           HamletbackGoliath.addCounters(CounterType.P1P1.createInstance(creature.getPower().getValue()), game);
            return true;
        }
        return false;
    }

    @Override
    public HamletbackGoliathEffect copy() {
        return new HamletbackGoliathEffect(this);
    }
}
