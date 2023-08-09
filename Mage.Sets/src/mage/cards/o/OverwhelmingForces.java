
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class OverwhelmingForces extends CardImpl {

    public OverwhelmingForces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}{B}");

        // Destroy all creatures target opponent controls. Draw a card for each creature destroyed this way.
        this.getSpellAbility().addEffect(new OverwhelmingForcesEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private OverwhelmingForces(final OverwhelmingForces card) {
        super(card);
    }

    @Override
    public OverwhelmingForces copy() {
        return new OverwhelmingForces(this);
    }
}

class OverwhelmingForcesEffect extends OneShotEffect {

    public OverwhelmingForcesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures target opponent controls. Draw a card for each creature destroyed this way";
    }

    public OverwhelmingForcesEffect(final OverwhelmingForcesEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingForcesEffect copy() {
        return new OverwhelmingForcesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && getTargetPointer().getFirst(game, source) != null) {
            int destroyedCreature = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getTargetPointer().getFirst(game, source), game)) {
                if (permanent.destroy(source, game, false)) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                game.getState().processAction(game);
                new DrawCardSourceControllerEffect(destroyedCreature).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
