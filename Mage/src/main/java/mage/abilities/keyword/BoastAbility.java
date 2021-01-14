package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.common.BoastCondition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.hint.common.BoastHint;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 * @author weirddan455
 */
public class BoastAbility extends ActivatedAbilityImpl {

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
    public String getRule() {
        return "Boast &mdash; " + super.getRule() + " <i>(Activate this ability only if this creature attacked this turn and only once each turn.)</i>";
    }
}
