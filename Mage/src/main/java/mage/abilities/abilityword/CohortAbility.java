package mage.abilities.abilityword;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author xenohedron
 */

public class CohortAbility extends SimpleActivatedAbility {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ALLY, "an untapped Ally you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CohortAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.setAbilityWord(AbilityWord.COHORT);
    }

    protected CohortAbility(final CohortAbility ability) {
        super(ability);
    }

    @Override
    public CohortAbility copy() {
        return new CohortAbility(this);
    }
}
