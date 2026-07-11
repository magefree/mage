package org.mage.test.cards.cost.prepare;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests for the "prepare" mechanic: while a permanent is prepared, its controller may cast a copy of
 * its prepare spell (following that spell's timing), and doing so unprepares the permanent.
 *
 * @author wakame1367
 */
public class PrepareCardsTest extends CardTestPlayerBase {

    // Emeritus of Abundance // Regrowth (creature // sorcery). Enters prepared, and re-prepares on
    // attack if you control eight or more lands.
    private static final String emeritus = "Emeritus of Abundance";
    private static final String regrowth = "Regrowth";
    // Emeritus of Ideation // Ancestral Recall (creature // instant). Enters prepared.
    private static final String ideation = "Emeritus of Ideation";
    private static final String recall = "Ancestral Recall";

    @Test
    public void testCastPreparedSorceryCopyReturnsCardAndUnprepares() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, emeritus); // enters prepared
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        // while prepared, cast a copy of Regrowth
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, regrowth, "Grizzly Bears");
        // it is unprepared after casting, so the copy can't be cast again
        checkPlayableAbility("unprepared", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + regrowth, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // the copy's effect resolved: the card is back in hand and the graveyard is empty
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
        // the permanent itself is untouched (only a copy was cast)
        assertPermanentCount(playerA, emeritus, 1);
    }

    @Test
    public void testCannotCastWhenNotPrepared() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, emeritus); // enters prepared
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        // castable while prepared
        checkPlayableAbility("prepared", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + regrowth, true);
        // cast it, which unprepares the permanent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, regrowth, "Grizzly Bears");
        // now unprepared -> not castable
        checkPlayableAbility("unprepared", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + regrowth, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertHandCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testReprepareOnAttackWithEightLands() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.BATTLEFIELD, playerA, emeritus); // enters prepared
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");

        // cast the prepared copy once, unpreparing the permanent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, regrowth, "Grizzly Bears");
        // attacking with eight or more lands re-prepares it (Emeritus has vigilance)
        attack(1, playerA, emeritus);
        // prepared again -> cast another copy after combat
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, regrowth, "Lightning Bolt");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, 0);
        assertPermanentCount(playerA, emeritus, 1);
    }

    @Test
    public void testSorceryPrepareSpellNotCastableOnOpponentTurn() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, emeritus); // enters prepared
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        // sorcery-speed prepare spell: castable on your own main phase...
        checkPlayableAbility("own main", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + regrowth, true);
        // ...but not during the opponent's turn
        checkPlayableAbility("opponent turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + regrowth, false);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testInstantPrepareSpellCastableOnOpponentTurn() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, ideation); // enters prepared, instant prepare spell

        // instant-speed prepare spell: castable during the opponent's turn
        checkPlayableAbility("opponent turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + recall, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, recall, playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Ancestral Recall drew three cards for playerA; the permanent stays and is now unprepared
        assertPermanentCount(playerA, ideation, 1);
        checkPlayableAbility("unprepared", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + recall, false);
    }
}
