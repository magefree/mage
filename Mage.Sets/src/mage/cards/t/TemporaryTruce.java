
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class TemporaryTruce extends CardImpl {

    public TemporaryTruce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Each player may draw up to two cards. For each card less than two a player draws this way, that player gains 2 life.
        this.getSpellAbility().addEffect(new TemporaryTruceEffect());
    }

    private TemporaryTruce(final TemporaryTruce card) {
        super(card);
    }

    @Override
    public TemporaryTruce copy() {
        return new TemporaryTruce(this);
    }
}

class TemporaryTruceEffect extends OneShotEffect {

    TemporaryTruceEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each player may draw up to two cards. For each card less than two a player draws this way, that player gains 2 life";
    }

    private TemporaryTruceEffect(final TemporaryTruceEffect effect) {
        super(effect);
    }

    @Override
    public TemporaryTruceEffect copy() {
        return new TemporaryTruceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsToDraw = player.getAmount(0, 2, "Draw how many cards?", game);
                    player.drawCards(cardsToDraw, source, game);
                    player.gainLife((2 - cardsToDraw) * 2, game, source);
                }
            }
            return true;
        }
        return false;
    }
}
