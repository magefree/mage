package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 * @author TheElk801
 * TODO: Implement this
 */
public class NightboundAbility extends StaticAbility {

    public NightboundAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private NightboundAbility(final NightboundAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "nightbound <i>(If a player casts at least two spells during their own turn, it becomes day next turn.)</i>";
    }

    @Override
    public NightboundAbility copy() {
        return new NightboundAbility(this);
    }
}
