
package mage.game.turn;

import java.util.UUID;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PreCombatMainStep extends Step {

    private static final FilterPermanent filter = new FilterPermanent("Saga");

    static {
        filter.add(new SubtypePredicate(SubType.SAGA));
    }

    public PreCombatMainStep() {
        super(PhaseStep.PRECOMBAT_MAIN, true);
        this.stepEvent = EventType.PRECOMBAT_MAIN_STEP;
        this.preStepEvent = EventType.PRECOMBAT_MAIN_STEP_PRE;
        this.postStepEvent = EventType.PRECOMBAT_MAIN_STEP_POST;
    }

    public PreCombatMainStep(final PreCombatMainStep step) {
        super(step);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        super.beginStep(game, activePlayerId);
        for (Permanent saga : game.getBattlefield().getAllActivePermanents(filter, activePlayerId, game)) {
            if (saga != null) {
                saga.addCounters(CounterType.LORE.createInstance(), null, game);
            }
        }
    }

    @Override
    public PreCombatMainStep copy() {
        return new PreCombatMainStep(this);
    }

}
