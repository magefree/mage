package org.mage.test.cards.single.tor;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class HypnoxTest extends CardTestPlayerBase {

    private static final String hypnox = "Hypnox";
    /** Hypnox {8}{B}{B}{B}
     * Flying
     * When Hypnox enters the battlefield, if you cast it from your hand, exile all cards from target opponent’s hand.
     * When Hypnox leaves the battlefield, return the exiled cards to their owner’s hand.
     */

    @Test
    public void testExileAndReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 11);
        addCard(Zone.HAND, playerA, hypnox);
        addCard(Zone.HAND, playerB, "Shock");
        addCard(Zone.HAND, playerB, "Watchwolf");
        addCard(Zone.BATTLEFIELD, playerA, "Bloodthrone Vampire");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hypnox);
        addTarget(playerA, playerB);

        checkPT("Hypnox", 1, PhaseStep.BEGIN_COMBAT, playerA, hypnox, 8, 8);
        checkExileCount("exiled from hand", 1, PhaseStep.BEGIN_COMBAT, playerB, "Shock", 1);
        checkExileCount("exiled from hand", 1, PhaseStep.BEGIN_COMBAT, playerB, "Watchwolf", 1);

        activateAbility(1, PhaseStep.END_COMBAT, playerA, "Sacrifice");
        setChoice(playerA, hypnox);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, hypnox, 1);
        assertHandCount(playerB, "Shock", 1);
        assertHandCount(playerB, "Watchwolf", 1);
    }

}
