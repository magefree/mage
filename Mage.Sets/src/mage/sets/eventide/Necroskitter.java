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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class Necroskitter extends CardImpl {

    public Necroskitter(UUID ownerId) {
        super(ownerId, 38, "Necroskitter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Wither (This deals damage to creatures in the form of -1/-1 counters.)
        this.addAbility(WitherAbility.getInstance());

        // Whenever a creature an opponent controls with a -1/-1 counter on it dies, you may return that card to the battlefield under your control.
        this.addAbility(new NecroskitterTriggeredAbility());

    }

    public Necroskitter(final Necroskitter card) {
        super(card);
    }

    @Override
    public Necroskitter copy() {
        return new Necroskitter(this);
    }
}

class NecroskitterTriggeredAbility extends TriggeredAbilityImpl {

    public NecroskitterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToBattlefieldUnderYourControlTargetEffect(), true);
    }

    public NecroskitterTriggeredAbility(NecroskitterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NecroskitterTriggeredAbility copy() {
        return new NecroskitterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null
                    && permanent.getCounters(game).containsKey(CounterType.M1M1)
                    && game.getOpponents(controllerId).contains(permanent.getControllerId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId(), game.getState().getZoneChangeCounter(event.getTargetId())));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls with a -1/-1 counter on it dies, you may return that card to the battlefield under your control.";
    }
}
