package org.mage.test.cards.single.unf;

import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class NearbyPlanetTest extends CardTestPlayerBase {

    /*
     * Rangeling (This card is every land type, including Plains, Island, Swamp, Mountain, Forest,
     * Desert, Gate, Lair, Locus, and all those Urza’s ones.)
     * Nearby Planet enters tapped.
     * When Nearby Planet enters, sacrifice it unless you pay {1}.
     */
    private final static String nearbyPlanet = "Nearby Planet";

    @Test
    public void testDomain() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.HAND, playerA, nearbyPlanet);
        addCard(Zone.HAND, playerA, "Might of Alara");
        // Domain - Target creature gets +1/+1 until end of turn for each basic land type among lands you control.
        addCard(Zone.BATTLEFIELD, playerA, "Kraken Hatchling"); // 0/4

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, nearbyPlanet);
        setChoice(playerA, true); // pay {1}

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Might of Alara", "Kraken Hatchling");
        setChoice(playerA, "Green"); // color of mana to add

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Might of Alara", 1);
        assertPowerToughness(playerA, "Kraken Hatchling", 5, 9);
    }

    @Test
    public void testDesert() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.HAND, playerA, nearbyPlanet);
        addCard(Zone.BATTLEFIELD, playerA, "Solitary Camel"); // 3/2
        // Solitary Camel has lifelink as long as you control a Desert or there is a Desert card in your graveyard.

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, nearbyPlanet);
        setChoice(playerA, true); // pay {1}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Solitary Camel", LifelinkAbility.getInstance(), true);
    }

}
