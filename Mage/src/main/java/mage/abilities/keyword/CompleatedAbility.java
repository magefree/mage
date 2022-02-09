package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class CompleatedAbility extends StaticAbility {

    public CompleatedAbility() {
        super(Zone.ALL, null);
        // TODO: implement this
    }

    private CompleatedAbility(final CompleatedAbility ability) {
        super(ability);
    }

    @Override
    public CompleatedAbility copy() {
        return new CompleatedAbility(this);
    }

    @Override
    public String getRule() {
        return "Compleated";
    }
}
