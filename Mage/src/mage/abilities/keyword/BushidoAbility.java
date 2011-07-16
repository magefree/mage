package mage.abilities.keyword;

import mage.Constants.Duration;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.continious.BoostSourceEffect;

public class BushidoAbility extends BlocksOrBecomesBlockedTriggeredAbility {
    private int value;

    public BushidoAbility(int value) {
        super(new BoostSourceEffect(value, value, Duration.EndOfTurn), false);
        this.value = value;
    }

    public BushidoAbility(final BushidoAbility ability) {
        super(ability);
        this.value = ability.value;
    }

    @Override
    public BushidoAbility copy() {
        return new BushidoAbility(this);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getRule() {
        return "Bushido " + value;
    }
}
