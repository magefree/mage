package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Plane;

/**
 * @author spjspj
 */
public class TheGreatForestPlane extends Plane {

    public TheGreatForestPlane() {
        this.setPlaneType(Planes.PLANE_THE_GREAT_FOREST);

        // Each creature assigns combat damage equal to its toughness rather than its power
        this.addAbility(new SimpleStaticAbility(Zone.COMMAND, new CombatDamageByToughnessAllEffect()));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, creatures you control get +0/+2 and gain trample until end of turn
        Ability ability = new ChaosEnsuesTriggeredAbility(new BoostControlledEffect(
                0, 2, Duration.EndOfTurn
        ).setText("creatures you control get +0/+2"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain trample until end of turn"));
        this.addAbility(ability);
    }

    private TheGreatForestPlane(final TheGreatForestPlane plane) {
        super(plane);
    }

    @Override
    public TheGreatForestPlane copy() {
        return new TheGreatForestPlane(this);
    }
}
