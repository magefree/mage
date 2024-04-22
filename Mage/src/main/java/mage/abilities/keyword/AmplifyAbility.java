
package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.effects.common.AmplifyEffect.AmplifyFactor;
import mage.constants.Zone;

/**
 * @author FenrisulfrX
 */
public class AmplifyAbility extends SimpleStaticAbility {

    public AmplifyAbility(AmplifyFactor amplifyFactor) {
        super(Zone.ALL, new AmplifyEffect(amplifyFactor));
    }

    protected AmplifyAbility(final AmplifyAbility ability) {
        super(ability);
    }

    @Override
    public AmplifyAbility copy() {
        return new AmplifyAbility(this);
    }

}
