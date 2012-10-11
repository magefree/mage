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

package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;


/**
 *
 * @author LevelX2
 */
public class AshZealot extends CardImpl<AshZealot> {

    public AshZealot (UUID ownerId) {
        super(ownerId, 86, "Ash Zealot", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}{R}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike, haste
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // Whenever a player casts a spell from a graveyard, Ash Zealot deals 3 damage to that player.
        this.addAbility(new AshZealotTriggeredAbility());


    }

    public AshZealot (final AshZealot card) {
        super(card);
    }

    @Override
    public AshZealot copy() {
        return new AshZealot(this);
    }
}

class AshZealotTriggeredAbility extends TriggeredAbilityImpl<AshZealotTriggeredAbility> {

    public AshZealotTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(3), false);
    }

    public AshZealotTriggeredAbility(final AshZealotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AshZealotTriggeredAbility copy() {
        return new AshZealotTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Constants.Zone.GRAVEYARD) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(spell.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell from a graveyard, {this} deals 3 damage to that player.";
    }
}