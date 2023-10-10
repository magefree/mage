
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class StrongholdDiscipline extends CardImpl {

    public StrongholdDiscipline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Each player loses 1 life for each creature they control.
        this.getSpellAbility().addEffect(new StrongholdDisciplineEffect());
    }

    private StrongholdDiscipline(final StrongholdDiscipline card) {
        super(card);
    }

    @Override
    public StrongholdDiscipline copy() {
        return new StrongholdDiscipline(this);
    }
}

class StrongholdDisciplineEffect extends OneShotEffect {

    StrongholdDisciplineEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each player loses 1 life for each creature they control";
    }

    private StrongholdDisciplineEffect(final StrongholdDisciplineEffect effect) {
        super(effect);
    }

    @Override
    public StrongholdDisciplineEffect copy() {
        return new StrongholdDisciplineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                final int count = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game).size();
                if (count > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.loseLife(count, game, source, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
