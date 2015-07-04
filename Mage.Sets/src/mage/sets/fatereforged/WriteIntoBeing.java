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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
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
public class WriteIntoBeing extends CardImpl {

    public WriteIntoBeing(UUID ownerId) {
        super(ownerId, 59, "Write into Being", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "FRF";

        // Look at the top two cards of your library. Manifest one of those cards, then put the other on the top or bottom of your library.
        this.getSpellAbility().addEffect(new WriteIntoBeingEffect());
    }

    public WriteIntoBeing(final WriteIntoBeing card) {
        super(card);
    }

    @Override
    public WriteIntoBeing copy() {
        return new WriteIntoBeing(this);
    }
}

class WriteIntoBeingEffect extends OneShotEffect {

    public WriteIntoBeingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Look at the top two cards of your library. Manifest one of those cards, then put the other on the top or bottom of your library. <i>(To manifest a card, put it onto the battlefield face down as a 2/2 creature. Turn it face up any time for its mana cost if it's a creature card.)</i>";
    }

    public WriteIntoBeingEffect(final WriteIntoBeingEffect effect) {
        super(effect);
    }

    @Override
    public WriteIntoBeingEffect copy() {
        return new WriteIntoBeingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 2));
            controller.lookAtCards(sourceObject.getName(), cards, game);
            Card cardToManifest = null;
            if (cards.size() > 1) {
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to manifest"));
                if (controller.chooseTarget(outcome, cards, target, source, game)) {
                    cardToManifest = cards.get(target.getFirstTarget(), game);
                }
            } else {
                cardToManifest = cards.getRandom(game);
            }
            if (!controller.getLibrary().getFromTop(game).equals(cardToManifest)) {
                Card cardToPutBack = controller.getLibrary().removeFromTop(game);
                cardToManifest = controller.getLibrary().removeFromTop(game);
                controller.getLibrary().putOnTop(cardToPutBack, game);
                controller.getLibrary().putOnTop(cardToManifest, game);
            }
            new ManifestEffect(1).apply(game, source);
            if (controller.getLibrary().size() > 0) {
                Card cardToPutBack = controller.getLibrary().getFromTop(game);
                String position = "on top";
                if (controller.chooseUse(Outcome.Detriment, "Put " + cardToPutBack.getName() + " on bottom of library?", source, game)) {
                    cardToPutBack.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
                    position = "on bottom";
                }
                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts the other card " + position + " of his or her library");
            }
            return true;
        }
        return false;
    }
}
