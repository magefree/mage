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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public class MazirekKraulDeathPriest extends CardImpl {

    public MazirekKraulDeathPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Insect");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player sacrifices another permanent, put a +1/+1 counter on each creature you control.
        this.addAbility(new PlayerSacrificesPermanentTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()), false));
    }

    public MazirekKraulDeathPriest(final MazirekKraulDeathPriest card) {
        super(card);
    }

    @Override
    public MazirekKraulDeathPriest copy() {
        return new MazirekKraulDeathPriest(this);
    }
}

class PlayerSacrificesPermanentTriggeredAbility extends TriggeredAbilityImpl {

    public PlayerSacrificesPermanentTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public PlayerSacrificesPermanentTriggeredAbility(final PlayerSacrificesPermanentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
        return mageObject != null && !event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever a player sacrifices another permanent, " + super.getRule();
    }

    @Override
    public PlayerSacrificesPermanentTriggeredAbility copy() {
        return new PlayerSacrificesPermanentTriggeredAbility(this);
    }
}
