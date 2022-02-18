
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class EvolutionaryEscalation extends CardImpl {

    public EvolutionaryEscalation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // At the beginning of your upkeep, put three +1/+1 counters on target creature you control and three +1/+1 counters on target creature an opponent controls.
        EvolutionaryEscalationEffect effect = new EvolutionaryEscalationEffect();
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private EvolutionaryEscalation(final EvolutionaryEscalation card) {
        super(card);
    }

    @Override
    public EvolutionaryEscalation copy() {
        return new EvolutionaryEscalation(this);
    }
}

class EvolutionaryEscalationEffect extends OneShotEffect {

    public EvolutionaryEscalationEffect() {
        super(Outcome.BoostCreature);
        staticText = "put three +1/+1 counters on target creature you control and three +1/+1 counters on target creature an opponent controls";
    }

    public EvolutionaryEscalationEffect(final EvolutionaryEscalationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {        
        Counter counter = CounterType.P1P1.createInstance(3);
        boolean addedCounters = false;
        for (Target target: source.getTargets()) {
            Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
            if (targetPermanent != null) {
                targetPermanent.addCounters(counter.copy(), source.getControllerId(), source, game);
                addedCounters = true;
            }
        }
        return addedCounters;
    }

    @Override
    public EvolutionaryEscalationEffect copy() {
        return new EvolutionaryEscalationEffect(this);
    }


}
