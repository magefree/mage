package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DreadSummons extends CardImpl {

    public DreadSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Each player puts the top X cards of their library into their graveyard. For each creature card put into a graveyard this way, you create a tapped 2/2 black Zombie creature token.
        this.getSpellAbility().addEffect(new DreadSummonsEffect());
    }

    private DreadSummons(final DreadSummons card) {
        super(card);
    }

    @Override
    public DreadSummons copy() {
        return new DreadSummons(this);
    }
}

class DreadSummonsEffect extends OneShotEffect {

    private static final Token token = new ZombieToken();

    DreadSummonsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "each player mills X cards. For each creature card put into a graveyard this way, " +
                "you create a tapped 2/2 black Zombie creature token";
    }

    private DreadSummonsEffect(final DreadSummonsEffect effect) {
        super(effect);
    }

    @Override
    public DreadSummonsEffect copy() {
        return new DreadSummonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int creatureCount = 0;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            creatureCount += player
                    .millCards(source.getManaCostsToPay().getX(), source, game)
                    .getCards(game)
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(card -> game.getState().getZone(card.getId()) == Zone.GRAVEYARD)
                    .filter(card1 -> card1.isCreature(game))
                    .count();
        }
        if (creatureCount > 0) {
            game.getState().processAction(game);
            token.putOntoBattlefield(creatureCount, game, source, source.getControllerId(), true, false);
        }
        return true;
    }
}
