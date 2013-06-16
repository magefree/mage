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
package mage.sets.alarareborn;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class MycoidShepherd extends CardImpl<MycoidShepherd> {

    public MycoidShepherd(UUID ownerId) {
        super(ownerId, 73, "Mycoid Shepherd", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{G}{W}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Fungus");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Mycoid Shepherd or another creature you control with power 5 or greater dies, you may gain 5 life.
        this.addAbility(new MycoidShepherdTriggeredAbility());
        
    }

    public MycoidShepherd(final MycoidShepherd card) {
        super(card);
    }

    @Override
    public MycoidShepherd copy() {
        return new MycoidShepherd(this);
    }
}

class MycoidShepherdTriggeredAbility extends TriggeredAbilityImpl<MycoidShepherdTriggeredAbility> {

    public MycoidShepherdTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(5), true);
    }

    public MycoidShepherdTriggeredAbility(final MycoidShepherdTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            MageObject lastKnown = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (lastKnown == null) {
                return false;
            }
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent permanent = zEvent.getTarget();
            if (permanent == null) {
                return false;
            }
            if (super.getSourceId().equals(event.getTargetId())
                    || permanent.getPower().getValue() > 4
                    && permanent.getControllerId().equals(controllerId)) {
                Zone after = game.getState().getZone(event.getTargetId());
                return after != null && Zone.GRAVEYARD.match(after);
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Mycoid Shepherd or another creature you control with power 5 or greater dies, you may gain 5 life.";
    }

    @Override
    public MycoidShepherdTriggeredAbility copy() {
        return new MycoidShepherdTriggeredAbility(this);
    }
}