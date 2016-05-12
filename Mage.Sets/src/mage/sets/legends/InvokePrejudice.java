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
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
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

    public InvokePrejudice(UUID ownerId) {
        super(ownerId, 62, "Invoke Prejudice", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}{U}{U}");
        this.expansionSetCode = "LEG";

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
        Card card = game.getCard(event.getSourceId());

        if (card == null || !(card.getCardType().contains(CardType.CREATURE))) {
            return false;
        }

        // Get colors of the card
        boolean castCreatureIsWhite = card.getColor(game).isWhite();
        boolean castCreatureIsBlue = card.getColor(game).isBlue();
        boolean castCreatureIsBlack = card.getColor(game).isBlack();
        boolean castCreatureIsRed = card.getColor(game).isRed();
        boolean castCreatureIsGreen = card.getColor(game).isGreen();

        // Compare to colours of creatures of controller on bf
        boolean hasToPay = true;
        boolean gotACreature = false;

        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), getControllerId(), game)) {
            gotACreature = true;
            if (castCreatureIsWhite && permanent.getColor(game).isWhite()) {
                hasToPay = false;
            }
            if (castCreatureIsBlue && permanent.getColor(game).isBlue()) {
                hasToPay = false;
            }
            if (castCreatureIsBlack && permanent.getColor(game).isBlack()) {
                hasToPay = false;
            }
            if (castCreatureIsRed && permanent.getColor(game).isRed()) {
                hasToPay = false;
            }
            if (castCreatureIsGreen && permanent.getColor(game).isGreen()) {
                hasToPay = false;
            }
        }

        if (hasToPay || !gotACreature) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(card.getId()));
            }
        }

        return hasToPay || !gotACreature;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a creature spell that doesn't share a color with a creature you control, counter that spell unless that player pays {X}, where X is its converted mana cost.";
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
            Card card = spell.getCard();
            if (card != null) {
                CounterUnlessPaysEffect effect = new CounterUnlessPaysEffect(new GenericManaCost(card.getConvertedManaCost()));
                effect.setTargetPointer(new FixedTarget(spell.getId()));
                result = effect.apply(game, source);
            }
        }
        return result;
    }
}
