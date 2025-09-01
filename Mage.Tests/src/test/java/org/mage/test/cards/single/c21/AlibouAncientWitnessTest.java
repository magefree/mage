package org.mage.test.cards.single.c21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AlibouAncientWitnessTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AlibouAncientWitness Alibou, Ancient Witness {3}{R}{W}
     * Legendary Artifact Creature — Golem
     * Other artifact creatures you control have haste.
     * Whenever one or more artifact creatures you control attack, Alibou deals X damage to any target and you scry X, where X is the number of tapped artifacts you control.
     * 4/5
     */
    private static final String alibou = "Alibou, Ancient Witness";

    @Test
    public void test_scry2() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancient Amphitheater");
        addCard(Zone.LIBRARY, playerA, "Baleful Strix");

        addCard(Zone.BATTLEFIELD, playerA, alibou);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        attack(1, playerA, alibou, playerB);
        attack(1, playerA, "Memnite", playerB);
        attack(1, playerA, "Goblin Piker", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);

        addTarget(playerA, playerB); // Alibou's trigger
        addTarget(playerA, "Baleful Strix^Ancient Amphitheater"); // put on bottom with Scry 2
        setChoice(playerA, "Ancient Amphitheater"); // order for bottom

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 4 - 1 - 2 - 2 - 2);
    }
}
