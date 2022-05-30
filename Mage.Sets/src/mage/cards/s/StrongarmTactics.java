
package mage.cards.s;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
public final class StrongarmTactics extends CardImpl {

    public StrongarmTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Each player discards a card. Then each player who didn't discard a creature card this way loses 4 life.
        this.getSpellAbility().addEffect(new StrongarmTacticsEffect());
    }

    private StrongarmTactics(final StrongarmTactics card) {
        super(card);
    }

    @Override
    public StrongarmTactics copy() {
        return new StrongarmTactics(this);
    }
}

class StrongarmTacticsEffect extends OneShotEffect {

    StrongarmTacticsEffect() {
        super(Outcome.Discard);
        this.staticText = "Each player discards a card. Then each player who didn't discard a creature card this way loses 4 life.";
    }

    StrongarmTacticsEffect(final StrongarmTacticsEffect effect) {
        super(effect);
    }

    @Override
    public StrongarmTacticsEffect copy() {
        return new StrongarmTacticsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        // Store for each player the cards to discard, that's important because all discard shall happen at the same time
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();
        if (controller != null) {
            // choose cards to discard
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int numberOfCardsToDiscard = Math.min(1, player.getHand().size());
                    Target target = new TargetDiscard(numberOfCardsToDiscard, numberOfCardsToDiscard, new FilterCard(), playerId);
                    player.chooseTarget(outcome, target, source, game);
                    Cards cards = new CardsImpl();
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
                                if (!(player.discard(card, false, source, game) && card.isCreature(game))) {
                                    player.loseLife(4, game, source, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
