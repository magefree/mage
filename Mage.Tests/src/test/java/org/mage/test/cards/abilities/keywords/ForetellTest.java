package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
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
        addTarget(playerB, cardD); // scrying D bottom (C remains on top)

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, scornEffigy, 2, 3);
        assertGraveyardCount(playerB, poisonCup, 1);
        assertGraveyardCount(playerA, flamespeaker, 1);
        assertPowerToughness(playerB, chancemetElves, 4, 3);
    }

    @Test
    public void testRanar() {
        skipInitShuffling();
        String ranar = "Ranar the Ever-Watchful"; // 2WU 2/3 Flying Vigilance
        // The first card you foretell each turn costs {0} to foretell.
        // Whenever one or more cards are put into exile from your hand or a spell or ability you control exiles
        // one or more permanents from the battlefield, create a 1/1 white Spirit creature token with flying.
        addCard(Zone.BATTLEFIELD, playerA, ranar);
        addCard(Zone.BATTLEFIELD, playerA, "Sage of the Falls"); // may loot on creature ETB
        addCard(Zone.HAND, playerA, poisonCup);
        addCard(Zone.LIBRARY, playerA, scornEffigy);
        addCard(Zone.HAND, playerA, "Wastes");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell"); // poison the cup
        setChoice(playerA, true); // yes to loot
        setChoice(playerA, "Wastes"); // discard

        checkExileCount("Poison the Cup foretold", 1, PhaseStep.BEGIN_COMBAT, playerA, poisonCup, 1);
        checkHandCardCount("scorn effigy drawn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, scornEffigy, 1);
        checkPlayableAbility("can't foretell another for free", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell", false);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell"); // scorn effigy
        setChoice(playerA, false); // no loot

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Spirit Token", 2);
        assertExileCount(playerA, 2);
        assertGraveyardCount(playerA, "Wastes", 1);

    }

    @Test
    public void testCosmosCharger() {
        addCard(Zone.BATTLEFIELD, playerA, "Cosmos Charger");
        // Foretelling cards from your hand costs {1} less and can be done on any player’s turn.

        addCard(Zone.HAND, playerA, scornEffigy);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");

        activateAbility(2, PhaseStep.UPKEEP, playerA, "Foretell");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, scornEffigy, 1);
    }

    @Test
    public void testAlrund() {
        String alrund = "Alrund, God of the Cosmos";
        // Alrund gets +1/+1 for each card in your hand and each foretold card you own in exile.

        addCard(Zone.BATTLEFIELD, playerA, alrund); // 1/1
        addCard(Zone.HAND, playerA, scornEffigy);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Cadaverous Bloom");
        // Exile a card from your hand: Add {B}{B} or {G}{G}.

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Exile a card from your hand: Add {B}{B}");
        setChoice(playerA, "Lightning Bolt");
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Foretell");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertExileCount(playerA, scornEffigy, 1);
        assertPowerToughness(playerA, alrund, 2, 2);
    }

    private static final String valkyrie = "Ethereal Valkyrie"; // 4/4 flying
    // Whenever this creature enters or attacks, draw a card, then exile a card from your hand face down.
    // It becomes foretold. Its foretell cost is its mana cost reduced by {2}.

    @Test
    public void testEtherealValkyrie() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        String saga = "Niko Defies Destiny";
        // I - You gain 2 life for each foretold card you own in exile.
        // II - Add {W}{U}. Spend this mana only to foretell cards or cast spells that have foretell.
        String crab = "Fortress Crab"; // 3U 1/6
        String puma = "Stonework Puma"; // {3} 2/2
        addCard(Zone.BATTLEFIELD, playerA, valkyrie);
        addCard(Zone.HAND, playerA, saga);
        addCard(Zone.HAND, playerA, crab);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 5);
        addCard(Zone.LIBRARY, playerA, "Wastes");
        addCard(Zone.LIBRARY, playerA, puma);

        attack(1, playerA, valkyrie, playerB);
        addTarget(playerA, crab); // exile becomes foretold

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, saga); // gain 2 life
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkExileCount("crab foretold", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, crab, 1);
        checkPlayableAbility("can't cast foretold same turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell", false);

        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, puma);

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 16);
        assertCounterCount(saga, CounterType.LORE, 2);
        assertPowerToughness(playerA, crab, 1, 6);
        assertPowerToughness(playerA, valkyrie, 4, 4);
        assertPowerToughness(playerA, puma, 2, 2);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Wastes", 1);
        assertTappedCount("Tundra", true, 5);
    }

    @Test
    public void testForetoldNotForetell() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Wastes");
        addCard(Zone.LIBRARY, playerA, "Darksteel Citadel");
        addCard(Zone.BATTLEFIELD, playerA, valkyrie);
        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer");
        addCard(Zone.HAND, playerA, "Papercraft Decoy");

        attack(1, playerA, valkyrie, playerB);
        addTarget(playerA, "Papercraft Decoy"); // exile becomes foretold

        checkPT("Dream Devourer not boosted", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dream Devourer", 0, 3);
        checkPlayableAbility("Can't cast this turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell", false);
        checkHandCardCount("card drawn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Darksteel Citadel", 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
        assertPowerToughness(playerA, "Papercraft Decoy", 2, 1);
        assertPowerToughness(playerA, "Dream Devourer", 0, 3);
        assertHandCount(playerA, 2);
    }


}
