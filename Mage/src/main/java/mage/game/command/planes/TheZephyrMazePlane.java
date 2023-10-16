package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.command.Plane;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author spjspj
 */
public class TheZephyrMazePlane extends Plane {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creatures with flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures without flying");

    static {
        filter1.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    private static final String flyingRule = "Creatures with flying get +2/+0";
    private static final String walkingRule = "Creatures without flying get -2/-0";

    public TheZephyrMazePlane() {
        this.setPlaneType(Planes.PLANE_THE_ZEPHYR_MAZE_FOG);

        // Creatures with flying get +2/+0
        this.addAbility(new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostAllEffect(
                        2, 0, Duration.WhileOnBattlefield, filter1, false
                )
        ));

        // Creatures without flying get -2/+0
        this.addAbility(new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostAllEffect(
                        -2, 0, Duration.WhileOnBattlefield, filter2, false
                )
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, target creature gains flying until end of turn
        Ability ability = new ChaosEnsuesTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TheZephyrMazePlane(final TheZephyrMazePlane plane) {
        super(plane);
    }

    @Override
    public TheZephyrMazePlane copy() {
        return new TheZephyrMazePlane(this);
    }
}
