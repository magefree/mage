package org.mage.test.cards.targets.attacking;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

public class ReselectDefenderAttackedByTargetEffectTest extends CardTestCommander4Players {
    
    private static final String SIGNPOST = "Misleading Signpost";
    private static final String LION = "Silvercoat Lion";
    private static final String PLANESWALKER = "Teferi, Master of Time";
    private static final String BATTLE = "Invasion of Segovia";
    private static final String ARCHON = "Blazing Archon";

    /**
     * When Misleading Signpost enters the battlefield during the declare attackers step, you may reselect which player or permanent target attacking creature is attacking.
     */
    @Test
    public void testSingleTarget() {
        addCard(Zone.BATTLEFIELD, playerA, LION);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, SIGNPOST);

        addCard(Zone.BATTLEFIELD, playerC, PLANESWALKER);

        // Player A declares an attack against Player B
        attack(1, playerA, LION, playerB);

        // Player B responds by casting Signpost, redirecting the attack to Player C's planeswalker
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, SIGNPOST);
        setChoice(playerB, true);
        addTarget(playerB, LION);
        addTarget(playerB, PLANESWALKER);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(PLANESWALKER, CounterType.LOYALTY, 1);
        assertLife(playerB, 20);
    }

    @Test
    public void testBattle() {
        addCard(Zone.BATTLEFIELD, playerA, LION);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, BATTLE);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, SIGNPOST);
        addCard(Zone.BATTLEFIELD, playerB, BATTLE);
        setChoice(playerB, "PlayerC");

        // Player A declares an attack against Player B
        attack(1, playerA, LION, playerB);

        // Player B responds by casting Signpost, redirecting the attack to the battle Player C is protecting
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, SIGNPOST);
        setChoice(playerB, true);
        addTarget(playerB, LION);
        addTarget(playerB, BATTLE);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(BATTLE, CounterType.DEFENSE, 2);
        assertLife(playerB, 20);
    }

    /**
     * Test of 508.7b
     */
    @Test
    public void testAvoidRestrictions() {
        addCard(Zone.BATTLEFIELD, playerA, LION);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, SIGNPOST);

        addCard(Zone.BATTLEFIELD, playerC, PLANESWALKER);
        addCard(Zone.BATTLEFIELD, playerC, ARCHON);

        // Player A attacks Player C's planeswalker, can't attack Player C because of Archon
        attack(1, playerA, LION, PLANESWALKER);

        // Player B should be able to redirect to Player C, ignoring Archon
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, SIGNPOST);
        setChoice(playerB, true);
        addTarget(playerB, LION);
        addTarget(playerB, playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Player C should be damaged, not planeswalker
        assertLife(playerC, 18);
        assertCounterCount(PLANESWALKER, CounterType.LOYALTY, 3);
    }
}
