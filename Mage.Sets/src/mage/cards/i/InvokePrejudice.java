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
package mage.cards.i;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class InvokePrejudice extends CardImpl {

    public InvokePrejudice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}{U}{U}");

        // Whenever an opponent casts a creature spell that doesn't share a color with a creature you control, counter that spell unless that player pays {X}, where X is its converted mana cost.
        this.addAbility(new InvokePrejudiceTriggeredAbility());
    }

    public InvokePrejudice(final InvokePrejudice card) {
        super(card);
    }

    @Override
    public InvokePrejudice copy() {
        return new InvokePrejudice(this);
    }
}

class InvokePrejudiceTriggeredAbility extends TriggeredAbilityImpl {

    public InvokePrejudiceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InvokePrejudiceEffect(), false);
    }

    public InvokePrejudiceTriggeredAbility(final InvokePrejudiceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InvokePrejudiceTriggeredAbility copy() {
        return new InvokePrejudiceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.getCardType().contains(CardType.CREATURE)) {
                boolean creatureSharesAColor = false;
                ObjectColor spellColor = spell.getColor(game);
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterControlledCreaturePermanent(), getControllerId(), game)) {
                    if (spellColor.shares(permanent.getColor(game))) {
                        creatureSharesAColor = true;
                        break;
                    }
                }
                if (!creatureSharesAColor) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a creature spell that doesn't share a color with a creature you control, " + super.getRule();
    }
}

class InvokePrejudiceEffect extends CounterUnlessPaysEffect {

    public InvokePrejudiceEffect() {
        super(new GenericManaCost(1));
        this.staticText = "counter that spell unless that player pays {X}, where X is its converted mana cost";
    }

    public InvokePrejudiceEffect(final InvokePrejudiceEffect effect) {
        super(effect);
    }

    @Override
    public InvokePrejudiceEffect copy() {
        return new InvokePrejudiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = true;
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            CounterUnlessPaysEffect effect = new CounterUnlessPaysEffect(new GenericManaCost(spell.getConvertedManaCost()));
            effect.setTargetPointer(getTargetPointer());
            result = effect.apply(game, source);
        }
        return result;
    }
}
