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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */


public class BredForTheHunt extends CardImpl<BredForTheHunt> {

    public BredForTheHunt(UUID ownerId) {
        super(ownerId, 59, "Bred for the Hunt", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{U}");
        this.expansionSetCode = "DGM";

        this.color.setGreen(true);
        this.color.setBlue(true);

        // Whenever a creature you control with a +1/+1 counter on it deals combat damage to a player, you may draw a card.
        this.addAbility(new BredForTheHuntTriggeredAbility());
    }

    public BredForTheHunt(final BredForTheHunt card) {
        super(card);
    }

    @Override
    public BredForTheHunt copy() {
        return new BredForTheHunt(this);
    }
}

class BredForTheHuntTriggeredAbility extends TriggeredAbilityImpl<BredForTheHuntTriggeredAbility> {

    public BredForTheHuntTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardControllerEffect(1));
        this.optional = true;
    }

    public BredForTheHuntTriggeredAbility(final BredForTheHuntTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BredForTheHuntTriggeredAbility copy() {
        return new BredForTheHuntTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.getControllerId().equals(getControllerId()) && creature.getCounters().getCount(CounterType.P1P1) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with a +1/+1 counter on it deals combat damage to a player, you may draw a card.";
    }

}
