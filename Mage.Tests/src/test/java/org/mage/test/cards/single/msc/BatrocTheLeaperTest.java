package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class BatrocTheLeaperTest extends CardTestPlayerBase {

    @Test
    public void testNotKicked() {
        addCard(Zone.HAND, playerA, "Batroc the Leaper");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Batroc the Leaper");
        setChoice(playerA, false); // Do not pay multikicker

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Batroc the Leaper", 1);
        assertCounterCount(playerA, "Batroc the Leaper", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Batroc the Leaper", 2, 2);
        assertLife(playerB, 20);
    }

    @Test
    public void testKickedTwiceDealsPowerToTwoTargets() {
        addCard(Zone.HAND, playerA, "Batroc the Leaper");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");    // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Batroc the Leaper");
        setChoice(playerA, true);  // Multikick 1
        setChoice(playerA, true);  // Multikick 2
        setChoice(playerA, false); // Stop kicking
        addTarget(playerA, "Hill Giant^Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Enters with two +1/+1 counters -> 4/4, deals 4 damage to Hill Giant and 4 damage to Grizzly Bears
        assertPermanentCount(playerA, "Batroc the Leaper", 1);
        assertCounterCount(playerA, "Batroc the Leaper", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Batroc the Leaper", 4, 4);
        assertPermanentCount(playerB, "Hill Giant", 0);
        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }
}
