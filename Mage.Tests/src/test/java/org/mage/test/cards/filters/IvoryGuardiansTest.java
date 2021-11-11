package org.mage.test.cards.filters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author nigelzor
 */
public class IvoryGuardiansTest extends CardTestPlayerBase {

    @Test
    public void testOneGuardian() {
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Hero");
        addCard(Zone.BATTLEFIELD, playerA, "Ivory Guardians");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Goblin Hero", 2, 2);
        assertPowerToughness(playerA, "Ivory Guardians", 3, 3);
    }

    @Test
    public void testOneGuardianAndRedToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Dragon Fodder");
        addCard(Zone.BATTLEFIELD, playerB, "Ivory Guardians");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dragon Fodder");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Goblin Token", 1, 1);
        assertPowerToughness(playerB, "Ivory Guardians", 3, 3);
    }

    @Test
    public void testOneGuardianAndRedPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Hero");
        addCard(Zone.BATTLEFIELD, playerA, "Ivory Guardians");
        addCard(Zone.BATTLEFIELD, playerB, "Mons's Goblin Raiders");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Goblin Hero", 2, 2);
        assertPowerToughness(playerA, "Ivory Guardians", 4, 4);
        assertPowerToughness(playerB, "Mons's Goblin Raiders", 1, 1);
    }

    @Test
    public void testTwoGuardiansAndRedPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Hero");
        addCard(Zone.BATTLEFIELD, playerA, "Ivory Guardians");
        addCard(Zone.BATTLEFIELD, playerA, "Ivory Guardians");
        addCard(Zone.BATTLEFIELD, playerB, "Mons's Goblin Raiders");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Goblin Hero", 2, 2);
        assertPowerToughness(playerA, "Ivory Guardians", 5, 5);
        assertPowerToughness(playerB, "Mons's Goblin Raiders", 1, 1);
    }

}
