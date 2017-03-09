package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JRHerlehy
 *         Created on 3/9/17.
 */
public class ClutchOfUndeathTest extends CardTestPlayerBase{

    @Test
    public void testEnchantNonZombie() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Griselbrand");
        addCard(Zone.HAND, playerA, "Clutch of Undeath");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clutch of Undeath", "Griselbrand");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Griselbrand", 4, 4);
    }

    @Test
    public void testEnchantZombie() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Gurmag Angler");
        addCard(Zone.HAND, playerA, "Clutch of Undeath");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clutch of Undeath", "Gurmag Angler");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Gurmag Angler", 8, 8);
    }

    @Test
    public void testEnchantChangeling() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Amoeboid Changeling");
        addCard(Zone.HAND, playerA, "Clutch of Undeath");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clutch of Undeath", "Amoeboid Changeling");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Amoeboid Changeling", 4, 4);
    }
}
