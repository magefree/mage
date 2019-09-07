package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public class EquipFilterAbility extends ActivatedAbilityImpl {

    private final FilterControlledCreaturePermanent filter;

    public EquipFilterAbility(FilterControlledCreaturePermanent filter, Cost cost) {
        super(Zone.BATTLEFIELD, new AttachEffect(Outcome.AddAbility, "Equip"), cost);
        this.addTarget(new TargetControlledCreaturePermanent(filter));
        this.filter = filter;
        this.timing = TimingRule.SORCERY;
    }

    private EquipFilterAbility(final EquipFilterAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public EquipFilterAbility copy() {
        return new EquipFilterAbility(this);
    }

    @Override
    public String getRule() {
        return "Equip " + filter.getMessage() + costs.getText() + manaCosts.getText();
    }
}
