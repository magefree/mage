package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CounterspellTest extends CardTestPlayerBase {

    /**
     * It looks like Boom//Bust can't be countered (although it says it's
     * countered in the log).
     *
     * Code: Select all 13:10: Benno casts Boom [8ce] targeting Mountain [4c8]
     * Island [80c] 13:10: Benno casts Counterspell [2b7] targeting Boom [8ce]
     * 13:10: Benno puts Boom [8ce] from stack into their graveyard 13:10: Boom
     * is countered by Counterspell [2b7] 13:10: Benno puts Counterspell [2b7]
     * from stack into their graveyard 13:10: Mountain [4c8] was destroyed
     * 13:10: Island [80c] was destroyed
     */
    @Test
    public void testCounterSplitSpell() {
        // Boom - Sorcery   {1}{R}
        // Destroy target land you control and target land you don't control.
        addCard(Zone.HAND, playerA, "Boom // Bust");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boom", "Mountain^Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Boom");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Boom // Bust", 1);
        assertGraveyardCount(playerB, "Counterspell", 1);

        assertPermanentCount(playerA, "Mountain", 2);
        assertPermanentCount(playerB, "Island", 2);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    /**
     * https://github.com/magefree/mage/issues/6823
     *
     * I cast Lightning Bolt, then tried to counter it with Memory Lapse, then
     * Twincast the Memory Lapse, using the copy to counter the original.
     *
     * Expected result: Original Memory Lapse countered, put on top of library;
     * Lightning Bolt resolves
     *
     * Actual Result: Original Memory Lapse says it gets countered, but still
     * resolves, Lightning Bolt is countered and put on top of library
     *
     * Of particular note, both the original and the copy of Memory Lapse have
     * the same card ID (6b5), which seems likely to cause issues.
     */
    @Test
    public void testCopyCounterToCounter() {
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Twincast");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Counter target spell. If that spell is countered this way, put it on top of its owner's library instead of into that player's graveyard.
        addCard(Zone.HAND, playerB, "Memory Lapse"); // Instant {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Memory Lapse", "Lightning Bolt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twincast", "Memory Lapse");

        setChoice(playerA, true); // change the target
        addTarget(playerA, "Memory Lapse");

        setStrictChooseMode(true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Twincast", 1);

        assertLibraryCount(playerB, "Memory Lapse", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    @Test
    public void testCopyCounterToCounterGraveyard() {
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Twincast");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Counter target spell
        addCard(Zone.HAND, playerB, "Counterspell"); // Instant {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Lightning Bolt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twincast", "Counterspell");

        setChoice(playerA, true); // change the target
        addTarget(playerA, "Counterspell");

        setStrictChooseMode(true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Twincast", 1);

        assertGraveyardCount(playerB, "Counterspell", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    @Test
    public void testCopyCounterToCounterExile() {
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Twincast");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        // CCounter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        addCard(Zone.HAND, playerB, "Dissipate"); // Instant {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Dissipate", "Lightning Bolt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twincast", "Dissipate");

        setChoice(playerA, true); // change the target
        addTarget(playerA, "Dissipate");

        setStrictChooseMode(true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Twincast", 1);

        assertExileCount(playerB, "Dissipate", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }
}
