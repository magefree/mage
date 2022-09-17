package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class SquadAbility extends StaticAbility {

    public SquadAbility(Cost cost) {
        super(Zone.STACK, null);
        // TODO: implement this
    }

    private SquadAbility(final SquadAbility ability) {
        super(ability);
    }

    @Override
    public SquadAbility copy() {
        return new SquadAbility(this);
    }

    @Override
    public String getRule() {
        return "Squad";
    }
}
