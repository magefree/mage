package org.mage.test.cards.single.c15;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class EzurisPredationTest extends CardTestPlayerBase {

    @Test
    public void test_MultipleFights_NeyithDrawsOneCard() {
        // Ezuri's Predation ruling:
        // Each of the "fights" happens at the same time.
        // (2015-11-04)
        //
        // Neyith of the Dire Hunt ruling:
        // You draw just one card as Neyith’s first ability resolves, no matter how many creatures you control
        // fought or became blocked.
        // (2020-06-23)
        //
        // For each creature your opponents control, create a 4/4 green Phyrexian Beast creature token.
        // Each of those tokens fights a different one of those creatures.
        addCard(Zone.HAND, playerA, "Ezuri's Predation"); // {5}{G}{G}{G}
        // {T}: Add {G}.
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        //
        // Whenever one or more creatures you control fight or become blocked, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Neyith of the Dire Hunt");
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ezuri's Predation");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 2);
        assertHandCount(playerA, 1); // Neyith's draw
    }
}
