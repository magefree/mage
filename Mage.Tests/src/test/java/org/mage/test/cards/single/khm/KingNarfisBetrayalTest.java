package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class KingNarfisBetrayalTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.k.KingNarfisBetrayal King Narfi's Betrayal} {1}{U}{B}
     * Enchantment — Saga
     * I — Each player mills four cards. Then you may exile a creature or planeswalker card from each graveyard.
     * II, III — Until end of turn, you may cast spells from among cards exiled with this Saga, and you may spend mana as though it were mana of any color to cast those spells.
     */
    private static final String betrayal = "King Narfi's Betrayal";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, betrayal, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);
        addCard(Zone.GRAVEYARD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, betrayal);
        setChoice(playerA, true);
        addTarget(playerA, "Grizzly Bears");
        setChoice(playerA, true);
        addTarget(playerA, "Elite Vanguard");

        checkExileCount("after I, Bears exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("after I, Vanguard exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Elite Vanguard", 1);

        // turn 3
        // do nothing there.

        // turn 5
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 5);
        assertGraveyardCount(playerB, 4);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }
}
