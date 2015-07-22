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
package mage.sets.invasion;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;

/**
 *
 * @author LoneFox
 */
public class DivinePresence extends CardImpl {

    public DivinePresence(UUID ownerId) {
        super(ownerId, 15, "Divine Presence", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "INV";

        // If a source would deal 4 or more damage to a creature or player, that source deals 3 damage to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DivinePresenceEffect()));
    }

    public DivinePresence(final DivinePresence card) {
        super(card);
    }

    @Override
    public DivinePresence copy() {
        return new DivinePresence(this);
    }
}

class DivinePresenceEffect extends ReplacementEffectImpl {

    public DivinePresenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a source would deal 4 or more damage to a creature or player, that source deals 3 damage to that creature or player instead.";
    }

    public DivinePresenceEffect(final DivinePresenceEffect effect) {
        super(effect);
    }

    @Override
    public DivinePresenceEffect copy() {
        return new DivinePresenceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getAmount() > 3;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(3);
        return false;
    }
}
