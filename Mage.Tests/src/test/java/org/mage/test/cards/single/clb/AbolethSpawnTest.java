package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Rowan-Gudmundsson
 */
public class AbolethSpawnTest extends CardTestPlayerBase {

    @Test
    public void TestAbolethSpawn() {
        addCard(Zone.BATTLEFIELD, playerA, "Aboleth Spawn");

        addCard(Zone.HAND, playerB, "Wall of Omens");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);

        addCard(Zone.LIBRARY, playerA, "Island", 200);
        addCard(Zone.LIBRARY, playerB, "Island", 200);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wall of Omens");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, 1);

        checkStackObject(
                "Has Aboleth trigger",
                2,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Whenever a creature entering the battlefield under an opponent's control causes a triggered ability of that creature to trigger",
                1);

        setChoice(playerA, true);

        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, 1);

        checkStackObject(
                "Has Wall of Omens trigger",
                2,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "When {this} enters the battlefield, draw a card.",
                2);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);

        execute();

        assertHandCount(playerB, 2);
        assertHandCount(playerA, 1);
    }
}
