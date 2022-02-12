package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RepayInKind extends CardImpl {

    public RepayInKind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Each player's life total becomes the lowest life total among all players.
        this.getSpellAbility().addEffect(new RepayInKindEffect());
    }

    private RepayInKind(final RepayInKind card) {
        super(card);
    }

    @Override
    public RepayInKind copy() {
        return new RepayInKind(this);
    }
}

class RepayInKindEffect extends OneShotEffect {

    public RepayInKindEffect() {
        super(Outcome.Tap);
        staticText = "Each player's life total becomes the lowest life total among all players";
    }

    public RepayInKindEffect(final RepayInKindEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lowestLife = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .min()
                .orElse(0);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.setLife(lowestLife, game, source);
            }
        }
        return true;
    }

    @Override
    public RepayInKindEffect copy() {
        return new RepayInKindEffect(this);
    }
}
