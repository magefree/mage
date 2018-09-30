
package mage.game.command.emblems;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 *
 * @author spjspj
 */
public final class ChandraRoaringFlameEmblem extends Emblem {

    /**
     * Emblem with "At the beginning of your upkeep, this emblem deals 3 damage
     * to you."
     */

    public ChandraRoaringFlameEmblem() {
        setName("Emblem Chandra");
        setExpansionSetCodeForImage("ORI");
        Effect effect = new DamageTargetEffect(3);
        effect.setText("this emblem deals 3 damage to you");
        this.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(Zone.COMMAND, effect, TargetController.YOU, false, true));
    }
}
