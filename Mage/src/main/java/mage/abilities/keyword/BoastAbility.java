package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.BoastCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.common.BoastHint;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.Collection;

/**
 * @author weirddan455
 */
public class BoastAbility extends ActivatedAbilityImpl {

    public BoastAbility(Effect effect, String manaString) {
        this(effect, new ManaCostsImpl<>(manaString));
    }

    public BoastAbility(Effect effect, Cost cost) {
        super(Zone.BATTLEFIELD, effect, cost);
        this.maxActivationsPerTurn = 1;
        this.addWatcher(new AttackedThisTurnWatcher());
        this.condition = BoastCondition.instance;
        this.addHint(BoastHint.instance);
    }

    private BoastAbility(BoastAbility ability) {
        super(ability);
    }

    @Override
    public BoastAbility copy() {
        return new BoastAbility(this);
    }

    // Needed to make this public for BoastHint to work correctly (called by BoastCondition)
    @Override
    public boolean hasMoreActivationsThisTurn(Game game) {
        return super.hasMoreActivationsThisTurn(game);
    }

    @Override
    public int getMaxActivationsPerTurn(Game game) {
        if (!game.isActivePlayer(getControllerId())) {
            return 1;
        }
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && !permanent.isCreature(game)) {
            return 1;
        }
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        getControllerId(), this, game
                ).stream()
                .map(p -> p.getAbilities(game))
                .flatMap(Collection::stream)
                .anyMatch(BoastTwiceAbility.class::isInstance) ? 2 : 1;
    }

    @Override
    public String getRule() {
        return "Boast &mdash; " + super.getRule() + " <i>(Activate only if this creature attacked this turn and only once each turn.)</i>";
    }

    private static final class BoastTwiceAbility extends SimpleStaticAbility {

        private BoastTwiceAbility() {
            super(new InfoEffect("Creatures you control can boast twice during each of your turns rather than once."));
        }

        private BoastTwiceAbility(final BoastTwiceAbility ability) {
            super(ability);
        }

        @Override
        public BoastTwiceAbility copy() {
            return new BoastTwiceAbility(this);
        }
    }

    public static BoastTwiceAbility makeBoastTwiceAbility() {
        return new BoastTwiceAbility();
    }
}
