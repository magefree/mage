package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 * @author TheElk801
 * TODO: Implement this
 */
public class EnlistAbility extends StaticAbility {

    public EnlistAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private EnlistAbility(final EnlistAbility ability) {
        super(ability);
    }

    @Override
    public EnlistAbility copy() {
        return new EnlistAbility(this);
    }

    @Override
    public String getRule() {
        return "Enlist <i>(As this creature attacks, you may tap a nonattacking creature you control " +
                "without summoning sickness. When you do, add its power to this creature’s until end of turn.)</i>";
    }
}
