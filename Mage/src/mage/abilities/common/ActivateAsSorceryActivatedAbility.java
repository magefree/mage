package mage.abilities.common;

import mage.Constants;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;

public class ActivateAsSorceryActivatedAbility extends ActivatedAbilityImpl<ActivateAsSorceryActivatedAbility> {
    public ActivateAsSorceryActivatedAbility(Constants.Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
        timing = Constants.TimingRule.SORCERY;
    }

    public ActivateAsSorceryActivatedAbility(final ActivateAsSorceryActivatedAbility ability) {
        super(ability);
    }

    @Override
    public ActivateAsSorceryActivatedAbility copy() {
        return new ActivateAsSorceryActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast a sorcery.";
    }
}
