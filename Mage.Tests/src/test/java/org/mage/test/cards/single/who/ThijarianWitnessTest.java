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
    private static final String tiny2 = "Memnite"; // 1/1
    private static final String big = "Alpine Grizzly"; // 4/2
    private static final String kill = "Infernal Grasp"; //Also can test with Lightning Bolt

    @Test
    public void test_AttackingAloneAfterKill() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerA, tiny);
        addCard(Zone.BATTLEFIELD, playerA, tiny2);
        addCard(Zone.BATTLEFIELD, playerB, "Badlands", 4);
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
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 4);
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
    public void test_DoubleBlocked() {
        //Auto-assign damage
        //setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerA, big);
        addCard(Zone.BATTLEFIELD, playerB, tiny);
        addCard(Zone.BATTLEFIELD, playerB, tiny2);

        attack(1, playerA, big);
        block(1, playerB, tiny, big);
        block(1, playerB, tiny2, big);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 1);
        assertPermanentCount(playerB, tiny, 0);
        assertPermanentCount(playerA, big, 0);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 0);
    }
    @Test
    public void test_DoubleBlocker() {
        //Auto-assign damage
        //setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerA, tiny);
        addCard(Zone.BATTLEFIELD, playerA, tiny2);
        addCard(Zone.BATTLEFIELD, playerB, "Night Market Guard");

        attack(1, playerA, tiny);
        attack(1, playerA, tiny2);
        block(1, playerB, "Night Market Guard", tiny);
        block(1, playerB, "Night Market Guard", tiny2);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 1);
        assertExileCount(playerA, 0);
        assertExileCount(playerB, 1);
    }
    @Test
    public void test_AttackAndBlock() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerA, tiny);
        addCard(Zone.BATTLEFIELD, playerB, tiny);

        attack(1, playerA, tiny);
        block(1, playerB, tiny, tiny);
        setChoice(playerA, ""); //stack triggers

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 2);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
    }
    @Test
    public void test_AttackMakesToken() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerA, "Skyknight Vanguard");
        addCard(Zone.HAND, playerB, kill);
        addCard(Zone.HAND, playerB, kill);
        addCard(Zone.BATTLEFIELD, playerB, "Badlands", 4);

        attack(1, playerA, "Skyknight Vanguard");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, kill, "Skyknight Vanguard");
        waitStackResolved(1, PhaseStep.DECLARE_ATTACKERS);
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, kill, "Soldier Token");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 2);
        assertExileCount(playerA, 1);
    }
    @Test
    public void test_multipleWitness() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, witness);
        addCard(Zone.BATTLEFIELD, playerB, witness);
        addCard(Zone.BATTLEFIELD, playerA, tiny);
        addCard(Zone.BATTLEFIELD, playerB, tiny);

        attack(1, playerA, tiny);
        block(1, playerB, tiny, tiny);
        setChoice(playerA, ""); //stack triggers
        setChoice(playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clue, 2);
        assertPermanentCount(playerB, clue, 2);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
    }
}
