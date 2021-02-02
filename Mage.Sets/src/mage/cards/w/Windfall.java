package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Windfall extends CardImpl {

    public Windfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Each player discards their hand, then draws cards equal to the greatest number of cards a player discarded this way.
        this.getSpellAbility().addEffect(new WindfallEffect());
    }

    private Windfall(final Windfall card) {
        super(card);
    }

    @Override
    public Windfall copy() {
        return new Windfall(this);
    }
}

class WindfallEffect extends OneShotEffect {

    WindfallEffect() {
        super(Outcome.Discard);
        staticText = "Each player discards their hand, then draws cards equal to the greatest number of cards a player discarded this way.";
    }

    private WindfallEffect(final WindfallEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxDiscarded = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int discarded = player.discard(player.getHand(), false, source, game).size();
            if (discarded > maxDiscarded) {
                maxDiscarded = discarded;
            }
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(maxDiscarded, source, game);
            }
        }
        return true;
    }

    @Override
    public WindfallEffect copy() {
        return new WindfallEffect(this);
    }
}
