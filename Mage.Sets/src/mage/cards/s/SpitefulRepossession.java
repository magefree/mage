package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpitefulRepossession extends CardImpl {

    public SpitefulRepossession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Spiteful Repossession deals damage to each opponent who controls more lands than you equal to the difference. Then create a number of Treasure tokens equal to the damage dealt this way.
        this.getSpellAbility().addEffect(new SpitefulRepossessionEffect());
    }

    private SpitefulRepossession(final SpitefulRepossession card) {
        super(card);
    }

    @Override
    public SpitefulRepossession copy() {
        return new SpitefulRepossession(this);
    }
}

class SpitefulRepossessionEffect extends OneShotEffect {

    SpitefulRepossessionEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to each opponent who controls more lands than you equal to the difference. " +
                "Then create a number of Treasure tokens equal to the damage dealt this way";
    }

    private SpitefulRepossessionEffect(final SpitefulRepossessionEffect effect) {
        super(effect);
    }

    @Override
    public SpitefulRepossessionEffect copy() {
        return new SpitefulRepossessionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int controlledLands = game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), source, game);
        int damageDealt = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            int lands = game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, opponentId, source, game);
            if (lands <= controlledLands) {
                continue;
            }
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                damageDealt += opponent.damage(lands - controlledLands, source, game);
            }
        }
        if (damageDealt > 0) {
            new TreasureToken().putOntoBattlefield(damageDealt, game, source);
        }
        return true;

    }
}
