package org.mage.test.cards.single.mid;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.v.VolatileArsonist Volatile Arsonist}
 * {3}{R}{R}
 * Creature - Human Werewolf 4/4
 * Menace, haste
 * Whenever Volatile Arsonist attacks, it deals 1 damage to each of up to one target creature,
 * up to one target player, and/or up to one target planeswalker.
 * Daybound
 */
public class VolatileArsonistTest extends CardTestPlayerBase {

    private static final String arsonist = "Volatile Arsonist";
    private static final String bears = "Grizzly Bears"; // 2/2
    private static final String jace = "Jace Beleren"; // loyalty 3

    /**
     * Each of the three targets is dealt 1 damage, not only the creature
     * (the effect's default first-target pointer reached only the first target).
     */
    @Test
    public void testDamagesEachTarget() {
        addCard(Zone.BATTLEFIELD, playerA, arsonist);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        addCard(Zone.BATTLEFIELD, playerB, jace);

        attack(1, playerA, arsonist, playerB);
        addTarget(playerA, bears);
        addTarget(playerA, playerB);
        addTarget(playerA, jace);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertDamageReceived(playerB, bears, 1);
        assertLife(playerB, 20 - 1);
        assertCounterCount(playerB, jace, CounterType.LOYALTY, 3 - 1);
    }

    /**
     * Only the player is targeted: the player still takes the damage.
     */
    @Test
    public void testOnlyPlayerTargeted() {
        addCard(Zone.BATTLEFIELD, playerA, arsonist);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        attack(1, playerA, arsonist, playerB);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, playerB);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertDamageReceived(playerB, bears, 0);
        assertLife(playerB, 20 - 1);
    }
}
