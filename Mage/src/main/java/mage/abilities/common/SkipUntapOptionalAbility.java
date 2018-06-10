
package mage.abilities.common;

import mage.abilities.effects.common.SkipUntapOptionalSourceEffect;
import mage.constants.Zone;

/**
 * Implements:
 * You may choose not to untap {this} during your untap step
 *
 * @author LevelX2
 */
public class SkipUntapOptionalAbility extends SimpleStaticAbility {

    public SkipUntapOptionalAbility() {
        super(Zone.BATTLEFIELD, new SkipUntapOptionalSourceEffect());
    }

    private SkipUntapOptionalAbility(SkipUntapOptionalAbility ability) {
        super(ability);
    }

    @Override
    public SkipUntapOptionalAbility copy() {
        return new SkipUntapOptionalAbility(this);
    }
}
