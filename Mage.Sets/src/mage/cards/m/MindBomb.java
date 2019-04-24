
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
public final class MindBomb extends CardImpl {

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
