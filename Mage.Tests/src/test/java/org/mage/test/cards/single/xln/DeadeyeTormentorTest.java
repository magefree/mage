package org.mage.test.cards.single.xln;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class DeadeyeTormentorTest extends CardTestPlayerBase {

    private static final String tormentor = "Deadeye Tormentor"; // 2B 2/2
    // Raid — When Deadeye Tormentor enters, if you attacked this turn, target opponent discards a card.

    @Test
    public void testConditionFalse() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, tormentor);
        addCard(Zone.HAND, playerB, "Ornithopter");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, tormentor);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Ornithopter", 1);
    }


    @Test
    public void testConditionTrue() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, tormentor);
        addCard(Zone.HAND, playerB, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");

        attack(1, playerA, "Raging Goblin", playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, tormentor);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Ornithopter", 0);
        assertGraveyardCount(playerB, "Ornithopter", 1);
    }
}
