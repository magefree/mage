package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class StartYourEnginesAbility extends StaticAbility {

    private static final Hint hint = new ValueHint("Your current speed", ControllerSpeedCount.instance);

    public StartYourEnginesAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addHint(hint);
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
        return "start your engines!";
    }
}
