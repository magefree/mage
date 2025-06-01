package org.mage.test.cards.single.bfz;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class UginsInsightTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.u.UginsInsight Ugin's Insight} {3}{U}{U}
     * Sorcery
     * Scry X, where X is the greatest mana value among permanents you control, then draw three cards.
     */
    private static final String insight = "Ugin's Insight";

    @Test
    public void test_greatest_0() {
        addCard(Zone.HAND, playerA, insight);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Barbarian Horde", 1); // 3/3 {3}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, insight);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 3);
    }

    @Test
    public void test_greatest_4() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ageless Guardian");
        addCard(Zone.LIBRARY, playerA, "Barktooth Warbeard");
        addCard(Zone.LIBRARY, playerA, "Catacomb Crocodile");
        addCard(Zone.LIBRARY, playerA, "Devilthorn Fox");

        addCard(Zone.HAND, playerA, insight);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Acolyte of Bahamut", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde", 1); // 3/3 {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Sword of Fire and Ice", 1); // {3}

        addCard(Zone.BATTLEFIELD, playerB, "Barbarian Horde", 1); // 3/3 {3}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, insight);
        addTarget(playerA, "Devilthorn Fox^Barktooth Warbeard"); // scry two to the bottom.
        setChoice(playerA, "Barktooth Warbeard"); // order 2 to the bottom.
        setChoice(playerA, "Catacomb Crocodile"); // order 2 to the top.

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 3);
        assertHandCount(playerA, "Mountain", 1);
        assertHandCount(playerA, "Catacomb Crocodile", 1);
        assertHandCount(playerA, "Ageless Guardian", 1);
    }
}
