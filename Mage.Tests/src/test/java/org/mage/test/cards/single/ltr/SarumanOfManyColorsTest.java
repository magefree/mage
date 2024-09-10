package org.mage.test.cards.single.ltr;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class SarumanOfManyColorsTest extends CardTestPlayerBase {

    static final String saruman = "Saruman of Many Colors";

    @Test
    // Author: alexander-novo
    // A simple test to make sure the ward ability is working correctly
    public void wardTest() {
        String bolt = "Lightning Bolt";
        addCard(Zone.BATTLEFIELD, playerB, saruman);
        // Two bolts for casting on Saruman, one for discarding
        addCard(Zone.HAND, playerA, bolt, 3);
        // A red herring card to ignore on the second cast
        addCard(Zone.HAND, playerA, "Mountain");

        // Mana for casting 2 lightning bolts
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, saruman, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, saruman, true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, bolt, 0);
        assertGraveyardCount(playerA, bolt, 3);
        assertDamageReceived(playerB, saruman, 3);
    }

    @Test
    // Author: alexander-novo
    // A test to make sure the happy path of casting a second spell works
    public void secondSpellTest() {
        String bolt = "Lightning Bolt";

        // Two spells to cast to trigger
        addCard(Zone.HAND, playerA, saruman);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.LIBRARY, playerB, bolt);

        // Mana for casting spells
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        skipInitShuffling();

        // Cast saruman, and then a second spell - make sure saruman triggers
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saruman, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        checkStackObject("Bolt Check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Whenever you cast your second spell each turn", 1);

        // Resolve the mill trigger - make sure the correct cards were milled and that the reflexive ability has triggered
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // I don't know why this is needed
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerB, true);
        checkGraveyardCount("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, 1);
        checkGraveyardCount("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mountain", 1);
        checkStackObject("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "When one or more cards are milled this way", 1);

        // Resolve the reflexive triggered ability - exiling the milled lightning bolt and casting it
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        // Choose player B to target for the next lightning bolt. Check to make sure there are now two lightning bolts on the stack
        addTarget(playerA, playerB);
        checkStackObject("Final check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bolt, 2);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2 * 3);
        assertGraveyardCount(playerB, bolt, 0);
        assertExileCount(playerB, bolt, 1);
    }

    @Test
    // Author: alexander-novo
    // A test to make sure the mana value restriction works properly
    public void manaValueTest() {
        String bolt = "Lightning Bolt";
        String helix = "Lightning Helix";

        // Two spells to cast to trigger
        addCard(Zone.HAND, playerA, saruman);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.LIBRARY, playerB, helix); // This time, put a card we can't cast

        // Mana for casting spells
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        skipInitShuffling();

        // Cast saruman, and then a second spell - make sure saruman triggers
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saruman, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        checkStackObject("Bolt Check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Whenever you cast your second spell each turn", 1);

        // Resolve the mill trigger - make sure the correct cards were milled and that the reflexive ability hasn't triggered because there are no targets
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // I don't know why this is needed
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerB, true);
        checkGraveyardCount("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerB, helix, 1);
        checkGraveyardCount("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mountain", 1);
        checkStackObject("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "When one or more cards are milled this way", 0);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerB, helix, 1);
    }

    @Test
    // Author: alexander-novo
    // A test to make sure the triggered ability still works if the original triggering spell is removed from the stack (such as by countering)
    public void counterSpellTest() {
        String bolt = "Lightning Bolt";
        String counter = "Counterspell";

        // Two spells to cast to trigger. Counter to test LKI
        addCard(Zone.HAND, playerA, saruman);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.HAND, playerB, counter);
        addCard(Zone.LIBRARY, playerB, bolt);

        // Mana for casting spells
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        skipInitShuffling();

        // Cast saruman, and then a second spell - make sure saruman triggers
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saruman, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        checkStackObject("Bolt Check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Whenever you cast your second spell each turn", 1);

        // Counter the original lightning bolt cast. Everything else should work the same as if this hadn't happened
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, counter, bolt, bolt);

        // Resolve the mill trigger - make sure the correct cards were milled and that the reflexive ability has triggered
        showStack("Counter check", 1, PhaseStep.PRECOMBAT_MAIN, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 2);
        // I don't know why this is needed
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkGraveyardCount("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, 1);
        checkGraveyardCount("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mountain", 1);
        checkStackObject("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "When one or more cards are milled this way", 1);

        // Resolve the reflexive triggered ability - exiling the milled lightning bolt and casting it
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        // Choose player B to target for the next lightning bolt. Check to make sure there are now two lightning bolts on the stack
        addTarget(playerA, playerB);
        checkStackObject("Final check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bolt, 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Original lightning bolt was countered
        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerB, bolt, 0);
        assertExileCount(playerB, bolt, 1);
    }

    @Test
    // Author: alexander-novo
    // A test to make sure the reflexive trigger doesn't happen if there were no cards milled
    public void noMillTest() {
        String bolt = "Lightning Bolt";

        // Two spells to cast to trigger
        addCard(Zone.HAND, playerA, saruman);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.GRAVEYARD, playerB, bolt);

        // Mana for casting spells
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        removeAllCardsFromLibrary(playerB);

        // Cast saruman, and then a second spell - make sure saruman triggers
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saruman, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        checkStackObject("Bolt Check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Whenever you cast your second spell each turn", 1);

        // Resolve the mill trigger - make sure cards weren't milled, and the reflexive ability wasn't triggered because of it (even though there is a valid target in the lightning bolt)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // I don't know why this is needed
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerB, true);
        checkGraveyardCount("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, 1);
        checkGraveyardCount("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mountain", 0);
        checkStackObject("Mill check", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "When one or more cards are milled this way", 0);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerB, bolt, 1);
    }
}
