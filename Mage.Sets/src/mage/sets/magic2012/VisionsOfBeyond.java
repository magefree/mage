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
package mage.sets.magic2012;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author nantuko
 */
public class VisionsOfBeyond extends CardImpl<VisionsOfBeyond> {

    public VisionsOfBeyond(UUID ownerId) {
        super(ownerId, 80, "Visions of Beyond", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "M12";

        this.color.setBlue(true);

        // Draw a card. If a graveyard has twenty or more cards in it, draw three cards instead.
        this.getSpellAbility().addEffect(new VisionsOfBeyondEffect());
    }

    public VisionsOfBeyond(final VisionsOfBeyond card) {
        super(card);
    }

    @Override
    public VisionsOfBeyond copy() {
        return new VisionsOfBeyond(this);
    }
}

class VisionsOfBeyondEffect extends OneShotEffect<VisionsOfBeyondEffect> {

    public VisionsOfBeyondEffect() {
        super(Constants.Outcome.DrawCard);
        staticText = "Draw a card. If a graveyard has twenty or more cards in it, draw three cards instead";
    }

    public VisionsOfBeyondEffect(VisionsOfBeyondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        int count = 1;
        for (UUID playerId: sourcePlayer.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.getGraveyard().size() >= 20) {
                    count = 3;
                    break;
                }
            }
        }
        sourcePlayer.drawCards(count, game);
        return true;
    }

    @Override
    public VisionsOfBeyondEffect copy() {
        return new VisionsOfBeyondEffect(this);
    }
}
