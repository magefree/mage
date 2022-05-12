package org.mage.test.cards.single.dis;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AzoriusAethermageTest extends CardTestPlayerBase {

    /**
     * Whenever you bounce a permanent (tokens included) you may pay {1}, if you do, draw a card
     */
    @Test
    public void testBouncedLand() {
        /*
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // Used for paying ability cost
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2*3);
        addCard(Zone.BATTLEFIELD, playerA, "Toggo, Goblin Weaponsmith");
        addCard(Zone.HAND, playerA, "Boomerang",3);

        // Permanents to bounce
        addCard(Zone.HAND, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Azorius Aethermage");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");    // Create a Rock token with Toggo
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boomerang", "Plains");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        setChoice(playerA, "Yes");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boomerang", "Rock");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        setChoice(playerA, "Yes");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boomerang", "Azorius Aethermage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        setChoice(playerA, "Yes");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertAllCommandsUsed();

        // 3 cards bounced: plains, token, and aethermage, but the token never makes it to the hand -> +2 cards in hand
        // 3 cards drawn -> +3 cards in hand
        assertHandCount(playerA, (1+1) + (1) + (1+1));
        */
    }
}
