package mage.game.command.planes;

import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.PlaneswalkToSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.Planes;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Plane;

/**
 * @author spjspj
 */
public class PanopticonPlane extends Plane {

    private static final String rule = "At the beginning of your draw step, draw an additional card";

    public PanopticonPlane() {
        this.setPlaneType(Planes.PLANE_PANOPTICON);

        // When you planeswalk to Panopticon, draw a card
        this.addAbility(new PlaneswalkToSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // At the beginning of your draw step, draw an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(
                Zone.COMMAND, new DrawCardSourceControllerEffect(1), TargetController.YOU, false
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, draw a card
        this.addAbility(new ChaosEnsuesTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private PanopticonPlane(final PanopticonPlane plane) {
        super(plane);
    }

    @Override
    public PanopticonPlane copy() {
        return new PanopticonPlane(this);
    }
}
