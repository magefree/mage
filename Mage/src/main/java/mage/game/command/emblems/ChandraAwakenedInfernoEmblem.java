package mage.game.command.emblems;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class ChandraAwakenedInfernoEmblem extends Emblem {

    /**
     * Emblem with "At the beginning of your upkeep, this emblem deals 1 damage
     * to you."
     */

    public ChandraAwakenedInfernoEmblem() {
        setName("Emblem Chandra");
        setExpansionSetCodeForImage("M20");
        this.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(
                Zone.COMMAND, new DamageControllerEffect(1, "this emblem"),
                TargetController.YOU, false, true
        ));
    }
}
