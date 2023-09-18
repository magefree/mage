package mage.game.command.emblems;

import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class VraskaGolgariQueenEmblem extends Emblem {

    // -9: You get an emblem with "Whenever a creature you control deals combat damage to a player, that player loses the game."
    public VraskaGolgariQueenEmblem() {
        super("Emblem Vraska");
        this.getAbilities().add(new DealsDamageToAPlayerAllTriggeredAbility(
                Zone.COMMAND, new LoseGameTargetPlayerEffect(),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true, true
        ));
    }

    private VraskaGolgariQueenEmblem(final VraskaGolgariQueenEmblem card) {
        super(card);
    }

    @Override
    public VraskaGolgariQueenEmblem copy() {
        return new VraskaGolgariQueenEmblem(this);
    }
}
