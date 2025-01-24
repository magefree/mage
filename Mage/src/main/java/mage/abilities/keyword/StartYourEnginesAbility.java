package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 * TODO: Implement this
 *
 * @author TheElk801
 */
public class StartYourEnginesAbility extends StaticAbility {

    public StartYourEnginesAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private StartYourEnginesAbility(final StartYourEnginesAbility ability) {
        super(ability);
    }

    @Override
    public StartYourEnginesAbility copy() {
        return new StartYourEnginesAbility(this);
    }

    @Override
    public String getRule() {
        return "Start your engines!";
    }
}
