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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class CoercedConfession extends CardImpl {

    public CoercedConfession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U/B}");


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

class CoercedConfessionMillEffect extends OneShotEffect {

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
            Cards cards = new CardsImpl();
            for(Card card: player.getLibrary().getTopCards(game, 4)) {
                cards.add(card);
                if (card.isCreature()) {
                    ++foundCreatures;
                }
            }
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
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
