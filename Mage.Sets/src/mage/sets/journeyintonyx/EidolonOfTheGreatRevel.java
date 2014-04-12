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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class EidolonOfTheGreatRevel extends CardImpl<EidolonOfTheGreatRevel> {

    public EidolonOfTheGreatRevel(UUID ownerId) {
        super(ownerId, 999, "Eidolon of the Great Revel", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{R}{R}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Spirit");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player casts a spell with converted mana cost 3 or less, Eidolon of the Great Revel deals 2 damage to that player.
        this.addAbility(new EidolonOfTheGreatRevelTriggeredAbility());

    }

    public EidolonOfTheGreatRevel(final EidolonOfTheGreatRevel card) {
        super(card);
    }

    @Override
    public EidolonOfTheGreatRevel copy() {
        return new EidolonOfTheGreatRevel(this);
    }
}


class EidolonOfTheGreatRevelTriggeredAbility extends TriggeredAbilityImpl<EidolonOfTheGreatRevelTriggeredAbility> {


    public EidolonOfTheGreatRevelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"));
    }


    public EidolonOfTheGreatRevelTriggeredAbility(final EidolonOfTheGreatRevelTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public EidolonOfTheGreatRevelTriggeredAbility copy() {
        return new EidolonOfTheGreatRevelTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(event.getType() == GameEvent.EventType.SPELL_CAST){
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if(spell != null && spell.getManaCost().convertedManaCost() <= 3){
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell with converted mana cost 3 or less, Eidolon of the Great Revel deals 2 damage to that player.";
    }
}
