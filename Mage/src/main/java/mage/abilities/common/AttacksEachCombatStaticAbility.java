package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class AttacksEachCombatStaticAbility extends StaticAbility {

    public AttacksEachCombatStaticAbility() {
        super(Zone.BATTLEFIELD, new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield, true));
    }

    protected AttacksEachCombatStaticAbility(AttacksEachCombatStaticAbility ability) {
        super(ability);
    }

    @Override
    public AttacksEachCombatStaticAbility copy() {
        return new AttacksEachCombatStaticAbility(this);
    }

}
