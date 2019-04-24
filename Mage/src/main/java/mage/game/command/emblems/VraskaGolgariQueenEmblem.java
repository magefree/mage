package mage.game.command.emblems;

import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 *
 * @author TheElk801
 */
public final class VraskaGolgariQueenEmblem extends Emblem {

    // -9: You get an emblem with "Whenever a creature you control deals combat damage to a player, that player loses the game."
    public VraskaGolgariQueenEmblem() {
        this.setName("Emblem Vraska");
        this.setExpansionSetCodeForImage("GRN");
        this.getAbilities().add(new DealsDamageToAPlayerAllTriggeredAbility(
                new LoseGameTargetPlayerEffect(),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.PLAYER, true
        ));
    }
}
