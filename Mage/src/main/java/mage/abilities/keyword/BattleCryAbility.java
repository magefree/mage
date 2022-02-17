package mage.abilities.keyword;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;

public class BattleCryAbility extends AttacksTriggeredAbility {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature();

    public BattleCryAbility() {
        super(new BoostControlledEffect(1, 0, Duration.EndOfTurn, filter, true), false);
    }

    public BattleCryAbility(final BattleCryAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Battle cry <i>(Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)</i>";
    }

    @Override
    public BattleCryAbility copy() {
        return new BattleCryAbility(this);
    }
}
