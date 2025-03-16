package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class MongrelPackTest extends CardTestPlayerBase {

    private static final String mp = "Mongrel Pack"; // 4/1
    // When this creature dies during combat, create four 1/1 green Dog creature tokens.
    private static final String wurm = "Craw Wurm"; // 6/4
    private static final String bb = "Blood Bairn"; // sac outlet

    @Test
    public void testDuringCombat() {
        addCard(Zone.BATTLEFIELD, playerA, mp);
        addCard(Zone.BATTLEFIELD, playerB, wurm);

        attack(1, playerA, mp, playerB);
        block(1, playerB, wurm, mp);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, mp, 1);
        assertGraveyardCount(playerB, wurm, 1);
        assertPermanentCount(playerA, "Dog Token", 4);
    }

    @Test
    public void testNotDuringCombat() {
        addCard(Zone.BATTLEFIELD, playerA, mp);
        addCard(Zone.BATTLEFIELD, playerA, bb);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, mp);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, mp, 1);
        assertPermanentCount(playerA, "Dog Token", 0);
    }

    @Test
    public void testDuringCombatNotFromCombat() {
        addCard(Zone.BATTLEFIELD, playerA, mp);
        addCard(Zone.BATTLEFIELD, playerA, bb);

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Sacrifice");
        setChoice(playerA, mp);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, mp, 1);
        assertPermanentCount(playerA, "Dog Token", 4);
    }

}
