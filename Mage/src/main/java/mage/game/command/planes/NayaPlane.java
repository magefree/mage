package mage.game.command.planes;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsAllEffect;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.command.Plane;
import mage.target.TargetPermanent;

/**
 * @author spjspj
 */
public class NayaPlane extends Plane {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("red, green or white creature you control");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.WHITE)
        ));
    }

    public NayaPlane() {
        this.setPlaneType(Planes.PLANE_NAYA);

        // You may play any number of lands on each of your turns
        this.addAbility(new SimpleStaticAbility(Zone.COMMAND, new PlayAdditionalLandsAllEffect(Integer.MAX_VALUE)));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, target red, green or white creature you control gets +1/+1 until end of turn for each land you control
        Ability ability = new ChaosEnsuesTriggeredAbility(new BoostTargetEffect(
                LandsYouControlCount.instance, LandsYouControlCount.instance, Duration.EndOfTurn
        ).setText("target red, green or white creature you control gets +1/+1 until end of turn for each land you control"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private NayaPlane(final NayaPlane plane) {
        super(plane);
    }

    @Override
    public NayaPlane copy() {
        return new NayaPlane(this);
    }
}
