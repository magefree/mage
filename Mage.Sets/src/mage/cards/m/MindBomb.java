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
package mage.cards.m;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

/**
 *
 * @author TheElk801
 */
public class MindBomb extends CardImpl {

    public MindBomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Each player may discard up to three cards. Mind Bomb deals damage to each player equal to 3 minus the number of cards he or she discarded this way.
        this.getSpellAbility().addEffect(new MindBombEffect());
    }

    public MindBomb(final MindBomb card) {
        super(card);
    }

    @Override
    public MindBomb copy() {
        return new MindBomb(this);
    }
}

class MindBombEffect extends OneShotEffect {

    public MindBombEffect() {
        super(Outcome.Neutral);
        this.staticText = "Each player may discard up to three cards."
                + " {this} deals damage to each player equal to 3 minus the number of cards he or she discarded this way";
    }

    public MindBombEffect(final MindBombEffect effect) {
        super(effect);
    }

    @Override
    public MindBombEffect copy() {
        return new MindBombEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Map<UUID, Cards> cardsToDiscard = new HashMap<>();

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cards = new CardsImpl();
                    Target target = new TargetDiscard(0, 3, new FilterCard(), playerId);
                    player.chooseTarget(outcome, target, source, game);
                    cards.addAll(target.getTargets());
                    cardsToDiscard.put(playerId, cards);
                }
            }
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
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cardsPlayer = cardsToDiscard.get(playerId);
                    if (cardsPlayer != null && !cardsPlayer.isEmpty()) {
                        player.damage(3 - cardsPlayer.size(), source.getId(), game, false, true);
                    }
                }
            }
            // reveal the searched lands, put in hands, and shuffle
//            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
//                Player player = game.getPlayer(playerId);
//                if (player != null) {
//                    Cards cardsPlayer = cardsToReveal.get(playerId);
//                    if (cardsPlayer != null) {
//                        for (UUID cardId : cardsPlayer) {
//                            Cards cards = new CardsImpl(game.getCard(cardId));
//                            Card card = game.getCard(cardId);
//                            player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ')', cards, game);
//                            player.moveCards(card, Zone.HAND, source, game);
//                            player.shuffleLibrary(source, game);
//                        }
//                    }
//                }
//            }
            return true;
        }
        return false;
    }
}
