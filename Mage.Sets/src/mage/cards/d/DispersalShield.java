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
package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.HighestConvertedManaCostValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author nigelzor
 */
public class DispersalShield extends CardImpl {

    public DispersalShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target spell if its converted mana cost is less than or equal to the highest converted mana cost among permanents you control.
        this.getSpellAbility().addEffect(new DispersalShieldEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public DispersalShield(final DispersalShield card) {
        super(card);
    }

    @Override
    public DispersalShield copy() {
        return new DispersalShield(this);
    }
}

class DispersalShieldEffect extends OneShotEffect {

    public DispersalShieldEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell if its converted mana cost is less than or equal to the highest converted mana cost among permanents you control";
    }

    public DispersalShieldEffect(DispersalShieldEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new DispersalShieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DynamicValue amount = new HighestConvertedManaCostValue();
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null && spell.getConvertedManaCost() <= amount.calculate(game, source, this)) {
            return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        }
        return false;
    }
}
