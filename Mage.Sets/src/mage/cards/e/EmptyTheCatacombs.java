
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EmptyTheCatacombs extends CardImpl {

    public EmptyTheCatacombs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Each player returns all creature cards from their graveyard to their hand.
        this.getSpellAbility().addEffect(new EmptyTheCatacombsEffect());
    }

    public EmptyTheCatacombs(final EmptyTheCatacombs card) {
        super(card);
    }

    @Override
    public EmptyTheCatacombs copy() {
        return new EmptyTheCatacombs(this);
    }
}

class EmptyTheCatacombsEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature");

    public EmptyTheCatacombsEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Each player returns all creature cards from their graveyard to their hand";
    }

    public EmptyTheCatacombsEffect(final EmptyTheCatacombsEffect effect) {
        super(effect);
    }

    @Override
    public EmptyTheCatacombsEffect copy() {
        return new EmptyTheCatacombsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card : player.getGraveyard().getCards(filter, game)) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                }
            }
        }
        return true;
    }
}