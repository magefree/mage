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
package mage.sets.khansoftarkir;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class TaigamsScheming extends CardImpl {

    public TaigamsScheming(UUID ownerId) {
        super(ownerId, 57, "Taigam's Scheming", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{U}");
        this.expansionSetCode = "KTK";


        // Look at the top five cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order
        this.getSpellAbility().addEffect(new TaigamsSchemingEffect());
    }

    public TaigamsScheming(final TaigamsScheming card) {
        super(card);
    }

    @Override
    public TaigamsScheming copy() {
        return new TaigamsScheming(this);
    }
}

class TaigamsSchemingEffect extends OneShotEffect {

    public TaigamsSchemingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top five cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order";
    }

    public TaigamsSchemingEffect(final TaigamsSchemingEffect effect) {
        super(effect);
    }

    @Override
    public TaigamsSchemingEffect copy() {
        return new TaigamsSchemingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            boolean topCardRevealed = controller.isTopCardRevealed();
            controller.setTopCardRevealed(false);
            // get cards from top
            Cards cards = new CardsImpl();
            int count = Math.min(controller.getLibrary().size(), 5);
            if (count > 0) {
                cards.addAll(controller.getLibrary().getTopCards(game, count));
                controller.lookAtCards(sourceObject.getName(), cards, game);
                // pick cards going to graveyard
                TargetCard target = new TargetCard(0,5, Zone.LIBRARY, new FilterCard("cards to put into your graveyard"));
                if (controller.choose(Outcome.Detriment, cards, target, game)) {
                    for (UUID cardId : (List<UUID>)target.getTargets()) {
                        Card card = cards.get(cardId, game);
                        if (card != null) {
                            cards.remove(card);
                            controller.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                        }
                    }
                }
                // The rest goes back to library in any order
                if (cards.size() > 0) {
                    game.informPlayers(controller.getLogName() + " puts " + cards.size() + " card" + (cards.size() ==1 ? "":"s")  + " back to his or her library");
                    target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put on your library (last chosen will be on top)"));
                    while (controller.isInGame() && cards.size() > 1) {
                        controller.choose(Outcome.Neutral, cards, target, game);
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                        }
                        target.clearChosen();
                    }
                    if (cards.size() == 1) {
                        Card card = cards.get(cards.iterator().next(), game);
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                }
            }
            controller.setTopCardRevealed(topCardRevealed);
            return true;
        }
        return false;
    }
}
