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
package mage.sets.legends;

import java.util.UUID;
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
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class PresenceOfTheMaster extends CardImpl {

    public PresenceOfTheMaster(UUID ownerId) {
        super(ownerId, 200, "Presence of the Master", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "LEG";

        // Whenever a player casts an enchantment spell, counter it.
        this.addAbility(new PresenceOfTheMasterTriggeredAbility());
    }

    public PresenceOfTheMaster(final PresenceOfTheMaster card) {
        super(card);
    }

    @Override
    public PresenceOfTheMaster copy() {
        return new PresenceOfTheMaster(this);
    }
}

class PresenceOfTheMasterTriggeredAbility extends TriggeredAbilityImpl {
   

    public PresenceOfTheMasterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterEffect());
    }


    public PresenceOfTheMasterTriggeredAbility(final PresenceOfTheMasterTriggeredAbility abiltity) {
        super(abiltity);
    }

    @java.lang.Override
    public PresenceOfTheMasterTriggeredAbility copy() {
        return new PresenceOfTheMasterTriggeredAbility(this);
    }

    @java.lang.Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }
    
    @java.lang.Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && (spell.getCardType().contains(CardType.ENCHANTMENT))){ 
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @java.lang.Override
    public String getRule() {
        return "Whenever a player casts an enchantment spell, counter it";
    }
}


class CounterEffect extends OneShotEffect {

    public CounterEffect() {
        super(Outcome.Detriment);
    }

    public CounterEffect(final CounterEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public CounterEffect copy() {
        return new CounterEffect(this);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        return game.getStack().counter(this.getTargetPointer().getFirst(game, source), source.getSourceId(), game);
    }

}
