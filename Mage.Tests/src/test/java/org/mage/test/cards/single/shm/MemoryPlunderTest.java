package org.mage.test.cards.single.shm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MemoryPlunderTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MemoryPlunder Memory Plunder} {U/B}{U/B}{U/B}{U/B}
     * Instant
     * You may cast target instant or sorcery card from an opponent’s graveyard without paying its mana cost.
     */
    private static final String plunder = "Memory Plunder";

    @Test
    public void test_Divination_Cast() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerB, "Divination");
        addCard(Zone.HAND, playerA, plunder);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.UPKEEP, playerA, plunder, "Divination");
        setChoice(playerA, true); // yes to cast

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, plunder, 1);
        assertGraveyardCount(playerB, "Divination", 1); // back in graveyard
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_Divination_No_Cast() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerB, "Divination");
        addCard(Zone.HAND, playerA, plunder);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.UPKEEP, playerA, plunder, "Divination");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, plunder, 1);
        assertGraveyardCount(playerB, "Divination", 1); // not moved
        assertHandCount(playerA, 0); // no cast so no draw
    }
}
