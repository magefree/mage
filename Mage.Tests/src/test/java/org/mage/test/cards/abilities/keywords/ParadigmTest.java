package org.mage.test.cards.abilities.keywords;

import mage.cards.Card;
import mage.cards.repository.CardRepository;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;

/**
 * 702.192a Paradigm represents two spell abilities, one of which creates a
 * delayed triggered ability: "Then exile this spell" and "After you first
 * resolve a spell with this name, you may cast a copy of it from exile without
 * paying its mana cost at the beginning of each of your first main phases."
 */
public class ParadigmTest extends CardTestPlayerBase {

    /**
     * Target player draws two cards and loses 2 life.
     * Paradigm
     */
    private static final String DISSERTATION = "Decorum Dissertation";

    private void prepareDissertation() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, DISSERTATION);
    }

    @Test
    public void testExiledAfterResolve() {
        prepareDissertation();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, DISSERTATION, 1);
        assertGraveyardCount(playerA, 0);
        assertLife(playerA, 18);
        assertHandCount(playerA, 2);
    }

    @Test
    public void testCastCopyForFreeEachFirstMainPhase() {
        prepareDissertation();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);

        // lands are tapped only on the first turn, so test by targets and life, not by mana
        setChoice(playerA, true); // turn 3: cast copy
        addTarget(playerA, playerB);
        setChoice(playerA, true); // turn 5: cast copy
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 16);
        // only the original card stays in exile, copies cease to exist
        assertExileCount(playerA, DISSERTATION, 1);
        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 0);
    }

    @Test
    public void testDecliningDoesNotEndTrigger() {
        prepareDissertation();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);

        setChoice(playerA, false); // turn 3: decline
        setChoice(playerA, true); // turn 5: cast copy
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 18);
    }

    @Test
    public void testCounteredSpellGetsNothing() {
        prepareDissertation();
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", DISSERTATION, DISSERTATION);

        // strict mode: any prompt on turn 3 would fail the test
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, DISSERTATION, 1);
        assertExileCount(playerA, DISSERTATION, 0);
        assertLife(playerA, 20);
    }

    @Test
    public void testSecondSpellWithSameNameGivesNoSecondTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.HAND, playerA, DISSERTATION, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setChoice(playerA, true); // turn 3: one copy only
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, DISSERTATION, 2);
        assertLife(playerA, 16);
        assertLife(playerB, 18);
    }

    @Test
    public void testTriggerWorksAfterCardLeavesExile() {
        prepareDissertation();
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        // Put target face-up exiled card into its owner's graveyard.
        addCard(Zone.HAND, playerB, "Pull from Eternity");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Pull from Eternity", DISSERTATION);

        setChoice(playerA, true); // turn 3: cast copy
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, DISSERTATION, 0);
        assertGraveyardCount(playerA, DISSERTATION, 1);
        assertLife(playerB, 18);
    }

    @Test
    public void testSpellWithoutTargets() {
        // Put two +1/+1 counters on each creature you control.
        String practicum = "Germination Practicum";
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, practicum);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, practicum);

        setChoice(playerA, true); // turn 3: cast copy

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Memnite", CounterType.P1P1, 4);
        assertExileCount(playerA, practicum, 1);
    }

    @Test
    public void testCounteredCopyDoesNotEndTrigger() {
        prepareDissertation();
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);

        setChoice(playerA, true); // turn 3: cast copy, it gets countered
        addTarget(playerA, playerB);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", DISSERTATION, DISSERTATION);

        setChoice(playerA, true); // turn 5: cast copy
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Counterspell", 1);
        assertLife(playerA, 18);
        assertLife(playerB, 18); // only the turn 5 copy resolved
        assertExileCount(playerA, DISSERTATION, 1);
        assertGraveyardCount(playerA, DISSERTATION, 0); // a countered copy leaves no card behind
    }

    @Test
    public void testEchocastingSymposium() {
        // Target player creates a token that's a copy of target creature you control.
        String symposium = "Echocasting Symposium";
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, symposium);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, symposium);
        addTarget(playerA, playerA);
        addTarget(playerA, "Memnite");

        setChoice(playerA, true); // turn 3: cast copy
        addTarget(playerA, playerB);
        addTarget(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 2);
        assertPermanentCount(playerB, "Memnite", 1);
        assertExileCount(playerA, symposium, 1);
    }

    @Test
    public void testRestorationSeminar() {
        // Return target nonland permanent card from your graveyard to the battlefield.
        String seminar = "Restoration Seminar";
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.GRAVEYARD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, "Ornithopter");
        addCard(Zone.HAND, playerA, seminar);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, seminar, "Memnite");

        setChoice(playerA, true); // turn 3: cast copy
        addTarget(playerA, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Ornithopter", 1);
        assertExileCount(playerA, seminar, 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testImprovisationCapstone() {
        // Exile cards from the top of your library until you exile cards with total mana value 4 or greater.
        // You may cast any number of spells from among them without paying their mana costs.
        String capstone = "Improvisation Capstone";
        String giant = "Hill Giant"; // mana value 4
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, capstone);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, giant, 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, capstone);
        setChoice(playerA, true); // cast the only exiled card, no dialog for choosing it

        setChoice(playerA, true); // turn 3: cast copy
        setChoice(playerA, true); // cast the only exiled card, no dialog for choosing it

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, giant, 2);
        assertExileCount(playerA, capstone, 1);
    }

    @Test
    public void testRulesText() {
        Card card = CardRepository.instance.findCard(DISSERTATION).createCard();
        List<String> rules = card.getRules();
        // same format as Epic: the keyword is on its own line of the spell ability's text
        Assert.assertEquals(rules.toString(), 1, rules.size());
        Assert.assertTrue(rules.get(0), rules.get(0).startsWith("Target player draws two cards and loses 2 life."));
        Assert.assertTrue(rules.get(0), rules.get(0).contains("<br>Paradigm <i>(Then exile this spell."));
    }

    /**
     * Only the resolve matters, not the cast: a player who resolves a copy of
     * another player's paradigm spell gets their own delayed trigger.
     */
    @Test
    public void testCopiedSpellGivesTriggerToCopyController() {
        prepareDissertation();
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerB, "Reverberate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISSERTATION, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reverberate", DISSERTATION, DISSERTATION);
        setChoice(playerB, false); // keep target of the copy

        setChoice(playerB, true); // turn 2: cast copy
        addTarget(playerB, playerA);
        setChoice(playerA, true); // turn 3: cast copy
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // original (2) + copy (2) + B's free copy (2)
        assertLife(playerA, 14);
        // A's free copy (2)
        assertLife(playerB, 18);
        assertExileCount(playerA, DISSERTATION, 1);
        assertExileCount(playerB, DISSERTATION, 0);
    }
}
