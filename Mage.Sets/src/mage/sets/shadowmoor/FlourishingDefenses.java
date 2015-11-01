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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfToken;

/**
 *
 * @author jeffwadsworth
 */
public class FlourishingDefenses extends CardImpl {

    public FlourishingDefenses(UUID ownerId) {
        super(ownerId, 114, "Flourishing Defenses", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");
        this.expansionSetCode = "SHM";

        // Whenever a -1/-1 counter is placed on a creature, you may put a 1/1 green Elf Warrior creature token onto the battlefield.
        this.addAbility(new FlourishingDefensesTriggeredAbility());

    }

    public FlourishingDefenses(final FlourishingDefenses card) {
        super(card);
    }

    @Override
    public FlourishingDefenses copy() {
        return new FlourishingDefenses(this);
    }
}

class FlourishingDefensesTriggeredAbility extends TriggeredAbilityImpl {

    FlourishingDefensesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ElfToken()), true);
    }

    FlourishingDefensesTriggeredAbility(final FlourishingDefensesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlourishingDefensesTriggeredAbility copy() {
        return new FlourishingDefensesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.M1M1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a -1/-1 counter is placed on a creature, you may put a 1/1 green Elf Warrior creature token onto the battlefield.";
    }
}
