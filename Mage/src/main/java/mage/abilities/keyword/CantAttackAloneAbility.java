
package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackAloneSourceEffect;
import mage.constants.Zone;

/**
 * @author magenoxx_at_googlemail.com
 */
public class CantAttackAloneAbility extends SimpleStaticAbility {

    public CantAttackAloneAbility() {
        super(Zone.BATTLEFIELD, new CantAttackAloneSourceEffect());
    }

    private CantAttackAloneAbility(CantAttackAloneAbility ability) {
        super(ability);
    }

    @Override
    public CantAttackAloneAbility copy() {
        return new CantAttackAloneAbility(this);
    }

}
