package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GracefulTakedownTest extends CardTestPlayerBase {

    /**
     * Graceful Takedown
     * {1}{G}
     * Sorcery
     *
     * Any number of target enchanted creatures you control and up to one other target creature you control each deal damage equal to their power to target creature you don’t control.
     */
    private static final String takedown = "Graceful Takedown";

    private static final String cub = "Bear Cub"; // 2/2
    private static final String piker = "Goblin Piker"; // 2/1
    private static final String strength = "Unholy Strength"; // Aura {B} +2/+1
    private static final String ancient = "Indomitable Ancients"; // 2/10

    @Test
    public void oneCreatureDealingDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerA, cub);
        addCard(Zone.BATTLEFIELD, playerA, piker);
        addCard(Zone.BATTLEFIELD, playerB, ancient);

        addCard(Zone.HAND, playerA, takedown);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, takedown);
        addTarget(playerA, cub);
        addTarget(playerA, ancient);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerB, ancient, 2);
    }

    @Test
    public void twoCreaturesDealingDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3);

        addCard(Zone.BATTLEFIELD, playerA, cub);
        addCard(Zone.BATTLEFIELD, playerA, piker);
        addCard(Zone.BATTLEFIELD, playerB, ancient);

        addCard(Zone.HAND, playerA, takedown);
        addCard(Zone.HAND, playerA, strength);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, strength, piker, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, takedown);
        addTarget(playerA, cub + "^" + piker);
        addTarget(playerA, ancient);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerB, ancient, 2 + 2 + 2);
    }

    @Test
    public void cantDamageWithTwoNonEnchanted() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerA, cub);
        addCard(Zone.BATTLEFIELD, playerA, piker);
        addCard(Zone.BATTLEFIELD, playerB, ancient);

        addCard(Zone.HAND, playerA, takedown);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, takedown);
        addTarget(playerA, cub + "^" + piker);
        addTarget(playerA, ancient);

        setStopAt(1, PhaseStep.END_COMBAT);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("PlayerA - Targets list was setup by addTarget")) {
                Assert.fail("must throw error about target setup not right, but got:\n" + e.getMessage());
            }
        }
    }
}