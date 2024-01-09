package mage.game.command.planes;

import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Plane;

/**
 * @author spjspj
 */
public class TazeemPlane extends Plane {

    public TazeemPlane() {
        this.setPlaneType(Planes.PLANE_TAZEEM);

        // Creatures can't block
        this.addAbility(new SimpleStaticAbility(
                Zone.COMMAND, new CantBlockAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.WhileOnBattlefield)
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, draw a card for each land you control
        this.addAbility(new ChaosEnsuesTriggeredAbility(new DrawCardSourceControllerEffect(LandsYouControlCount.instance).setText("draw a card for each land you control")));
    }

    private TazeemPlane(final TazeemPlane plane) {
        super(plane);
    }

    @Override
    public TazeemPlane copy() {
        return new TazeemPlane(this);
    }
}
