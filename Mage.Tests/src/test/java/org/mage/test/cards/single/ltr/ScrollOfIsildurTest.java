package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ScrollOfIsildurTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.ScrollOfIsildur Scroll of Isildur} {2}{U}
     * Enchantment — Saga
     * (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
     * I — Gain control of up to one target artifact for as long as you control this Saga. The Ring tempts you.
     * II — Tap up to two target creatures. Put a stun counter on each of them.
     * III — Draw a card for each tapped creature target opponent controls.
     */
    private static final String scroll = "Scroll of Isildur";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, scroll, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Symbiotic Deployment", 1); // skip your draw step
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Bear Cub", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scroll);
        addTarget(playerA, "Memnite");

        checkPermanentCount("T1: Memnite is controlled by A", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);

        // turn 3
        addTarget(playerA, "Grizzly Bears^Bear Cub");

        checkPermanentCount("T3: Memnite is controlled by A", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);

        // turn 5
        checkPermanentCount("T5: Memnite is controlled by A before III", 5, PhaseStep.UPKEEP, playerA, "Memnite", 1);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Memnite", 1);
        assertHandCount(playerA, 2);
    }
}
