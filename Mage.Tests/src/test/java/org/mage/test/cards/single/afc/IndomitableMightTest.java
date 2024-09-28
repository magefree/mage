package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class IndomitableMightTest extends CardTestPlayerBase {

    private static final String might = "Indomitable Might"; // 3G flash aura +3/+3 may assign combat damage as though weren't blocked
    private static final String bear = "Runeclaw Bear"; // 2/2
    private static final String centaur = "Centaur Courser"; // 3/3
    private static final String drake = "Azure Drake"; // 2/4 flying
    private static final String crab = "Fortress Crab"; // 1/6

    @Test
    public void testAsThoughEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, centaur);
        addCard(Zone.BATTLEFIELD, playerB, drake);
        addCard(Zone.BATTLEFIELD, playerB, crab);
        addCard(Zone.HAND, playerA, might);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, might, bear);

        checkPT("boosted", 1, PhaseStep.BEGIN_COMBAT, playerA, bear, 5, 5);

        attack(1, playerA, bear, playerB);
        attack(1, playerA, centaur, playerB);

        block(1, playerB, drake, centaur);
        block(1, playerB, crab, bear);

        setChoice(playerA, true); // yes to assign damage as though not blocked (only enchanted creature)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);
        assertDamageReceived(playerA, bear, 1);
        assertDamageReceived(playerA, centaur, 2);
        assertDamageReceived(playerB, drake, 3);
        assertDamageReceived(playerB, crab, 0);
    }

}
