/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.common;

import mage.abilities.effects.common.combat.CanBlockOnlyFlyingEffect;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */

public class CanBlockOnlyFlyingAbility extends SimpleStaticAbility {

    public CanBlockOnlyFlyingAbility() {
        super(Zone.BATTLEFIELD, new CanBlockOnlyFlyingEffect(Duration.WhileOnBattlefield));
    }

    private CanBlockOnlyFlyingAbility(CanBlockOnlyFlyingAbility ability) {
        super(ability);
    }

    @Override
    public CanBlockOnlyFlyingAbility copy() {
        return new CanBlockOnlyFlyingAbility(this);
    }
}
