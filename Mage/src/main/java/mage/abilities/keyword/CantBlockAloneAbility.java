

package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockAloneSourceEffect;
import mage.constants.Zone;

/**
 * @author notgreat
 */
public class CantBlockAloneAbility extends SimpleStaticAbility {

    public CantBlockAloneAbility() {
        super(Zone.BATTLEFIELD, new CantBlockAloneSourceEffect());
    }

    private CantBlockAloneAbility(CantBlockAloneAbility ability) {
        super(ability);
    }

    @Override
    public CantBlockAloneAbility copy() {
        return new CantBlockAloneAbility(this);
    }

}
