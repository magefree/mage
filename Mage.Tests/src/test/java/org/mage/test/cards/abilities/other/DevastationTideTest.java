
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DevastationTideTest extends CardTestPlayerBase {

    @Test
    public void testReturnNonLandPermanents() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Devastation Tide"); // {3}{U}{U}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // Creature
        addCard(Zone.BATTLEFIELD, playerA, "Vampiric Rites"); // Enchantment
        addCard(Zone.BATTLEFIELD, playerA, "Hedron Archive"); // Artifact
        addCard(Zone.BATTLEFIELD, playerA, "Karn Liberated"); // Planeswalker
        addCard(Zone.BATTLEFIELD, playerA, "Nimbus Maze", 1); // Land

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // Creature
        addCard(Zone.BATTLEFIELD, playerB, "Vampiric Rites"); // Enchantment
        addCard(Zone.BATTLEFIELD, playerB, "Hedron Archive"); // Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Karn Liberated"); // Planeswalker
        addCard(Zone.BATTLEFIELD, playerB, "Nimbus Maze", 1); // Land

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devastation Tide");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, "Vampiric Rites", 1);
        assertHandCount(playerA, "Hedron Archive", 1);
        assertHandCount(playerA, "Karn Liberated", 1);
        assertHandCount(playerA, "Nimbus Maze", 0);

        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Vampiric Rites", 1);
        assertHandCount(playerB, "Hedron Archive", 1);
        assertHandCount(playerB, "Karn Liberated", 1);
        assertHandCount(playerB, "Nimbus Maze", 0);

    }

}
