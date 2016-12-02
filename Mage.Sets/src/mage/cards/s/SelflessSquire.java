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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public class SelflessSquire extends CardImpl {

    public SelflessSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Selfless Squire enters the battlefield, prevent all damage that would be dealt to you this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PreventDamageToControllerEffect(Duration.EndOfTurn), false));

        // Whenever damage that would be dealt to you is prevented, put that many +1/+1 counters on Selfless Squire.
        this.addAbility(new SelflessSquireTriggeredAbility());
    }

    public SelflessSquire(final SelflessSquire card) {
        super(card);
    }

    @Override
    public SelflessSquire copy() {
        return new SelflessSquire(this);
    }
}

class SelflessSquireTriggeredAbility extends TriggeredAbilityImpl {

    public SelflessSquireTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public SelflessSquireTriggeredAbility(final SelflessSquireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SelflessSquireTriggeredAbility copy() {
        return new SelflessSquireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return EventType.PREVENTED_DAMAGE == event.getType();
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getControllerId())) {
            getEffects().clear();
            getEffects().add(new AddCountersSourceEffect(CounterType.P1P1.createInstance(event.getAmount())));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever damage that would be dealt to you is prevented, put that many +1/+1 counters on {this}.";
    }
}
