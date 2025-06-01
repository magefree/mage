package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SianiEyeOfTheStormTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SianiEyeOfTheStorm Siani, Eye of the Storm} {3}{U}
     * Legendary Creature — Djinn Monk
     * Flying
     * Whenever Siani attacks, scry X, where X is the number of attacking creatures with flying.
     * Partner (You can have two commanders if both have partner.)
     * 3/2
     */
    private static final String siani = "Siani, Eye of the Storm";

    @Test
    public void test_scry2() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancient Amphitheater");
        addCard(Zone.LIBRARY, playerA, "Baleful Strix");

        addCard(Zone.BATTLEFIELD, playerA, siani);
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        attack(1, playerA, siani, playerB);
        attack(1, playerA, "Air Elemental", playerB);
        attack(1, playerA, "Goblin Piker", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);

        addTarget(playerA, "Baleful Strix^Ancient Amphitheater"); // put on bottom with Scry 2
        setChoice(playerA, "Ancient Amphitheater"); // order for bottom

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 4 - 2 - 2);
    }
}
