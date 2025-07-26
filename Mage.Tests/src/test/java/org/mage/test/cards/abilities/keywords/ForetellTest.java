package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jeffwadsworth
 */
public class ForetellTest extends CardTestPlayerBase {

    @Test
    public void testForetellKeyword() {
        // verify that the foretold card is in the exile zone
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);  // Foretell cost 2 mana
        addCard(Zone.HAND, playerA, "Behold the Multiverse");  // (Instant) Scry 2 and draw 2 cards
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Behold the Multiverse", 1);

    }

    @Test
    public void testForetoldCastSameTurnAsForetold() {
        // verify that foretold card can't be cast the same turn it was foretold
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);  // Foretell cost 2 mana and {1}{U} for foretell cast from exile
        addCard(Zone.HAND, playerA, "Behold the Multiverse");  // (Instant) Scry 2 and draw 2 cards
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore");
        checkPlayableAbility("Can't cast turn it was forefold", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell {1}{U}", false);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Behold the Multiverse", 1); // still in exile because it can't be cast the same turn
    }

    @Test
    public void testForetoldCastOtherTurnAsForetold() {
        // verify that foretold card can be cast on a turn other than the one it was foretold
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);  // Foretell cost 2 mana and {1}{U} for foretell cast from exile
        addCard(Zone.HAND, playerA, "Behold the Multiverse");  // (Instant) Scry 2 and draw 2 cards

        // Setting up for the scry 2
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Altar of Dementia");
        addCard(Zone.LIBRARY, playerA, "Millstone");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {1}{U}");
        addTarget(playerA, "Millstone"); // scrying to the bottom.

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Behold the Multiverse", 0); // no longer in exile
        assertGraveyardCount(playerA, "Behold the Multiverse", 1);  // now in graveyard
        assertHandCount(playerA, 2); // 2 cards drawn
    }

    @Test
    public void testDreamDevourerTrigger() {
        // Issue #8876
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer", 1); // if a card is foretelled, this gets +2 power
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5); // 3 mana for suspend and 2 for foretell
        addCard(Zone.HAND, playerA, "Sol Talisman", 1);  // Suspend card
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // card to foretell

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Susp"); // suspend the Sol Talisman
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore"); // foretell the Lightning Bolt
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Sol Talisman", 1); // suspend card in exile
        assertExileCount(playerA, "Lightning Bolt", 1); // foretold card in exile
        assertPowerToughness(playerA, "Dream Devourer", 2, 3);  // +2 power boost from trigger due to foretell of Lightning Bolt
    }


    // Tests needed to check watcher scope issue (see issue #7493 and issue #13774)

    private static final String scornEffigy = "Scorn Effigy"; // {3} 2/3 foretell {0}
    private static final String poisonCup = "Poison the Cup"; // {1}{B}{B} instant destroy target creature
    // Foretell {1}{B}, if spell was foretold, scry 2
    private static final String flamespeaker = "Flamespeaker Adept"; // {2}{R} 2/3
    // Whenever you scry, gets +2/+0 and first strike until end of turn
    private static final String chancemetElves = "Chance-Met Elves"; // {2}{G} 3/2
    // Whenever you scry, gets a +1/+1 counter, triggers once per turn

    private static final String cardE = "Elite Vanguard";
    private static final String cardD = "Devilthorn Fox";
    private static final String cardC = "Canopy Gorger";
    private static final String cardB = "Barbtooth Wurm";
    private static final String cardA = "Alaborn Trooper";

    private void setupLibrariesEtc() {
        // make a library of 5 cards, bottom : E D C B A : top
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, cardE);
        addCard(Zone.LIBRARY, playerA, cardD);
        addCard(Zone.LIBRARY, playerA, cardC);
        addCard(Zone.LIBRARY, playerA, cardB);
        addCard(Zone.LIBRARY, playerA, cardA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, cardE);
        addCard(Zone.LIBRARY, playerB, cardD);
        addCard(Zone.LIBRARY, playerB, cardC);
        addCard(Zone.LIBRARY, playerB, cardB);
        addCard(Zone.LIBRARY, playerB, cardA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, flamespeaker);
        addCard(Zone.BATTLEFIELD, playerB, chancemetElves);
        addCard(Zone.HAND, playerA, scornEffigy);
    }

    @Test
    public void testForetellWatcherPlayerA() {
        setupLibrariesEtc();
        addCard(Zone.HAND, playerA, poisonCup);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scornEffigy);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        checkExileCount("foretold in exile", 2, PhaseStep.PRECOMBAT_MAIN, playerA, poisonCup, 1);
        // turn 3, draw card A
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {1}{B}", chancemetElves);
        // foretold, so scry 2 (cards B and C)
        addTarget(playerA, cardB); // scrying B bottom (C remains on top)

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, scornEffigy, 2, 3);
        assertGraveyardCount(playerA, poisonCup, 1);
        assertGraveyardCount(playerB, chancemetElves, 1);
        assertPowerToughness(playerA, flamespeaker, 4, 3);
    }

    @Test
    public void testForetellWatcherPlayerB() {
        setupLibrariesEtc();
        addCard(Zone.HAND, playerB, poisonCup);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scornEffigy);
        // turn 2, draw card A
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Foretell");
        checkExileCount("foretold in exile", 2, PhaseStep.PRECOMBAT_MAIN, playerB, poisonCup, 1);
        // turn 4, draw card B
        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Foretell {1}{B}", flamespeaker);
        // foretold, so scry 2 (cards C and D)
        addTarget(playerA, cardD); // scrying D bottom (C remains on top)

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, scornEffigy, 2, 3);
        assertGraveyardCount(playerB, poisonCup, 1);
        assertGraveyardCount(playerA, flamespeaker, 1);
        assertPowerToughness(playerB, chancemetElves, 4, 3);
    }



}
