package org.mage.test.cards.single.leg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class HellfireTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.h.Hellfire Hellfire} {2}{B}{B}{B}
     * Sorcery
     * Destroy all nonblack creatures. Hellfire deals X plus 3 damage to you,
     * where X is the number of creatures that died this way.
     */
    private static final String hellfire = "Hellfire";

    @Test
    public void test_NoCreatureDies_StillDealsThree() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, hellfire);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hellfire);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, hellfire, 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void test_CreaturesDie_DealsXPlusThree() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // nonblack, dies
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // nonblack, dies
        addCard(Zone.BATTLEFIELD, playerB, "Vampire Bats"); // black, survives
        addCard(Zone.HAND, playerA, hellfire);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hellfire);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Vampire Bats", 1);
        assertLife(playerA, 20 - 5);
    }
}
