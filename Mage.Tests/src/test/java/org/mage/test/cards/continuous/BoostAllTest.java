
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BoostAllTest extends CardTestPlayerBase {

    /**
     * Verdeloth the Ancient pump effect affect it self. Printed "Other"
     */
    @Test
    public void testBoostWithOther() {
        // Kicker {X}
        // Saproling creatures and other Treefolk creatures get +1/+1.
        // When Verdeloth the Ancient enters the battlefield, if it was kicked, put X 1/1 green Saproling creature tokens onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Verdeloth the Ancient", 1); // 4/7
        addCard(Zone.BATTLEFIELD, playerA, "Heartwood Treefolk", 1); // 3/4
        addCard(Zone.BATTLEFIELD, playerB, "Heartwood Treefolk", 1); // 3/4

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Verdeloth the Ancient", 4, 7);
        assertPowerToughness(playerA, "Heartwood Treefolk", 4, 5);
        assertPowerToughness(playerA, "Heartwood Treefolk", 4, 5);

    }

    @Test
    public void testTribalUnity() {
        // Creatures of the chosen type get +X/+X
        addCard(Zone.HAND, playerA, "Tribal Unity", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Akki Blizzard-Herder", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Bloom Tender", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Akki Blizzard-Herder", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Bloom Tender", 1); // 1/1
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tribal Unity");
        setChoice(playerA, "X=2");
        setChoice(playerA, "Elf");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        
        assertPowerToughness(playerA, "Akki Blizzard-Herder", 1, 1);
        assertPowerToughness(playerA, "Bloom Tender", 3, 3);
        assertPowerToughness(playerB, "Akki Blizzard-Herder", 1, 1);
        assertPowerToughness(playerB, "Bloom Tender", 3, 3);
    }
}
