package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.TimingRule;
import mage.constants.Zone;

public class ActivateAsSorceryActivatedAbility extends ActivatedAbilityImpl {

    public ActivateAsSorceryActivatedAbility(Effect effect, Cost cost) {
        this(Zone.BATTLEFIELD, effect, cost);
    }

    public ActivateAsSorceryActivatedAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
        timing = TimingRule.SORCERY;
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
        if (mayActivate == TargetController.OPPONENT) {
            return super.getRule() + " Only your opponents may activate this ability and only as a sorcery.";
        }
        return super.getRule() + " Activate only as a sorcery.";
    }
}
