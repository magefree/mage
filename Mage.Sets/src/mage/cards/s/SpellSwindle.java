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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author TheElk801
 */
public class SpellSwindle extends CardImpl {

    public SpellSwindle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target spell. Create X colorless Treasure artifact tokens, where X is that spell's converted mana cost. They have "T, Sacrifice this artifact: Add one mana of any color to your mana pool."
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new SpellSwindleEffect());
    }

    public SpellSwindle(final SpellSwindle card) {
        super(card);
    }

    @Override
    public SpellSwindle copy() {
        return new SpellSwindle(this);
    }
}

class SpellSwindleEffect extends OneShotEffect {

    public SpellSwindleEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell. Create X colorless Treasure artifact tokens, where X is that spell's converted mana cost. "
                + "They have \"{T}, Sacrifice this artifact: Add one mana of any color to your mana pool";
    }

    public SpellSwindleEffect(final SpellSwindleEffect effect) {
        super(effect);
    }

    @Override
    public SpellSwindleEffect copy() {
        return new SpellSwindleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackObject != null) {
            game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
            return new CreateTokenEffect(new TreasureToken(), stackObject.getConvertedManaCost()).apply(game, source);
        }
        return false;
    }
}
