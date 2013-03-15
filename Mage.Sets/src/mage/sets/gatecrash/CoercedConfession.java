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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class CoercedConfession extends CardImpl<CoercedConfession> {

    public CoercedConfession(UUID ownerId) {
        super(ownerId, 217, "Coerced Confession", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{U/B}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Target player puts the top four cards of his or her library into his or her graveyard. You draw a card for each creature card put into a graveyard this way.
        getSpellAbility().addEffect(new CoercedConfessionMillEffect());
        getSpellAbility().addTarget(new TargetPlayer());
    }

    public CoercedConfession(final CoercedConfession card) {
        super(card);
    }

    @Override
    public CoercedConfession copy() {
        return new CoercedConfession(this);
    }
}

class CoercedConfessionMillEffect extends OneShotEffect<CoercedConfessionMillEffect> {

    public CoercedConfessionMillEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Target player puts the top four cards of his or her library into his or her graveyard. You draw a card for each creature card put into a graveyard this way";
    }

    public CoercedConfessionMillEffect(final CoercedConfessionMillEffect effect) {
        super(effect);
    }

    @Override
    public CoercedConfessionMillEffect copy() {
        return new CoercedConfessionMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int foundCreatures = 0;
            int cardsCount = Math.min(4, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    if (card.getCardType().contains(CardType.CREATURE)) {
                        ++foundCreatures;
                    }
                    card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, false);
                }
            }
            if (foundCreatures > 0) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    controller.drawCards(foundCreatures, game);
                }
            }
            return true;
        }
        return false;
    }
}
