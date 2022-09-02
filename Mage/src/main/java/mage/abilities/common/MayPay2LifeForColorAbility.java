package mage.abilities.common;

import mage.ObjectColor;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class MayPay2LifeForColorAbility extends StaticAbility {

    private final ObjectColor color;

    public MayPay2LifeForColorAbility(String color) {
        super(Zone.BATTLEFIELD, null);
        this.color = new ObjectColor(color);
        // TODO: Implement this
    }

    private MayPay2LifeForColorAbility(final MayPay2LifeForColorAbility ability) {
        super(ability);
        this.color = ability.color;
    }

    @Override
    public MayPay2LifeForColorAbility copy() {
        return new MayPay2LifeForColorAbility(this);
    }

    @Override
    public String getRule() {
        return "As an additional cost to cast " + color.getDescription() +
                " permanent spells, you may pay 2 life. Those spells cost {" + color +
                "} less to cast if you paid life this way. This effect reduces only " +
                "the amount of " + color.getDescription() + " mana you pay.";
    }
}
