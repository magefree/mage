

package mage.abilities.keyword;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OptionalAdditionalCost;

/**
 * 20121001 702.31c Multikicker is a variant of the kicker ability. "Multikicker [cost]" means
 * "You may pay an additional [cost] any number of times as you cast this spell."
 * A multikicker cost is a kicker cost.
 *
 * @author LevelX2
 */

public class MultikickerAbility extends KickerAbility {

    protected static final String MultikickerKeyword = "Multikicker";
    protected static final String MultikickerReminder = "You may pay an additional {cost} any number of times as you cast this spell.";

    public MultikickerAbility(String manaString) {
        super(MultikickerKeyword, MultikickerReminder);
        OptionalAdditionalCost multikickerCost = this.addKickerCost(manaString);
        multikickerCost.setRepeatable(true);
    }

    public MultikickerAbility(Cost cost) {
        super(MultikickerKeyword, MultikickerReminder);
        OptionalAdditionalCost multikickerCost = this.addKickerCost(cost);
        multikickerCost.setRepeatable(true);
    }

    protected MultikickerAbility(final MultikickerAbility ability) {
        super(ability);
    }

    @Override
    public MultikickerAbility copy() {
        return new MultikickerAbility(this);
    }
}