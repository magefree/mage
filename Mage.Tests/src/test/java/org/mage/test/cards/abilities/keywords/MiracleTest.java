package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.94a
 * Miracle is a static ability linked to a triggered ability. (See rule 603.11.) “Miracle [cost]” means “You
 * may reveal this card from your hand as you draw it if it’s the first card you’ve drawn this turn. When you
 * reveal this card this way, you may cast it by paying [cost] rather than its mana cost.”
 * <p>
 * 702.94b
 * If a player chooses to reveal a card using its miracle ability, they play with that card revealed until
 * that card leaves their hand, that ability resolves, or that ability otherwise leaves the stack. (See rule
 * 701.20a.)
 *
 * @author noxx, JayDi85
 */
public class MiracleTest extends CardTestPlayerBase {

    /**
     * Tests miracle alternative cost
     */
    @Test
    public void testMiracleCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Put all creatures on the bottom of their owners' libraries.
        addCard(Zone.LIBRARY, playerA, "Terminus");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // check Terminus was played
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

    /**
     * Tests working on extra turn
     */
    @Test
    public void testMiracleOnExtraTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.LIBRARY, playerA, "Terminus");
        addCard(Zone.LIBRARY, playerA, "Temporal Mastery");
        addCard(Zone.HAND, playerA, "Think Twice");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // check Terminus was played
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

    /**
     * Test that you can cast a card by miracle if you don't put it back to library before casting
     */
    @Test
    public void testMiracleWillWorkFromHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Thunderous Wrath"); // must be the top most card
        addCard(Zone.HAND, playerA, "Brainstorm");
        skipInitShuffling();

        castSpell(1, PhaseStep.UPKEEP, playerA, "Brainstorm");
        addTarget(playerA, "Forest");
        addTarget(playerA, "Plains");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Brainstorm", 1);
        assertHandCount(playerA, "Thunderous Wrath", 0);
        assertGraveyardCount(playerA, "Thunderous Wrath", 1);
        assertHandCount(playerA, 0);
        // check Thunderous Wrath was played
        assertLife(playerA, 20);
        assertLife(playerB, 15);

    }

    @Test
    public void test_MultipleMiracleInstances_PrintedAndGranted() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Put all creatures on the bottom of their owners' libraries.
        // Miracle {W}
        addCard(Zone.LIBRARY, playerA, "Terminus"); // has printed Miracle {W} + granted Miracle {0}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        // Miracle is implemented via DRAW_CARD replacement effects, so two Miracle instances prompt for replacement order;
        // this extra choice can be removed if Miracle is rewritten to another mechanism.
        setChoice(playerA, "Terminus"); // choose replacement effect order
        setChoice(playerA, true); // reveal Terminus for miracle
        // Two miracle triggers go on the stack; put Miracle {0} on top to resolve first
        setChoice(playerA, "Miracle {W}"); // bottom of stack (resolves second, card already left hand)
        setChoice(playerA, true); // cast Terminus for Miracle {0}
        setChoice(playerA, false); // decline leftover Miracle {W} trigger (card already left hand)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Terminus", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Molecule Man", 0);
    }

    @Test
    public void test_MultipleMiracleInstances_FirstStifled_SecondStillCasts() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Put all creatures on the bottom of their owners' libraries.
        // Miracle {W}
        addCard(Zone.LIBRARY, playerA, "Terminus"); // has printed Miracle {W} + granted Miracle {0}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); // pays Miracle {W} if Miracle {0} is countered
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        // Counter target activated or triggered ability.
        addCard(Zone.HAND, playerB, "Stifle"); // {U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        // Miracle is implemented via DRAW_CARD replacement effects, so two Miracle instances prompt for replacement order;
        // this extra choice can be removed if Miracle is rewritten to another mechanism.
        setChoice(playerA, "Terminus"); // choose replacement effect order
        setChoice(playerA, true); // reveal Terminus for miracle
        setChoice(playerA, "Miracle {W}"); // Miracle {W} on bottom, Miracle {0} on top
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // let Think Twice resolve so miracle triggers are on stack

        // PlayerB counters the top Miracle {0} trigger; Miracle {W} trigger still resolves and casts Terminus
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Stifle", "stack ability (Miracle {0}");
        setChoice(playerA, true); // cast Terminus for Miracle {W}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Terminus", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertTappedCount("Plains", true, 1);
    }

    @Test
    public void test_AlreadyDrewCardBeforeMiracleGranterEntered_NoMiracle() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.HAND, playerA, "Molecule Man"); // {6}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        // Draw a card.
        addCard(Zone.HAND, playerA, "Opt"); // {U}
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // second draw this turn
        addCard(Zone.LIBRARY, playerA, "Forest"); // first draw this turn (via Opt)

        // Draw first card before Molecule Man is on the battlefield
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opt");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry 1: keep Forest on top
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Molecule Man
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Molecule Man");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Draw second card this turn -> must NOT trigger miracle
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_CardLeavesHandBeforeMiracleResolves_CantCast() {
        // Terminus ruling:
        // If the card with miracle leaves your hand before the triggered ability resolves, you won't be able to cast it
        // using its miracle ability.
        // (2018-07-13)
        skipInitShuffling();

        // Draw three cards, then put two cards from your hand on top of your library in any order.
        addCard(Zone.HAND, playerA, "Brainstorm"); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Forest");
        // Put all creatures on the bottom of their owners' libraries.
        // Miracle {W}
        addCard(Zone.LIBRARY, playerA, "Terminus"); // top card -> first draw
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); // could pay Miracle {W} if it were still in hand
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brainstorm");
        setChoice(playerA, true); // reveal Terminus for miracle
        // Put Terminus and Forest back on top of library during Brainstorm resolution
        addTarget(playerA, "Terminus"); // first card put on top
        addTarget(playerA, "Forest"); // second card put on top
        setChoice(playerA, true); // optional miracle trigger resolves, but Terminus left hand -> cannot cast

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1); // Terminus was not cast
        assertLibraryCount(playerA, "Terminus", 1);
    }

    @Test
    public void test_SkippedUntapStep_Stasis_MiracleStillResetsEachTurn() {
        skipInitShuffling();

        // Players skip their untap steps.
        // At the beginning of your upkeep, sacrifice Stasis unless you pay {U}.
        addCard(Zone.BATTLEFIELD, playerA, "Stasis");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // pay Stasis upkeep on turn 1 and turn 3
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Terminus: Miracle {W}
        addCard(Zone.LIBRARY, playerA, "Terminus"); // drawn turn 3 draw step (first draw of turn 3, untap step skipped)
        addCard(Zone.LIBRARY, playerA, "Forest"); // drawn turn 1 via Think Twice (first draw of turn 1)
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); // pays Miracle {W} on turn 3
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        // Turn 1 upkeep: pay {U} for Stasis
        setChoice(playerA, true); // pay {U} for Stasis on turn 1
        // Turn 1: draw Forest via Think Twice
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        // Turn 3 upkeep: pay {U} for Stasis (untap step was skipped!)
        setChoice(playerA, true); // pay {U} for Stasis on turn 3
        // Turn 3 draw step: draw Terminus as first card of turn 3 -> Miracle MUST trigger despite skipped untap step
        setChoice(playerA, true); // reveal Terminus for miracle
        setChoice(playerA, true); // cast Terminus for Miracle {W}

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Terminus", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }

    @Test
    public void test_TeferiTimeRaveler_PreventsMiracleCastDuringDrawStep() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerB, "Molecule Man");
        // Each opponent can cast spells only any time they could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Time Raveler");
        //
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // drawn in turn 2 draw step -> miracle {0}

        // Turn 2 draw step: playerB reveals Grizzly Bears for miracle, trigger resolves during draw step,
        // but Teferi prevents casting any spell during the draw step.
        setChoice(playerB, true); // reveal Grizzly Bears for miracle
        setChoice(playerB, true); // try to cast Grizzly Bears for miracle {0}

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }

    @Test
    public void test_Humility_RemovesGrantedMiracle() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // All creatures lose all abilities and have base power and toughness 1/1.
        addCard(Zone.BATTLEFIELD, playerB, "Humility");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // first draw, but Molecule Man lost its ability

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_ControlChange_NewControllerGetsGrantedMiracle() {
        skipInitShuffling();

        // Nonland cards in your hand have miracle {0}.
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        // Enchant creature
        // You control enchanted creature.
        addCard(Zone.HAND, playerB, "Control Magic"); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // drawn turn 3 after losing Molecule Man -> NO miracle
        addCard(Zone.LIBRARY, playerA, "Forest"); // drawn turn 1
        addCard(Zone.LIBRARY, playerB, "Centaur Courser"); // drawn turn 4 draw step (controls Molecule Man) -> miracle {0}
        addCard(Zone.LIBRARY, playerB, "Hill Giant"); // top -> drawn turn 2 draw step (before Control Magic)

        // Turn 1: PlayerA draws Forest with Think Twice (no miracle for land)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        // Turn 2: PlayerB steals Molecule Man with Control Magic
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Control Magic", "Molecule Man");

        // Turn 3 draw step: PlayerA draws Grizzly Bears -> NO miracle (no longer controls Molecule Man)
        // Turn 4 draw step: PlayerB draws Centaur Courser -> miracle {0} from controlled Molecule Man
        setChoice(playerB, true); // reveal Centaur Courser for miracle
        setChoice(playerB, true); // cast Centaur Courser for miracle {0}

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Molecule Man", 1);
        assertPermanentCount(playerB, "Centaur Courser", 1);
    }

    @Test
    public void test_DrawFromBottom_RiverSong_TriggersMiracle() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        // Meet in Reverse -- You draw cards from the bottom of your library rather than the top.
        addCard(Zone.BATTLEFIELD, playerA, "River Song");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Put all creatures on the bottom of their owners' libraries.
        // Miracle {W}
        addCard(Zone.LIBRARY, playerA, "Terminus"); // bottom of library -> drawn by River Song
        addCard(Zone.LIBRARY, playerA, "Forest"); // top of library (not drawn)
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); // pays Miracle {W}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Terminus for miracle
        setChoice(playerA, true); // cast Terminus for Miracle {W}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Terminus", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertTappedCount("Plains", true, 1);
    }

    @Test
    public void test_AbundanceReplacesDraw_NoMiracle() {
        // Terminus ruling:
        // If an effect puts a card into your hand without using the word "draw," the card wasn't drawn.
        // (2018-07-13)
        skipInitShuffling();

        // If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library
        // until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way
        // on the bottom of your library in any order.
        addCard(Zone.BATTLEFIELD, playerA, "Abundance");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Put all creatures on the bottom of their owners' libraries.
        // Miracle {W}
        addCard(Zone.LIBRARY, playerA, "Terminus");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        // Abundance asks whether to replace the draw in applies(); then chooseReplacementEffect orders Abundance before Miracle
        // (Miracle is implemented via a DRAW_CARD replacement effect, so the extra ordering choice is expected).
        setChoice(playerA, true); // use Abundance replacement
        setChoice(playerA, "Abundance"); // apply Abundance first -> replaces draw completely, so Miracle never triggers
        setChoice(playerA, false); // choose nonland (reveals Terminus and puts into hand without drawing)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Terminus", 1);
        assertGraveyardCount(playerA, "Terminus", 0);
        assertTappedCount("Plains", false, 1);
    }

    @Test
    public void test_ThoughtReflection_MiracleOnFirstDrawnCardOnly() {
        // Terminus ruling:
        // Multiple card draws are always treated as a sequence of individual card draws. For example, if you haven't drawn
        // any cards yet during a turn and cast a spell that instructs you to draw three cards, you'll draw them one at a
        // time. Only the first card drawn this way may be revealed and cast using its miracle ability.
        // (2018-07-13)
        skipInitShuffling();

        // If you would draw a card, draw two cards instead.
        addCard(Zone.BATTLEFIELD, playerA, "Thought Reflection");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Return all nonland permanents to their owners' hands.
        // Miracle {1}{U}
        addCard(Zone.LIBRARY, playerA, "Devastation Tide"); // second card drawn -> NO miracle
        // Put all creatures on the bottom of their owners' libraries.
        // Miracle {W}
        addCard(Zone.LIBRARY, playerA, "Terminus"); // first card drawn -> Miracle {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1); // pays Miracle {W}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        // Miracle is implemented via a DRAW_CARD replacement effect, so XMage asks to order Thought Reflection vs Terminus.
        setChoice(playerA, "Thought Reflection"); // replace 1 draw with 2 draws first
        setChoice(playerA, true); // reveal Terminus (first drawn card) for miracle
        // Second card (Devastation Tide) must NOT prompt for miracle
        setChoice(playerA, true); // cast Terminus for Miracle {W}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Terminus", 1);
        assertHandCount(playerA, "Devastation Tide", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Thought Reflection", 1); // Devastation Tide was not cast
    }
}
