
package org.mage.test.commander.FFA3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

/**
 *
 * @author LevelX2
 */
public class SerraAscendantTest extends CardTestCommander3PlayersFFA {

    /**
     * Serra Ascendant is not working properly. Playing commander free for all,
     * and when life total was less than 30 Serra remained a 6/6.
     */
    @Test
    public void TestChangePTTo11() {

        // Lifelink (Damage dealt by this creature also causes you to gain that much life.)
        // As long as you have 30 or more life, Serra Ascendant gets +5/+5 and has flying.
        addCard(Zone.HAND, playerA, "Serra Ascendant"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Draw cards equal to the power of target creature you control.
        addCard(Zone.HAND, playerA, "Soul's Majesty"); // Sorcery - {4}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.HAND, playerC, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Ascendant", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul's Majesty", "Serra Ascendant");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerC, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerC, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, "Soul's Majesty", 1);
        assertHandCount(playerA, 7); // 6 from Soul's Majesty + 1 from draw phase
        assertGraveyardCount(playerB, "Lightning Bolt", 2);
        assertGraveyardCount(playerC, "Lightning Bolt", 2);
        assertPermanentCount(playerA, "Serra Ascendant", 1);
        assertPowerToughness(playerA, "Serra Ascendant", 1, 1);
        assertLife(playerA, 28);
        assertLife(playerB, 40);
        assertLife(playerC, 40);
    }

}
