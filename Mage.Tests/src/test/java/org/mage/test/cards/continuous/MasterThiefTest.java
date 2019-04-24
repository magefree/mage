
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author JayDi85
 */
public class MasterThiefTest extends CardTestPlayerBase {

    @Test
    public void testMasterThief_GetControlOnEnterBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Master Thief", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);

        // cast and get control of shield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerB, "Accorder's Shield");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 5);
        assertPermanentCount(playerA, "Master Thief", 1);
        assertPermanentCount(playerA, "Accorder's Shield", 1);

        assertPermanentCount(playerB, 0);
    }

    @Test
    public void testMasterThief_LostControlOnSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Master Thief", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bearer of the Heavens", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar", 1);

        // cast and get control of shield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerB, "Accorder's Shield");

        // sacrifice Master Thief -- must lost control
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature");
        addTarget(playerA, "Master Thief");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 10);
        assertPermanentCount(playerA, "Master Thief", 0);
        assertPermanentCount(playerA, "Accorder's Shield", 0);
        assertPermanentCount(playerA, "Ashnod's Altar", 1);
        assertPermanentCount(playerA, "Bearer of the Heavens", 1);
        assertPowerToughness(playerA, "Bearer of the Heavens", 10, 10);

        assertPermanentCount(playerB, "Accorder's Shield", 1);
        assertPermanentCount(playerB, 1);
    }

    @Test
    public void testMasterThief_LostControlOnSacrificeButArtifactAttached() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Master Thief", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bearer of the Heavens", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar", 1);

        // cast and get control of shield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerB, "Accorder's Shield");

        // attach and boost
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip {3}");
        addTarget(playerA, "Bearer of the Heavens");

        // sacrifice Master Thief -- must lost control, but attached and boosted
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature");
        addTarget(playerA, "Master Thief");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 10);
        assertPermanentCount(playerA, "Master Thief", 0);
        assertPermanentCount(playerA, "Accorder's Shield", 0);
        assertPermanentCount(playerA, "Ashnod's Altar", 1);
        assertPermanentCount(playerA, "Bearer of the Heavens", 1);
        assertPowerToughness(playerA, "Bearer of the Heavens", 10, 10 + 3);

        assertPermanentCount(playerB, "Accorder's Shield", 1);
        assertPermanentCount(playerB, 1);
    }
}
