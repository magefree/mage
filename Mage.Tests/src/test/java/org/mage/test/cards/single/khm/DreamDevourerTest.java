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
        checkPlayableAbility("foretell exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell", true);
        checkPT("no boost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dream Devourer", 0, 3);

        // normal cast and no boost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after normal cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("after normal cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkPT("after normal cast - no boost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dream Devourer", 0, 3);

        // foretell for {2}
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore");
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

        assertPermanentCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void testSplitCard() {
        removeAllCardsFromHand(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer");
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 5);
        addCard(Zone.HAND, playerA, "Discovery // Dispersal", 2);

        checkPlayableAbility("normal cast left side", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Discovery", true);
        checkPlayableAbility("normal cast right side", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dispersal", true);
        checkPlayableAbility("foretell exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after foretell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discovery // Dispersal", 2);

        checkPlayableAbility("foretell cast left side", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {U/B}", true);
        checkPlayableAbility("foretell cast right side", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {1}{U}{B}", true);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {U/B}");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {1}{U}{B}");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after foretell cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Discovery // Dispersal", 0);

        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testMDFCLand() {
        removeAllCardsFromHand(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Akoum Warrior", 1);

        checkPlayableAbility("normal cast left side", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("play land right side", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", true);
        checkPlayableAbility("foretell exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after foretell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // Check that cost reduction worked (Foretell cost should be {3}{R})
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 2);

        checkPlayableAbility("foretell cast left side", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell {3}{R}", true);
        checkPlayableAbility("play foretold land", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Akoum Teeth", false);
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell {3}{R}");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after foretell cast", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkExileCount("after foretell cast", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Akoum Warrior", 0);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Akoum Warrior", 1);
    }

    @Test
    public void testMDFCNonland() {
        removeAllCardsFromHand(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer");
        addCard(Zone.BATTLEFIELD, playerA, "City of Brass", 4);
        addCard(Zone.HAND, playerA, "Jorn, God of Winter", 2);

        checkPlayableAbility("normal cast left side", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Jorn", true);
        checkPlayableAbility("normal cast right side", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Kaldring", true);
        checkPlayableAbility("foretell exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after foretell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jorn, God of Winter", 2);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {G}");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {U}{B}");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after foretell cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Jorn, God of Winter", 1);
        checkPermanentCount("after foretell cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Kaldring, the Rimestaff", 1);
        checkExileCount("after foretell cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Jorn, God of Winter", 0);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Jorn, God of Winter", 1);
        assertPermanentCount(playerA, "Kaldring, the Rimestaff", 1);
    }

    @Test
    public void testAdventureCard() {
        removeAllCardsFromHand(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Lonesome Unicorn", 2);

        checkPlayableAbility("creature cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lone", true);
        checkPlayableAbility("adventure cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Rider", true);
        checkPlayableAbility("foretell exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after foretell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lonesome Unicorn", 2);

        checkPlayableAbility("foretell creature cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {2}{W}", true);
        checkPlayableAbility("foretell adventure cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {W}", true);
        checkPlayableAbility("creature cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lone", false);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {W}");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);

        // Check that the creature is playable from adventure zone after casting with Foretell
        checkPlayableAbility("creature cast after foretell", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lone", true);

        // Tap 2 lands to verify cost reduction worked
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 2);
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell {2}{W}");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after foretell cast", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lonesome Unicorn", 1);
        checkPermanentCount("after foretell cast", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Knight Token", 1);

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lonesome Unicorn");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after adventure cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Lonesome Unicorn", 2);
        checkExileCount("after foretell cast", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lonesome Unicorn", 0);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lonesome Unicorn", 2);
        assertPermanentCount(playerA, "Knight Token", 1);
    }
}
