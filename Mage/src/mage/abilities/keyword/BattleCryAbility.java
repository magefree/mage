package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.filter.common.FilterAttackingCreature;

public class BattleCryAbility extends AttacksTriggeredAbility {
    public BattleCryAbility() {
        super(new BoostControlledEffect(1, 0, Constants.Duration.EndOfTurn, new FilterAttackingCreature(), true), false);
    }

    public BattleCryAbility(final BattleCryAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Battle cry (Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)";
    }

    @Override
    public BattleCryAbility copy() {
        return new BattleCryAbility(this);
    }
}
