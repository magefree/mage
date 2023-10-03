package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author JayDi85
 */
public final class AwakenTheErstwhile extends CardImpl {

    public AwakenTheErstwhile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Each player discards all the cards in their hand, then creates that many 2/2 black Zombie creature tokens.
        this.getSpellAbility().addEffect(new AwakenTheErstwhileEffect());
    }

    private AwakenTheErstwhile(final AwakenTheErstwhile card) {
        super(card);
    }

    @Override
    public AwakenTheErstwhile copy() {
        return new AwakenTheErstwhile(this);
    }
}

class AwakenTheErstwhileEffect extends OneShotEffect {

    public AwakenTheErstwhileEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player discards all the cards in their hand, then creates that many 2/2 black Zombie creature tokens";
    }

    private AwakenTheErstwhileEffect(final AwakenTheErstwhileEffect effect) {
        super(effect);
    }

    @Override
    public AwakenTheErstwhileEffect copy() {
        return new AwakenTheErstwhileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // discard hands
            Map<UUID, Integer> cardsAmount = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsInHand = player.getHand().size();
                    if (cardsInHand > 0) {
                        player.discard(cardsInHand, false, false, source, game);
                        cardsAmount.put(playerId, cardsInHand);
                    }
                }
            }

            // create tokens
            cardsAmount.entrySet().forEach(discardedHand -> {
                Player player = game.getPlayer(discardedHand.getKey());
                int tokensCount = discardedHand.getValue();
                if (player != null && tokensCount > 0) {
                    new ZombieToken().putOntoBattlefield(tokensCount, game, source, player.getId());
                }
            });

            return true;
        }
        return false;
    }
}
