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
package mage.sets.innistrad;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author nantuko
 */
public class ChampionOfTheParish extends CardImpl<ChampionOfTheParish> {

    public ChampionOfTheParish(UUID ownerId) {
        super(ownerId, 6, "Champion of the Parish", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another Human enters the battlefield under your control, put a +1/+1 counter on Champion of the Parish.
        this.addAbility(new HumanEntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    public ChampionOfTheParish(final ChampionOfTheParish card) {
        super(card);
    }

    @Override
    public ChampionOfTheParish copy() {
        return new ChampionOfTheParish(this);
    }
}

class HumanEntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl {

    public HumanEntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
    }

    public HumanEntersBattlefieldTriggeredAbility(HumanEntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            UUID targetId = event.getTargetId();
            Permanent permanent = game.getPermanent(targetId);
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD
                    && permanent.getControllerId().equals(this.controllerId)
                    && (permanent.hasSubtype("Human") && !targetId.equals(this.getSourceId()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another Human enters the battlefield under your control, " + super.getRule();
    }

    @Override
    public HumanEntersBattlefieldTriggeredAbility copy() {
        return new HumanEntersBattlefieldTriggeredAbility(this);
    }
}
