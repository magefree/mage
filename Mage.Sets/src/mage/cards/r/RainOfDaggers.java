
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
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
public final class RainOfDaggers extends CardImpl {

    public RainOfDaggers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Destroy all creatures target opponent controls. You lose 2 life for each creature destroyed this way.
        this.getSpellAbility().addEffect(new RainOfDaggersEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private RainOfDaggers(final RainOfDaggers card) {
        super(card);
    }

    @Override
    public RainOfDaggers copy() {
        return new RainOfDaggers(this);
    }
}

class RainOfDaggersEffect extends OneShotEffect {

    public RainOfDaggersEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures target opponent controls. You lose 2 life for each creature destroyed this way";
    }

    public RainOfDaggersEffect(final RainOfDaggersEffect effect) {
        super(effect);
    }

    @Override
    public RainOfDaggersEffect copy() {
        return new RainOfDaggersEffect(this);
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
                new LoseLifeSourceControllerEffect(destroyedCreature * 2).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
