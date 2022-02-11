
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author emerald000
 */
public class EscalateAbility extends SimpleStaticAbility {

    public EscalateAbility(Cost cost) {
        super(Zone.ALL, new EscalateEffect(cost));
        this.setRuleAtTheTop(true);
    }

    public EscalateAbility(final EscalateAbility ability) {
        super(ability);
    }

    @Override
    public EscalateAbility copy() {
        return new EscalateAbility(this);
    }
}

class EscalateEffect extends CostModificationEffectImpl {

    private final Cost cost;

    EscalateEffect(Cost cost) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.cost = cost;
        this.staticText = "Escalate " + cost.getText() + " <i>(Pay this cost for each mode chosen beyond the first.)</i>";
    }

    EscalateEffect(final EscalateEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int numCosts = abilityToModify.getModes().getSelectedModes().size() - 1;
        for (int i = 0; i < numCosts; i++) {
            abilityToModify.addCost(cost.copy());
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility)) {
            return abilityToModify.getModes().getSelectedModes().size() > 1;
        }
        return false;
    }

    @Override
    public EscalateEffect copy() {
        return new EscalateEffect(this);
    }
}
