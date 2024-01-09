package mage.game.command.planes;

import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.command.Plane;

/**
 * @author spjspj
 */
public class TurriIslandPlane extends Plane {

    private static final FilterCard filter = new FilterCreatureCard("creaure spells");

    public TurriIslandPlane() {
        this.setPlaneType(Planes.PLANE_TURRI_ISLAND);

        // Creature spells cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.COMMAND, new SpellsCostReductionAllEffect(filter, 2)));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, reveal the top three cards of your library. Put all creature cards revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new ChaosEnsuesTriggeredAbility(new RevealLibraryPutIntoHandEffect(
                3, StaticFilters.FILTER_CARD_CREATURES, Zone.GRAVEYARD
        )));
    }

    private TurriIslandPlane(final TurriIslandPlane plane) {
        super(plane);
    }

    @Override
    public TurriIslandPlane copy() {
        return new TurriIslandPlane(this);
    }
}
