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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author LevelX2
 */
public class RakshasaVizier extends CardImpl {

    public RakshasaVizier(UUID ownerId) {
        super(ownerId, 193, "Rakshasa Vizier", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Cat");
        this.subtype.add("Demon");


        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever one or more cards are put into exile from your graveyard, put that many +1/+1 counters on Rakshasa Vizier.
        // TODO: Handle effects that move more than one card with one trigger (e.g. if opponent want to counter a trigger, he has now to counter multiple instead of one).
        this.addAbility(new RakshasaVizierTriggeredAbility());
    }

    public RakshasaVizier(final RakshasaVizier card) {
        super(card);
    }

    @Override
    public RakshasaVizier copy() {
        return new RakshasaVizier(this);
    }
}

class RakshasaVizierTriggeredAbility extends TriggeredAbilityImpl {

    public RakshasaVizierTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    public RakshasaVizierTriggeredAbility(final RakshasaVizierTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.GRAVEYARD
                && zEvent.getToZone() == Zone.EXILED) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(getControllerId())) {
                return true;
            }

        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards are put into exile from your graveyard, put that many +1/+1 counters on {this}.";
    }

    @Override
    public RakshasaVizierTriggeredAbility copy() {
        return new RakshasaVizierTriggeredAbility(this);
    }
}
