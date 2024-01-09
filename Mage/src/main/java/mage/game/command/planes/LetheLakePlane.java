package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.constants.Planes;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Plane;
import mage.target.TargetPlayer;

/**
 * @author spjspj
 */
public class LetheLakePlane extends Plane {

    public LetheLakePlane() {
        this.setPlaneType(Planes.PLANE_LETHE_LAKE);

        // At the beginning of your upkeep, put the top ten cards of your libary into your graveyard
        this.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(
                Zone.COMMAND, new MillCardsControllerEffect(10), TargetController.YOU, false
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, target player puts the top ten cards of their library into their graveyard
        Ability ability = new ChaosEnsuesTriggeredAbility(new MillCardsTargetEffect(10));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LetheLakePlane(final LetheLakePlane plane) {
        super(plane);
    }

    @Override
    public LetheLakePlane copy() {
        return new LetheLakePlane(this);
    }
}
