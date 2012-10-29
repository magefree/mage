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
package mage.sets.tempest;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class DeathPitsOfRath extends CardImpl<DeathPitsOfRath> {

    public DeathPitsOfRath(UUID ownerId) {
        super(ownerId, 21, "Death Pits of Rath", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "TMP";

        this.color.setBlack(true);

        // Whenever a creature is dealt damage, destroy it. It can't be regenerated.
        this.addAbility(new DeathPitsOfRathTriggeredAbility());
    }

    public DeathPitsOfRath(final DeathPitsOfRath card) {
        super(card);
    }

    @Override
    public DeathPitsOfRath copy() {
        return new DeathPitsOfRath(this);
    }
}

class DeathPitsOfRathTriggeredAbility extends TriggeredAbilityImpl<DeathPitsOfRathTriggeredAbility> {

    public DeathPitsOfRathTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect(true));
    }

    public DeathPitsOfRathTriggeredAbility(final DeathPitsOfRathTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public DeathPitsOfRathTriggeredAbility copy() {
        return new DeathPitsOfRathTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE) {
            for(Effect effect : this.getEffects())
            {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature is dealt damage, destroy it. It can't be regenerated";
    }
}