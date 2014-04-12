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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class InterpretTheSigns extends CardImpl<InterpretTheSigns> {

    public InterpretTheSigns(UUID ownerId) {
        super(ownerId, 43, "Interpret the Signs", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{5}{U}");
        this.expansionSetCode = "JOU";

        this.color.setBlue(true);

        // Scry 3, then reveal the top card of your library. Draw cards equal to that card's converted mana cost.
        this.getSpellAbility().addEffect(new ScryEffect(3));
        this.getSpellAbility().addEffect(new InterpretTheSignsEffect());

    }

    public InterpretTheSigns(final InterpretTheSigns card) {
        super(card);
    }

    @Override
    public InterpretTheSigns copy() {
        return new InterpretTheSigns(this);
    }
}

class InterpretTheSignsEffect extends OneShotEffect<InterpretTheSignsEffect> {

    public InterpretTheSignsEffect() {
        super(Outcome.DrawCard);
        this.staticText = ", then reveal the top card of your library. Draw cards equal to that card's converted mana cost";
    }

    public InterpretTheSignsEffect(final InterpretTheSignsEffect effect) {
        super(effect);
    }

    @Override
    public InterpretTheSignsEffect copy() {
        return new InterpretTheSignsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.drawCards(card.getManaCost().convertedManaCost(), game);
            }
            return true;
        }
        return false;
    }
}
