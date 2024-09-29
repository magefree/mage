package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class UlamogTheDefilerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.u.UlamogTheDefiler Ulamog, the Defiler} {10}
     * Legendary Creature — Eldrazi
     * When you cast this spell, target opponent exiles half their library, rounded up.
     * Ward—Sacrifice two permanents.
     * Ulamog, the Defiler enters the battlefield with a number of +1/+1 counters on it equal to the greatest mana value among cards in exile.
     * Ulamog has annihilator X, where X is the number of +1/+1 counters on it.
     * 7/7
     */
    private static final String ulamog = "Ulamog, the Defiler";

    @Test
    public void test_OnlyLandsExiled() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.HAND, playerA, ulamog);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 10);
        addCard(Zone.LIBRARY, playerB, "Taiga", 11);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ulamog);
        addTarget(playerA, playerB);

        attack(3, playerA, ulamog, playerB);
        // Annihilator 0 triggers

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, 6);
        assertLife(playerB, 20 - 7);
        assertGraveyardCount(playerB, "Mountain", 0);
    }

    @Test
    public void test_Annihilator2_FromFreshlyExiled() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.HAND, playerA, ulamog);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 10);
        addCard(Zone.LIBRARY, playerB, "Taiga", 11);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ulamog);
        addTarget(playerA, playerB);

        attack(3, playerA, ulamog, playerB);
        // Annihilator 2 triggers
        setChoice(playerB, "Mountain", 2);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, 7);
        assertExileCount(playerB, "Elite Vanguard", 1);
        assertExileCount(playerB, "Grizzly Bears", 1);
        assertLife(playerB, 20 - 9);
        assertGraveyardCount(playerB, "Mountain", 2);
    }

    @Test
    public void test_Annihilator2_FromOldExiled() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.HAND, playerA, ulamog);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 10);
        addCard(Zone.LIBRARY, playerB, "Taiga", 11);
        addCard(Zone.EXILED, playerA, "Grizzly Bears");
        addCard(Zone.EXILED, playerA, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ulamog);
        addTarget(playerA, playerB);

        attack(3, playerA, ulamog, playerB);
        // Annihilator 2 triggers
        setChoice(playerB, "Mountain", 2);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, 6);
        assertLife(playerB, 20 - 9);
        assertGraveyardCount(playerB, "Mountain", 2);
    }

    @Test
    public void test_Annihilator4_Change_FromCounterLater() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.HAND, playerA, ulamog);
        addCard(Zone.BATTLEFIELD, playerA, "Luminarch Aspirant"); // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 10);
        addCard(Zone.LIBRARY, playerB, "Taiga", 11);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ulamog);
        addTarget(playerA, playerB);
        addTarget(playerA, ulamog); // Aspirant trigger

        attack(3, playerA, ulamog, playerB);
        addTarget(playerA, ulamog); // Aspirant trigger
        // Annihilator 4 triggers
        setChoice(playerB, "Mountain", 4);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, 7);
        assertLife(playerB, 20 - 11);
        assertGraveyardCount(playerB, "Mountain", 4);
    }

}
