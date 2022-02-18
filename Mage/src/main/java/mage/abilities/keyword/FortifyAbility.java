package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */

//20091005 - 702.64
public class FortifyAbility extends ActivatedAbilityImpl {

    public FortifyAbility(int cost) {
        this(Outcome.AddAbility, new GenericManaCost(cost));
    }

    public FortifyAbility(Outcome outcome, Cost cost) {
        this(outcome, cost, new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
    }

    public FortifyAbility(Outcome outcome, Cost cost, Target target) {
        super(Zone.BATTLEFIELD, new AttachEffect(outcome, "Fortify"), cost);
        this.addTarget(target);
        this.timing = TimingRule.SORCERY;
    }

    public FortifyAbility(final FortifyAbility ability) {
        super(ability);
    }

    @Override
    public FortifyAbility copy() {
        return new FortifyAbility(this);
    }


    @Override
    public String getRule() {
        return "Fortify " + costs.getText() + manaCosts.getText() + " (" + manaCosts.getText() + ": <i>Attach to target land you control. Fortify only as a sorcery.)</i>";
    }
}
