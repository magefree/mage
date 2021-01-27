package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalesOfTheAncestors extends CardImpl {

    public TalesOfTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Each player with fewer cards in hand than the player with the most cards in hand draws cards equal to the difference.
        this.getSpellAbility().addEffect(new TalesOfTheAncestorsEffect());

        // Foretell {1}{U}
        this.addAbility(new ForetellAbility(this, "{1}{U}"));
    }

    private TalesOfTheAncestors(final TalesOfTheAncestors card) {
        super(card);
    }

    @Override
    public TalesOfTheAncestors copy() {
        return new TalesOfTheAncestors(this);
    }
}

class TalesOfTheAncestorsEffect extends OneShotEffect {

    TalesOfTheAncestorsEffect() {
        super(Outcome.Benefit);
        staticText = "each player with fewer cards in hand than the player " +
                "with the most cards in hand draws cards equal to the difference";
    }

    private TalesOfTheAncestorsEffect(final TalesOfTheAncestorsEffect effect) {
        super(effect);
    }

    @Override
    public TalesOfTheAncestorsEffect copy() {
        return new TalesOfTheAncestorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int cardCount = 0;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            cardCount = Math.max(cardCount, player.getHand().size());
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int diff = cardCount - player.getHand().size();
            if (diff > 0) {
                player.drawCards(diff, source, game);
            }
        }
        return true;
    }
}
