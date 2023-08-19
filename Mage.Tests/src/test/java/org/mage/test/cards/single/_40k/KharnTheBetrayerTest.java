package org.mage.test.cards.single._40k;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class KharnTheBetrayerTest extends CardTestCommander4Players {
    
    private static String KHARN = "Khârn the Betrayer";

    @Test
    public void testEffect() {
        addCard(Zone.BATTLEFIELD, playerA, KHARN, 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 5);
        addCard(Zone.LIBRARY, playerB, "Mountain", 5);

        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 2);
        addCard(Zone.HAND, playerC, "Lightning Bolt", 2);

        // Player C pings Kharn, which triggers his effect.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerC, "Lightning Bolt", KHARN);
        // Player A chooses Player B to gain control, draws 2 cards when losing control.
        setChoice(playerA, "PlayerB");

        // // Player C pings Kharn, triggering effect again.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerC, "Lightning Bolt", KHARN);
        // // Player B chooses player A, draws 2 cards when losing control.
        setChoice(playerB, "PlayerC");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, 3);
        assertHandCount(playerB, 3);
        assertHandCount(playerC, 0);
        assertPermanentCount(playerC, KHARN, 1);
    }
}
