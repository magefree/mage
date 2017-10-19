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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.RiftmarkedKnightToken;

/**
 *
 * @author JRHerlehy
 */
public class RiftmarkedKnight extends CardImpl {

    public RiftmarkedKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from black; flanking
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        this.addAbility(new FlankingAbility());
        // Suspend 3-{1}{W}{W}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl("{1}{W}{W}"), this));
        // When the last time counter is removed from Riftmarked Knight while it's exiled, put a 2/2 black Knight creature token with flanking, protection from white, and haste onto the battlefield.
        this.addAbility(new RiftmarkedKnightTriggeredAbility());
    }

    public RiftmarkedKnight(final RiftmarkedKnight card) {
        super(card);
    }

    @Override
    public RiftmarkedKnight copy() {
        return new RiftmarkedKnight(this);
    }
}

class RiftmarkedKnightTriggeredAbility extends TriggeredAbilityImpl {

    public RiftmarkedKnightTriggeredAbility() {
        super(Zone.EXILED, new CreateTokenEffect(new RiftmarkedKnightToken()), false);
    }

    public RiftmarkedKnightTriggeredAbility(final RiftmarkedKnightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (event.getTargetId().equals(this.getSourceId()) && game.getCard(event.getTargetId()).getCounters(game).getCount(CounterType.TIME) == 0);
    }

    @Override
    public String getRule() {
        return "When the last time counter is removed from {this} while it's exiled, " + super.getRule();
    }

    @Override
    public RiftmarkedKnightTriggeredAbility copy() {
        return new RiftmarkedKnightTriggeredAbility(this);
    }
}
