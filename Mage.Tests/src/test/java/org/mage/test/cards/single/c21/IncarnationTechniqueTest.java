package org.mage.test.cards.single.c21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */

public class IncarnationTechniqueTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.i.IncarnationTechnique Incarnation Technique} {4}{B}
     * Sorcery
     * Demonstrate (When you cast this spell, you may copy it. If you do, choose an opponent to also copy it.)
     * Mill five cards, then return a creature card from your graveyard to the battlefield.
     */
    private static final String technique = "Incarnation Technique";

    private static final String myr = "Omega Myr"; // vanilla 1/2

    /**
     * Emrakul, the Aeons Torn {15}
     * Legendary Creature — Eldrazi
     * This spell can’t be countered.
     * When you cast this spell, take an extra turn after this one.
     * Flying, protection from spells that are one or more colors, annihilator 6
     * When Emrakul is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.
     * 15/15
     */
    private static final String emrakul = "Emrakul, the Aeons Torn";

    /**
     * Cosi's Trickster {U}
     * Creature — Merfolk Wizard
     * Whenever an opponent shuffles their library, you may put a +1/+1 counter on this creature.
     * 1/1
     */
    private static final String trickster = "Cosi's Trickster";

    @Test
    public void testNoCreatureToReturn() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, technique);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, technique);
        setChoice(playerA, true); // yes to demonstrate
        setChoice(playerA, playerB.getName()); // choosing B for demonstrate copy

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, 5 * 2 + 1);
        assertGraveyardCount(playerB, 5);
    }

    @Test
    public void testCreatureInGraveyard() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, technique);
        addCard(Zone.GRAVEYARD, playerA, myr);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, technique);
        setChoice(playerA, false); // no to demonstrate
        setChoice(playerA, myr); // returned creature

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, myr, 1);
        assertGraveyardCount(playerA, 5 + 1);
    }

    @Test
    public void testCreatureMilled() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, technique);
        addCard(Zone.LIBRARY, playerA, myr);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, technique);
        setChoice(playerA, false); // no to demonstrate
        setChoice(playerA, myr); // returned creature

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, myr, 1);
        assertGraveyardCount(playerA, 4 + 1);
    }

    @Test
    public void testEmrakul() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, technique);
        addCard(Zone.BATTLEFIELD, playerB, trickster);
        addCard(Zone.LIBRARY, playerA, emrakul);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, technique);
        setChoice(playerA, false); // no to demonstrate
        setChoice(playerA, emrakul); // returned creature
        setChoice(playerB, true); // yes to Cosi's Trickster may trigger

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, emrakul, 1);
        assertGraveyardCount(playerA, 0);
        assertCounterCount(playerB, trickster, CounterType.P1P1, 1);
    }
}
