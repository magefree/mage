package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ItThatHeraldsTheEndTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.i.ItThatHeraldsTheEnd It That Heralds the End} {1}{C}
     * Creature — Eldrazi Drone
     * Colorless spells you cast with mana value 7 or greater cost {1} less to cast.
     * Other colorless creatures you control get +1/+1.
     * 2/2
     */
    private static final String it = "It That Heralds the End";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, it);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 7);
        addCard(Zone.HAND, playerA, "Ebony Rhino");
        addCard(Zone.HAND, playerB, "Ebony Rhino");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ebony Rhino");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ebony Rhino");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Swamp", true, 6);
        assertTappedCount("Forest", true, 7);
        assertPermanentCount(playerA, "Ebony Rhino", 1);
        assertPowerToughness(playerA, "Ebony Rhino", 5, 6);
        assertPermanentCount(playerB, "Ebony Rhino", 1);
        assertPowerToughness(playerB, "Ebony Rhino", 4, 5);
    }

}
