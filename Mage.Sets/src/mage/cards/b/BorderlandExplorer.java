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
package mage.cards.b;

import java.util.HashMap;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetDiscard;

/**
 *
 * @author fireshoes
 */
public class BorderlandExplorer extends CardImpl {

    public BorderlandExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF, SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Borderland Explorer enters the battlefield, each player may discard a card. Each player who discarded a card this way may search his or her library
        // for a basic land card, reveal it, put it into his or her hand, then shuffle his or her library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BorderlandExplorerEffect()));
    }

    public BorderlandExplorer(final BorderlandExplorer card) {
        super(card);
    }

    @Override
    public BorderlandExplorer copy() {
        return new BorderlandExplorer(this);
    }
}

class BorderlandExplorerEffect extends OneShotEffect {

    public BorderlandExplorerEffect() {
        super(Outcome.Neutral);
        this.staticText = "each player may discard a card. Each player who discarded a card this way may search his or her library " +
            "for a basic land card, reveal it, put it into his or her hand, then shuffle his or her library";
    }

    public BorderlandExplorerEffect(final BorderlandExplorerEffect effect) {
        super(effect);
    }

    @Override
    public BorderlandExplorerEffect copy() {
        return new BorderlandExplorerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        // Store for each player the cards to discard, that's important because all discard shall happen at the same time
        HashMap<UUID, Cards> cardsToDiscard = new HashMap<>();
        // Store for each player the lands to reveal, that's important because all reveals shall happen at the same time
        HashMap<UUID, Cards> cardsToReveal = new HashMap<>();
        if (controller != null) {
            // choose cards to discard
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cards = new CardsImpl();
                    Target target = new TargetDiscard(0, 1, new FilterCard(), playerId);
                    player.chooseTarget(outcome, target, source, game);
                    cards.addAll(target.getTargets());
                    cardsToDiscard.put(playerId, cards);
                }
            }
            // discard all chosen cards
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cardsPlayer = cardsToDiscard.get(playerId);
                    if (cardsPlayer != null) {
                        for (UUID cardId : cardsPlayer) {
                            Card card = game.getCard(cardId);
                            if (card != null) {
                                player.discard(card, source, game);
                            }
                        }
                    }
                }
            }
            // search for a land for each player that discarded
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cardsPlayer = cardsToDiscard.get(playerId);
                    if (cardsPlayer != null) {
                        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, new FilterBasicLandCard());
                        if (player.searchLibrary(target, game)) {
                            if (!target.getTargets().isEmpty()) {
                                Cards cards = new CardsImpl(target.getTargets());
                                cards.addAll(target.getTargets());
                                cardsToReveal.put(playerId, cards);
                            }
                        }
                    }
                }
            }
            // reveal the searched lands, put in hands, and shuffle
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cardsPlayer = cardsToReveal.get(playerId);
                    if (cardsPlayer != null) {
                        for (UUID cardId : cardsPlayer) {
                            Cards cards = new CardsImpl(game.getCard(cardId));
                            Card card = game.getCard(cardId);
                            player.revealCards(card.getIdName() + " (" + player.getName() + ')', cards, game);
                            player.moveCardToHandWithInfo(card, source.getSourceId(), game);
                            player.shuffleLibrary(source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
