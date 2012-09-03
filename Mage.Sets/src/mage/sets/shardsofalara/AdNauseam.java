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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class AdNauseam extends CardImpl<AdNauseam> {

    public AdNauseam(UUID ownerId) {
        super(ownerId, 63, "Ad Nauseam", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");
        this.expansionSetCode = "ALA";

        this.color.setBlack(true);

        // Reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost. You may repeat this process any number of times.
        this.getSpellAbility().addEffect(new AdNauseamEffect());
    }

    public AdNauseam(final AdNauseam card) {
        super(card);
    }

    @Override
    public AdNauseam copy() {
        return new AdNauseam(this);
    }
}

class AdNauseamEffect extends OneShotEffect<AdNauseamEffect> {

    public AdNauseamEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost. You may repeat this process any number of times";
    }

    public AdNauseamEffect(final AdNauseamEffect effect) {
        super(effect);
    }

    @Override
    public AdNauseamEffect copy() {
        return new AdNauseamEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String message = "Reveal the top card of your library and put that card into your hand? You lose life equal to its converted mana cost.";
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        while (player.chooseUse(outcome, message, game) && player.getLibrary().size() > 0) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);

                int cmc = card.getManaCost().convertedManaCost();
                if (cmc > 0) {
                    player.loseLife(cmc, game);
                }
            }
        }
        return true;
    }
}
