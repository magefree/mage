package org.mage.test.cards.single.uds;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PhyrexianNegatorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.PhyrexianNegator Phyrexian Negator} {2}{B}
     * Creature — Phyrexian Horror
     * Trample
     * Whenever Phyrexian Negator is dealt damage, sacrifice that many permanents.
     * 5/5
     */
    private static final String negator = "Phyrexian Negator";

    @Test
    public void test_Trigger_Combat() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, negator);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);

        attack(1, playerA, negator);
        block(1, playerB, "Grizzly Bears", negator);
        block(1, playerB, "Memnite", negator);

        setChoice(playerA, "X=2"); // damage to Bears
        setChoice(playerA, "X=3"); // damage to Memnite

        setChoice(playerA, "Mountain", 3); // sac those.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerA, negator, 3);
        assertPermanentCount(playerA, "Mountain", 10 - 3);
    }

    @Test
    public void test_Trigger_NonCombat() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, negator);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", negator);

        setChoice(playerA, "Mountain", 2); // sac those.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, negator, 2);
        assertPermanentCount(playerA, "Mountain", 10 - 2);
    }
}
