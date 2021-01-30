package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class DreamDevourerTest extends CardTestPlayerBase {

    @Test
    public void test_GainAndReduce() {
        removeAllCardsFromHand(playerA);

        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by 2.
        // Whenever you foretell a card, Dream Devourer gets +2/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer"); // 0/3
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears", 2); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 2); // 2 for normal cast, 2 for exile

        // bears must have foretell and normal cast
        checkPlayableAbility("normal cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        checkPlayableAbility("foretell exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: Foretell", true);
        checkPT("no boost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dream Devourer", 0, 3);

        // normal cast and no boost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after normal cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("after normal cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkPT("after normal cast - no boost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dream Devourer", 0, 3);

        // foretell for {2}
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: Fore");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after foretell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("after foretell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPT("after foretell - boosted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dream Devourer", 0 + 2, 3 + 0);

        // boost must ends on next turn
        checkPT("turn 2 - boosted end", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Dream Devourer", 0, 3);

        // turn 3 - spend mana for cost reduce test (4 green -> 1 green)
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 3);

        // turn 3 - can play with cost reduce for {G}
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell {G}");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after foretell cast", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 2);
        checkExileCount("after foretell  cast", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkPT("after foretell cast - no boost", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dream Devourer", 0, 3);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
    }
}
