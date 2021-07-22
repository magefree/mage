package mage.abilities.common;

import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author maurer.it_at_gmail.com
 */
public class CantBlockAbility extends SimpleStaticAbility {

    public CantBlockAbility() {
        super(Zone.BATTLEFIELD, new CantBlockSourceEffect(Duration.WhileOnBattlefield));
    }

    private CantBlockAbility(CantBlockAbility ability) {
        super(ability);
    }

    @Override
    public CantBlockAbility copy() {
        return new CantBlockAbility(this);
    }
}
