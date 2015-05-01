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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author jeffwadsworth
 */
public class RiverKelpie extends CardImpl {

    public RiverKelpie(UUID ownerId) {
        super(ownerId, 49, "River Kelpie", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Beast");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever River Kelpie or another permanent is put onto the battlefield from a graveyard, draw a card.
        this.addAbility(new RiverKelpieTriggeredAbility());

        // Whenever a player casts a spell from a graveyard, draw a card.
        this.addAbility(new RiverKelpieTriggeredAbility2());

        // Persist
        this.addAbility(new PersistAbility());
    }

    public RiverKelpie(final RiverKelpie card) {
        super(card);
    }

    @Override
    public RiverKelpie copy() {
        return new RiverKelpie(this);
    }
}

class RiverKelpieTriggeredAbility extends TriggeredAbilityImpl {

    @Override
    public RiverKelpieTriggeredAbility copy() {
        return new RiverKelpieTriggeredAbility(this);
    }

    public RiverKelpieTriggeredAbility(final RiverKelpieTriggeredAbility ability) {
        super(ability);
    }

    public RiverKelpieTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getFromZone() == Zone.GRAVEYARD && zEvent.getToZone() == Zone.BATTLEFIELD;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another permanent is put onto the battlefield from a graveyard, draw a card.";
    }
}

class RiverKelpieTriggeredAbility2 extends TriggeredAbilityImpl {

    public RiverKelpieTriggeredAbility2() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public RiverKelpieTriggeredAbility2(final RiverKelpieTriggeredAbility2 ability) {
        super(ability);
    }

    @Override
    public RiverKelpieTriggeredAbility2 copy() {
        return new RiverKelpieTriggeredAbility2(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getZone() == Zone.GRAVEYARD;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell from a graveyard, draw a card.";
    }
}
