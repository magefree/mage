

package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackAloneSourceEffect;
import mage.abilities.effects.common.combat.CantBlockAloneSourceEffect;
import mage.constants.Zone;

/**
 * @author notgreat
 */
public class CantAttackOrBlockAloneAbility extends SimpleStaticAbility {

    public CantAttackOrBlockAloneAbility() {
        super(Zone.BATTLEFIELD, new CantAttackAloneSourceEffect().setText("{this} can't attack or block alone"));
        this.addEffect(new CantBlockAloneSourceEffect().setText(""));
    }

    private CantAttackOrBlockAloneAbility(CantAttackOrBlockAloneAbility ability) {
        super(ability);
    }

    @Override
    public CantAttackOrBlockAloneAbility copy() {
        return new CantAttackOrBlockAloneAbility(this);
    }

}
