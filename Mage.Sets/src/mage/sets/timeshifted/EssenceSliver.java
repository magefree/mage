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
package mage.sets.timeshifted;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author cbt33
 */
public class EssenceSliver extends CardImpl {

    public EssenceSliver(UUID ownerId) {
        super(ownerId, 8, "Essence Sliver", Rarity.SPECIAL, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "TSB";
        this.subtype.add("Sliver");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals damage, its controller gains that much life.
        this.addAbility(new DealsDamageAllTriggeredAbility());

    }

    public EssenceSliver(final EssenceSliver card) {
        super(card);
    }

    @Override
    public EssenceSliver copy() {
        return new EssenceSliver(this);
    }
}

class DealsDamageAllTriggeredAbility extends TriggeredAbilityImpl {

    public DealsDamageAllTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EssenceSliverGainThatMuchLifeEffect(), false);
    }

    public DealsDamageAllTriggeredAbility(final DealsDamageAllTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageAllTriggeredAbility copy() {
        return new DealsDamageAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE
                || event.getType() == EventType.DAMAGED_PLAYER
                || event.getType() == EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature != null && creature.hasSubtype("Sliver")) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Sliver deals damage, its controller" + super.getRule();
    }
}

class EssenceSliverGainThatMuchLifeEffect extends OneShotEffect {

    public EssenceSliverGainThatMuchLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "its controller gains that much life";
    }

    public EssenceSliverGainThatMuchLifeEffect(final EssenceSliverGainThatMuchLifeEffect effect) {
        super(effect);
    }

    @Override
    public EssenceSliverGainThatMuchLifeEffect copy() {
        return new EssenceSliverGainThatMuchLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                controller.gainLife(amount, game);

            }
            return true;
        }
        return false;
    }
}
