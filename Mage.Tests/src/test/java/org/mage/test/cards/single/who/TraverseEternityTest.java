package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TraverseEternityTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TraverseEternity Traverse Eternity} {2}{U}{U}
     * Sorcery
     * Draw cards equal to the greatest mana value among historic permanents you control. (Artifacts, legendaries, and Sagas are historic.)
     */
    private static final String traverse = "Traverse Eternity";

    @Test
    public void test_greatest_2() {
        addCard(Zone.HAND, playerA, traverse);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Acolyte of Bahamut", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde", 1); // 3/3 {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Arwen Undomiel", 1); // {G}{U} -- historic

        addCard(Zone.BATTLEFIELD, playerB, "Barktooth Warbeard", 1); // 7 mv -- historic

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, traverse);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2);
    }

    @Test
    public void test_greatest_3() {
        addCard(Zone.HAND, playerA, traverse);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Acolyte of Bahamut", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde", 1); // 3/3 {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Sword of Fire and Ice", 1); // {3} -- historic
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda", 1); // {W} -- historic

        addCard(Zone.BATTLEFIELD, playerB, "Barktooth Warbeard", 1); // 7 mv -- historic

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, traverse);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 3);
    }
}
