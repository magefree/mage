
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class RoarOfReclamation extends CardImpl {

    public RoarOfReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        // Each player returns all artifact cards from their graveyard to the battlefield.
        this.getSpellAbility().addEffect(new RoarOfReclamationEffect());
    }

    private RoarOfReclamation(final RoarOfReclamation card) {
        super(card);
    }

    @Override
    public RoarOfReclamation copy() {
        return new RoarOfReclamation(this);
    }
}

class RoarOfReclamationEffect extends OneShotEffect {

    public RoarOfReclamationEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Each player returns all artifact cards from their graveyard to the battlefield";
    }

    private RoarOfReclamationEffect(final RoarOfReclamationEffect effect) {
        super(effect);
    }

    @Override
    public RoarOfReclamationEffect copy() {
        return new RoarOfReclamationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_ARTIFACT, game), Zone.BATTLEFIELD, source, game);
            }
        }
        return true;
    }
}
