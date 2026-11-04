package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class JenniferWaltersTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.j.JenniferWalters Jennifer Walters // The Sensational She-Hulk}
     * The Sensational She-Hulk {3}{G}{W}{W}
     * Legendary Creature - Gamma Hero 6/6
     * Reach, trample
     * Your opponents can't cast spells during your turn.
     * Whenever a creature you control is dealt damage, you may have The Sensational She-Hulk deal that much damage
     * to any target. Do this only once each turn.
     */
    private static final String walters = "Jennifer Walters";
    private static final String sheHulk = "The Sensational She-Hulk";

    @Test
    public void test_DamageGoesToTheChosenTarget() {
        addCard(Zone.HAND, playerA, walters);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sheHulk);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Hill Giant");
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20 - 2);
        assertDamageReceived(playerA, "Hill Giant", 2);
    }
}
