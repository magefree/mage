package mage.game.command.planes;

import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Plane;

/**
 * @author spjspj
 */
public class TrugaJunglePlane extends Plane {

    public TrugaJunglePlane() {
        this.setPlaneType(Planes.PLANE_TRUGA_JUNGLE);

        // All lands have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(
                Zone.COMMAND,
                new GainAbilityAllEffect(
                        new AnyColorManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS
                )
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, reveal the top three cards of your libary.  Put all land cards revealed this way into your hand the rest on the bottom of your library in any order.
        this.addAbility(new ChaosEnsuesTriggeredAbility(new RevealLibraryPutIntoHandEffect(
                3, StaticFilters.FILTER_CARD_LANDS, Zone.LIBRARY
        )));
    }

    private TrugaJunglePlane(final TrugaJunglePlane plane) {
        super(plane);
    }

    @Override
    public TrugaJunglePlane copy() {
        return new TrugaJunglePlane(this);
    }
}
