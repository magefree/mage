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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class EnduringScalelord extends CardImpl {

    public EnduringScalelord(UUID ownerId) {
        super(ownerId, 222, "Enduring Scalelord", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Dragon");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more +1/+1 counters are place on another creature you control, you may put a +1/+1 counter on Enduring Scaleguard.
        this.addAbility(new EnduringScalelordTriggeredAbility());

    }

    public EnduringScalelord(final EnduringScalelord card) {
        super(card);
    }

    @Override
    public EnduringScalelord copy() {
        return new EnduringScalelord(this);
    }
}

class EnduringScalelordTriggeredAbility extends TriggeredAbilityImpl {

    EnduringScalelordTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    EnduringScalelordTriggeredAbility(final EnduringScalelordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EnduringScalelordTriggeredAbility copy() {
        return new EnduringScalelordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return (permanent != null
                    && !event.getTargetId().equals(this.getSourceId())
                    && permanent.getCardType().contains(CardType.CREATURE)
                    && permanent.getControllerId().equals(this.getControllerId()));
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are place on another creature you control, you may put a +1/+1 counter on {this}.";
    }
}
