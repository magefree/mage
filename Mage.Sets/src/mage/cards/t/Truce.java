
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
public final class Truce extends CardImpl {

    public Truce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Each player may draw up to two cards. For each card less than two a player draws this way, that player gains 2 life.
        this.getSpellAbility().addEffect(new TruceEffect());
    }

    private Truce(final Truce card) {
        super(card);
    }

    @Override
    public Truce copy() {
        return new Truce(this);
    }
}

class TruceEffect extends OneShotEffect {

    TruceEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each player may draw up to two cards. For each card less than two a player draws this way, that player gains 2 life";
    }

    private TruceEffect(final TruceEffect effect) {
        super(effect);
    }

    @Override
    public TruceEffect copy() {
        return new TruceEffect(this);
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
