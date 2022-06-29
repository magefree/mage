package mage.game.command.emblems;

import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class VraskaGolgariQueenEmblem extends Emblem {

    // -9: You get an emblem with "Whenever a creature you control deals combat damage to a player, that player loses the game."
    public VraskaGolgariQueenEmblem() {
        this.setName("Emblem Vraska");
        availableImageSetCodes = Arrays.asList("MED", "GRN");
        this.getAbilities().add(new DealsDamageToAPlayerAllTriggeredAbility(
                Zone.COMMAND, new LoseGameTargetPlayerEffect(),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true, true
        ));
    }
}
