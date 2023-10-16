package mage.game.command.planes;

import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAllEffect;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.command.Plane;
import mage.game.permanent.token.EldraziAnnihilatorToken;

/**
 * @author spjspj
 */
public class HedronFieldsOfAgadeemPlane extends Plane {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 7 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 6));
    }

    public HedronFieldsOfAgadeemPlane() {
        this.setPlaneType(Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);

        // Creatures with power 7 or greater can't attack or block 
        this.addAbility(new SimpleStaticAbility(
                Zone.COMMAND, new CantAttackBlockAllEffect(Duration.WhileOnBattlefield, filter)
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        this.addAbility(new ChaosEnsuesTriggeredAbility(new CreateTokenEffect(new EldraziAnnihilatorToken())));
    }

    private HedronFieldsOfAgadeemPlane(final HedronFieldsOfAgadeemPlane plane) {
        super(plane);
    }

    @Override
    public HedronFieldsOfAgadeemPlane copy() {
        return new HedronFieldsOfAgadeemPlane(this);
    }
}
