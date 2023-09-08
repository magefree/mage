package org.mage.test.cards.targets.attacking;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

public class ReselectDefenderTargetEffectTest extends CardTestCommander4Players {
    
    private static final String SIGNPOST = "Misleading Signpost";
    private static final String MAGE = "Portal Mage";
    private static final String LION = "Silvercoat Lion";
    private static final String PLANESWALKER = "Teferi, Master of Time";
    private static final String BATTLE = "Invasion of Segovia";

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

        // Player B responds by casting Signpost, redirecting the attack to Player C's planeswalker
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
}
