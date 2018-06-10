
package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanAttackOnlyAloneEffect;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public class CanAttackOnlyAloneAbility extends SimpleStaticAbility {

    public CanAttackOnlyAloneAbility() {
        super(Zone.BATTLEFIELD, new CanAttackOnlyAloneEffect());
    }

    private CanAttackOnlyAloneAbility(CanAttackOnlyAloneAbility ability) {
        super(ability);
    }

    @Override
    public CanAttackOnlyAloneAbility copy() {
        return new CanAttackOnlyAloneAbility(this);
    }

}
