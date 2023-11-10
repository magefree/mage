package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class ThijarianWitnessTest extends CardTestPlayerBase {

    /**
     * Thijarian Witness {1}{G}
     * Creature - Alien Cleric
     * Flash
     * Bear Witness - Whenever another creature dies, if it was attacking or blocking alone, exile it and investigate.
     * 0/4
     */
    private static final String witness = "Thijarian Witness";
    private static final String clue = "Clue Token";
    private static final String tiny = "Raging Goblin"; // 1/1
    private static final String tiny2 = "Banehound"; // 1/1
    private static final String big = "Thundering Giant"; // 4/3
    private static final String kill = "Lightning Bolt";

    @Test
    public void test_AttackingAloneAfterKill() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerA, tiny);
        addCard(Zone.BATTLEFIELD, playerA, tiny2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, kill, 2);

        attack(1, playerA, tiny);
        attack(1, playerA, tiny2);
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, kill, tiny, true);
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, kill, tiny2, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 1);
        assertExileCount(playerA, 1);
    }
    @Test
    public void test_BlockingAloneAfterKill() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerA, big);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, kill);
        addCard(Zone.BATTLEFIELD, playerB, tiny);
        addCard(Zone.BATTLEFIELD, playerB, tiny2);

        attack(1, playerA, big);
        block(1, playerB, tiny, big);
        block(1, playerB, tiny2, big);
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, kill, tiny2);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 1);
        assertPermanentCount(playerB, tiny, 0);
        assertPermanentCount(playerA, big, 1);
        assertExileCount(playerB, 1);
    }
    @Test
    public void test_AttackAndBlock() {
        //auto-stack triggers
        //setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerA, tiny);
        addCard(Zone.BATTLEFIELD, playerB, tiny);

        attack(1, playerA, tiny);
        block(1, playerB, tiny, tiny);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 2);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
    }
    @Test
    public void test_multipleWitness() {
        //auto-stack triggers
        //setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerB, witness);
        addCard(Zone.BATTLEFIELD, playerA, tiny);
        addCard(Zone.BATTLEFIELD, playerB, tiny);

        attack(1, playerA, tiny);
        block(1, playerB, tiny, tiny);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 2);
        assertPermanentCount(playerB, clue, 2);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
    }
}
