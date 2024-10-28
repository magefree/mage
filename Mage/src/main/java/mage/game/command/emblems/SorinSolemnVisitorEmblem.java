package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class SorinSolemnVisitorEmblem extends Emblem {

    /**
     * Emblem: "At the beginning of each opponent's upkeep, that player
     * sacrifices a creature."
     */
    public SorinSolemnVisitorEmblem() {
        super("Emblem Sorin");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.COMMAND, TargetController.OPPONENT, new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "that player"), false);
        this.getAbilities().add(ability);
    }

    private SorinSolemnVisitorEmblem(final SorinSolemnVisitorEmblem card) {
        super(card);
    }

    @Override
    public SorinSolemnVisitorEmblem copy() {
        return new SorinSolemnVisitorEmblem(this);
    }
}
