package org.mage.test.cards.single.dmu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BriarHydraTest extends CardTestPlayerBase {

    private static final String thranPortal = "Thran Portal";

    /**
     * {@link mage.cards.b.BriarHydra Briar Hydra} {5}{G}
     * Creature — Plant Hydra
     * Trample
     * Domain — Whenever Briar Hydra deals combat damage to a player, put X +1/+1 counters on target creature you control, where X is the number of basic land types among lands you control.
     * 6/6
     */
    private static final String hydra = "Briar Hydra";

    /**
     * There was a bug when the Domain value was 0, the Hydra would incorrectly
     * add a +1/+1 counter to the target.
     */
    @Test
    public void test_NoDomain() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, hydra);

        attack(1, playerA, hydra, playerB);
        addTarget(playerA, hydra); // trigger does target

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, hydra, 6, 6);
    }

    @Test
    public void test_Domain_2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, hydra);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou");

        attack(1, playerA, hydra, playerB);
        addTarget(playerA, hydra); // trigger does target

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, hydra, 6 + 2, 6 + 2);
    }
}
