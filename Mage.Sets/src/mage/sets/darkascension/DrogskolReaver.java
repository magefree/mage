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
package mage.sets.darkascension;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleTriggeredAbility;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Loki
 */
public class DrogskolReaver extends CardImpl<DrogskolReaver> {

    public DrogskolReaver(UUID ownerId) {
        super(ownerId, 137, "Drogskol Reaver", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{W}{U}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
        // Whenever you gain life, draw a card.
        this.addAbility(new DrogskolReaverAbility());
    }

    public DrogskolReaver(final DrogskolReaver card) {
        super(card);
    }

    @Override
    public DrogskolReaver copy() {
        return new DrogskolReaver(this);
    }
}

class DrogskolReaverAbility extends TriggeredAbilityImpl<DrogskolReaverAbility> {

    public DrogskolReaverAbility() {
        super(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), false);
    }

    public DrogskolReaverAbility(final DrogskolReaverAbility ability) {
        super(ability);
    }

    @Override
    public DrogskolReaverAbility copy() {
        return new DrogskolReaverAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.GAINED_LIFE && event.getPlayerId().equals(controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you gain life, draw a card.";
    }

}