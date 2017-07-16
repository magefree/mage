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
package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class KalastriaHighborn extends CardImpl {

    public KalastriaHighborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add("Vampire");
        this.subtype.add("Shaman");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new KalastriaHighbornTriggeredAbility());
    }

    public KalastriaHighborn(final KalastriaHighborn card) {
        super(card);
    }

    @Override
    public KalastriaHighborn copy() {
        return new KalastriaHighborn(this);
    }
}

class KalastriaHighbornTriggeredAbility extends TriggeredAbilityImpl {
    KalastriaHighbornTriggeredAbility ( ) {
        super(Zone.ALL, new LoseLifeTargetEffect(2), false);
        this.addCost(new ManaCostsImpl("{B}"));
        this.addTarget(new TargetPlayer());
        this.getEffects().add(new GainLifeEffect(2));
    }

    KalastriaHighbornTriggeredAbility ( KalastriaHighbornTriggeredAbility ability ) {
        super(ability);
    }

    @Override
    public KalastriaHighbornTriggeredAbility copy() {
        return new KalastriaHighbornTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
            // ayrat: make sure Kalastria Highborn is on battlefield
            if (game.getPermanent(this.getSourceId()) == null) {
                // or it is being removed
                if (game.getLastKnownInformation(this.getSourceId(), Zone.BATTLEFIELD) == null) {
                    return false;
                }
            }

            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            Permanent permanent = zEvent.getTarget();

        return permanent != null &&
                zEvent.getToZone() == Zone.GRAVEYARD &&
                zEvent.getFromZone() == Zone.BATTLEFIELD &&
                (permanent.getControllerId().equals(this.getControllerId()) &&
                permanent.hasSubtype(SubType.VAMPIRE, game) || permanent.getId().equals(this.getSourceId()));
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another Vampire you control is put"
                + " into a graveyard from the battlefield, you may pay {B}. If you"
                + " do, target player loses 2 life and you gain 2 life.";
    }
}
