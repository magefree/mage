package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 * @author TheElk801
 * TODO: Implement this
 */
public class DayboundAbility extends StaticAbility {

    public DayboundAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private DayboundAbility(final DayboundAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "daybound <i>(If a player casts no spells during their own turn, it becomes night next turn.)</i>";
    }

    @Override
    public DayboundAbility copy() {
        return new DayboundAbility(this);
    }
}
